//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.08 at 09:32:24 PM MSK 
//


package com.epam.brest.courses.soap_api;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="list_of_developersInfo" type="{http://epam.com/brest/courses/soap_api}developerInfo" maxOccurs="unbounded" minOccurs="0"/>
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
    "listOfDevelopersInfo"
})
@XmlRootElement(name = "selectDevelopersFromProjects_DevelopersResponse")
public class SelectDevelopersFromProjectsDevelopersResponse {

    @XmlElement(name = "list_of_developersInfo")
    protected List<DeveloperInfo> listOfDevelopersInfo;

    /**
     * Gets the value of the listOfDevelopersInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listOfDevelopersInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListOfDevelopersInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeveloperInfo }
     * 
     * 
     */
    public List<DeveloperInfo> getListOfDevelopersInfo() {
        if (listOfDevelopersInfo == null) {
            listOfDevelopersInfo = new ArrayList<DeveloperInfo>();
        }
        return this.listOfDevelopersInfo;
    }

}
