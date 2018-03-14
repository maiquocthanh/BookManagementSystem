package bookmanagement;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BookModel;

public class BookController {

    private static final String TABLE_BOOKS = "books";

    private static final String COLUMN_ID = "ID";

    private static final String COLUMN_TITLE = "Title";

    private static final String COLUMN_AUTHOR = "Author";

    private static final String COLUMN_PUBLISHER = "Publisher";

    private static final String COLUMN_PRICE = "Price";

    private Connection connection;
    List<BookModel> books;

    public BookController() {
        if (connection == null) {

            try {
                connection = (Connection) ConnectUtils.getConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addBooks(BookModel bookModel) throws SQLException {
        String sql = "INSERT INTO " + TABLE_BOOKS + " values(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bookModel.getId());
        ps.setString(2, bookModel.getTitle());
        ps.setString(3, bookModel.getAuthor());
        ps.setString(4, bookModel.getPublisher());
        ps.setFloat(5, bookModel.getPrice());
        ps.executeUpdate();
        ps.close();

    }

    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_BOOKS + " WHERE " + COLUMN_ID + " = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();

    }

    public void updateBook(BookModel bookModel) throws SQLException {
        String sql = "UPDATE " + TABLE_BOOKS + " SET "
                + COLUMN_TITLE + " = ?, "
                + COLUMN_AUTHOR + " = ?, "
                + COLUMN_PUBLISHER + " = ?, "
                + COLUMN_PRICE + " = ? WHERE "
                + COLUMN_ID + " = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, bookModel.getTitle());
        ps.setString(2, bookModel.getAuthor());
        ps.setString(3, bookModel.getPublisher());
        ps.setFloat(4, bookModel.getPrice());
        ps.setInt(5, bookModel.getId());
        ps.executeUpdate();
        ps.close();

    }

    public List<BookModel> getAllBook() throws SQLException {
        books = new ArrayList<>();
        String sql = "SELECT *  From " + TABLE_BOOKS;
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            BookModel book = new BookModel();
            book.setId(rs.getInt(1));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            book.setPublisher(rs.getString(4));
            book.setPrice(rs.getFloat(5));
            books.add(book);

        }
        rs.close();
        return books;

    }

    public List<BookModel> searchBook(String keyword) throws SQLException {
        List<BookModel> books = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_BOOKS
                + " WHERE "
                + COLUMN_TITLE + " LIKE '%" + keyword + "%'"
                + " OR " + COLUMN_AUTHOR + " LIKE '%" + keyword + "%'"
                + " OR " + COLUMN_PUBLISHER + " LIKE '%" + keyword + "%'"
                + " OR " + COLUMN_ID + " LIKE '%" + keyword + "%'";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            BookModel book = new BookModel();
            book.setId(rs.getInt(1));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            book.setPublisher(rs.getString(4));
            book.setPrice(rs.getFloat(5));
            books.add(book);
        }
        rs.close();
        return books;
    }

    public BookModel getBookByID(int id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_BOOKS + " WHERE " + COLUMN_ID + " = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        BookModel book = new BookModel();
        while (rs.next()) {
            book.setId(rs.getInt(1));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            book.setPublisher(rs.getString(4));
            book.setPrice((int) rs.getFloat(5));
            break;
        }
        rs.close();
        return book;
    }

}
