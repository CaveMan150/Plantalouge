/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EntityBeans.Plants;
import EntityBeans.Tasks;
import EntityBeans.Users;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn.CellEditEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import query.PlantsController;
import query.TasksController;
import query.UsersController;
import query.exceptions.IllegalOrphanException;
import query.exceptions.NonexistentEntityException;

/**
 *
 * @author falbellaihi
 */
@ManagedBean
@SessionScoped
public class Controller {

    private String username; 
    private String password;
    private int type =-10;
    private Plants selectedPlant;
 


    private UsersController uController = new UsersController(); 
    private List<Users> usersList;
    private List<Plants> plantList;
    private PlantsController pController = new PlantsController();
    private List<Tasks> tasksList;
    private TasksController tController = new TasksController();
    private String WaterAmount;
    private Date WaterTime;
    private String FertilizerAmount;
    private Date StartDate;
    private Date ExpectedEndDate;
    private String OtherNotes;
    
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
        
    }
public List<Tasks> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Tasks> tasksList) {
        this.tasksList = tasksList;
    }
   
    public List<Plants> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plants> plantList) {
        this.plantList = plantList;
    }

     public void removePlant(int ID, int index){
        try {
            System.out.println("removeplant remove plant rempppppppfijdsjdkjss");
            
            pController.destroy(ID);
            plantList.remove(index);
            
            //   return ID;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     public void removeTask(int ID, int index){
        try {
            System.out.println("remove task remove plant rempppppppfijdsjdkjss");
            
            tController.destroy(ID);
            tasksList.remove(index);
            
            //   return ID;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getWaterAmount() {
        return WaterAmount;
    }

    public void setWaterAmount(String WaterAmount) {
        this.WaterAmount = WaterAmount;
    }

    public Date getWaterTime() {
        return WaterTime;
    }

    public void setWaterTime(Date WaterTime) {
        this.WaterTime = WaterTime;
    }

    public String getFertilizerAmount() {
        return FertilizerAmount;
    }

    public void setFertilizerAmount(String FertilizerAmount) {
        this.FertilizerAmount = FertilizerAmount;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getExpectedEndDate() {
        return ExpectedEndDate;
    }

    public void setExpectedEndDate(Date ExpectedEndDate) {
        this.ExpectedEndDate = ExpectedEndDate;
    }

    public String getOtherNotes() {
        return OtherNotes;
    }

    
    public void setOtherNotes(String OtherNotes) {
        this.OtherNotes = OtherNotes;
    }

    public Plants getSelectedPlant() {
        return selectedPlant;
    }

    public void setSelectedPlant(Plants selectedPlant) {
        this.selectedPlant = selectedPlant;
    }
    
    
    public void createTask()
    {
        try {
            Tasks tasks = new Tasks();
            tasks.setEndDate(ExpectedEndDate);
            tasks.setFertilizer(FertilizerAmount);
            tasks.setOtherNotes(OtherNotes);
            tasks.setStartDate(StartDate);
            tasks.setWaterAmount(WaterAmount);
            tasks.setWaterTime(WaterTime);
            tasks.setPlantID(selectedPlant);
            //tasks.setWorkSchedule(workSchedule);
            tController.create(tasks);
            
                    
                    } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public void editTask(Tasks t){
        try {
            tController.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public void editPlant(Plants p){
         
      
        try {
            //Object newValue = event.getNewValue();
            
            // Plants plant = pController.findPlants(ID);
            pController.edit(p);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
         
     }
    
     public void editUser(Users u){
         
        
        try {
            //Object newValue = event.getNewValue();
            
            // Plants plant = pController.findPlants(ID);
            uController.edit(u);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         
     }
    
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

  public String getType(int t)
  {
      switch(t)
      {
          case 0:
              return "Admin";
          case 1:
              return "Student";
          case 2:
              return "Donator";
      
      }
  return "Unknown";
  }
    
    
    
    
    public String login(){
        System.out.println("\n\n\n test test "+username + " -- "+ password);
        
        Users user = uController.login(username, password,type);
        if(user == null){
            return null;
        }
        else{
            System.out.println("\n\n\n test test ");
           plantList = pController.findPlantsEntities();
           usersList=uController.findUsersEntities(); //retrieves all the users from the database
           tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
 
           switch(type)
            {
                case 0:
                    
                    return "AdminControlPanel.xhtml"; 
                case 1:
                     return "StudentControlPanel.xhtml";
                     
                default:
                    return "AdminControlPanel.xhtml";
            }
        
        }
    }
   public String logout(){
     
     username = null;
     password = null;
     type = -10;
     FacesContext.getCurrentInstance().getExternalContext().invalidateSession();  
     return "index.xhtml";
   
   }
}
