/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlinebookstore.service;

import com.onlinebookstore.schema.Shipment;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        System.out.println("Shipment from "+source+" to "+destination);
        Shipment shipment = new Shipment();
        shipment.setInvoice(new Date().toString());
        shipment.setCreditcard(creditcard);
        shipment.setDestination(destination);
        shipment.setSource(source);
        shipment.setDate(new Date(new Date().getTime() + 1000*3600*24*5).toString());
        System.out.println("The shipment will be delivered on " + shipment.getDate().toString());
        if(destination.equals("Moon")) {
            System.out.println("The destination is Moon!");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ShipmentService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return shipment;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cancelShipment")
    public String cancelShipment(@WebParam(name = "invoice")
    String invoice) {
        return "The shipment for order " + invoice +" is canceled!";
    }

}
