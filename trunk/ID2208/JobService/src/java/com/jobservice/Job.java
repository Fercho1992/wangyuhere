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
public class Job {

    University university = new University();
    Employment employment = new Employment();
    Company company = new Company();

    /**
     * Web service operation
     * @param name
     */
    @WebMethod(operationName = "getApplicantProfile")
    public String getApplicantProfile(@WebParam(name = "shortCV")
    String shortCV, @WebParam(name = "name")
    String name) {

        String profile = "job\\ApplicantProfile.xml";
        String trans = "job\\Transcripts.xml";
        String record = "job\\EmploymentRecord.xml";
        String info = "job\\CompanyInfo.xml";
        String cv = "job\\ShortCV.xml";

        FileSystem.writeFile(trans, university.getTranscripts(name));
        FileSystem.writeFile(record, employment.getEmploymentRecord(name));
        FileSystem.writeFile(info, company.getCompanyInfo());
        FileSystem.writeFile(cv, shortCV);

        FileSystem.process(profile, trans, record, info, cv);

        return FileSystem.readFile(profile);
    }

}
