/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Folder;

/**
 *
 * @author LENOVO
 */
public class FolderDAO {
    private DBContext dbContext;

    public FolderDAO(DBContext dbContext) {
        this.dbContext = dbContext;
    }
    
    public boolean checkConnection() throws Exception {
        try (Connection conn = dbContext.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public Folder getFolderByID(int folderID) throws Exception{
        Folder folder;
        String query = "SELECT * FROM Folder WHERE FolderID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, folderID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    folder = new Folder(
                            rs.getInt("FolderID"),
                            rs.getString("Name"),
                            rs.getInt("ParentID"),
                            rs.getInt("OwnerID"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getString("PrivacyLevel")
                            
                    );
                    return folder;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving folder by FolderID: " + e.getMessage());
        }

        return null;
    }
    
    
    public Folder getUserRootFolderByUserID(int userID) throws Exception{
        Folder folder;
        String query = "SELECT * FROM Folder WHERE ParentID IS NULL AND UserID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    folder = new Folder(
                            rs.getInt("FolderID"),
                            rs.getString("Name"),
                            rs.getInt("ParentID"),
                            rs.getInt("OwnerID"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getString("PrivacyLevel")
                            
                    );
                    return folder;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving folder by FolderID: " + e.getMessage());
        }

        return null;
    }
    
    public List<Folder> getListFoldersByParentID(int parentID) throws Exception{
        List<Folder> folders = new ArrayList();
        String query = "SELECT * FROM Folder WHERE ParentID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, parentID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Folder folder = new Folder(
                            rs.getInt("FolderID"),
                            rs.getString("Name"),
                            rs.getInt("ParentID"),
                            rs.getInt("OwnerID"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getString("PrivacyLevel")
                            
                    );
                    folders.add(folder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving folder by ParentID: " + e.getMessage());
        }

        return folders;
    }
    
    
}
