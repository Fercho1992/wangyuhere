//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.14 at 09:27:50 em CET 
//


package hw1.jaxb.applicantProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for applicantProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="applicantProfileType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/ShortCV}cvType">
 *       &lt;sequence>
 *         &lt;element name="education" type="{http://www.example.org/ApplicantProfile}educationType"/>
 *         &lt;element name="workExperience" type="{http://www.example.org/ApplicantProfile}workExperienceType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applicantProfileType", propOrder = {
    "education",
    "workExperience"
})
public class ApplicantProfileType
    extends CvType
{

    @XmlElement(required = true)
    protected EducationType education;
    @XmlElement(required = true)
    protected WorkExperienceType workExperience;

    /**
     * Gets the value of the education property.
     * 
     * @return
     *     possible object is
     *     {@link EducationType }
     *     
     */
    public EducationType getEducation() {
        return education;
    }

    /**
     * Sets the value of the education property.
     * 
     * @param value
     *     allowed object is
     *     {@link EducationType }
     *     
     */
    public void setEducation(EducationType value) {
        this.education = value;
    }

    /**
     * Gets the value of the workExperience property.
     * 
     * @return
     *     possible object is
     *     {@link WorkExperienceType }
     *     
     */
    public WorkExperienceType getWorkExperience() {
        return workExperience;
    }

    /**
     * Sets the value of the workExperience property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkExperienceType }
     *     
     */
    public void setWorkExperience(WorkExperienceType value) {
        this.workExperience = value;
    }

}