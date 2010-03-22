/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlinebookstore.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author wangyu
 */
@WebService()
public class HelloWorld {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello() {
        //TODO write your implementation code here:
        return "Hello World";
    }

}
