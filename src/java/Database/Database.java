/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.Book;
import Model.Loan;
import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

/**
 *
 * @author kietluong
 */
public class Database implements DatabaseInfo {

    // Kết nối đến Database
    public static Connection getConnect() {
        try {
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver" + e);
        }
        try {
            Connection con = DriverManager.getConnection(DBURL, USERDB, PASSDB);
            return con;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public static User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy user
    }

    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM Users"; // ✅ Truy vấn lấy toàn bộ dữ liệu từ bảng Users

        try (Connection con = getConnect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static boolean login(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection con = getConnect(); PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có dữ liệu => đăng nhập thành công
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Đăng nhập thất bại
    }

    public static List<Book> viewAvailableBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection con = Database.getConnect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Books WHERE quantity > 0")) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublicationDate(rs.getDate("publication_date"));
                book.setQuantity(rs.getInt("quantity"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Loan> getUserLoans(int userId) {
        List<Loan> loans = new ArrayList<>();
        try (Connection conn = getConnect()) {
            String sql = "SELECT id, book_id, borrower_id, loan_date, return_date FROM Loans WHERE borrower_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getInt("id"),
                        rs.getInt("borrower_id"), // Đúng với cột trong DB
                        rs.getInt("book_id"),
                        rs.getDate("loan_date"), // Đúng với cột trong DB
                        rs.getDate("return_date") // Đúng với cột trong DB
                );
                loans.add(loan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loans;
    }

    public static boolean borrowBook(int bookId, int borrowerId, Date loanDate, Date returnDate) {
        String sql = "INSERT INTO Loans (book_id, borrower_id, loan_date, return_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.setInt(2, borrowerId);
            ps.setDate(3, loanDate);
            ps.setDate(4, returnDate);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    
//    public boolean validateUser(String username, String password) {
//        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
//        try (Connection con = Database.getConnect();
//             PreparedStatement ps = con.prepareStatement(query)) {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public static List<UserBean> getAllUsers() {
//        List<UserBean> userList = new ArrayList<>();
//        String query = "SELECT * FROM Users";
//        try (Connection con = Database.getConnect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                UserBean user = new UserBean();
//                user.username = rs.getString("username");
//                user.password = rs.getString("password");
//                userList.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return userList;
//    }
    // Lấy thông tin Customer theo ID
//    public static Customer getCustomer(int id) {
//        Customer c = null;
//        try (Connection con = getConnect()) {
//            PreparedStatement stmt = con.prepareStatement(
//                    "SELECT FirstName, LastName, Email, Address, Phone, RegistrationDate FROM Customers WHERE CustomerID=?"
//            );
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                String firstName = rs.getString("FirstName");
//                String lastName = rs.getString("LastName");
//                String email = rs.getString("Email");
//                String address = rs.getString("Address");
//                String phone = rs.getString("Phone");
//                Date registrationDate = rs.getDate("RegistrationDate");
//
//                c = new Customer(id, firstName, lastName, email, "", address, phone, registrationDate);
//            }
//            con.close();
//        } catch (Exception ex) {
//            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return c;
//    }
//
//    // Đăng ký tài khoản mới
//    public static int registerCustomer(String firstName, String lastName, String email, String password, String address, String phone, Date registrationDate) {
//        int id = -1;
//        String sql = "INSERT INTO Customers (FirstName, LastName, Email, Password, Address, Phone, RegistrationDate) "
//                + "OUTPUT INSERTED.CustomerID VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection con = getConnect(); PreparedStatement stmt = con.prepareStatement(sql)) {
//
//            stmt.setString(1, firstName);
//            stmt.setString(2, lastName);
//            stmt.setString(3, email);
//            stmt.setString(4, password);
//            stmt.setString(5, address);
//            stmt.setString(6, phone);
//            stmt.setDate(7, new java.sql.Date(registrationDate.getTime()));
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    id = rs.getInt(1);
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return id;
//    }
//
//    // Cập nhật thông tin Customer
//    public static Customer update(Customer c) {
//        try (Connection con = getConnect()) {
//            PreparedStatement stmt = con.prepareStatement(
//                    "UPDATE Customers SET FirstName=?, LastName=?, Email=?, Address=?, Phone=? WHERE CustomerID=?"
//            );
//            stmt.setString(1, c.getFirstName());
//            stmt.setString(2, c.getLastName());
//            stmt.setString(3, c.getEmail());
//            stmt.setString(4, c.getAddress());
//            stmt.setString(5, c.getPhone());
//            stmt.setInt(6, c.getCustomerID());
//
//            int rc = stmt.executeUpdate();
//            con.close();
//            return rc > 0 ? c : null;
//        } catch (Exception ex) {
//            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException("Invalid data");
//        }
//    }
//
//    // Xóa Customer theo ID
//    public static int delete(int id) {
//        try (Connection con = getConnect()) {
//            PreparedStatement stmt = con.prepareStatement("DELETE FROM Customers WHERE CustomerID=?");
//            stmt.setInt(1, id);
//            int rc = stmt.executeUpdate();
//            con.close();
//            return rc;
//        } catch (Exception ex) {
//            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return 0;
//    }
//
//    // Lấy danh sách tất cả Customer
//    public static ArrayList<Customer> listAll() {
//        ArrayList<Customer> list = new ArrayList<Customer>();
//        try (Connection con = getConnect()) {
//            PreparedStatement stmt = con.prepareStatement(
//                    "SELECT * FROM Customers c LEFT JOIN Orders o ON c.CustomerID = o.CustomerID WHERE o.OrderID IS NULL");
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                list.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8)));
//            }
//            con.close();
//            return list;
//        } catch (Exception ex) {
//            Logger.getLogger(FruitDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    // Kiểm tra đăng nhập
//    public static boolean login(String email, String password) {
//        boolean isValid = false;
//        try (Connection con = getConnect()) {
//            PreparedStatement stmt = con.prepareStatement("SELECT Password FROM Customers WHERE Email=?");
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                String storedPassword = rs.getString("Password");
//                if (storedPassword.equals(password)) {
//                    isValid = true;
//                }
//            }
//            con.close();
//        } catch (Exception ex) {
//            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return isValid;
//    }
    // Main để test
    public static void main(String[] args) {
        List<User> users = getAllUsers();
        for (User user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("-------------------------");
        }
    }

}
