/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pack.DBConnection;

/**
 *
 * @author Dinesh
 */
public class UserLogin extends HttpServlet {

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
        try {
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            HttpSession user = request.getSession();

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String userType = request.getParameter("utype");
            String sql = "";
            Connection con = DBConnection.getConn();
            Statement st = con.createStatement();
            ResultSet rs = null;
            if (userType.equals("Admin")) {
                if (username.equals("admin@gmail.com") && password.equals("admin")) {
                    user.setAttribute("user", "admin");
                    out.println("<script>");
                    out.println("alert('Login Success!')");
                    out.println("location='admin_home.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Invalid Username or password!')");
                    out.println("location='index.html'");
                    out.println("</script>");
                }

            } else if (userType.equalsIgnoreCase("staff")) {
                sql = "SELECT * FROM tbl_staff WHERE staff_email='" + username + "' AND staff_password='" + password + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    user.setAttribute("user", "Staff");
                    user.setAttribute("username", username);
                    user.setAttribute("name", rs.getString("staff_name"));
                    user.setAttribute("user_id",rs.getString("staff_id"));
                     user.setAttribute("department",rs.getString("staff_department"));
                    out.println("<script>");
                    out.println("alert('Login Success!')");
                    out.println("location='staff_home.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Invalid Username or password!')");
                    out.println("location='index.html'");
                    out.println("</script>");
                }
            } else {

                sql = "SELECT * FROM tbl_librarians WHERE email='" + username + "' AND password='" + password + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    user.setAttribute("user", "Librarian");
                    user.setAttribute("username", username);
                    user.setAttribute("name", rs.getString("full_name"));
                    out.println("<script>");
                    out.println("alert('Login Success!')");
                    out.println("location='library_home.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Invalid Username or password!')");
                    out.println("location='index.html'");
                    out.println("</script>");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
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
