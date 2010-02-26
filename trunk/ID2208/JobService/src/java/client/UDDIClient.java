/**
 * Created on Feb 26, 2010, 6:15:29 PM
 * Author: wangyu
 */
package client;

import com.jobservice.Company;
import com.jobservice.util.FileSystem;
import com.jobservice.util.UDDI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.registry.JAXRException;

public class UDDIClient {

    public static void main(String[] args) {
        try {
            UDDI.register(FileSystem.getProperties("company\\company.properties"));
            UDDI.register(FileSystem.getProperties("employment\\employment.properties"));
            UDDI.register(FileSystem.getProperties("job\\job.properties"));
            UDDI.register(FileSystem.getProperties("jobs\\jobs.properties"));
            UDDI.register(FileSystem.getProperties("university\\university.properties"));
        } catch (JAXRException ex) {
            Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
