/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class StringUtils {
    public static boolean isNullOrBlankOrEmpty (String input){
        return input == null || input.isBlank() || input.isEmpty();
    }
    
    
    public static String getFileTypeFromExtension(String extension) {
    if (extension == null) return "Other";

    extension = extension.toLowerCase();

    List<String> pdfExts = Arrays.asList("pdf");
    List<String> wordExts = Arrays.asList("doc", "docx");
    List<String> excelExts = Arrays.asList("xls", "xlsx", "xlsm");
    List<String> imageExts = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    if (pdfExts.contains(extension)) {
        return "PDF";
    } else if (wordExts.contains(extension)) {
        return "Word";
    } else if (excelExts.contains(extension)) {
        return "Excel";
    } else if (imageExts.contains(extension)) {
        return "Image";
    } else {
        return "Other";
    }
}

}
