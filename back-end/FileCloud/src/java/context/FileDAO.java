/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Files;

/**
 *
 * @author LENOVO
 */
public class FileDAO {
     private DBContext dbContext;

    public FileDAO() {
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
    
    public boolean insertFile(Files file){
        String query = "INSERT INTO Files (Name, FolderID, FileType, OwnerID, FilePath, PrivacyLevel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, file.getName());
            ps.setInt(2, file.getFolderID());
            ps.setString(3, file.getFileType());
            ps.setInt(4, file.getOwnerID());
            ps.setString(5, file.getFilePath());
            ps.setString(6, file.getPrvacyLevel());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 
    
    public Files getFileByID(int fileID) throws Exception{
        Files file;
        String query = "SELECT * FROM Files WHERE FileID = ? AND STATUS = 1";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fileID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    file = new Files(
                            rs.getInt("FileID"),
                            rs.getString("Name"),
                            rs.getInt("FolderID"),
                            rs.getString("FileType"),
                            rs.getInt("OwnerID"),
                            rs.getString("FilePath"),
                            rs.getTimestamp("UploadedAt"),
                            rs.getString("PrivacyLevel")
                            
                    );
                    return file;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving file by FileID: " + e.getMessage());
        }

        return null;
    }
    
    
    
    
    public boolean deleteFileByID(int fileID) {
        Files file;
        String query = "UPDATE Files SET Status = 0 WHERE FileID = ?";

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fileID);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }

    }
    
    
    public List<Files> getFilesByParentID(int parentID, int userID) throws Exception{
        List<Files> files = new ArrayList<>();
        Files file;
        String query = "SELECT * FROM Files WHERE FolderID = ? AND STATUS = 1";
        if (parentID == 0){
            query = "SELECT * FROM Files WHERE FolderID IS NULL AND OwnerID = ? AND STATUS = 1";
        }

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, parentID);
            if(parentID == 0){
                ps.setInt(1, userID);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    file = new Files(
                            rs.getInt("FileID"),
                            rs.getString("Name"),
                            rs.getInt("FolderID"),
                            rs.getString("FileType"),
                            rs.getInt("OwnerID"),
                            rs.getString("FilePath"),
                            rs.getTimestamp("UploadedAt"),
                            rs.getString("PrivacyLevel")
                            
                    );
                    files.add(file);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving file by PostID: " + e.getMessage());
        }

        return files;
    }
    
    
    public String getFolderPath(int folderID) {
        StringBuilder pathBuilder = new StringBuilder();
        try (Connection conn = dbContext.getConnection()) {
            while (folderID != 0) {
                String sql = "SELECT Name, ParentID FROM Folders WHERE folderID = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, folderID);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String folderName = rs.getString("Name");
                            folderID = rs.getInt("ParentID"); 
                            pathBuilder.insert(0, folderName + File.separator); 
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pathBuilder.toString();
    }

}
