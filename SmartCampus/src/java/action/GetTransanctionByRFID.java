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
public class GetTransanctionByRFID extends HttpServlet {

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
            String rfid_id = request.getParameter("id");
            String sql = "SELECT * FROM tbl_book_transactions tbt INNER JOIN tbl_students ts ON tbt.student_id=ts.student_rfid "
                    + "INNER JOIN tbl_books tb ON tbt.book_id=tb.book_id WHERE tbt.student_id='" + rfid_id + "' "
                    + "ORDER BY tbt.transaction_status ";
            Connection con = DBConnection.getConn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            JSONObject json = new JSONObject();
            String output = "<div class='module-head'><h3>Book Transactions </h3> </div> <div class='module-body table'>";
            output += "<table cellpadding='0' cellspacing='0' border='0' class='datatable-1 table table-bordered table-striped display' width='100%'>";
            output += "<table cellpadding='0' cellspacing='0' border='0' class='datatable-1 table table-bordered table-striped display' width='100%'>";
            output += "<thead><tr><th>Sr No</th><th>Student Name</th><th>Department</th><th>Year</th><th>Book Title</th>";
            output += "<th>Author</th><th>Date</th><th>Return Date</th><th>Status</th><th>Action</th></tr></thead><tbody>";
            int i = 0;
            while (rs.next()) {
                i++;
                output += "<tr class='odd gradeX'>";
                output += "<td>" + i + "</td>";
                output += "<td>" + rs.getString("student_name") + "</td>";
                output += "<td>" + rs.getString("student_department") + "</td>";
                output += "<td>" + rs.getString("student_year") + "</td>";
                output += "<td>" + rs.getString("book_title") + "</td>";
                output += "<td>" + rs.getString("book_author") + "</td>";
                output += "<td>" + rs.getString("date_time") + "</td>";
                output += "<td>" + rs.getString("return_date") + "</td>";
                output += "<td>" + rs.getString("due_charges") + "</td>";
                if (rs.getInt("transaction_status") != 0)
                {
                    output+="<td><i class='menu-icon text-success icon-check' title='Successfully Returned'></i></td>";
                }else
                {
                    output+="<td><a href='return_book.jsp?id="+rs.getString("transaction_id")+"' onclick='return confirm('Are you sure?')' title='Return Book' class='text-info'><i class='menu-icon icon-reply'></i></a></td>";
                }
                
            }
            output +="</tbody></table></div>";
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
