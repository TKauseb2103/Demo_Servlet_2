<%-- 
    Document   : borrowBook
    Created on : Feb 28, 2025, 1:42:12?PM
    Author     : kietluong
--%>
<%@page import="Model.Book"%>
<%@page import="Database.Database"%>
<%@page import="Model.User"%>
<%@page import="java.util.List"%>

<%
//    User user = (User) session.getAttribute("user");
//    if (user == null) {
//        response.sendRedirect("login.jsp");
//        return;
//    }

    List<Book> books = Database.viewAvailableBooks();
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Borrow a Book</title>
        <meta charset="UTF-8">
    </head>
    <body>

        <h2>Borrow a Book</h2>
        <form action="BorrowBookServlet" method="post">
            <label>Select Book:</label>
            <select name="bookId">
                <% for (Book book : books) {%>
                <option value="<%= book.getId()%>"><%= book.getTitle()%> by <%= book.getAuthor()%></option>
                <% }%>
            </select>
            <button type="submit">Borrow</button>
        </form>
        <br>
        <a href="dashboard.jsp">Back to Dashboard</a>

    </body>
</html>

