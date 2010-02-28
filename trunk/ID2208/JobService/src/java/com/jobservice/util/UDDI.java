/**
 * Created on Feb 26, 2010, 4:00:14 PM
 * Author: wangyu
 */

package com.jobservice.util;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FindQualifier;
import javax.xml.registry.JAXRException;
import javax.xml.registry.JAXRResponse;
import javax.xml.registry.LifeCycleManager;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.EmailAddress;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.PersonName;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.SpecificationLink;
import javax.xml.registry.infomodel.TelephoneNumber;
import javax.xml.registry.infomodel.User;

public class UDDI {

    public static void register(Properties p) throws JAXRException {
        if(isServiceRegistered(p)) {
            return;
        }
        // ------------------- Set Connection properties -------------------
            Properties connProps = new Properties();
            connProps.setProperty("javax.xml.registry.queryManagerURL", p.getProperty("query.url"));
            connProps.setProperty("javax.xml.registry.lifeCycleManagerURL", p.getProperty("publish.url"));
            connProps.setProperty("javax.xml.registry.factoryClass", "com.sun.xml.registry.uddi.ConnectionFactoryImpl");
            // ------------------- Set Connection Factory -------------------
            ConnectionFactory factory = ConnectionFactory.newInstance();
            factory.setProperties(connProps);
            Connection conn = factory.createConnection();
            // ---------------- Getting Registry service Object ---------------
            RegistryService rs = conn.getRegistryService();
            BusinessQueryManager bqm = rs.getBusinessQueryManager();
            BusinessLifeCycleManager blcm = rs.getBusinessLifeCycleManager();
            String username = p.getProperty("user.name");
            String password = p.getProperty("user.password");
            // Get authorization from the registry
            PasswordAuthentication passwdAuth =
                    new PasswordAuthentication(username, password.toCharArray());
            HashSet<PasswordAuthentication> creds =
                    new HashSet<PasswordAuthentication>();
            creds.add(passwdAuth);
            conn.setCredentials(creds);


            //------- Define Name and Description of Organization
            InternationalString s = blcm.createInternationalString(p.getProperty("org.name"));
            Organization org = blcm.createOrganization(s);
            s = blcm.createInternationalString(p.getProperty("org.description"));
            org.setDescription(s);
            //------- Create primary contact, set name
            User primaryContact = blcm.createUser();
            PersonName pName = blcm.createPersonName(p.getProperty("org.contact.name"));
            primaryContact.setPersonName(pName);
            //-------- Set primary contact phone number
            TelephoneNumber tNum = blcm.createTelephoneNumber();
            tNum.setNumber(p.getProperty("org.contact.phone"));
            Collection<TelephoneNumber> phoneNums = new ArrayList<TelephoneNumber>();
            phoneNums.add(tNum);
            primaryContact.setTelephoneNumbers(phoneNums);
            //------ Set primary contact email address
            EmailAddress emailAddress = blcm.createEmailAddress(p.getProperty("org.email"));
            Collection<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
            emailAddresses.add(emailAddress);
            primaryContact.setEmailAddresses(emailAddresses);
            // Set primary contact for organization
            org.setPrimaryContact(primaryContact);

            // Set classification scheme (Taxonomy) to NAICS
            ClassificationScheme cScheme =
                    bqm.findClassificationSchemeByName(null, p.getProperty("class.naics"));
            // Create and add classification
            InternationalString sn =
                    blcm.createInternationalString(p.getProperty("class.sn"));
            String sv = p.getProperty("class.sv");
            Classification classification =
                    blcm.createClassification(cScheme, sn, sv);
            Collection<Classification> classifications = new ArrayList<Classification>();
            classifications.add(classification);
            // Set organization's classification
            org.addClassifications(classifications);

            Collection<Service> services = new ArrayList<Service>();
            InternationalString istr = blcm.createInternationalString(p.getProperty("service.name"));
            Service service = blcm.createService(istr);
            istr = blcm.createInternationalString(p.getProperty("service.description"));
            service.setDescription(istr);


            // Create service bindings
            Collection<ServiceBinding> serviceBindings = new ArrayList<ServiceBinding>();
            ServiceBinding binding = blcm.createServiceBinding();
            istr = blcm.createInternationalString(p.getProperty("binding.description"));
            binding.setDescription(istr);
            // allow us to publish a fictitious URI without an error
            binding.setValidateURI(false);
            binding.setAccessURI(p.getProperty("binding.uri"));
            serviceBindings.add(binding);
            // Add service bindings to service
            service.addServiceBindings(serviceBindings);
            // Add service to services, then add services to organization
            services.add(service);

            org.addService(service);


            // Publishing Business Entity
            Collection<Organization> orgs = new ArrayList<Organization>();
            orgs.add(org);
            BulkResponse response;
            response = blcm.saveOrganizations(orgs);
            if (response.getStatus() == JAXRResponse.STATUS_SUCCESS) {
                Collection keys = response.getCollection();
                Iterator keyIter = keys.iterator();
                while (keyIter.hasNext()) {
                    Key orgKey = (Key) keyIter.next();
                    String id = orgKey.getId();
                    System.out.println("Organization key is " + id);
                }
            }

            //--------- Create Concept and as an External Link ----------------
            Concept specConcept;
            specConcept = blcm.createConcept(null, p.getProperty("concept.name"), "");
            InternationalString s1 = blcm.createInternationalString(p.getProperty("concept.description"));
            specConcept.setDescription(s1);
            ExternalLink wsdlLink = blcm.createExternalLink(p.getProperty("concept.wsdl.link"), p.getProperty("concept.wsdl.name"));
            specConcept.addExternalLink(wsdlLink);


            /*--- Find the uddi-org:types classification scheme define by
            the UDDI specification, using well-known key id.*/
            String uuid_types = p.getProperty("udditype");
            ClassificationScheme uddiOrgTypes = (ClassificationScheme) bqm.getRegistryObject(uuid_types, LifeCycleManager.CLASSIFICATION_SCHEME);
            /*---Create a classification, specifying the scheme and the
            taxonomy name and value defined for WSDL documents by the UDDI
            specification.*/
            Classification wsdlSpecClassification =
                    blcm.createClassification(uddiOrgTypes, "wsdlSpec", "wsdlSpec");
            specConcept.addClassification(wsdlSpecClassification);
            // Define classifications
            Collection<Concept> concepts = new ArrayList<Concept>();
            concepts.add(specConcept);
            // Save Concept
            BulkResponse concResponse = blcm.saveConcepts(concepts);
            String conceptKeyId = null;
            Collection concExceptions = concResponse.getExceptions();


            // Retrieve the (assigned ) Key from save concept
            Key concKey = null;
            if (concExceptions == null) {
                System.out.println("WSDL Specification Concept saved");
                Collection<Key> keys = concResponse.getCollection();
                Iterator<Key> keyIter = keys.iterator();
                if (keyIter.hasNext()) {
                    concKey = keyIter.next();
                    conceptKeyId = concKey.getId();
                    System.out.println("Concept key is " + conceptKeyId);
                }
            }
            // Retrieve the concept from Registry
            Concept retSpecConcept =
                    (Concept) bqm.getRegistryObject(conceptKeyId, LifeCycleManager.CONCEPT);

            // Associate concept to Binding object
            SpecificationLink retSpeclLink =
                    blcm.createSpecificationLink();
            retSpeclLink.setSpecificationObject(retSpecConcept);
            binding.addSpecificationLink(retSpeclLink);
    }

