package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.TimeFrame;

public class TimeFrameDAO {
	private Connection con;

	public TimeFrameDAO(Connection con) {
		this.con = con;
	}

	public TimeFrame getTimeFrame(int timeFrameCode) throws SQLException {
		TimeFrame timeFrame = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM time_frame WHERE time_frame_code=?");
			st.setInt(1, timeFrameCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			if (rs.next()) {
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");

				timeFrame = new TimeFrame(timeFrameCode, startTime, endTime);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// 時間枠を返却
		return timeFrame;
	}

	public ArrayList<TimeFrame> getAllTimeFrames() throws SQLException {
		ArrayList<TimeFrame> list = new ArrayList<TimeFrame>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM time_frame");

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			while (rs.next()) {
				int code = rs.getInt("time_frame_code");
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");

				TimeFrame timeFrame = new TimeFrame(code, startTime, endTime);
				list.add(timeFrame);
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
