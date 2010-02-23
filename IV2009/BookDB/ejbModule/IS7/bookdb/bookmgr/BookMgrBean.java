package IS7.bookdb.bookmgr;

import IS7.bookdb.Book;

import java.util.Vector;

import java.sql.*;
import javax.sql.DataSource;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.annotation.Resource;

@Remote(BookMgr.class)
@Stateless(name = "BookMgr")
public class BookMgrBean implements BookMgr
{
    // Declare database connection

    @Resource(mappedName = "java:/jdbc/BookDB")
    private DataSource mDataSource;

    public Vector<Book> getAllBooks() throws Exception
    {
        try
        {
            Vector<Book> books = new Vector<Book>();

            Connection con = mDataSource.getConnection();
            String query = "SELECT * FROM Book";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Book newbook = new Book(rs.getInt("booknr"), rs.getString("title"), rs.getInt("price"), rs.getInt("year"));
                books.add(newbook);
            }
            stmt.close();
            con.close();
            return books;
        }
        catch (SQLException ex)
        {
            // Throw an error that the client can catch
            throw new Exception("getAllBooks() - DB Error: " + ex.getMessage());
        }
    }

    public Vector<Book> getBooksBySubject(String subject) throws Exception
    {
        try
        {
            Vector<Book> books = new Vector<Book>();

            Connection con = mDataSource.getConnection();
            String query = "SELECT * FROM Book WHERE booknr IN (SELECT booknr FROM BookSubject bs, Subject s WHERE s.subjectnr = bs.subjectnr AND s.subject = ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, subject);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Book newbook = new Book(rs.getInt("booknr"), rs.getString("title"), rs.getInt("price"), rs.getInt("year"));
                books.add(newbook);
            }

            stmt.close();
            con.close();
            return books;
        }
        catch (SQLException ex)
        {
            // Throw an error that the client can catch
            throw new Exception("getBooksBySubject() - DB Error: " + ex.getMessage());
        }
    }

    public Book getBook(int booknr) throws Exception
    {
        try
        {
            Connection con = mDataSource.getConnection();
            String query = "SELECT * FROM Book WHERE booknr = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, booknr);
            ResultSet rs = stmt.executeQuery();

            Book thebook = null;

            if (rs.next())
            {
                thebook = new Book(rs.getInt("booknr"), rs.getString("title"),
                        rs.getInt("price"), rs.getInt("year"));
            }
            stmt.close();
            con.close();
            return thebook;
        }
        catch (SQLException ex)
        {
            // Throw an error that the client can catch
            throw new Exception("getBook() - DB Error: " + ex.getMessage());
        }
    }
}
