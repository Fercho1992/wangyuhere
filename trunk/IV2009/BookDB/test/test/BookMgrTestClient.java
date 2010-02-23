package test;

import javax.naming.Context;
import javax.naming.InitialContext;

import IS7.bookdb.Book;
import IS7.bookdb.bookmgr.BookMgr;

import java.util.Vector;
import java.util.Scanner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BookMgrTestClient
{

    public static void main(String[] args)
    {
        try
        {
            Context ctx = new InitialContext();
            ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
            ctx.addToEnvironment("java.naming.factory.url.pkgs",
                    "org.jboss.naming:org.jnp.interfaces");
            Logger.getRootLogger().setLevel(Level.OFF);

            BookMgr bookMgr = (BookMgr) ctx.lookup("BookMgr/remote");

            Scanner s = new Scanner(System.in);
            boolean stay = true;
            while (stay)
            {
                System.out.println("Choose one of the following options:");
                System.out.println("------------------------------------");
                System.out.println("1. Show all books!");
                System.out.println("2. Show books about a specific subject!");
                System.out.println("3. Show a specific book (by specifying a booknr)!");
                System.out.println("4. Exit!");
                System.out.println("------------------------------------");
                System.out.print("Enter your choice: ");
                switch (new Integer(s.nextLine()))
                {
                    case 1:
                        printBookList(bookMgr.getAllBooks());
                        break;
                    case 2:
                        System.out.print("Enter a subject: ");
                        String subject = s.nextLine();
                        printBookList(bookMgr.getBooksBySubject(subject));
                        break;
                    case 3:
                        System.out.print("Enter a booknr: ");
                        int booknr = new Integer(s.nextLine());
                        printHeader();
                        printBook(bookMgr.getBook(booknr));
                        break;
                    case 4:
                        stay = false;
                        break;
                }// end of switch
            }// end of while
        } // end of try block
        catch (Exception ex)
        {
            System.err.println("Caught an unexpected exception :" + ex);
            ex.printStackTrace();
        }// end of catch
    }// end of main

    private static void printHeader()
    {
        System.out.println("Booknr  Title                           Price  Year");
        System.out.println("---------------------------------------------------");
    }

    private static void printBook(Book book)
    {
        if (book != null)
        {
            System.out.print(book.getBooknr() + "\t");
            System.out.print(book.getTitle() + "\t");
            System.out.print(book.getPrice() + "\t");
            System.out.println(book.getYear());
        }
    }

    private static void printBookList(Vector<Book> books)
    {
        printHeader();
        for (Book b : books)
        {
            printBook(b);
        }
    }
}