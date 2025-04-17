/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author LENOVO
 */
public class ConfigLoader {
    private static Properties properties = new Properties();

//    static {
//        try (InputStream input = new FileInputStream("config.properties")) {
//            properties.load(input);
//        } catch (IOException e) {
//            e.printStackTrace(); 
//        }
//    }
    
    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Could not find config.properties in classpath!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

