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

        String user_id = session.getAttribute("user_id").toString();
        String sql = "SELECT * FROM tbl_subjects WHERE staff_id='" + user_id + "'";
        Connection con = DBConnection.getConn();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

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
                                <div  id="warehouseModal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Select Subject & Start Attendance</h4>
                                            </div>

                                            <div class="modal-body">

                                                <div class="form-control">
                                                    <label for="subject_name" class="col-form-label">Select Subject:</label>
                                                    <select name="subject_name" id="subject_name" class="controls row-fluid" required>
                                                        <option value="">Select Subject</option>
                                                        <%                                                            while (rs.next()) {
                                                        %>
                                                        <option value="<%=rs.getString("subject_id")%>"><%=rs.getString("subject_name")%></option>
                                                        <%
                                                            }
                                                            rs.close();
                                                            st.close();
                                                            con.close();
                                                        %>
                                                    </select>
                                                </div>

                                            </div>
                                            <div class="modal-footer">
                                                <h2 class="modal-title" id="demo"></h2>
                                                <button type="submit"  onclick="this.disabled=true;startAttendance();" id="takeAttendance" class="btn btn-primary">Start Attendance</button>
                                            </div>

                                        </div>
                                        <!-- /.modal-content -->
                                    </div>
                                    <!-- /.modal-dialog -->
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

        <script>
            var myInterval=null;
            var seconds = 120;
            var fiveMin = 0;
            function myTimer() {
                var timeleft = seconds - fiveMin; // let's say now is 01:30, then current seconds is 60+30 = 90. And 90%300 = 90, finally 300-90 = 210. That's the time left!

                fiveMin = fiveMin + 1;
                var second=timeleft % 60;
                if(second<10)
                {
                    second="0"+second;
                }
                var result = parseInt(timeleft / 60) + ':' + second; //formart seconds back into mm:ss 
                document.getElementById('demo').innerHTML = result;
                if (timeleft <= 0)
                {
                     var sub_id=document.getElementById("subject_name").value;
                    $.ajax({
                    type: "POST",
                    url: "StopMarkingAttendance",
                    data: "subjectId=" + sub_id,
                    async: false,
                    success: function (response) {
                       
                    },
                    error: function () {
                        
                    }
                });
                    
                    alert('Attendace Marked Successfully...');
                    myStop();
                }
                var sub_id=document.getElementById("subject_name").value;
                 $.ajax({
                    type: "POST",
                    url: "MarkAttendance",
                    data: "subjectId=" + sub_id,
                    async: false,
                    success: function (response) {
                        if(response.output)
                        {
                        console.log("attendance marked..");
                    }
                    },
                    error: function () {
                        
                    }
                });
            }

            function myStop() {
                clearInterval(myInterval);
            }
            
            function startAttendance()
            {
                var sub_id=document.getElementById("subject_name").value;
                if(sub_id==="")
                {
                    alert('Please select subject');
                    var btn = document.getElementById('takeAttendance');
                    btn.disabled = false;
                }
                else
                {
                    myInterval = setInterval(myTimer, 1000);
                }
                
            }
        </script>
    </body>
