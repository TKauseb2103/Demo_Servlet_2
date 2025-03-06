/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Database.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kietluong
 */
public class Book {

    private int id;
    private String title;
    private String author;
    private Date publicationDate;
    private int quantity;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to borrow a book
    public boolean borrowBook(int bookId, int userId) {
        try (Connection con = Database.getConnect(); PreparedStatement ps = con.prepareStatement("UPDATE Books SET quantity = quantity - 1 WHERE id = ? AND quantity > 0")) {
            ps.setInt(1, bookId);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                PreparedStatement loanPs = con.prepareStatement("INSERT INTO Loans (book_id, borrower_id, loan_date) VALUES (?, ?, NOW())");
                loanPs.setInt(1, bookId);
                loanPs.setInt(2, userId);
                loanPs.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to view available books
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
}
