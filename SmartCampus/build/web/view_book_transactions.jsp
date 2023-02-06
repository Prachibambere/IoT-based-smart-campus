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
        ResultSet rs = st.executeQuery("SELECT * FROM tbl_book_transactions tbt INNER JOIN tbl_students ts ON tbt.student_id=ts.student_rfid INNER JOIN tbl_books tb ON tbt.book_id=tb.book_id ORDER BY tbt.transaction_status");


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
                                <button class="btn btn-success pull-right" data-toggle="modal" data-target="#bookModal">Issue Book</button> 
                                <div class="module-head">
                                    <h3>Book Transactions </h3>

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
                                                    Student Name
                                                </th>
                                                <th>
                                                    Department
                                                </th>
                                                <th>
                                                    Year
                                                </th>
                                                <th>
                                                    Book Title
                                                </th>
                                                <th>
                                                    Author
                                                </th>
                                                <th>
                                                    Date
                                                </th>
                                                <th>
                                                    Return Date
                                                </th>
                                                <th>
                                                    Status
                                                </th>
                                                <th>
                                                    Action
                                                </th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <%                                                int i = 0;
                                                while (rs.next()) {
                                                    i++;
                                            %>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <%=i%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("student_name")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("student_department")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("student_year")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("book_title")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("book_author")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("date_time")%>
                                                </td>
                                                <td class="center">
                                                    <%=rs.getString("return_date")%>
                                                </td>
                                                <td class="center">
                                                    <%=rs.getString("due_charges")%>
                                                </td>

                                                <td>
                                                    <% if (rs.getInt("transaction_status") != 0) {
                                                    %>
                                                    <i class="menu-icon text-success icon-check" title="Successfully Returned"></i>
                                                    <%
                                                    } else {
                                                    %>

                                                    <a href="return_book.jsp?id=<%=rs.getString("transaction_id")%>" onclick="return confirm('Are you sure?')" title="Return Book" class="text-info"><i class="menu-icon icon-reply"></i></a>
                                                        <%}%>
                                                </td>

                                            </tr>
                                            <%
                                                }
                                                rs.close();
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

        <div class="modal fade" id="bookModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Add Book Details</h4>
                    </div>
                    <form action="AddBookTransaction" method="POST">
                        <div class="modal-body">


                            <div class="form-control">
                                <label for="student_rfid" class="col-form-label">Scan RFID:</label>
                                <input type="text" required="" class="controls row-fluid" id="student_rfid" name="student_rfid">
                            </div>
                            <div class="student_info" id="student_info" style="display:none">

                            </div>
                            <div class="form-control">
                                <label for="book_title" class="col-form-label">Book Title:</label>
                                <select name="book_title" id="book_title" class="controls row-fluid" required>
                                    <option value="">Select Book Title</option>
                                    <%
                                        String sql = "SELECT * FROM tbl_books";
                                        rs = st.executeQuery(sql);
                                        while (rs.next()) {
                                    %>
                                    <option value="<%=rs.getString("book_id")%>"><%=rs.getString("book_title")%></option>
                                    <%
                                        }
                                        rs.close();
                                        st.close();
                                        con.close();
                                    %>
                                </select>
                            </div>
                            <div class="book_info" id="book_info" style="display:none">

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
                             document.getElementById("student_rfid").value = response.rfid; 
                             getStudentInfo();
                        }
                    },
                    error: function () {

                    }
                });
            }
            function myStop() {
                clearInterval(myInterval);
            }
            
            
            $('#book_title').change(function () {
                var x = document.getElementById("book_info");
                $.ajax({
                    type: "POST",
                    url: "GetBookDetailsById",
                    data: "id=" + $(this).val(),
                    async: false,
                    success: function (response) {

                        x.style.display = "block";
                        document.getElementById("book_info").innerHTML = response.output;
                    },
                    error: function () {
                        x.style.display = "none";
                    }
                });

            });

            $('#student_rfid').change(function () {
                var x = document.getElementById("student_info");
                $.ajax({
                    type: "POST",
                    url: "GetStudentDetailsById",
                    data: "id=" + $(this).val(),
                    async: false,
                    success: function (response) {

                        x.style.display = "block";
                        document.getElementById("student_info").innerHTML = response.output;
                    },
                    error: function () {
                        x.style.display = "none";
                    }
                });

            });
            
            function getStudentInfo()
            {
                  var x = document.getElementById("student_info");
                $.ajax({
                    type: "POST",
                    url: "GetStudentDetailsById",
                    data: "id=" + document.getElementById("student_rfid").value,
                    async: false,
                    success: function (response) {

                        x.style.display = "block";
                        document.getElementById("student_info").innerHTML = response.output;
                    },
                    error: function () {
                        x.style.display = "none";
                    }
                });
            }

        </script>
    </body>
