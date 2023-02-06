<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="pack.DBConnection"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html lang="en">

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

        int studentNo = 0, staffNo = 0, rfid = 0,librarians=0;
        Connection con = DBConnection.getConn();
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("SELECT COUNT(*) as total FROM tbl_students");
        if (rs.next()) {
            studentNo = rs.getInt("total");
        }
        rs.close();
        rs = st.executeQuery("SELECT COUNT(*) as total FROM tbl_staff");
        if (rs.next()) {
            staffNo = rs.getInt("total");
        }
        rs.close();
        rs = st.executeQuery("SELECT COUNT(*) as total FROM tbl_rfid");
        if (rs.next()) {
            rfid = rs.getInt("total");
        }
        rs.close();
        rs = st.executeQuery("SELECT COUNT(*) as total FROM tbl_librarians");
        if (rs.next()) {
            librarians = rs.getInt("total");
        }

    %>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                        <i class="icon-reorder shaded"></i></a><a class="brand" href="admin_home.jsp">Smart Campus </a>
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
                            <div class="btn-controls">
                                <div class="btn-box-row row-fluid">
                                    <a href="view_students.jsp" class="btn-box big span4"><i class=" icon-group"></i><b><%=studentNo%></b>
                                        <p class="text-muted">
                                            Students</p>
                                    </a>
                                    <a href="view_staff.jsp" class="btn-box big span4"><i class="icon-user"></i><b><%=staffNo%></b>
                                        <p class="text-muted">
                                            Staff</p>
                                    </a>
                                    <a href="librarians.jsp" class="btn-box big span4"><i class="icon-user"></i><b><%=librarians%></b>
                                        <p class="text-muted">
                                            Librarian</p>
                                    </a>
                                    <a href="view_rfid.jsp" class="btn-box big span4"><i class="icon-money"></i><b><%=rfid%></b>
                                        <p class="text-muted">
                                            RFID</p>
                                    </a>
                                </div>

                            </div>
                            <!--/#btn-controls-->

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
