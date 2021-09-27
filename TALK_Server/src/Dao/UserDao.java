package Dao;


import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dto.User;
import dbconnChatDB.ConnChatDB;

public class UserDao {
    public void userInsert(String id, String password, String picture) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "INSERT INTO user (ID, PASSWORD, PICTURE)"
                + "VALUE (?, ?, ?)";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.setString(3, picture);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
    }

    public User userName(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT ID, PICTURE, Status_message FROM user WHERE ID = ?";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String usid = rs.getString("ID");
                String picture = rs.getString("PICTURE");
                String statusMessage = rs.getString("Status_message");

                return new User(usid, picture, statusMessage);

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
    
    public String selectImage(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT PICTURE FROM user where ID = ?;";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String image = rs.getString("PICTURE");
                return image;
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
    
    
    public int userImage() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT count FROM image_count";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int count = rs.getInt("count");
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeRS(rs);
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
        return -1;
    }

    public User userloginCk(String id, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT ID, Status_message, PICTURE FROM user WHERE ID = (?) And PASSWORD = (?)";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String usid = rs.getString("ID");
                String Status_message = rs.getString("Status_message");
                String PICTURE = rs.getString("PICTURE");
  
                return new User(usid, Status_message,PICTURE);
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

    public User userNameAndPicAndStatus(String id, String picture, String statusMessage) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT PICTURE, Status_message FROM user WHERE ID = ?";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String uid = rs.getString("ID");
                String uPicture = rs.getString("PICTURE");
                String uStatusMessage = rs.getString("Status_message");

                return new User(uid, picture, statusMessage);
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
    
    

    public void userUpdate(String id, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "UPDATE user SET PASSWORD = ?" + "WHERE ID = ?";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, password);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
    }

    
    
    
    public void imageCountUpdate(int count) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "update image_count SET count = ? where name = 'image';";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, count);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
    }
    
    
    
    public void userPicUpdate(String id, String picture) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "UPDATE user SET PICTURE = ?" + "WHERE ID = ?";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, picture);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
    }

    public void userStatusUpdate(String id, String statusMessage) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "UPDATE user SET Status_message = ?" + "WHERE ID = ?";

        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, statusMessage);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
    }

    public void userDelete(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String query = "DELETE FROM user WHERE id = ?";


        try {
            conn = ConnChatDB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnChatDB.closeStmt(pstmt);
            ConnChatDB.closeConnection(conn);
        }
    }
}