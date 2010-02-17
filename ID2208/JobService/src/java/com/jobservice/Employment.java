/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobservice;

import com.jobservice.util.FileSystem;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author wangyu
 */
@WebService()
public class Employment {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getEmploymentRecord")
    public String getEmploymentRecord(@WebParam(name = "name")
    String name) {
        return FileSystem.readFile("employment\\"+name+".xml");
    }

}
