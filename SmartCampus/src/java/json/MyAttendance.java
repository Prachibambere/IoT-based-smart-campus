/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import action.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pack.DBConnection;

/**
 *
 * @author PHOENIX DINESH
 */
public class MyAttendance extends HttpServlet {

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
            String id = request.getParameter("rfid");
            Connection con = DBConnection.getConn();
            Statement st = con.createStatement();
            String department = "";
            String name = "";
            String year = "";
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_students WHERE student_rfid='" + id + "'");
            if (rs.next()) {
                name = rs.getString("student_name");
                department = rs.getString("student_department");
                year = rs.getString("student_year");
            }
            ArrayList<Subject> list = new ArrayList<>();

            String sql = "SELECT * FROM tbl_subjects WHERE year='" + year + "' AND department='" + department + "'";
            rs.close();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String sub_id = rs.getString("subject_id");
                String sub_name = rs.getString("subject_name");
                int lectures = rs.getInt("lectures");
                Subject sub = new Subject(sub_id, sub_name, lectures);
                list.add(sub);
            }
            rs.close();
            JSONArray array = new JSONArray();
            JSONObject json = new JSONObject();
            for (Subject sub : list) {

                JSONObject object = new JSONObject();

                String sub_name = sub.getName();
                String sub_id = sub.getId();
                int lectures = sub.getLectures();
                int presentee = 0;
                //get subjects
                sql = "SELECT COUNT(*) as total  FROM tbl_attendance WHERE student_rfid='" + id + "' AND subject_id='" + sub_id + "'";
                rs.close();
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    presentee = rs.getInt(1);
                }
                double percent = ((double) presentee / lectures) * 100.0;
                String str_percent = new DecimalFormat("#.##").format(percent);

                object.put("subject", sub_name);
                object.put("lectures", lectures);
                object.put("presents", presentee);
                object.put("percentage", str_percent);
                array.put(object);

            }

            json.put("AttendanceInfo", array);
            json.put("success", "true");

            response.setContentType("application/json");
            response.getWriter().write(json.toString());

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
