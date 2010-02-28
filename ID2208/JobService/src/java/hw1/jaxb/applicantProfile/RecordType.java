//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.14 at 09:27:50 em CET 
//


package hw1.jaxb.applicantProfile;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recordType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recordType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="companies" type="{http://www.example.org/EmploymentRecord}companiesType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="currentEmployment" type="{http://www.w3.org/2001/XMLSchema}string" default="NO" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordType", namespace = "http://www.example.org/EmploymentRecord", propOrder = {
    "name",
    "age",
    "companies"
})
public class RecordType {

    @XmlElement(namespace = "http://www.example.org/EmploymentRecord", required = true)
    protected String name;
    @XmlElement(namespace = "http://www.example.org/EmploymentRecord", required = true)
    protected BigInteger age;
    @XmlElement(namespace = "http://www.example.org/EmploymentRecord", required = true)
    protected CompaniesType companies;
    @XmlAttribute
    protected String currentEmployment;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the age property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAge(BigInteger value) {
        this.age = value;
    }

    /**
     * Gets the value of the companies property.
     * 
     * @return
     *     possible object is
     *     {@link CompaniesType }
     *     
     */
    public CompaniesType getCompanies() {
        return companies;
    }

    /**
     * Sets the value of the companies property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompaniesType }
     *     
     */
    public void setCompanies(CompaniesType value) {
        this.companies = value;
    }

    /**
     * Gets the value of the currentEmployment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentEmployment() {
        if (currentEmployment == null) {
            return "NO";
        } else {
            return currentEmployment;
        }
    }

    /**
     * Sets the value of the currentEmployment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentEmployment(String value) {
        this.currentEmployment = value;
    }

}
