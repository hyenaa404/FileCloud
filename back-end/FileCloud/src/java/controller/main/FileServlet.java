/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import context.FileDAO;


@WebServlet(name = "FileServlet", urlPatterns = {"/file"})
public class FileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FileDAO fileDAO = new FileDAO();
        String filePath = "";
        try {filePath = getServletContext().getRealPath("\\").replace("build\\", "") + "\\" + fileDAO.getFileByID(1).getFilePath();}
        catch(Exception e){
            System.out.println("error");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(filePath);
            return;
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        fileInputStream.read(fileBytes);
        fileInputStream.close();

        String base64File = Base64.getEncoder().encodeToString(fileBytes);
        
        response.setContentType("application/json");
        response.getWriter().write("{\"fileData\": \"" + base64File + "\"}");
        
        
        
//        response.setContentType("application/json");
//        response.getWriter().write("{\"fileURL\": \"" + filePath + "\"}");
        
    }
}
