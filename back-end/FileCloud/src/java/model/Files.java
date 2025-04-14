/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Files {
    

    private int fileID;
    private String name;
    private int folderID;
    private String fileType;
    private int ownerID;
    private String filePath;
    private Date uploadedAt;
    private String prvacyLevel;

    public Files() {
    }

    public Files(int fileID, String name, int folderID, String fileType, int ownerID, String filePath, Date uploadedAt, String prvacyLevel) {
        this.fileID = fileID;
        this.name = name;
        this.folderID = folderID;
        this.fileType = fileType;
        this.ownerID = ownerID;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
        this.prvacyLevel = prvacyLevel;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFolderID() {
        return folderID;
    }

    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getPrvacyLevel() {
        return prvacyLevel;
    }

    public void setPrvacyLevel(String prvacyLevel) {
        this.prvacyLevel = prvacyLevel;
    }
    
    
    
    
}
