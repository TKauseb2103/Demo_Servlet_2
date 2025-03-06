/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Database.Database;
import Model.Loan;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author kietluong
 */
public class LoanBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoanBookServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoanBookServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp"); // Chưa đăng nhập thì chuyển hướng về trang login
            return;
        }

        int userId = user.getId(); // Lấy userId từ User object

        // Lấy danh sách sách mượn của user từ Database
        List<Loan> userLoans = Database.getUserLoans(userId);

        // Đặt danh sách này vào request attribute
        request.setAttribute("userLoans", userLoans);

        // Chuyển tiếp đến trang JSP mà không làm mất attribute
        request.getRequestDispatcher("manageLoans.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        User user = (User) request.getSession().getAttribute("user");
//
//        if (user == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//
//        int bookId = Integer.parseInt(request.getParameter("bookId"));
//        int borrowerId = user.getId();
//        Date loanDate = new Date(System.currentTimeMillis());
//
//        boolean success = Database.borrowBook(bookId, borrowerId, loanDate);
//
//        if (success) {
//            response.sendRedirect("ManageLoansServlet");
//        } else {
//            request.setAttribute("error", "Failed to borrow book. Please try again.");
//            request.getRequestDispatcher("borrowBook.jsp").forward(request, response);
//        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
