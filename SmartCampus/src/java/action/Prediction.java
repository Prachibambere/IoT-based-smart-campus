/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weka.classifiers.functions.SMO;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.CSVLoader;

/**
 *
 * @author PHOENIX DINESH
 */
public class Prediction extends HttpServlet {

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
            String s1 = request.getParameter("fes1");
            String s2 = request.getParameter("fes2");
            String s3 = request.getParameter("ses1");
            String s4 = request.getParameter("ses2");
            String s5 = request.getParameter("tes1");
            String s6 = request.getParameter("tes2");
            String s7 = request.getParameter("bes1");
            String s8 = request.getParameter("attend");

            int fes1 = 0;
            int fes2 = 0;
            int ses1 = 0;
            int ses2 = 0;
            int tes1 = 0;
            int tes2 = 0;
            int be1 = 0;
            int attend = 0;

            try {
                fes1 = Integer.parseInt(s1);
                fes2 = Integer.parseInt(s2);
                ses1 = Integer.parseInt(s3);
                ses2 = Integer.parseInt(s4);
                tes1 = Integer.parseInt(s5);
                tes2 = Integer.parseInt(s6);
                be1 = Integer.parseInt(s7);
                attend = Integer.parseInt(s8);

                CSVLoader csv = new CSVLoader();
                ServletContext sc = this.getServletContext();
                String res = "";
                String sg = sc.getRealPath("/");
                String pp = sg.substring(0, sg.indexOf("build"));
                System.out.println("path = " + pp);
                String finalpath = pp + "web/files/Dataset1.csv";
                HttpSession sn = request.getSession();
                sn.setAttribute("FilePath", pp);
//                graphcomp.path = finalpath;
                csv.setSource(new File(finalpath));

                Instances data = csv.getDataSet();
                data.setClassIndex(data.numAttributes() - 1);
                SMO sm = new SMO();
                sm.buildClassifier(data);

                Instance ins1 = new SparseInstance(8);
                String[] inpath1 = {"data"};

                ins1.setValue(0, fes1);
                ins1.setValue(1, fes2);
                ins1.setValue(2, ses1);
                ins1.setValue(3, ses2);
                ins1.setValue(4, tes1);
                ins1.setValue(5, tes2);
                ins1.setValue(6, be1);
                ins1.setValue(7, attend);

                ins1.setDataset(data);

                int r1 = (int) sm.classifyInstance(ins1);
                System.out.println(r1);
                PrintWriter out = response.getWriter();
                String cs[] = sm.classAttributeNames();
                String ms = cs[r1];
                if (ms.contains("First")) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Result is Predicted as First Class');");
                    out.println("location='analysis.jsp';");
                    out.println("</script>");
                    return;
                } else if (ms.contains("Higher")) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Result is Predicted as Higher Second Class');");
                    out.println("location='analysis.jsp';");
                    out.println("</script>");
                    return;
                } else if (ms.contains("Fail")) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Result is Predicted as Fail');");
                    out.println("location='analysis.jsp';");
                    out.println("</script>");
                    return;
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Result is Predicted as distinction');");
                    out.println("location='analysis.jsp';");
                    out.println("</script>");
                    return;
                }

            } catch (Exception e) {
                e.printStackTrace();

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
