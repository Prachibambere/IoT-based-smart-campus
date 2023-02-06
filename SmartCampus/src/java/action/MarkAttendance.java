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
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class MarkAttendance extends HttpServlet {

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
            String sub_id = request.getParameter("subjectId");
            String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            String sql = "SELECT * from tbl_subjects WHERE subject_id='" + sub_id + "'";
            Connection con = DBConnection.getConn();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            JSONObject json = new JSONObject();
            if (rs.next()) {
                int lecture = rs.getInt("lectures");
                String year =rs.getString("year");
                String department=rs.getString("department");
                lecture++;
                sql = "SELECT * FROM  tbl_current_rfid tcr INNER JOIN tbl_students ts ON tcr.rfid=ts.student_rfid WHERE "
                        + "ts.student_year='"+year+"' AND student_department='"+department+"'";
                rs.close();
                rs = st.executeQuery(sql);

                if (rs.next()) {
                    //get current rfid    
                    String rfid = rs.getString("rfid");
                    sql = "DELETE FROM tbl_current_rfid";
                    st.executeUpdate(sql);
                    //check if entry for same lecture
                    sql = "SELECT * FROM tbl_attendance WHERE subject_id='" + sub_id + "' AND lecture_id='" + lecture + "' AND student_rfid='" + rfid + "' AND lecture_date='" + date + "'";
                    rs.close();
                    rs = st.executeQuery(sql);
                    if (rs.next()) {

                    } else {
                        //mark attendance
                        sql = "INSERT INTO tbl_attendance(subject_id,lecture_id,student_rfid,lecture_date) VALUES "
                                + "('" + sub_id + "','" + lecture + "','" + rfid + "','" + date + "')";
                        int row_affected = st.executeUpdate(sql);

                        if (row_affected > 0) {
                            json.put("output", true);
                        }
                    }

                }
            }
            rs.close();
            st.close();
            con.close();
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
