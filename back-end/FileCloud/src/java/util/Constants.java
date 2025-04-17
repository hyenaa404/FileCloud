/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author LENOVO
 */
public class Constants {
    
    public static String GOOGLE_CLIENT_ID = ConfigLoader.get("GOOGLE_CLIENT_ID");
    public static String GOOGLE_CLIENT_SECRET = ConfigLoader.get("GOOGLE_CLIENT_SECRET");
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/FileCloud/login-google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://oauth2.googleapis.com/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
    
   
    public static void main(String[] args) {
        System.out.println(GOOGLE_CLIENT_ID +" and " +  GOOGLE_CLIENT_SECRET);
        System.out.println(" and " + GOOGLE_REDIRECT_URI);
    }
    
}

