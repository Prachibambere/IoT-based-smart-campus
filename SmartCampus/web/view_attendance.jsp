<%@page import="java.text.DecimalFormat"%>
<%@page import="action.Subject"%>
<%@page import="java.awt.print.Book"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="pack.DBConnection"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html lang="en">
    <head>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Smart Campus</title>
        <link type="text/css" href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link type="text/css" href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
        <link type="text/css" href="css/theme.css" rel="stylesheet">
        <link type="text/css" href="images/icons/css/font-awesome.css" rel="stylesheet">
        <link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
              rel='stylesheet'>
    </head>
    <%
        HttpSession user_session = request.getSession();
        if (user_session.getAttribute("user") == null) {
            out.println("<script>");
            out.println("alert('Session expired!')");
            out.println("location='logout.jsp'");
            out.println("</script>");
        }
        String id = request.getParameter("id");
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
        rs=st.executeQuery(sql);
        while(rs.next())
        {
            String sub_id=rs.getString("subject_id");
            String sub_name=rs.getString("subject_name");
            int lectures=rs.getInt("lectures");
            Subject sub=new Subject(sub_id, sub_name, lectures);
            list.add(sub);
        }
        rs.close();

    %>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                        <i class="icon-reorder shaded"></i></a><a class="brand" href="staff_home.jsp">Smart Campus</a>
                    <div class="nav-collapse collapse navbar-inverse-collapse">

                        <ul class="nav pull-right">

                            <li class="nav-user dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="images/user.png" class="nav-avatar" />
                                    <b class="caret"></b></a>
                                <ul class="dropdown-menu">

                                    <li><a href="logout.jsp">Logout</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <!-- /.nav-collapse -->
                </div>
            </div>
            <!-- /navbar-inner -->
        </div>
        <!-- /navbar -->
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <jsp:include page="common/staff_nav.jsp" />
                    <!--/.span3-->
                    <div class="span9">
                        <div class="content">

                            <div class="module">
                               
                                <div class="module-head">
                                    <h3>Attendance Details</h3>

                                </div>
                                <div class="module-body table">
                                    <table cellpadding="0" cellspacing="0" border="0" class="datatable-1 table table-bordered table-striped display"
                                           width="100%">
                                        <thead>
                                            <tr>
                                                <th>
                                                    Sr No
                                                </th>
                                                <th>
                                                    Subject
                                                </th>
                                                <th>
                                                    Total Lectures
                                                </th>
                                                <th>
                                                    Presents
                                                </th>
                                                <th>
                                                    (%)
                                                </th>
                                              
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <%       
                                                int i=0;
                                                for(Subject sub:list)
                                                {
                                                    i++;
                                                String sub_name=sub.getName();
                                                String sub_id=sub.getId();
                                                int lectures=sub.getLectures();
                                                int presentee=0;
                                                //get subjects
                                                sql="SELECT COUNT(*) as total  FROM tbl_attendance WHERE student_rfid='"+id+"' AND subject_id='"+sub_id+"'";
                                                rs.close();
                                                rs=st.executeQuery(sql);
                                                if(rs.next())
                                                {
                                                    presentee=rs.getInt(1);
                                                }
                                                double percent=((double)presentee/lectures)*100.0;
                                                String str_percent=new DecimalFormat("#.##").format(percent);
                                            %>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <%=i%>
                                                </td>
                                                <td>
                                                    <%=sub_name%>
                                                </td>
                                                <td>
                                                    <%=lectures%>
                                                </td>
                                                <td class="center">
                                                    <%=presentee%>
                                                </td>
                                                <td class="center">
                                                    <%=str_percent%>
                                                </td>
                                            <%
                                                }
                                                
                                            %>

                                        </tbody>
                                       
                                    </table>
                                </div>
                            </div>
                            <!--/.module-->
                        </div>
                        <!--/.content-->
                    </div>
                    <!--/.span9-->
                </div>
            </div>
            <!--/.container-->
        </div>
        <!--/.wrapper-->
        <div class="footer">
            <div class="container">
                <b class="copyright">&copy; 2021 Smart Campus </b> All rights reserved.
            </div>
        </div>

        
        <script src="scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script src="scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="scripts/flot/jquery.flot.js" type="text/javascript"></script>
        <script src="scripts/flot/jquery.flot.resize.js" type="text/javascript"></script>
        <script src="scripts/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="scripts/common.js" type="text/javascript"></script>

    </body>
