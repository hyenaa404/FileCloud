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
import context.PermissionDAO;
import jakarta.servlet.http.HttpSession;
import model.Files;
import model.Permission;

@WebServlet(name = "FileServlet", urlPatterns = {"/file"})
public class FilePreviewServlet extends HttpServlet {

    Files fileProperties;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int fileID;
        try {
            fileID = Integer.parseInt(request.getParameter("FileID"));

            if (retrieveFileDetail(fileID, response)) {
                handleDisplay(response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

//        response.setContentType("application/json");
//        response.getWriter().write("{\"fileURL\": \"" + filePath + "\"}");
    }

    private boolean retrieveFileDetail(int fileID, HttpServletResponse response)
            throws ServletException, IOException {

        FileDAO fileDAO = new FileDAO();
        try {
            fileProperties = fileDAO.getFileByID(fileID);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return fileProperties != null;

    }

//    private void denyAccess(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }
    private void handleDisplay(HttpServletResponse response) throws ServletException, IOException {
        String filePath = getServletContext().getRealPath("\\").replace("build\\", "") + "\\" + fileProperties.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(fileProperties.getFilePath());
            return;
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        fileInputStream.read(fileBytes);
        fileInputStream.close();

        String base64File = Base64.getEncoder().encodeToString(fileBytes);

        response.setContentType("application/json");
        response.getWriter().write("{\"fileData\": \"" + base64File + "\", " + "\"userRole\": \"" + "Owner" + "\"}");

    }

    private void handlePermission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (checkOwner(request)) {

        }
    }

    private boolean checkOwner(HttpServletRequest request) {
        // get userID from session
        HttpSession session = request.getSession(false);
        int userID;
        try {
            userID = Integer.parseInt(session.getAttribute("userID").toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return fileProperties.getOwnerID() == userID;

    }

    private Permission getPermission(int fileID) {
        //get userID từ session
        PermissionDAO pDAO = new PermissionDAO();
        try {
            return pDAO.checkPrivatePermission(fileID, 0, "user email");
        } catch (Exception e) {
            return null;
        }
    }
}
