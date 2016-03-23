/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author falbellaihi
 */
@Entity
@Table(name = "labels")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Labels.findAll", query = "SELECT l FROM Labels l"),
    @NamedQuery(name = "Labels.findByPlantID", query = "SELECT l FROM Labels l WHERE l.plantID = :plantID"),
    @NamedQuery(name = "Labels.findByLabelName", query = "SELECT l FROM Labels l WHERE l.labelName = :labelName")})
public class Labels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Plant_ID")
    private Integer plantID;
    @Basic(optional = false)
    @Column(name = "Label_Name")
    private String labelName;
    @Basic(optional = false)
    @Lob
    @Column(name = "Additional_Notes")
    private String additionalNotes;
    @JoinColumn(name = "Plant_ID", referencedColumnName = "Plant_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Plants plants;

    public Labels() {
    }

    public Labels(Integer plantID) {
        this.plantID = plantID;
    }

    public Labels(Integer plantID, String labelName, String additionalNotes) {
        this.plantID = plantID;
        this.labelName = labelName;
        this.additionalNotes = additionalNotes;
    }

    public Integer getPlantID() {
        return plantID;
    }

    public void setPlantID(Integer plantID) {
        this.plantID = plantID;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Plants getPlants() {
        return plants;
    }

    public void setPlants(Plants plants) {
        this.plants = plants;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plantID != null ? plantID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Labels)) {
            return false;
        }
        Labels other = (Labels) object;
        if ((this.plantID == null && other.plantID != null) || (this.plantID != null && !this.plantID.equals(other.plantID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + plantID;
    }
    
}
