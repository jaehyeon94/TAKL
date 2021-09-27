package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Dao.UserDao;
import Dto.User;
import aboutdb.MyConnectionProvider;

public class TestDB {
	public static void main(String[] args) {
		//readById(1);
		User user;
		UserDao userDao = new UserDao();
		user = userDao.userName("aaa");
		System.out.println(user.toString());
		
		
	}

	private static void readById(int i) {
		try (Connection conn = MyConnectionProvider.getConnection()) {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM user";

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String password = rs.getString("password");
				String picture = rs.getString("picture");
				String status_message = rs.getString("status_message");
				
				System.out.println(id + " : " + password + 
						", " + picture + ", " + status_message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}