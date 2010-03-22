/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinebookstore.service;

import com.onlinebookstore.schema.Store;
import com.onlinebookstore.schema.User;
import com.onlinebookstore.util.FileSystem;
import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author wangyu
 */
@WebService()
public class Authorization {

    @WebMethod(operationName = "authorize")
    public User authorize(@WebParam(name = "id") String id, @WebParam(name = "password") String password) {
        System.out.println("Authorize " + id);
        try {
            JAXBContext jc = JAXBContext.newInstance("com.onlinebookstore.schema");
            Store store = (Store) jc.createUnmarshaller().unmarshal(new File(FileSystem.SERVER_PATH + "src\\xml\\onlinebookstore.xml"));
            Iterator<User> i = store.getUsers().getUser().iterator();
            while (i.hasNext()) {
                User u = i.next();
                if (u.getId().equals(id) && u.getPassword().equals(password)) {
                    return u;
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        User u = new User();
        u.setId("");
        return u;
    }
}
