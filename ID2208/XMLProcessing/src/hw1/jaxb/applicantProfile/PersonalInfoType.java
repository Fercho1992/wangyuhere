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
 * <p>Java class for personalInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personalInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hobby" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gender">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="male"/>
 *               &lt;enumeration value="female"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="personalStatement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personalInfoType", namespace = "http://www.example.org/ShortCV", propOrder = {
    "age",
    "country",
    "hobby",
    "gender",
    "personalStatement"
})
public class PersonalInfoType {

    @XmlElement(namespace = "http://www.example.org/ShortCV")
    protected int age;
    @XmlElement(namespace = "http://www.example.org/ShortCV", required = true)
    protected String country;
    @XmlElement(namespace = "http://www.example.org/ShortCV", required = true)
    protected String hobby;
    @XmlElement(namespace = "http://www.example.org/ShortCV", required = true)
    protected String gender;
    @XmlElement(namespace = "http://www.example.org/ShortCV", required = true)
    protected String personalStatement;

    /**
     * Gets the value of the age property.
     * 
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     */
    public void setAge(int value) {
        this.age = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the hobby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * Sets the value of the hobby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHobby(String value) {
        this.hobby = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the personalStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalStatement() {
        return personalStatement;
    }

    /**
     * Sets the value of the personalStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalStatement(String value) {
        this.personalStatement = value;
    }

}
