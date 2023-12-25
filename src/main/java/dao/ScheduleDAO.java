package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import beans.Instructor;
import beans.Lesson;
import beans.Member;
import beans.Schedule;
import beans.TimeFrame;

public class ScheduleDAO {
	private Connection con;

	public ScheduleDAO(Connection con) {
		this.con = con;
	}

	public Schedule getSchedule(int scheduleCode) throws SQLException {
		Schedule schedule = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM schedule WHERE schedule_code=?");
			st.setInt(1, scheduleCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			LessonDAO lessonDao = new LessonDAO(con);
			TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
			InstructorDAO instructorDao = new InstructorDAO(con);

			// 結果を参照
			if (rs.next()) {
				int lessonCode = rs.getInt("lesson_code");
				Lesson lesson = lessonDao.getLesson(lessonCode);
				String eventDate = rs.getDate("event_date").toString();
				int timeFrameCode = rs.getInt("time_frame_code");
				TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
				int instructorCode = rs.getInt("instructor_code");
				Instructor instructor = instructorDao.getInstructor(instructorCode);
				String streamingId = rs.getString("streaming_id");
				String streamingPass = rs.getString("streaming_pass");
				int cancelFlag = rs.getInt("cancel_flag");

				schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// スケジュールを返却
		return schedule;
	}
	
	// 利用者側のスケジュール検索用日付取得
	private Calendar[] getCompDate(){
    	//現在日時を取得
		Calendar clNow = Calendar.getInstance();

		int nowMonth = clNow.get(Calendar.MONTH);	//現在月
		int nowDate = clNow.get(Calendar.DATE);	//現在日
		int nowHour = clNow.get(Calendar.HOUR_OF_DAY);	//現在時間

		//現在日時の時分秒ミリ秒を0にする
		clNow.set(Calendar.HOUR, 0);
		clNow.set(Calendar.MINUTE, 0);
		clNow.set(Calendar.SECOND, 0);
		clNow.set(Calendar.MILLISECOND, 0);

		// 比較用のオブジェクトに現在日時をコピー
		Calendar clCompStart = (Calendar)clNow.clone();
		Calendar clCompEnd = (Calendar)clNow.clone();
		
		//18時以前なら翌日分より、後なら翌々日分より表示
		if(nowHour < 18) {	
			clCompStart.set(Calendar.DATE, nowDate+1);
		}else {
			clCompStart.set(Calendar.DATE, nowDate+2);
		}

		//15日までなら当月分を、16日以降なら当月分と翌月分を表示
		if(nowDate <= 15) {
			clCompEnd.set(Calendar.MONTH, nowMonth+1);
			clCompEnd.set(Calendar.DATE, 1);
		}else {
			clCompEnd.set(Calendar.MONTH, nowMonth+2);
			clCompEnd.set(Calendar.DATE, 1);
		}

		// 検索対象の開始日付と終了日付を返す
		Calendar[] compDate = {clCompStart, clCompEnd};
		return compDate;
	}

	public List<Schedule> getScheduleByLessonCategory(String code) throws SQLException {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;

		Calendar[] compDate = getCompDate();	// 検索対象の開始日付と終了日付

		if(!code.equals("all")) {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT schedule_code,s.lesson_code,event_date,time_frame_code,instructor_code,streaming_id,streaming_pass,cancel_flag,lesson_category_code FROM schedule s INNER JOIN lesson l ON s.lesson_code=l.lesson_code WHERE l.lesson_category_code=? AND event_date>=? AND event_date<? ORDER BY event_date,time_frame_code");
			st.setString(1, code);
			st.setDate(2, new java.sql.Date(compDate[0].getTime().getTime()));
			st.setDate(3, new java.sql.Date(compDate[1].getTime().getTime()));
		}else {
			st = con.prepareStatement("SELECT * FROM schedule WHERE event_date>=? AND event_date<? ORDER BY event_date,time_frame_code");
			st.setDate(1, new java.sql.Date(compDate[0].getTime().getTime()));
			st.setDate(2, new java.sql.Date(compDate[1].getTime().getTime()));
		}
		
		// SQL文を発行
		ResultSet rs = st.executeQuery();

		LessonDAO lessonDao = new LessonDAO(con);
		TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
		InstructorDAO instructorDao = new InstructorDAO(con);

		// 結果を参照
		while (rs.next()) {
			int scheduleCode = rs.getInt("schedule_code");
			int lessonCode = rs.getInt("lesson_code");
			Lesson lesson = lessonDao.getLesson(lessonCode);
			String eventDate = rs.getDate("event_date").toString();
			int timeFrameCode = rs.getInt("time_frame_code");
			TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
			int instructorCode = rs.getInt("instructor_code");
			Instructor instructor = instructorDao.getInstructor(instructorCode);
			String streamingId = rs.getString("streaming_id");
			String streamingPass = rs.getString("streaming_pass");
			int cancelFlag = rs.getInt("cancel_flag");

			Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

			list.add(schedule);
		}

		// リストを返却
		return list;
	}
	
	public List<Schedule> getScheduleByTimeFrame(String strDate, String code) throws SQLException {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;
		
		Calendar[] compDate = getCompDate();	// 検索対象の開始日付と終了日付

		try {
			if(!code.equals("all")) {
				int iCode = Integer.parseInt(code);

				// PreparedStatementの取得
				st = con.prepareStatement("SELECT * FROM schedule WHERE event_date=? AND event_date>=? AND event_date<? AND time_frame_code=? ORDER BY event_date,time_frame_code");
				
				st.setDate(1, java.sql.Date.valueOf(strDate));
				st.setDate(2, new java.sql.Date(compDate[0].getTime().getTime()));
				st.setDate(3, new java.sql.Date(compDate[1].getTime().getTime()));
				st.setInt(4, iCode);
			}else {
				// PreparedStatementの取得
				st = con.prepareStatement("SELECT * FROM schedule WHERE event_date=? AND event_date>=? AND event_date<? ORDER BY event_date,time_frame_code");
				
				st.setDate(1, java.sql.Date.valueOf(strDate));
				st.setDate(2, new java.sql.Date(compDate[0].getTime().getTime()));
				st.setDate(3, new java.sql.Date(compDate[1].getTime().getTime()));
			}

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			LessonDAO lessonDao = new LessonDAO(con);
			TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
			InstructorDAO instructorDao = new InstructorDAO(con);

			// 結果を参照
			while (rs.next()) {
				int scheduleCode = rs.getInt("schedule_code");
				int lessonCode = rs.getInt("lesson_code");
				Lesson lesson = lessonDao.getLesson(lessonCode);
				String eventDate = rs.getDate("event_date").toString();
				int timeFrameCode = rs.getInt("time_frame_code");
				TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
				int instructorCode = rs.getInt("instructor_code");
				Instructor instructor = instructorDao.getInstructor(instructorCode);
				String streamingId = rs.getString("streaming_id");
				String streamingPass = rs.getString("streaming_pass");
				int cancelFlag = rs.getInt("cancel_flag");

				Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

				list.add(schedule);
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

	public List<Schedule> getScheduleByInstructor(String code) throws SQLException {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;
		
		Calendar[] compDate = getCompDate();	// 検索対象の開始日付と終了日付

		try {
			if(!code.equals("all")) {
				int iCode = Integer.parseInt(code);

				// PreparedStatementの取得
				st = con.prepareStatement("SELECT * FROM schedule WHERE instructor_code=? AND event_date>=? AND event_date<? ORDER BY event_date,time_frame_code");
				st.setInt(1, iCode);
				st.setDate(2, new java.sql.Date(compDate[0].getTime().getTime()));
				st.setDate(3, new java.sql.Date(compDate[1].getTime().getTime()));
			}else {
				st = con.prepareStatement("SELECT * FROM schedule WHERE event_date>=? AND event_date<? ORDER BY event_date,time_frame_code");
				st.setDate(1, new java.sql.Date(compDate[0].getTime().getTime()));
				st.setDate(2, new java.sql.Date(compDate[1].getTime().getTime()));
			}

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			LessonDAO lessonDao = new LessonDAO(con);
			TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
			InstructorDAO instructorDao = new InstructorDAO(con);

			// 結果を参照
			while (rs.next()) {
				int scheduleCode = rs.getInt("schedule_code");
				int lessonCode = rs.getInt("lesson_code");
				Lesson lesson = lessonDao.getLesson(lessonCode);
				String eventDate = rs.getDate("event_date").toString();
				int timeFrameCode = rs.getInt("time_frame_code");
				TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
				int instructorCode = rs.getInt("instructor_code");
				Instructor instructor = instructorDao.getInstructor(instructorCode);
				String streamingId = rs.getString("streaming_id");
				String streamingPass = rs.getString("streaming_pass");
				int cancelFlag = rs.getInt("cancel_flag");

				Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

				list.add(schedule);
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

	public List<Schedule> getScheduleByLessonCategoryForManager(String code) throws SQLException {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;

		// PreparedStatementの取得
		st = con.prepareStatement("SELECT schedule_code,s.lesson_code,event_date,time_frame_code,instructor_code,streaming_id,streaming_pass,cancel_flag,lesson_category_code FROM schedule s INNER JOIN lesson l ON s.lesson_code=l.lesson_code WHERE l.lesson_category_code=? ORDER BY event_date,time_frame_code");
		st.setString(1, code);
		
		// SQL文を発行
		ResultSet rs = st.executeQuery();

		LessonDAO lessonDao = new LessonDAO(con);
		TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
		InstructorDAO instructorDao = new InstructorDAO(con);

		// 結果を参照
		while (rs.next()) {
			int scheduleCode = rs.getInt("schedule_code");
			int lessonCode = rs.getInt("lesson_code");
			Lesson lesson = lessonDao.getLesson(lessonCode);
			String eventDate = rs.getDate("event_date").toString();
			int timeFrameCode = rs.getInt("time_frame_code");
			TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
			int instructorCode = rs.getInt("instructor_code");
			Instructor instructor = instructorDao.getInstructor(instructorCode);
			String streamingId = rs.getString("streaming_id");
			String streamingPass = rs.getString("streaming_pass");
			int cancelFlag = rs.getInt("cancel_flag");

			Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

			list.add(schedule);
		}

		// リストを返却
		return list;
	}

	public List<Schedule> getScheduleByTimeFrameForManager(String strDate, String code) throws SQLException {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;

		try {
			int iCode = Integer.parseInt(code);

			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM schedule WHERE event_date=? AND time_frame_code=? ORDER BY event_date,time_frame_code");
			
			st.setDate(1, java.sql.Date.valueOf(strDate));
			st.setInt(2, iCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			LessonDAO lessonDao = new LessonDAO(con);
			TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
			InstructorDAO instructorDao = new InstructorDAO(con);

			// 結果を参照
			while (rs.next()) {
				int scheduleCode = rs.getInt("schedule_code");
				int lessonCode = rs.getInt("lesson_code");
				Lesson lesson = lessonDao.getLesson(lessonCode);
				String eventDate = rs.getDate("event_date").toString();
				int timeFrameCode = rs.getInt("time_frame_code");
				TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
				int instructorCode = rs.getInt("instructor_code");
				Instructor instructor = instructorDao.getInstructor(instructorCode);
				String streamingId = rs.getString("streaming_id");
				String streamingPass = rs.getString("streaming_pass");
				int cancelFlag = rs.getInt("cancel_flag");

				Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

				list.add(schedule);
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

	public List<Schedule> getScheduleByInstructorForManager(String code) throws SQLException {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;

		try {
			int iCode = Integer.parseInt(code);

			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM schedule WHERE instructor_code=? ORDER BY event_date,time_frame_code");
			st.setInt(1, iCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			LessonDAO lessonDao = new LessonDAO(con);
			TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
			InstructorDAO instructorDao = new InstructorDAO(con);

			// 結果を参照
			while (rs.next()) {
				int scheduleCode = rs.getInt("schedule_code");
				int lessonCode = rs.getInt("lesson_code");
				Lesson lesson = lessonDao.getLesson(lessonCode);
				String eventDate = rs.getDate("event_date").toString();
				int timeFrameCode = rs.getInt("time_frame_code");
				TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
				int instructorCode = rs.getInt("instructor_code");
				Instructor instructor = instructorDao.getInstructor(instructorCode);
				String streamingId = rs.getString("streaming_id");
				String streamingPass = rs.getString("streaming_pass");
				int cancelFlag = rs.getInt("cancel_flag");

				Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

				list.add(schedule);
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
	
	public boolean checkDuplicateInstructor(String eventDate, int timeFrameCode, int instructorCode) throws SQLException {
		boolean result = true;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM schedule WHERE event_date=? AND time_frame_code=? AND instructor_code=?");
			st.setDate(1, java.sql.Date.valueOf(eventDate));
			st.setInt(2, timeFrameCode);
			st.setInt(3, instructorCode);
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			if (rs.next()) {
				result = false;
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}
		
		return result;
	}

	public int insertSchedule(Schedule schedule) throws SQLException {
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("INSERT INTO schedule(lesson_code,event_date,time_frame_code,instructor_code,streaming_id,streaming_pass,cancel_flag) VALUES(?,?,?,?,?,?,?)");
			
			st.setInt(1, schedule.getLesson().getLessonCode());
			st.setDate(2, java.sql.Date.valueOf(schedule.getEventDate()));
			st.setInt(3, schedule.getTimeFrame().getTimeFrameCode());
			st.setInt(4, schedule.getInstructor().getInstructorCode());
			st.setString(5, schedule.getStreamingId());
			st.setString(6, schedule.getStreamingPass());
			st.setInt(7, 0);
			
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

	public List<Member> getOutputCsvMember(String scheduleCode) throws SQLException {
		List<Member> memberList = new ArrayList<Member>();
		PreparedStatement st = null;
		PreparedStatement subSt = null;

		try {
			st = con.prepareStatement("SELECT member_no FROM reserve WHERE schedule_code=? AND cancel_flag=0 ORDER BY member_no");
			st.setInt(1, Integer.parseInt(scheduleCode));

			MemberDAO memberDao = new MemberDAO(con);

			// SQL文を発行
			ResultSet rs = st.executeQuery();
			
			// 結果を参照
			while (rs.next()) {
				String memberNo = rs.getString("member_no");
				Member member = memberDao.getMember(memberNo);

				memberList.add(member);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// リストを返却
		return memberList;
	}

	public List<Schedule> getScheduleByToday() throws SQLException {
		List<Schedule> list = new ArrayList<Schedule>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM schedule WHERE event_date=? ORDER BY time_frame_code");
			
			// 現在日の取得
			Calendar calendar = Calendar.getInstance();
	        java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			st.setDate(1, date);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			LessonDAO lessonDao = new LessonDAO(con);
			TimeFrameDAO timeFrameDao = new TimeFrameDAO(con);
			InstructorDAO instructorDao = new InstructorDAO(con);

			// 結果を参照
			while (rs.next()) {
				int scheduleCode = rs.getInt("schedule_code");
				int lessonCode = rs.getInt("lesson_code");
				Lesson lesson = lessonDao.getLesson(lessonCode);
				String eventDate = rs.getDate("event_date").toString();
				int timeFrameCode = rs.getInt("time_frame_code");
				TimeFrame timeFrame = timeFrameDao.getTimeFrame(timeFrameCode);
				int instructorCode = rs.getInt("instructor_code");
				Instructor instructor = instructorDao.getInstructor(instructorCode);
				String streamingId = rs.getString("streaming_id");
				String streamingPass = rs.getString("streaming_pass");
				int cancelFlag = rs.getInt("cancel_flag");

				Schedule schedule = new Schedule(scheduleCode,lesson,eventDate,timeFrame,instructor,streamingId,streamingPass,cancelFlag);

				list.add(schedule);
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
}
