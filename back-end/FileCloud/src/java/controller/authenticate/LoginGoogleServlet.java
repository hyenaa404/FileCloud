/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authenticate;

import com.google.gson.Gson;
import context.AccountDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.GoogleAccount;
import util.GoogleUtils;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/login-google"})
public class LoginGoogleServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginGoogleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginGoogleServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            Account responseAccount = null;
            String accessToken = GoogleUtils.getToken(code);
            GoogleAccount googlePojo = GoogleUtils.getUserInfo(accessToken);

            HttpSession session = request.getSession();
            if (!isAccountExist(googlePojo.getEmail())) {
                account = new Account();
                account.setFullName(googlePojo.getName());
                account.setEmail(googlePojo.getEmail());
                if(accountDAO.createAccountGoogle(account)){
                    responseAccount = accountDAO.checkAccountByEmail(account.getEmail());
                }else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            }
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            session.setAttribute("user", account);
            session.setMaxInactiveInterval(10 * 24 * 60 * 60);out.print("{\"user\": " + new Gson().toJson(responseAccount) + "}");
            response.sendRedirect("http://localhost:3000/login-google");
        }
    }
    AccountDAO accountDAO = new AccountDAO();
    Account account;

    protected boolean isAccountExist(String email) {

        account = accountDAO.checkAccountByEmail(email);
        if (account == null) {
            return false;
        } else {
            return true;
        }
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
