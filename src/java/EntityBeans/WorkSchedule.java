/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBeans;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Falbe
 */
@Entity
@Table(name = "work_schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkSchedule.findAll", query = "SELECT w FROM WorkSchedule w"),
    @NamedQuery(name = "WorkSchedule.findByTimein", query = "SELECT w FROM WorkSchedule w WHERE w.timein = :timein"),
    @NamedQuery(name = "WorkSchedule.findByTimeOut", query = "SELECT w FROM WorkSchedule w WHERE w.timeOut = :timeOut"),
    @NamedQuery(name = "WorkSchedule.findByDate", query = "SELECT w FROM WorkSchedule w WHERE w.date = :date"),
    @NamedQuery(name = "WorkSchedule.findByTaskID", query = "SELECT w FROM WorkSchedule w WHERE w.taskID = :taskID"),
    @NamedQuery(name = "WorkSchedule.findByUserID", query = "SELECT w FROM WorkSchedule w WHERE w.userID = :userID")})
public class WorkSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Time_in")
    @Temporal(TemporalType.DATE)
    private Date timein;
    @Basic(optional = false)
    @Column(name = "time_out")
    @Temporal(TemporalType.DATE)
    private Date timeOut;
    @Basic(optional = false)
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Lob
    @Column(name = "Comments")
    private String comments;
    @Id
    @Basic(optional = false)
    @Column(name = "Task_ID")
    private Integer taskID;
    @Basic(optional = false)
    @Column(name = "User_ID")
    private int userID;
    @JoinColumn(name = "Task_ID", referencedColumnName = "Task_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Tasks tasks;

    public WorkSchedule() {
    }

    public WorkSchedule(Integer taskID) {
        this.taskID = taskID;
    }

    public WorkSchedule(Integer taskID, Date timein, Date timeOut, Date date, String comments, int userID) {
        this.taskID = taskID;
        this.timein = timein;
        this.timeOut = timeOut;
        this.date = date;
        this.comments = comments;
        this.userID = userID;
    }

    public Date getTimein() {
        return timein;
    }

    public void setTimein(Date timein) {
        this.timein = timein;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
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
        if (!(object instanceof WorkSchedule)) {
            return false;
        }
        WorkSchedule other = (WorkSchedule) object;
        if ((this.taskID == null && other.taskID != null) || (this.taskID != null && !this.taskID.equals(other.taskID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityBeans.WorkSchedule[ taskID=" + taskID + " ]";
    }
    
}
