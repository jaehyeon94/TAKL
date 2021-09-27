package dbconnChatDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnChatDB {
private final static Properties props = new Properties();
	
	static {
		try (FileInputStream input 
			= new FileInputStream("chat_db.properties")) {
			props.load(input);
			Class.forName(props.getProperty("DRIVER"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}			
	}
	
	public static Connection getConnection() {
		String url = props.getProperty("URL");
		String id = props.getProperty("ID");
		String password = props.getProperty("PASSWORD");

		Connection con = null;
		try {
			con = DriverManager.getConnection(url,
					id, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeRS(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStmt(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}