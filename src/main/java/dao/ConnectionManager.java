package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	public static Connection getConnection() {
		Connection con = null;

		try {
            Class.forName("com.mysql.jdbc.Driver");

            // データベースへ接続
            con = DriverManager.getConnection(
            		"jdbc:mysql://localhost/nscdb", "duke", "system5");
		}catch(ClassNotFoundException e) {
			System.err.println("JDBCドライバのロードに失敗");
		}catch(SQLException e) {
			System.err.println("データベースの接続にに失敗");
		}

		return con;
	}
}
