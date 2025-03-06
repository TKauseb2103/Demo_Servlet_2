<%-- 
    Document   : available_books
    Created on : Feb 28, 2025, 1:14:00?PM
    Author     : kietluong
--%>
<%@page import="Database.Database"%>
<%@page import="java.util.List"%>
<%@page import="Model.Book"%>
<jsp:useBean id="bookBean" class="Model.Book" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Available Books</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                background-color: #f8f9fa;
            }
            h2 {
                color: #007BFF;
            }
            table {
                width: 80%;
                margin: auto;
                border-collapse: collapse;
                background: white;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ddd;
            }
            th {
                background: #007BFF;
                color: white;
            }
            tr:nth-child(even) {
                background: #f2f2f2;
            }
        </style>
    </head>
    <body>

        <h2>Available Books</h2>

        <table>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Publication Date</th>
                <th>Quantity</th>
            </tr>
            <%
                List<Book> books = Database.viewAvailableBooks();
                for (Book book : books) {
            %>
            <tr>
                <td><%= book.getId()%></td>
                <td><%= book.getTitle()%></td>
                <td><%= book.getAuthor()%></td>
                <td><%= book.getPublicationDate()%></td>
                <td><%= book.getQuantity()%></td>
            </tr>
            <%
                }
            %>
        </table>

    </body>
</html>


