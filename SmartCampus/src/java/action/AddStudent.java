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
import pack.DBConnection;

/**
 *
 * @author PHOENIX DINESH
 */
public class AddStudent extends HttpServlet {

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
            PrintWriter out = response.getWriter();
            String email = request.getParameter("email");
            String name = request.getParameter("student_name");
            String mobile = request.getParameter("mobile");
            String address = request.getParameter("address");
            String department = request.getParameter("department");
            String rfid = request.getParameter("rfid");
            String year = request.getParameter("year");
            String sql = "SELECT * from tbl_students WHERE student_email='" + email + "'";
            Connection con = DBConnection.getConn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                out.println("<script>");
                out.println("alert('Email already added!')");
                out.println("location='view_students.jsp'");
                out.println("</script>");
            } else {
                sql = "INSERT INTO tbl_students(student_name,student_email,student_mobile,student_address,student_year,student_department,student_rfid) "
                        + "VALUES('" + name + "','" + email + "','" + mobile + "','" + address + "','" + year + "','" + department + "','" + rfid + "')";
                int row_affected = st.executeUpdate(sql);
                if (row_affected > 0) {
                    //update status of RFID
                    sql = "UPDATE tbl_rfid SET rfid_status=1 WHERE rfid='" + rfid + "'";
                    row_affected = st.executeUpdate(sql);
                    out.println("<script>");
                    out.println("alert('Student added successfully!')");
                    out.println("location='view_students.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Error!')");
                    out.println("location='view_students.jsp'");
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
