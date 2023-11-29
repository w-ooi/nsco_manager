package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Facility;
import beans.Instructor;

public class InstructorDAO {
	private Connection con;

	public InstructorDAO(Connection con) {
		this.con = con;
	}

	public Instructor getInstructor(int instructorCode) throws SQLException {
		Instructor instructor = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM instructor WHERE instructor_code=?");
			st.setInt(1, instructorCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			FacilityDAO facilityDao = new FacilityDAO(con);

			// 結果を参照
			if (rs.next()) {
				String instructorName = rs.getString("instructor_name");
				int facilityCode = rs.getInt("facility_code");
				Facility facility = facilityDao.getFacility(facilityCode);
				String imageFile = rs.getString("image_file");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");

				instructor = new Instructor(instructorCode,instructorName,facility,imageFile,loginId,password);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// インストラクターを返却
		return instructor;
	}

	public ArrayList<Instructor> getAllInstructors() throws SQLException {
		ArrayList<Instructor> list = new ArrayList<Instructor>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM instructor");

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			FacilityDAO facilityDao = new FacilityDAO(con);

			// 結果を参照
			while (rs.next()) {
				int instructorCode = rs.getInt("instructor_code");
				String instructorName = rs.getString("instructor_name");
				int facilityCode = rs.getInt("facility_code");
				
				if(facilityCode > 0) {
					Facility facility = facilityDao.getFacility(facilityCode);
					String imageFile = rs.getString("image_file");
					String loginId = rs.getString("login_id");
					String password = rs.getString("password");
					
					Instructor instructor = new Instructor(instructorCode,instructorName,facility,imageFile,loginId,password);
	
					list.add(instructor);
				}
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

	public Instructor getInstructorByLoginId(String loginId, String password) throws SQLException {
		Instructor instructor = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM instructor WHERE login_id=? and password=?");
			st.setString(1, loginId);
			st.setString(2, password);
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			FacilityDAO facilityDao = new FacilityDAO(con);

			// 結果を参照
			if (rs.next()) {
				int instructorCode = rs.getInt("instructor_code");
				String instructorName = rs.getString("instructor_name");
				int facilityCode = rs.getInt("facility_code");
				Facility facility = facilityDao.getFacility(facilityCode);
				String imageFile = rs.getString("image_file");

				instructor = new Instructor(instructorCode,instructorName,facility,imageFile,loginId,password);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// 会員を返却
		return instructor;	
	}
}
