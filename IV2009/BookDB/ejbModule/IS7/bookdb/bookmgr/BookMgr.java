package IS7.bookdb.bookmgr;

import java.util.Vector;

import IS7.bookdb.Book;

public interface BookMgr
{
    // Returns a vector of Books containing all the books
    public Vector<Book> getAllBooks() throws Exception;

    // Returns a vector of Books containing all the books about this subject, empty Vector if nothing found
    public Vector<Book> getBooksBySubject(String subject) throws Exception;

    // Returns the Book for the given booknr, or null
    public Book getBook(int booknr) throws Exception;
}
