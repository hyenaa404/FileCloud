/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class Permission {
//    PermissionID INT IDENTITY(1,1) PRIMARY KEY,
//    UserID INT NOT NULL,
//    FolderID INT NULL,
//    FileID INT NULL,
//    Role
    private int permissionID;
    private String email;
    private int folderID;
    private int fileID;
    private String role;

    public Permission() {
    }

    public Permission(int permissionID, String email, int folderID, int fileID, String role) {
        this.permissionID = permissionID;
        this.email = email;
        this.folderID = folderID;
        this.fileID = fileID;
        this.role = role;
    }

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFolderID() {
        return folderID;
    }

    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
