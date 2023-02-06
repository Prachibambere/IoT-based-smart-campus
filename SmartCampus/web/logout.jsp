<%-- 
    Document   : logout
    Created on : Feb 14, 2019, 1:12:46 PM
    Author     : Dinesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <%
           session.invalidate();
           response.sendRedirect("index.html");
       %>
    </body>
</html>
