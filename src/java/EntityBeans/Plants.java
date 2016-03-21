/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Falbe
 */
@Entity
@Table(name = "plants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plants.findAll", query = "SELECT p FROM Plants p"),
    @NamedQuery(name = "Plants.findByPlantID", query = "SELECT p FROM Plants p WHERE p.plantID = :plantID"),
    @NamedQuery(name = "Plants.findByGenus", query = "SELECT p FROM Plants p WHERE p.genus = :genus"),
    @NamedQuery(name = "Plants.findBySpecies", query = "SELECT p FROM Plants p WHERE p.species = :species"),
    @NamedQuery(name = "Plants.findByPictureID", query = "SELECT p FROM Plants p WHERE p.pictureID = :pictureID"),
    @NamedQuery(name = "Plants.findByTableNumber", query = "SELECT p FROM Plants p WHERE p.tableNumber = :tableNumber"),
    @NamedQuery(name = "Plants.findByTablePosition", query = "SELECT p FROM Plants p WHERE p.tablePosition = :tablePosition")})
public class Plants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Plant_ID")
    private Integer plantID;
    @Basic(optional = false)
    @Column(name = "Genus")
    private String genus;
    @Basic(optional = false)
    @Column(name = "Species")
    private String species;
    @Basic(optional = false)
    @Column(name = "Picture_ID")
    private String pictureID;
    @Basic(optional = false)
    @Column(name = "Table_Number")
    private String tableNumber;
    @Basic(optional = false)
    @Column(name = "Table_Position")
    private String tablePosition;
    @Basic(optional = false)
    @Lob
    @Column(name = "Other_Notes")
    private String otherNotes;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "Task_ID", referencedColumnName = "Task_ID")
    @ManyToOne(optional = false)
    private Tasks taskID;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "plants")
    private Labels labels;

    public Plants() {
    }

    public Plants(Integer plantID) {
        this.plantID = plantID;
    }

    public Plants(Integer plantID, String genus, String species, String pictureID, String tableNumber, String tablePosition, String otherNotes) {
        this.plantID = plantID;
        this.genus = genus;
        this.species = species;
        this.pictureID = pictureID;
        this.tableNumber = tableNumber;
        this.tablePosition = tablePosition;
        this.otherNotes = otherNotes;
    }

    public Integer getPlantID() {
        return plantID;
    }

    public void setPlantID(Integer plantID) {
        this.plantID = plantID;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(String tablePosition) {
        this.tablePosition = tablePosition;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Tasks getTaskID() {
        return taskID;
    }

    public void setTaskID(Tasks taskID) {
        this.taskID = taskID;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
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
        if (!(object instanceof Plants)) {
            return false;
        }
        Plants other = (Plants) object;
        if ((this.plantID == null && other.plantID != null) || (this.plantID != null && !this.plantID.equals(other.plantID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityBeans.Plants[ plantID=" + plantID + " ]";
    }
    
}
