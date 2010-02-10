//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.11 at 12:00:25 fm CET 
//


package hw1.jaxb.transcripts;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hw1.jaxb.transcripts package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Transcripts_QNAME = new QName("http://www.example.org/Transcripts", "transcripts");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hw1.jaxb.transcripts
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TranscriptsType }
     * 
     */
    public TranscriptsType createTranscriptsType() {
        return new TranscriptsType();
    }

    /**
     * Create an instance of {@link CoursesType }
     * 
     */
    public CoursesType createCoursesType() {
        return new CoursesType();
    }

    /**
     * Create an instance of {@link CourseType }
     * 
     */
    public CourseType createCourseType() {
        return new CourseType();
    }

    /**
     * Create an instance of {@link TranscriptType }
     * 
     */
    public TranscriptType createTranscriptType() {
        return new TranscriptType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TranscriptsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Transcripts", name = "transcripts")
    public JAXBElement<TranscriptsType> createTranscripts(TranscriptsType value) {
        return new JAXBElement<TranscriptsType>(_Transcripts_QNAME, TranscriptsType.class, null, value);
    }

}
