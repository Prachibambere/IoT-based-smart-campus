<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="pack.Helper"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="pack.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%
    String transaction_id = request.getParameter("id");
    String sql = "SELECT * FROM tbl_book_transactions WHERE transaction_id='" + transaction_id + "'";
    Connection con = DBConnection.getConn();
    Statement st = con.createStatement();
    String startDate = "";
    ResultSet rs = st.executeQuery(sql);
    if (rs.next()) {
        startDate = rs.getString("date_time");
    }

    double charges = 0.0;
    long days = Helper.diffInDays(startDate);
    if (days > Helper.MINIMUM_DAYS) {
        long diff = days - Helper.MINIMUM_DAYS;
        charges = diff * Helper.CHARGES_PER_DAY;
    }

    String dateStop = "";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    dateStop = format.format(cal.getTime());

    sql = "UPDATE tbl_book_transactions SET due_charges='" + charges + "', transaction_status=1, return_date='" + dateStop + "' WHERE transaction_id='" + transaction_id + "'";

    int row_affected = st.executeUpdate(sql);
    if (row_affected > 0) {
        out.println("<script>");
        out.println("alert('Book Status Updated!')");
        out.println("location='view_book_transactions.jsp'");
        out.println("</script>");
    } else {
        out.println("<script>");
        out.println("alert('Book Status Not Updated!')");
        out.println("location='view_book_transactions.jsp'");
        out.println("</script>");
    }

%>