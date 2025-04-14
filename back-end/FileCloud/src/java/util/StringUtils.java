/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author LENOVO
 */
public class StringUtils {
    public static boolean isNullOrBlankOrEmpty (String input){
        return input == null || input.isBlank() || input.isEmpty();
    }
}
