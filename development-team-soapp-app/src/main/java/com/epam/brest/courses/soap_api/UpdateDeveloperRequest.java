//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.03 at 09:19:27 PM MSK 
//


package com.epam.brest.courses.soap_api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="developerInfo" type="{http://epam.com/brest/courses/soap_api}developerInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "developerInfo"
})
@XmlRootElement(name = "updateDeveloperRequest")
public class UpdateDeveloperRequest {

    @XmlElement(required = true)
    protected DeveloperInfo developerInfo;

    /**
     * Gets the value of the developerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DeveloperInfo }
     *     
     */
    public DeveloperInfo getDeveloperInfo() {
        return developerInfo;
    }

    /**
     * Sets the value of the developerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeveloperInfo }
     *     
     */
    public void setDeveloperInfo(DeveloperInfo value) {
        this.developerInfo = value;
    }

}
