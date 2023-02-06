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
        ResultSet rs = st.executeQuery("SELECT * FROM tbl_subjects");


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
                                <button class="btn btn-success pull-right" data-toggle="modal" data-target="#studentModal">Add Subject</button> 
                                <div class="module-head">
                                    <h3>Subject Information</h3>

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
                                                    Subject Name
                                                </th>
                                                <th>
                                                    Department
                                                </th>
                                                <th>
                                                    Year
                                                </th>
                                                <th>
                                                    Staff ID
                                                </th>
                                                <th>
                                                    Action
                                                </th>

                                            </tr>
                                        </thead>
                                        <tbody>

                                            <%                                                while (rs.next()) {
                                            %>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <%=rs.getString("subject_id")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("subject_name")%>
                                                </td>
                                                <td>
                                                    <%=rs.getString("department")%>
                                                </td>
                                                <td class="center">
                                                    <%=rs.getString("year")%>
                                                </td>
                                                <td class="center">
                                                    <%=rs.getString("staff_id")%>
                                                </td>
                                                <td>

                                                    <a href="assign_subject.jsp?id=<%=rs.getString("subject_id")%>&subject=<%=rs.getString("subject_name")%>" title="Assign Staff"><i class="menu-icon icon-edit"></i></a>

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

        <div class="modal fade" id="studentModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Add Subject Details</h4>
                    </div>
                    <form action="AddSubject">
                        <div class="modal-body">
                          
                            <div class="form-control">
                                <label for="subject_name" class="col-form-label">Subject Name:</label>
                                <input type="text" required="" class="controls row-fluid" id="subject_name" name="subject_name">
                            </div>
                           
                            <div class="form-control">
                                <label for="department" class="col-form-label">Department</label>
                                <select name="department" id="department" class="controls row-fluid" required>
                                    <option value="">Select Department</option>
                                    <option value="Computer Science and Engineering"> Computer Science and Engineering</option>
                                    <option value="Electronics and Telecommunication"> E&TC Engineering</option>
                                    <option value="Civil Engineering"> Civil Engineering</option>
                                    <option value="Mechanical Engineering"> Mechanical Engineering</option>
                                </select>

                            </div>
                            <div class="form-control">
                                <label for="year" class="col-form-label">Year</label>
                                <select name="year" id="year" class="controls row-fluid" required>
                                    <option value="">Select Year</option>
                                    <option value="First Year"> First Year</option>
                                    <option value="Second Year"> Second Year</option>
                                    <option value="Third Year"> Third Year</option>
                                    <option value="Last Year"> Last Year</option>
                                </select>

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

    </body>
