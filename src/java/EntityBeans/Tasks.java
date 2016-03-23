/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author falbellaihi
 */
@Entity
@Table(name = "tasks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tasks.findAll", query = "SELECT t FROM Tasks t"),
    @NamedQuery(name = "Tasks.findByTaskID", query = "SELECT t FROM Tasks t WHERE t.taskID = :taskID"),
    @NamedQuery(name = "Tasks.findByWaterTime", query = "SELECT t FROM Tasks t WHERE t.waterTime = :waterTime"),
    @NamedQuery(name = "Tasks.findByStartDate", query = "SELECT t FROM Tasks t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "Tasks.findByEndDate", query = "SELECT t FROM Tasks t WHERE t.endDate = :endDate")})
public class Tasks implements Serializable {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tasks")
    private WorkSchedule workSchedule;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Task_ID")
    private Integer taskID;
    @Basic(optional = false)
    @Lob
    @Column(name = "Water_Amount")
    private String waterAmount;
    @Basic(optional = false)
    @Column(name = "Water_Time")
    @Temporal(TemporalType.DATE)
    private Date waterTime;
    @Basic(optional = false)
    @Lob
    @Column(name = "Fertilizer")
    private String fertilizer;
    @Basic(optional = false)
    @Column(name = "Start_Date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "End_Date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @Lob
    @Column(name = "Other_Notes")
    private String otherNotes;
    @JoinColumn(name = "Plant_ID", referencedColumnName = "Plant_ID")
    @ManyToOne(optional = false)
    private Plants plantID;

    public Tasks() {
    }

    public Tasks(Integer taskID) {
        this.taskID = taskID;
    }

    public Tasks(Integer taskID, String waterAmount, Date waterTime, String fertilizer, Date startDate, Date endDate, String otherNotes) {
        this.taskID = taskID;
        this.waterAmount = waterAmount;
        this.waterTime = waterTime;
        this.fertilizer = fertilizer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.otherNotes = otherNotes;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public String getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(String waterAmount) {
        this.waterAmount = waterAmount;
    }

    public Date getWaterTime() {
        return waterTime;
    }

    public void setWaterTime(Date waterTime) {
        this.waterTime = waterTime;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public Plants getPlantID() {
        return plantID;
    }

    public void setPlantID(Plants plantID) {
        this.plantID = plantID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskID != null ? taskID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tasks)) {
            return false;
        }
        Tasks other = (Tasks) object;
        if ((this.taskID == null && other.taskID != null) || (this.taskID != null && !this.taskID.equals(other.taskID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + taskID;
    }

    public WorkSchedule getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(WorkSchedule workSchedule) {
        this.workSchedule = workSchedule;
    }
    
}
