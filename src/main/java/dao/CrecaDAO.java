package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Creca;

public class CrecaDAO {
	private Connection con;

	public CrecaDAO(Connection con) {
		this.con = con;
	}

	public Creca getCreca(int crecaCompId) throws SQLException {
		Creca creca = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM creca WHERE creca_comp_id=?");
			st.setInt(1, crecaCompId);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			if (rs.next()) {
				String name = rs.getString("creca_comp_name");

				creca = new Creca(crecaCompId, name);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// クレジットカードを返却
		return creca;
	}

	public ArrayList<Creca> getAllCrecas() throws SQLException {
		ArrayList<Creca> list = new ArrayList<Creca>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM creca");

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			while (rs.next()) {
				int code = rs.getInt("creca_comp_id");
				String name = rs.getString("creca_comp_name");

				Creca creca = new Creca(code, name);
				list.add(creca);
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
