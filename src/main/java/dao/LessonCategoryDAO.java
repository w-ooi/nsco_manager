package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.LessonCategory;

public class LessonCategoryDAO {
	private Connection con;

	public LessonCategoryDAO(Connection con) {
		this.con = con;
	}

	public LessonCategory getLessonCategory(int lessonCategoryCode) throws SQLException {
		LessonCategory category = null;
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM lesson_category WHERE lesson_category_code=?");
			st.setInt(1, lessonCategoryCode);

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			if (rs.next()) {
				String name = rs.getString("lesson_category_name");

				category = new LessonCategory(lessonCategoryCode, name);
			}
		} finally {
			// リソースの解放
			if (st != null) {
				st.close();
			}
		}

		// カテゴリを返却
		return category;
	}

	public ArrayList<LessonCategory> getAllLessonCategories() throws SQLException {
		ArrayList<LessonCategory> list = new ArrayList<LessonCategory>();
		PreparedStatement st = null;

		try {
			// PreparedStatementの取得
			st = con.prepareStatement("SELECT * FROM lesson_category");

			// SQL文を発行
			ResultSet rs = st.executeQuery();

			// 結果を参照
			while (rs.next()) {
				int code = rs.getInt("lesson_category_code");
				String name = rs.getString("lesson_category_name");

				LessonCategory category = new LessonCategory(code, name);
				list.add(category);
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
