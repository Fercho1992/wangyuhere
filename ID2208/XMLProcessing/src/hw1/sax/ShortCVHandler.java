package hw1.sax;

import hw1.jaxb.applicantProfile.ApplicantProfileType;
import hw1.jaxb.applicantProfile.ContactInfoType;
import hw1.jaxb.applicantProfile.PersonalInfoType;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ShortCVHandler extends DefaultHandler {

	private PersonalInfoType pit;
	private ContactInfoType cit;
	private ApplicantProfileType apf;
	
	private String current = "";
	
	public ShortCVHandler(ApplicantProfileType apf) {
		pit = apf.getPersonalInformation();
		cit = apf.getContactInformation();
		this.apf = apf;
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		String s = new String(arg0, arg1, arg2).trim();
		if(current.equals("shortCV:age")) {
			pit.setAge(Integer.valueOf(s));
		} else if(current.equals("shortCV:country")) {
			pit.setCountry(s);
		} else if(current.equals("shortCV:hobby")) {
			pit.setHobby(s);
		} else if(current.equals("shortCV:gender")) {
			pit.setGender(s);
		} else if(current.equals("shortCV:personalStatement")) {
			pit.setPersonalStatement(s);
		} else if(current.equals("shortCV:email")) {
			cit.setEmail(s);
		} else if(current.equals("shortCV:phoneNo.")) {
			cit.setPhoneNo(Integer.valueOf(s));
		} 
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {
		current = arg2;
		if(current.equals("shortCV:shortCV")) {
			apf.setName(arg3.getValue("name"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		current = "";
	}
	
	

}
