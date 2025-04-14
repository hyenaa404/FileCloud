/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Permission;

/**
 *
 * @author LENOVO
 */
public class PermissionDAO {
    private DBContext dbContext;

    public PermissionDAO() {
        dbContext = new DBContext();
    }
    
    public boolean checkConnection() throws Exception {
        try (Connection conn = dbContext.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
      
    public Permission checkPrivatePermission(int fileID, int folderID, String email) throws Exception{
        Permission permission;
        String query = fileID == 0 ? "SELECT * FROM Permission WHERE FolderID = ? AND Email = ?" : "SELECT * FROM Permission WHERE FileID = ? AND Email = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fileID == 0? folderID : fileID);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    permission = new Permission(
                            rs.getInt("PermissionID"),
                            rs.getString("Email"),
                            rs.getInt("FolderID"),
                            rs.getInt("FileID"),
                            rs.getString("Role")
                            
                    );
                    return permission;
                           
                }
            }
        
        return null;
    }
    
    
    public String checkOtherPermission(int fileID, int folderID) throws Exception{
        String query = fileID == 0 ? "SELECT * FROM Permission WHERE FolderID = ? AND Email IS NULL" : "SELECT * FROM Permission WHERE FileID = ? AND Email IS NULL";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fileID == 0? folderID : fileID);

            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString("Role");
                           
                }
            }
        
        return null;
    }
}
