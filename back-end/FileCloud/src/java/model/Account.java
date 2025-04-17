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
public class Account {
    private int userID;
    private String fullName;
    private String email;
    private String password;
    private Date createdAt;

    public Account() {
    }

    public Account(int userID, String fullName, String email, String password, Date timePickup) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.createdAt = timePickup;
    }

    public Account(String fullName, String email, Date createdAt) {
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
    }
    
    

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    
    
    
}
