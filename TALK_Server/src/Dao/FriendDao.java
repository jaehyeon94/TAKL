package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dto.Friend;
import Dto.User;
import aboutdb.MyConnectionProvider;
import dbconnChatDB.ConnChatDB;

public class FriendDao {
	public void FriendInsert(String myUserId, String otherUserId) {
		// 친구추가
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO friend_list (myUser_id, otherUser_id)" + "VALUE (?, ?)";

		try {
			conn = MyConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, myUserId);
			pstmt.setString(2, otherUserId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
	}

	public void FriendDelete(String myId, String otherUserId) {
		// 친구 삭제
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "DELETE FROM friend_list WHERE MyUser_id = ? AND otherUser_id = ?";

		try {
			conn = MyConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, myId);
			pstmt.setString(2, otherUserId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
	}

	public ArrayList<User> friendList(String id) {
		// 친구목록
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "SELECT ID, Status_message,PICTURE FROM user" + " WHERE ID IN (SELECT otherUser_id"
				+ " FROM friend_list" + " WHERE otherUser_id = ID AND myUser_id = '" + id + "');";

		ArrayList<User> list = new ArrayList<>();

		try {
			conn = MyConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String Id = rs.getString("ID");
				String Picture = rs.getString("PICTURE");
				String statusMessage = rs.getString("Status_message");

				list.add(new User(Id, statusMessage, Picture));

			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeRS(rs);
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return null;

	}

	public Friend FriendCk(String id, String friendId) {
		// 친구 프로필 보기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "SELECT MyUser_id, otherUser_id FROM friend_list WHERE MyUser_id = ? and otherUser_id = ?";

		try {
			conn = MyConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, friendId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String usid = rs.getString("MyUser_id");
				String friendid = rs.getString("otherUser_id");

				return new Friend(usid,friendid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeRS(rs);
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return null;
	}
}