    static boolean isServiceRegistered(Properties p) {
        try {
            // ------------------- Set Connection properties -------------------
            Properties connProps = new Properties();
            connProps.setProperty("javax.xml.registry.queryManagerURL", p.getProperty("query.url"));
            connProps.setProperty("javax.xml.registry.lifeCycleManagerURL", p.getProperty("publish.url"));
            connProps.setProperty("javax.xml.registry.factoryClass", "com.sun.xml.registry.uddi.ConnectionFactoryImpl");
            // ------------------- Set Connection Factory -------------------
            ConnectionFactory factory = ConnectionFactory.newInstance();
            factory.setProperties(connProps);
            Connection conn = factory.createConnection();
            // ---------------- Getting Registry service Object ---------------
            RegistryService rs = conn.getRegistryService();
            BusinessQueryManager bqm = rs.getBusinessQueryManager();
            BusinessLifeCycleManager blcm = rs.getBusinessLifeCycleManager();

            // Define find qualifiers and name patterns
            Collection<String> findQualifiers = new ArrayList<String>();
            findQualifiers.add(FindQualifier.SORT_BY_NAME_DESC);
            Collection<String> namePatterns = new ArrayList<String>();
            // qString refers to the organization name we are looking for
            namePatterns.add("%"+p.getProperty("org.name")+"%");
            // Find orgs with names that matches qString
            BulkResponse response = bqm.findOrganizations(findQualifiers, namePatterns, null, null, null, null);

            //Iterate over discovered organizations and collect their service binding
            Collection orgs = response.getCollection();
            Iterator orgIter = orgs.iterator();
            while (orgIter.hasNext()) {
                Organization org = (Organization) orgIter.next();
                Collection services = org.getServices();
                Iterator svcIter = services.iterator();
                while (svcIter.hasNext()) {
                    Service svc = (Service) svcIter.next();
                    Collection serviceBindings = svc.getServiceBindings();
                    Iterator sbIter = serviceBindings.iterator();
                    while (sbIter.hasNext()) {
                        ServiceBinding sb = (ServiceBinding) sbIter.next();
                        if(sb.getAccessURI().toLowerCase().equals(p.getProperty("binding.uri").toLowerCase())) {
                            return true;
                        }
                    }
                }
            }
        } catch (JAXRException ex) {
            Logger.getLogger(UDDI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


}
