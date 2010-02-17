//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.14 at 09:27:50 em CET 
//


package hw1.jaxb.applicantProfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for degreeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="degreeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Bachelor"/>
 *     &lt;enumeration value="Master"/>
 *     &lt;enumeration value="Doctor"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum DegreeType {

    @XmlEnumValue("Bachelor")
    BACHELOR("Bachelor"),
    @XmlEnumValue("Master")
    MASTER("Master"),
    @XmlEnumValue("Doctor")
    DOCTOR("Doctor");
    private final String value;

    DegreeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DegreeType fromValue(String v) {
        for (DegreeType c: DegreeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
