/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jobservice;

import com.jobservice.util.FileSystem;
import java.io.File;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import jaxb.jobDescription.JobType;
import jaxb.jobDescription.JobsType;

/**
 *
 * @author wangyu
 */
@WebService()
public class SearchJob {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "search")
    public String search(@WebParam(name = "keyword")
    String keyword) {
        if(keyword == null) keyword = "";
        try {
            JAXBContext jc = JAXBContext.newInstance("jaxb.jobDescription");
            JobsType jobs = ((JAXBElement<JobsType>)jc.createUnmarshaller().unmarshal(new File(FileSystem.SERVER_PATH+"jobs\\JobDescription.xml"))).getValue();
            Iterator<JobType> i = jobs.getJob().iterator();
            while(i.hasNext()) {
                JobType job = i.next();
                if(!job.getName().contains(keyword) && !job.getDescription().contains(keyword)) {
                    i.remove();
                }
            }
            jaxb.jobDescription.ObjectFactory of = new jaxb.jobDescription.ObjectFactory();

            StringWriter writer = new StringWriter();
            Marshaller m = jc.createMarshaller();
			m.setProperty("jaxb.formatted.output", true);
            m.marshal(of.createJobs(jobs), writer);
            
            return writer.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(SearchJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "error";
    }

}
