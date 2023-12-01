package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import beans.Member;
import beans.Reserve;
import beans.Schedule;

public class ReserveDAO {
	private Connection con;

	public ReserveDAO(Connection con) {
		this.con = con;
	}

	public Reserve getReserve(int reserveCode) throws SQLException {
		Reserve reserve = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM reserve WHERE reserve_code=?");
			st.setInt(1, reserveCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			MemberDAO memberDao = new MemberDAO(con);
			ScheduleDAO scheduleDao = new ScheduleDAO(con);

			// 結果を参照
			if (rs.next()) {
				String memberNo = rs.getString("member_no");
				Member member = memberDao.getMember(memberNo);
				int scheduleCode = rs.getInt("schedule_code");
				Schedule schedule = scheduleDao.getSchedule(scheduleCode);
				int attendanceFlag = rs.getInt("attendance_flag");
				int cancelFlag = rs.getInt("cancel_flag");
				int lessonEvaluation = rs.getInt("lesson_evaluation");
				int instructorEvaluation = rs.getInt("instructor_evaluation");

				reserve = new Reserve(reserveCode, member, schedule, attendanceFlag, cancelFlag, lessonEvaluation,
						instructorEvaluation);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// 予約を返却
		return reserve;
	}

	//予約する
	public int setReserve(String memberNo, int scheduleCode) throws SQLException {
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("INSERT INTO reserve VALUES(null, ?, ?, 0, 0, 0, 0)");
			st.setString(1, memberNo);
			st.setInt(2, scheduleCode);

			// SQL文を発行
			intResult = st.executeUpdate();
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		return intResult;
	}

	//レッスン受講前の予約リスト
	public ArrayList<Reserve> getBeforeTakeLessonReserves(String memberNo) throws SQLException {
		ArrayList<Reserve> list = new ArrayList<Reserve>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM reserve WHERE member_no=? AND attendance_flag=0 AND cancel_flag=0");
			st.setString(1, memberNo);
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			MemberDAO memberDao = new MemberDAO(con);
			ScheduleDAO scheduleDao = new ScheduleDAO(con);

			// 結果を参照
			while (rs.next()) {
				int reserveCode = rs.getInt("reserve_code");
				Member member = memberDao.getMember(memberNo);
				int scheduleCode = rs.getInt("schedule_code");
				Schedule schedule = scheduleDao.getSchedule(scheduleCode);
				int attendanceFlag = rs.getInt("attendance_flag");
				int cancelFlag = rs.getInt("cancel_flag");
				int lessonEvaluation = rs.getInt("lesson_evaluation");
				int instructorEvaluation = rs.getInt("instructor_evaluation");

				Reserve reserve = new Reserve(reserveCode, member, schedule, attendanceFlag, cancelFlag,
						lessonEvaluation, instructorEvaluation);

				list.add(reserve);
			}

			//開催日の昇順でソート
			Collections.sort(list, new Comparator<Reserve>() {
				@Override
				public int compare(Reserve reserveFirst, Reserve reserveSecond) {

					return reserveFirst.getSchedule().getEventDate().compareTo(reserveSecond.getSchedule().getEventDate());
				}
			});
		
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// リストを返却
		return list;
	}

	//レッスン受講後の予約リスト(最大30件)
	public ArrayList<Reserve> getAfterTakeLessonReserves(String memberNo) throws SQLException {
		ArrayList<Reserve> list = new ArrayList<Reserve>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM reserve WHERE member_no=? AND attendance_flag=1 ORDER BY reserve_code DESC LIMIT 30");
			st.setString(1, memberNo);
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			MemberDAO memberDao = new MemberDAO(con);
			ScheduleDAO scheduleDao = new ScheduleDAO(con);
			
			// 結果を参照
			while (rs.next()) {
				int reserveCode = rs.getInt("reserve_code");
				Member member = memberDao.getMember(memberNo);
				int scheduleCode = rs.getInt("schedule_code");
				Schedule schedule = scheduleDao.getSchedule(scheduleCode);
				int attendanceFlag = rs.getInt("attendance_flag");
				int cancelFlag = rs.getInt("cancel_flag");
				int lessonEvaluation = rs.getInt("lesson_evaluation");
				int instructorEvaluation = rs.getInt("instructor_evaluation");

				Reserve reserve = new Reserve(reserveCode, member, schedule, attendanceFlag, cancelFlag,
						lessonEvaluation, instructorEvaluation);

				list.add(reserve);
				
				//開催日の降順でソート
				Collections.sort(list, new Comparator<Reserve>() {
					@Override
					public int compare(Reserve reserveFirst, Reserve reserveSecond) {

						return reserveSecond.getSchedule().getEventDate().compareTo(reserveFirst.getSchedule().getEventDate());
					}
				});
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// リストを返却
		return list;
	}

	//予約のキャンセル
	public int cancelReserve(int reserveCode) throws SQLException {
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("UPDATE reserve SET cancel_flag=1 WHERE reserve_code=?");
			st.setInt(1, reserveCode);

			// SQL文を発行
			intResult = st.executeUpdate();
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		return intResult;
	}

	// アンケート入力
	public int updateEvaluation(int reserveCode, int lessonEvaluation, int instructorEvaluation) throws SQLException {
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("UPDATE reserve SET lesson_evaluation=?,instructor_evaluation=? WHERE reserve_code=?");
			st.setInt(1, lessonEvaluation);
			st.setInt(2, instructorEvaluation);
			st.setInt(3, reserveCode);

			// SQL文を発行
			intResult = st.executeUpdate();
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		return intResult;
	}

	public List<Reserve> getReserveBySchedule(String code) throws SQLException {
		ArrayList<Reserve> list = new ArrayList<Reserve>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT r.reserve_code,r.member_no,r.schedule_code,r.attendance_flag,r.cancel_flag,r.lesson_evaluation,r.instructor_evaluation "
					+ "FROM reserve r "
					+ "INNER JOIN schedule s ON r.schedule_code = s.schedule_code "
					+ "WHERE r.cancel_flag=0 AND s.cancel_flag=0 AND r.schedule_code=?");
			st.setInt(1, Integer.parseInt(code));
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			MemberDAO memberDao = new MemberDAO(con);
			ScheduleDAO scheduleDao = new ScheduleDAO(con);
			
			// 結果を参照
			while (rs.next()) {
				int reserveCode = rs.getInt("reserve_code");
				String memberNo = rs.getString("member_no");
				Member member = memberDao.getMember(memberNo);
				int scheduleCode = rs.getInt("schedule_code");
				Schedule schedule = scheduleDao.getSchedule(scheduleCode);
				int attendanceFlag = rs.getInt("attendance_flag");
				int cancelFlag = rs.getInt("cancel_flag");
				int lessonEvaluation = rs.getInt("lesson_evaluation");
				int instructorEvaluation = rs.getInt("instructor_evaluation");

				Reserve reserve = new Reserve(reserveCode, member, schedule, attendanceFlag, cancelFlag,
						lessonEvaluation, instructorEvaluation);

				list.add(reserve);
				
				//開催日の降順でソート
				Collections.sort(list, new Comparator<Reserve>() {
					@Override
					public int compare(Reserve reserveFirst, Reserve reserveSecond) {

						return reserveSecond.getSchedule().getEventDate().compareTo(reserveFirst.getSchedule().getEventDate());
					}
				});
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// リストを返却
		return list;
	}

	public int updateAttendance(List<Reserve> reserveList) throws SQLException {
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("UPDATE reserve SET attendance_flag=? WHERE reserve_code=?");
			
			for(Reserve reserve:reserveList) {
				st.setInt(1, reserve.getAttendanceFlag());
				st.setInt(2, reserve.getReserveCode());

				// SQL文を発行
				int tmpResult = st.executeUpdate();
				if(tmpResult == 1) {
					intResult++;
				}
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}
		
		return intResult;
	}
}
