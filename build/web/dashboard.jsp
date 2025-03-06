<%-- 
    Document   : dashboard.jsp
    Created on : Feb 28, 2025, 1:41:09 PM
    Author     : kietluong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="Model.User" scope="session" />

<!DOCTYPE html>
<html>
    <head>
        <title>User Dashboard</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                background-color: #f8f9fa;
            }
            .container {
                width: 60%;
                margin: 20px auto;
                padding: 20px;
                background: white;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
            .button {
                display: block;
                width: 200px;
                margin: 10px auto;
                padding: 10px;
                text-decoration: none;
                color: white;
                background-color: #007BFF;
                border-radius: 5px;
                text-align: center;
            }
            .button:hover {
                background-color: #0056b3;
            }
            .logout {
                background-color: red;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>Welcome to nehh, ${user.username}!</h2> <!-- Sử dụng EL để hiển thị username -->
            <p>Select an action:</p>

            <a href="borrowBook.jsp" class="button">Borrow a Book</a>
            <a href="LoanBookServlet" class="button">Manage Loans</a>
            <a href="LogoutServlet" class="button logout">Logout</a>
        </div>

    </body>
</html>

