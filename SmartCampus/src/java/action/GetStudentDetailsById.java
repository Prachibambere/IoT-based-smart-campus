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
import org.json.JSONObject;
import pack.DBConnection;

/**
 *
 * @author PHOENIX DINESH
 */
public class GetStudentDetailsById extends HttpServlet {

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
            String student_id = request.getParameter("id");
            System.out.println("Student id: "+student_id);
            String sql = "SELECT * FROM tbl_students WHERE student_rfid='" + student_id+"'";
            Connection con = DBConnection.getConn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            JSONObject json = new JSONObject();
            String output="<p>";
            if (rs.next()) {
               output+="<b>Name: </b>"+rs.getString("student_name")+"<br>";
               output+="<b>Email: </b>"+rs.getString("student_email")+"<br>";
               output+="<b>Department: </b>"+rs.getString("student_department")+"<br>";
               output+="<b>Year: </b>"+rs.getString("student_year")+"<br>";
            }else
            {
                output+="<span>No Student Details Found</span>";
            }
            output+="</p>";
            json.put("output", output);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json.toString());
            out.flush();
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
