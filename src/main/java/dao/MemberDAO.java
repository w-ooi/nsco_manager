package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import beans.Creca;
import beans.Member;

public class MemberDAO {
	private Connection con;

	public MemberDAO(Connection con) {
		this.con = con;
	}

	public Member getMember(String memberNo) throws SQLException {
		Member member = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM member WHERE member_no=?");
			st.setString(1, memberNo);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			CrecaDAO crecaDao = new CrecaDAO(con);

			// 結果を参照
			if (rs.next()) {
				String nameSei = rs.getString("name_sei");
				String nameMei = rs.getString("name_mei");
				String kanaSei = rs.getString("kana_sei");
				String kanaMei = rs.getString("kana_mei");
				String email = rs.getString("email");
				String nickname = rs.getString("nickname");
				String password = rs.getString("password");
				int crecaCompId = rs.getInt("creca_comp_id");
				Creca creca = crecaDao.getCreca(crecaCompId);
				String crecaNo = rs.getString("creca_no");
				String crecaExpiration = rs.getString("creca_expiration");

				member = new Member(memberNo,nameSei,nameMei,kanaSei,kanaMei,email,nickname,password,creca,crecaNo,crecaExpiration);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// 会員を返却
		return member;
	}

	public Member getMemberById(String memberNo, String password) throws SQLException {
		Member member = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM member WHERE member_no=? and password=?");
			st.setString(1, memberNo);
			st.setString(2, password);
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			CrecaDAO crecaDao = new CrecaDAO(con);

			// 結果を参照
			if (rs.next()) {
				String nameSei = rs.getString("name_sei");
				String nameMei = rs.getString("name_mei");
				String kanaSei = rs.getString("kana_sei");
				String kanaMei = rs.getString("kana_mei");
				String email = rs.getString("email");
				String nickname = rs.getString("nickname");
				int crecaCompId = rs.getInt("creca_comp_id");
				Creca creca = crecaDao.getCreca(crecaCompId);
				String crecaNo = rs.getString("creca_no");
				String crecaExpiration = rs.getString("creca_expiration");

				member = new Member(memberNo,nameSei,nameMei,kanaSei,kanaMei,email,nickname,password,creca,crecaNo,crecaExpiration);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// 会員を返却
		return member;
	}
	
	//ニックネームの重複チェック
	public boolean checkDuplicateNickname(String nickname) throws SQLException {
		boolean result = true;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM member WHERE nickname=?");
			st.setString(1, nickname);
			
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

	//メールアドレスの重複チェック
	public boolean checkDuplicateEmail(String email) throws SQLException {
		boolean result = true;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM member WHERE email=?");
			st.setString(1, email);
			
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
	
	//新規会員番号取得
	private String getNewMemberNo() throws SQLException{
		String memberNo = "1000000000";
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT member_no FROM member ORDER BY member_no DESC");
			
			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			if (rs.next()) {
				int randInt = new Random().nextInt(6) + 5;	// 5~10の乱数取得
				long longMemberNo = Integer.parseInt(rs.getString("member_no")) + randInt;
				memberNo = new Long(longMemberNo).toString();
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}
		
		return memberNo;
	}
	
	//新規メンバー登録
	public int insertMember(Member member) throws SQLException{
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			st.setString(1, getNewMemberNo());
			st.setString(2, member.getNameSei());
			st.setString(3, member.getNameMei());
			st.setString(4, member.getKanaSei());
			st.setString(5, member.getKanaMei());
			st.setString(6, member.getEmail());
			st.setString(7, member.getNickname());
			st.setString(8, member.getPassword());
			st.setInt(9, member.getCreca().getCrecaCompId());
			st.setString(10, member.getCrecaNo());
			st.setString(11, member.getCreacaExpiration());
			
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
	
	//パスワード変更
	public int updatePassword(String memberNo, String newPassword) throws SQLException {
		int intResult = 0;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("UPDATE member SET password=? WHERE member_no=?");
			st.setString(1, newPassword);
			st.setString(2, memberNo);
			
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
}
