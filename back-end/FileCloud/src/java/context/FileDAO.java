/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        return true;
    }
    
    public Files getFileByID(int fileID) throws Exception{
        Files file;
        String query = "SELECT * FROM Files WHERE FileID = ?";

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
    
    
    public Files getFileByParentID(int parentID) throws Exception{
        
        Files file;
        String query = "SELECT * FROM Files WHERE FolderID = ?";
        if (parentID == 0){
            query = "SELECT * FROM Files WHERE FolderID IS NULL";
        }

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, parentID);

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
                           
//    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving file by PostID: " + e.getMessage());
        }

        return null;
    }
    
    
    public static void main(String[] args) {
        FileDAO dao = new FileDAO();
        try {
            Files file = dao.getFileByID(1);
        System.out.println(file.getFilePath());
        }catch(Exception e){
            System.out.println("Error");
        }
    }

}
