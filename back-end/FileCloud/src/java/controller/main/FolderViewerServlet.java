/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.main;

import com.google.gson.Gson;
import context.FileDAO;
import context.FolderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.Account;
import model.Files;
import model.Folder;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "FolderViewerServlet", urlPatterns = {"/folder"})
public class FolderViewerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    Folder folderProperties;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int folderID;
        try {
            folderID = Integer.parseInt(request.getParameter("FolderID"));

            if (retrieveFolderDetail(folderID, response)) {
                handleDisplay(response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (NumberFormatException e) {
            
            HttpSession session = request.getSession(false);
            if(session!= null && session.getAttribute("user") != null){
                int userID = ((Account)session.getAttribute("user")).getUserID();
                handleDisplayRootFolder(userID, response);
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
    }
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }
    
    
    
   
    
    private boolean retrieveFolderDetail(int fileID, HttpServletResponse response)
            throws ServletException, IOException {

        FolderDAO folderDAO = new FolderDAO();
        try {
            folderProperties = folderDAO.getFolderByID(fileID);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return folderProperties != null;

    }
    
    private void handleDisplay(HttpServletResponse response) throws ServletException, IOException {
        List<Files> listFile = retrieveFilesList(folderProperties.getFolderID(), response);
        List<Folder> listFolder = retrieveFoldersList(folderProperties.getFolderID(), response);

        response.setContentType("application/json");
        response.getWriter().write("{\"listFile\": \"" +  new Gson().toJson(listFile) + "\", " + "\"listFolder\": \"" +  new Gson().toJson(listFolder) + "\"}");

    }
    
    private void handleDisplayRootFolder(int userID, HttpServletResponse response) throws ServletException, IOException {
        List<Files> listFile = retrieveRootFilesList(userID, response);
        List<Folder> listFolder = retrieveRootFolderList(userID, response);

        response.setContentType("application/json");
        response.getWriter().write("{\"listFile\": " + new Gson().toJson(listFile) + ", " + "\"listFolder\": " + new Gson().toJson(listFolder) + "}");

    }
    
    
    private List<Files> retrieveFilesList(int folderID, HttpServletResponse response)
            throws ServletException, IOException {

        List<Files> fileList;
        FileDAO fileDAO = new FileDAO();
        try {
            fileList = fileDAO.getFilesByParentID(folderID,0);
            return fileList;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;

    }
    
    private List<Files> retrieveRootFilesList(int userID, HttpServletResponse response)
            throws ServletException, IOException {

        List<Files> fileList;
        FileDAO fileDAO = new FileDAO();
        try {
            fileList = fileDAO.getFilesByParentID(0,userID);
            return fileList;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;

    }
    
     private List<Folder> retrieveRootFolderList(int userID, HttpServletResponse response){
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList;
        try {
            foldersList = folderDAO.getListFoldersByParentID(0, userID);
            return foldersList;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }
    
    private List<Folder> retrieveFoldersList(int folderID, HttpServletResponse response)
            throws ServletException, IOException {

        List<Folder> foldersList;
        FolderDAO forderDAO = new FolderDAO();
        try {
            foldersList = forderDAO.getListFoldersByParentID(folderID,0);
            return foldersList;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
