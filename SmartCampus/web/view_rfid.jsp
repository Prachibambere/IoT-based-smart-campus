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
        Connection con = DBConnection.getConn();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM tbl_rfid");


    %>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                        <i class="icon-reorder shaded"></i></a><a class="brand" href="admin_home.jsp">Smart Campus</a>
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
               <jsp:include page="common/admin_nav.jsp" />
                    <!--/.span3-->
                    <div class="span9">
                        <div class="content">

                            <div class="module">
                                <button class="btn btn-success pull-right" data-toggle="modal" data-target="#rfidModal">Add New RFID</button> 
                                <div class="module-head">
                                    <h3>RFID Information</h3>

                                </div>
                                <div class="module-body table">
                                    <table cellpadding="0" cellspacing="0" border="0" class="datatable-1 table table-bordered table-striped	 display"
                                           width="100%">
                                        <thead>
                                            <tr>
                                                <th>
                                                    Sr No
                                                </th>
                                                <th>
                                                    RFID
                                                </th>
                                                <th>
                                                    Status
                                                </th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <%                                                while (rs.next()) {
                                            %>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <%=rs.getString("id")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("rfid")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("rfid_status")%>
                                                </td>

                                            </tr>
                                            <%
                                                }
                                            %>

                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>
                                                    Sr No
                                                </th>
                                                <th>
                                                    RFID
                                                </th>
                                                <th>
                                                    Status
                                                </th>

                                            </tr>
                                        </tfoot>
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


        <div class="modal fade" id="rfidModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Add RFID</h4>
                    </div>
                    <form action="AddRFID">
                        <div class="modal-body">
                            <div class="form-control">
                                <label for="rfid" class="col-form-label">RFID Number:</label>
                                <input type="text" required="" class="controls row-fluid" id="rfid" name="rfid">
                            </div>
                           

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <script src="scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script src="scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="scripts/flot/jquery.flot.js" type="text/javascript"></script>
        <script src="scripts/flot/jquery.flot.resize.js" type="text/javascript"></script>
        <script src="scripts/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="scripts/common.js" type="text/javascript"></script>
<script>
            const myInterval = setInterval(myTimer, 1000);
            var seconds = 60;
            var fiveMin = 0;
            function myTimer() {

                $.ajax({
                    type: "POST",
                    url: "GetCurrentRFID",
                    async: false,
                    success: function (response) {
                       
                        if (response.rfid != "")
                        {
                             document.getElementById("rfid").value = response.rfid;
//                            readTransactions(response.rfid);
//                            clearInterval(myInterval);
                        }
                    },
                    error: function () {

                    }
                });
            }
            function myStop() {
                clearInterval(myInterval);
            }
        </script>
    </body>
