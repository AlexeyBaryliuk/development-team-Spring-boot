//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.03 at 09:19:27 PM MSK 
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
 *         &lt;element name="projects_developersInfo" type="{http://epam.com/brest/courses/soap_api}projects_developersInfo" maxOccurs="unbounded" minOccurs="0"/>
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
    "projectsDevelopersInfo"
})
@XmlRootElement(name = "selectDevelopersFromProjects_DevelopersResponse")
public class SelectDevelopersFromProjectsDevelopersResponse {

    @XmlElement(name = "projects_developersInfo")
    protected List<ProjectsDevelopersInfo> projectsDevelopersInfo;

    /**
     * Gets the value of the projectsDevelopersInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projectsDevelopersInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjectsDevelopersInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProjectsDevelopersInfo }
     * 
     * 
     */
    public List<ProjectsDevelopersInfo> getProjectsDevelopersInfo() {
        if (projectsDevelopersInfo == null) {
            projectsDevelopersInfo = new ArrayList<ProjectsDevelopersInfo>();
        }
        return this.projectsDevelopersInfo;
    }

}
