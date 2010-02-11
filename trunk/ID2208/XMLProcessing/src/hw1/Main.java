package hw1;

import hw1.jaxb.applicantProfile.ApplicantProfileType;
import hw1.jaxb.applicantProfile.ContactInfoType;
import hw1.jaxb.applicantProfile.CourseType;
import hw1.jaxb.applicantProfile.CoursesType;
import hw1.jaxb.applicantProfile.DegreeType;
import hw1.jaxb.applicantProfile.EducationItemType;
import hw1.jaxb.applicantProfile.EducationType;
import hw1.jaxb.applicantProfile.ObjectFactory;
import hw1.jaxb.applicantProfile.PersonalInfoType;
import hw1.jaxb.applicantProfile.WorkExperienceType;
import hw1.jaxb.applicantProfile.WorkType;
import hw1.jaxb.transcripts.TranscriptsType;
import hw1.sax.ShortCVHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ObjectFactory of = new ObjectFactory();
		ApplicantProfileType apf = of.createApplicantProfileType();

		// using JAXB to parse Transcripts
		EducationType et = of.createEducationType();
		apf.setEducation(et);
		try {
			JAXBContext jc = JAXBContext.newInstance("hw1.jaxb.transcripts");
			TranscriptsType transcripts = ((JAXBElement<TranscriptsType>) jc
					.createUnmarshaller().unmarshal(
							new File("./xml/Transcripts.xml"))).getValue();

			EducationItemType eit = of.createEducationItemType();
			et.getEducationItem().add(eit);
			hw1.jaxb.transcripts.TranscriptType transcript = transcripts
					.getTranscript();
			eit.setUniversity(transcript.getUniversity());
			eit.setDegree(DegreeType.fromValue(transcript.getDegree().value()));

			CoursesType cst = of.createCoursesType();
			eit.setCourses(cst);
			float total = 0;
			float ects = 0;
			for (hw1.jaxb.transcripts.CourseType c : transcript.getCourses()
					.getCourse()) {
				CourseType ct = of.createCourseType();
				ct.setCourseName(c.getCourseName());
				ct.setEct(c.getEct());
				ct.setGrade(c.getGrade());
				cst.getCourse().add(ct);
				int grade = 70 - (int)c.getGrade().charAt(0);
				total += c.getEct().floatValue()*grade;
				ects += c.getEct().floatValue();
			}
			eit.setGpa(total/ects);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		// using JAXB to parse EmploymentRecord.xml
		WorkExperienceType wet = of.createWorkExperienceType();
		apf.setWorkExperience(wet);
		try {
			JAXBContext jc = JAXBContext.newInstance("hw1.jaxb.employmentRecord");
			List<hw1.jaxb.employmentRecord.CompanyType> companies = ((JAXBElement<hw1.jaxb.employmentRecord.RecordType>) jc
					.createUnmarshaller().unmarshal(
							new File("./xml/EmploymentRecord.xml"))).getValue().getCompanies().getCompany();

			for (hw1.jaxb.employmentRecord.CompanyType c : companies) {
				WorkType wt = of.createWorkType();
				wet.getWork().add(wt);
				wt.setLengthofTime(c.getLengthofTime());
				wt.setPositionorTitle(c.getPositionorTitle());
				wt.setName(c.getName());
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		// using DOM to parse CompanyInfo.xml
		try {
			// Get a factory object for DocumentBuilder objects
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// to make the parser a validating parse
			factory.setValidating(true);
			// To parse a XML document with a namespace,
			factory.setNamespaceAware(true);

			// to ignore cosmetic whitespace between elements.
			factory.setIgnoringElementContentWhitespace(true);
			factory.setAttribute(
					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
			// specifies the XML schema document to be used for validation.
			factory.setAttribute(
					"http://java.sun.com/xml/jaxp/properties/schemaSource",
					"CompanyInfo.xsd");

			// Get a DocumentBuilder (parser) object

			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML input file to create a
			// Document object that represents the
			// input XML file.
			// /
			Document document = builder.parse(new File("./xml/CompanyInfo.xml"));

			Hashtable<String, String[]> infos = new Hashtable<String, String[]>();
			Node root = document.getFirstChild();
			for(int i = 0; i < root.getChildNodes().getLength(); i++) {
				Node n = root.getChildNodes().item(i);
				if(n.getNodeName().equals("companyInfo:companyInfo")) {
					String name = n.getAttributes().getNamedItem("name").getTextContent();
					String cal = getNodeValue(n.getChildNodes(), "companyInfo:classification");
					String re = getNodeValue(n.getChildNodes(), "companyInfo:region");
					infos.put(name, new String[] {cal, re});
				}
			}
			
			for(WorkType w : wet.getWork()) {
				if(infos.containsKey(w.getName())) {
					w.setClassification(infos.get(w.getName())[0]);
					w.setRegion(infos.get(w.getName())[1]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		// using SAX to parse ShortCV.xml
		PersonalInfoType pit = of.createPersonalInfoType();
		ContactInfoType cit = of.createContactInfoType();
		apf.setPersonalInformation(pit);
		apf.setContactInformation(cit);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxp;
			saxp = factory.newSAXParser();
			ShortCVHandler handler = new ShortCVHandler(apf);
			saxp.parse("./xml/ShortCV.xml", handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			JAXBElement<ApplicantProfileType> xml = of.createApplicantProfile(apf);
			JAXBContext jc = JAXBContext.newInstance( "hw1.jaxb.applicantProfile" );
			Marshaller m = jc.createMarshaller();
			m.setProperty("jaxb.formatted.output", true);
	        m.marshal(xml, new BufferedWriter(new FileWriter("./xml/ApplicantProfile.xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// using XSLT to transform the xml
		try {
			TransformerFactory f = TransformerFactory.newInstance();
			Source s = new StreamSource("./xml/ApplicantProfile.xsl");
			Transformer t = f.newTransformer(s);
			t.transform(new StreamSource("./xml/ApplicantProfile.xml"), new StreamResult(new FileOutputStream("./xml/ApplicantProfile.html")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Process OK...");
        
	}
	
	static Node findNode(NodeList nodes, String nodeName)
	 {
		 for(int i=0; i<nodes.getLength(); i++)
		 {
			 if(nodes.item(i).getNodeName().equals(nodeName))
			 {
				 return nodes.item(i);
			 }
		 }
		 return null;
	 }
	 
	 static String getNodeValue(NodeList nodes, String nodeName)
	 {
		 Node result = findNode(nodes, nodeName);
		 if(result != null)
		 {
			 return result.getFirstChild().getNodeValue();
		 }
		 return null;
	 }

}
