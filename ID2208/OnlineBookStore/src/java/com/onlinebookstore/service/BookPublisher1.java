/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlinebookstore.service;

import com.onlinebookstore.schema.Book;
import com.onlinebookstore.schema.Order;
import com.onlinebookstore.util.FileSystem;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.netbeans.xml.schema.books.Books;

/**
 *
 * @author wangyu
 */
@WebService()
public class BookPublisher1 {
/**
     * Web service operation
     */
    @WebMethod(operationName = "searchBooks1")
    public List<Book> searchBooks1(@WebParam(name = "keyword")
    String keyword) {
        System.out.println("Search book " +keyword);
        List<Book> result = new ArrayList<Book>();
        try {
            JAXBContext jc = JAXBContext.newInstance("org.netbeans.xml.schema.books");
            Books books = (Books) jc.createUnmarshaller().unmarshal(new File(FileSystem.SERVER_PATH + "src\\xml\\books1.xml"));
            Iterator<Book> i = books.getBook().iterator();
            while (i.hasNext()) {
                Book b = i.next();
                if(b.getAuthor().contains(keyword) || b.getTitle().contains(keyword) || b.getIsbn().contains(keyword)) {
                    result.add(b);
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "orderBookPurchase1")
    public Order orderBookPurchase1(@WebParam(name = "title")
    String title, @WebParam(name = "creditcard")
    String creditcard) {
        System.out.println("Order book " + title + " using " + creditcard);
        Order order = new Order();
        order.setCreditcard(creditcard);
        order.setInvoice(new Date().toString());
        order.setLocation("DSV");
        order.setStatus("new");
        try {
           JAXBContext jc = JAXBContext.newInstance("org.netbeans.xml.schema.books");
            Books books = (Books) jc.createUnmarshaller().unmarshal(new File(FileSystem.SERVER_PATH + "src\\xml\\books.xml"));
            Iterator<Book> i = books.getBook().iterator();
            if (i.hasNext()) {
                Book b = i.next();
                if(b.getTitle().contains(title)) {
                    order.setBook(b);
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "purchaseCancel1")
    public String purchaseCancel1(@WebParam(name = "invoice")
    String invoice) {
        return invoice+" was canceled!";
    }
}
