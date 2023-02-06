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

        String rfid = request.getParameter("scanned_rfid");
        if (rfid != null) {
            out.print("<script>alert('detected');</script>");
        } else {
            rfid = "";
        }

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
                    <jsp:include page="common/library_nav.jsp" />
                    <!--/.span3-->
                    <div class="span9">
                        <div class="content">

                            <div class="module">
                                <div  id="warehouseModal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Scan RFID and Get Details</h4>
                                            </div>

                                            <div class="modal-body">

                                                <div class="form-control">
                                                    <label for="scanned_rfid" class="col-form-label">Scanned RFID:</label>
                                                    <input type="text" required="" class="controls row-fluid" id="scanned_rfid" value="<%=rfid%>" name="scanned_rfid" readonly="">

                                                </div>

                                            </div>

                                        </div>
                                        <!-- /.modal-content -->
                                    </div>
                                    <!-- /.modal-dialog -->
                                </div>
                            </div>
                            <div class="module" id="transactions" style="display:none">
                                <h1>Data Received...</h1>
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
                        document.getElementById("scanned_rfid").value = response.rfid;
                        if (response.rfid != "")
                        {
                            readTransactions(response.rfid);
                            clearInterval(myInterval);
                        }
                    },
                    error: function () {

                    }
                });
            }


            function readTransactions(str)
            {
                var x = document.getElementById("transactions");
                $.ajax({
                    type: "POST",
                    url: "GetTransanctionByRFID",
                    data: "id=" + str,
                    async: false,
                    success: function (response) {

                        x.style.display = "block";
                        document.getElementById("transactions").innerHTML = response.output;
                    },
                    error: function () {
                        x.style.display = "none";
                    }
                });
            }
            function myStop() {
                clearInterval(myInterval);
            }
        </script>
    </body>
