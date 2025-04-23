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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import model.Account;
import model.Files;
import model.Permission;
import org.apache.catalina.util.StringUtil;
import util.StringUtils;

@WebServlet(name = "FileServlet", urlPatterns = {"/file"})
@MultipartConfig
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

        String fileName = fileProperties.getName();
        String extension = StringUtils.getFileExtension(fileName); 
        String contentType = StringUtils.getMimeTypeByExtension(extension);

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");


//        response.setHeader("Content-Disposition", "inline; filename=\"" + fileProperties.getName() + "\"");

        try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        }

//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] fileBytes = new byte[(int) file.length()];
//        fileInputStream.read(fileBytes);
//        fileInputStream.close();
//
//        String base64File = Base64.getEncoder().encodeToString(fileBytes);
//
//        response.setContentType("application/json");
//        response.getWriter().write("{\"fileData\": \"" + base64File + "\", " + "\"userRole\": \"" + "Owner" + "\"}");
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

    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        int folderID = Integer.parseInt(req.getParameter("folderID"));
//        Part filePart = req.getPart("file");
//        if (filePart != null && filePart.getSize()>0){
//            
//            InputStream fileStream = filePart.getInputStream();
//            // Đọc dữ liệu từ InputStream vào một mảng byte
//            byte[] fileBytes = fileStream.readAllBytes();
//        }
//    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int folderID = 0;
        try {
            folderID = Integer.parseInt(req.getParameter("folderID"));
        } catch (NumberFormatException e) {
            System.out.println(req.getParameter("folderID"));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        HttpSession session = req.getSession();
        Account user = (Account) session.getAttribute("user");
        Part filePart = req.getPart("file");
        FileDAO fileDAO = new FileDAO();

        if (filePart != null && filePart.getSize() > 0) {
            // Build full folder path
            String fullFolderPath = "uploads\\user_" + user.getUserID() + "\\" + fileDAO.getFolderPath(folderID);

            String basePath = getServletContext().getRealPath("\\").replace("build\\", "") + "\\";
            File saveDir = new File(basePath, fullFolderPath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String extension = "";
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                extension = fileName.substring(dotIndex + 1).toLowerCase();
            }
            String fileType = StringUtils.getFileTypeFromExtension(extension);

            File file = new File(saveDir, fileName);
            try (InputStream input = filePart.getInputStream(); FileOutputStream output = new FileOutputStream(file)) {
                input.transferTo(output);
            }

            fileProperties = new Files();
            fileProperties.setName(fileName);
            fileProperties.setFileType(fileType);
            fileProperties.setFilePath(fullFolderPath + "\\" + fileName);
            fileProperties.setOwnerID(user.getUserID());
            fileProperties.setPrvacyLevel("Private");
            fileProperties.setFolderID(folderID);
            if (!fileDAO.insertFile(fileProperties)) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print("{}");
            }

//        resp.getWriter().write("File uploaded to: " + file.getAbsolutePath());
        }
    }

}
