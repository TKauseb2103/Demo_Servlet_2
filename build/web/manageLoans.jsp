<%-- 
    Document   : manageLoans
    Created on : Feb 28, 2025, 1:42:45?PM
    Author     : kietluong
--%>
<%@page import="Database.Database"%>
<%@page import="Model.Loan"%>
<%@page import="Model.User"%>
<%@page import="Model.Book"%>
<%@page import="java.util.List"%>

<%
    // L?y danh sách sách t? request attribute (không ph?i session)
    List<Loan> loans = (List<Loan>) request.getAttribute("userLoans");

    // L?y user ??ng nh?p t? session

%>

<!DOCTYPE html>
<html>
    <head>
        <title>Manage Loans</title>
        <meta charset="UTF-8">
    </head>
    <body>

        <h2>Your Borrowed Books</h2>
        <table border="1">
            <tr>
                <th>Loan</th>
                <th>Book Title</th>
                <th>Borrow Date</th>
                <th>Return Date</th>
                <th>Return</th>
            </tr>
            <% for (Loan loan : loans) {%>
            <tr>
                <td><%= loan.getLoanId()%></td>
                <td><%= loan.getBookId()%></td>
                <td><%= loan.getBorrowDate()%></td>
                <td><%= loan.getReturnDate()%></td>
                <td>
                    <form action="LoanBookServlet" method="post">
                        <input type="hidden" name="bookId" value="<%= loan.getLoanId()%>">
                        <button type="submit">Return</button>
                    </form>
                </td>
            </tr>
            <% }%>
        </table>

        <br>
        <a href="dashboard.jsp">Back to Dashboard</a>

    </body>
</html>


