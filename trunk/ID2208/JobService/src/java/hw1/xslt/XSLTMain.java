package hw1.xslt;

import java.io.FileOutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TransformerFactory f = TransformerFactory.newInstance();
			Source s = new StreamSource("./xml/ApplicantProfile.xsl");
			Transformer t = f.newTransformer(s);
			t.transform(new StreamSource("./xml/ApplicantProfile.xml"), new StreamResult(new FileOutputStream("./xml/ApplicantProfile.html")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
