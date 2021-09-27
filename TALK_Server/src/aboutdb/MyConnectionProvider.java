package aboutdb;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnectionProvider {
	private final static Properties props = new Properties();
	static { 
		try(FileInputStream input 
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
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}