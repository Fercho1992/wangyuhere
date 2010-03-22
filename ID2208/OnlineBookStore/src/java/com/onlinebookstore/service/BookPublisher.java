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
public class BookPublisher {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "searchBooks")
    public List<Book> searchBooks(@WebParam(name = "keyword")
    String keyword) {
        List<Book> result = new ArrayList<Book>();
        try {
           JAXBContext jc = JAXBContext.newInstance("org.netbeans.xml.schema.books");
            Books books = (Books) jc.createUnmarshaller().unmarshal(new File(FileSystem.SERVER_PATH + "src\\xml\\books.xml"));
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
        if(result.isEmpty()) {
            Book b = new Book();
            b.setTitle("");
            b.setAuthor("");
            b.setIsbn("");
            b.setPrice(Double.MAX_VALUE);
            result.add(b);
        }
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "orderBookPurchase")
    public Order orderBookPurchase(@WebParam(name = "title")
    String title, @WebParam(name = "creditcard")
    String creditcard){
        System.out.println("Order book " + title + " using " + creditcard);
        Order order = new Order();
        order.setCreditcard(creditcard);
        order.setInvoice(new Date().toString());
        order.setLocation("KTH");
        order.setStatus("new");
        try {
           JAXBContext jc = JAXBContext.newInstance("org.netbeans.xml.schema.books");
            Books books = (Books) jc.createUnmarshaller().unmarshal(new File(FileSystem.SERVER_PATH + "src\\xml\\books.xml"));
            Iterator<Book> i = books.getBook().iterator();
            while (i.hasNext()) {
                Book b = i.next();
                if(b.getTitle().contains(title)) {
                    order.setBook(b);
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(creditcard.equals("Invalid")) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BookPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return order;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "purchaseCancel")
    public String purchaseCancel(@WebParam(name = "invoice")
    String invoice) {
        return invoice+" was canceled!";
    }

}
