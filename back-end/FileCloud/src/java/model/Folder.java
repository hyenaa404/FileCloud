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
public class Folder {
//    CREATE TABLE Folders (
//    FolderID INT PRIMARY KEY IDENTITY,
//    Name NVARCHAR(255) NOT NULL,
//    ParentID INT, 
//    OwnerID INT FOREIGN KEY REFERENCES Users(UserID),
//    CreatedAt DATETIME DEFAULT GETDATE(),
//    PrivacyLevel NVARCHAR(20) CHECK (PrivacyLevel IN ('Private', 'Public', 'Organization'))
    private int folderID;
    private String name;
    private int parent;
    private int ownerID;
    private Date createdAt;
    private String privacyLevel;

    public Folder() {
    }

    public Folder(int folderID, String name, int parent, int ownerID, Date createdAt, String privacyLevel) {
        this.folderID = folderID;
        this.name = name;
        this.parent = parent;
        this.ownerID = ownerID;
        this.createdAt = createdAt;
        this.privacyLevel = privacyLevel;
    }

    public int getFolderID() {
        return folderID;
    }

    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(String privacyLevel) {
        this.privacyLevel = privacyLevel;
    }
    
    
}
