//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.10 at 11:33:50 em CET 
//


package hw1.jaxb.applicantProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for workExperienceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workExperienceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="work" type="{http://www.example.org/ApplicantProfile}workType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workExperienceType", propOrder = {
    "work"
})
public class WorkExperienceType {

    @XmlElement(namespace = "http://www.example.org/ApplicantProfile", required = true)
    protected WorkType work;

    /**
     * Gets the value of the work property.
     * 
     * @return
     *     possible object is
     *     {@link WorkType }
     *     
     */
    public WorkType getWork() {
        return work;
    }

    /**
     * Sets the value of the work property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkType }
     *     
     */
    public void setWork(WorkType value) {
        this.work = value;
    }

}
