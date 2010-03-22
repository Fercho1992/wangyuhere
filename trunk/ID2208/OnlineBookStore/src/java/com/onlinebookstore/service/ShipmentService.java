/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlinebookstore.service;

import com.onlinebookstore.schema.Shipment;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author wangyu
 */
@WebService()
public class ShipmentService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "orderShipment")
    public Shipment orderShipment(@WebParam(name = "weight")
    String weight, @WebParam(name = "source")
    String source, @WebParam(name = "destination")
    String destination, @WebParam(name = "creditcard")
    String creditcard) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cancelShipment")
    public String cancelShipment(@WebParam(name = "invoice")
    String invoice) {
        //TODO write your implementation code here:
        return null;
    }

}
