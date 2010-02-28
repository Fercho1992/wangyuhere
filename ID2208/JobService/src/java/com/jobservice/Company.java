/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobservice;

import com.jobservice.util.FileSystem;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author wangyu
 */
@WebService()
public class Company {

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "getCompanyInfo")
    public String getCompanyInfo() {
        return FileSystem.readFile("company\\CompanyInfo.xml");
    }

}
