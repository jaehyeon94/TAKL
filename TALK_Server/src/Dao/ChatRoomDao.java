package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dto.ChatRoom;
import Dto.User;
import dbconnChatDB.ConnChatDB;

public class ChatRoomDao {

	public ArrayList<Integer> getMyRoomNo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select A.room_id" + " from chat_room as A, chat_room_member as B"
				+ " where A.room_id = B.room_id and B.user_id = '" + id + "'";
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int room_id = rs.getInt("room_id");
				numbers.add(room_id);
			}
			return numbers;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeRS(rs);
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return numbers;
	}

	public ChatRoom getMyRoom(int myRoomNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int no = 0;
		String name = "";
		String query = "select A.room_id, B.user_id, A.room_name, C.Status_message,C.PICTURE" + " from chat_room as A"
				+ " inner join chat_room_member as B" + " on A.room_id = B.room_id"
				+ " LEFT OUTER JOIN user C ON C.ID = B.user_id" + " where A.room_id = '" + myRoomNumber + "'";
		ChatRoom chatRoom = new ChatRoom();
		ArrayList<User> userList = new ArrayList<User>();
		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int room_id = rs.getInt("room_id");
				String user_id = rs.getString("user_id");
				String room_name = rs.getString("room_name");
				String status_message = rs.getString("Status_message");
				String PICTURE = rs.getString("PICTURE");
				userList.add(new User(user_id, status_message, PICTURE));
				no = room_id;
				name = room_name;
			}
			chatRoom = new ChatRoom(no, name, userList, "");
			return chatRoom;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeRS(rs);
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return chatRoom;
	}

	public int chatRoomInsert(String room_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int LAST_INSERT_ID = -1;
		String query = "INSERT INTO chat_room (room_name)" + "VALUES (?)";
		String a = "SELECT LAST_INSERT_ID();";
		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, room_name);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(a);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				LAST_INSERT_ID = rs.getInt("LAST_INSERT_ID()");

				// return new ChatRoom(cUser_id, path);
			}

			return LAST_INSERT_ID;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return -1;
	}

	public void chatRoomMemberInsert(int LAST_INSERT_ID,String room_Member_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "insert into chat_room_member (room_id,user_id) VALUE(?,?)";

		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, LAST_INSERT_ID);
			pstmt.setString(2, room_Member_name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
	}

	public ChatRoom getChatRoomById(String user_id, String path) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM chat_room WHERE user_id = ?";

		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String cUser_id = rs.getString("user_id");

				// return new ChatRoom(cUser_id, path);
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

	public int updateChat(String path, int room_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String query = "UPDATE chat_room SET path = ?" + " WHERE room_id = ?";

		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, path);
			pstmt.setInt(2, room_id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return -1;
	}

	public int deleteChat(String path, int room_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String query = "DELETE FROM chat_room WHERE path = ?, room_id = ?";

		try {
			conn = ConnChatDB.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, path);
			pstmt.setInt(2, room_id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnChatDB.closeStmt(pstmt);
			ConnChatDB.closeConnection(conn);
		}
		return -1;
	}
}