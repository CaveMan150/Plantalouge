/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EntityBeans.Plants;
import EntityBeans.Tasks;
import EntityBeans.Users;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import query.PlantsController;
import query.TasksController;
import query.UsersController;

/**
 *
 * @author falbellaihi
 */
@ManagedBean
@SessionScoped
public class Controller {

    private String username; 
    private String password;
    private int type;
    private UsersController uController = new UsersController(); 
    private List<Users> usersList;
    private List<Plants> plantList;
    private PlantsController pController = new PlantsController();
    private List<Tasks> tasksList;
    private TasksController tController = new TasksController();

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
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
        
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
           
            switch(type)
            {
                case 0:
                    usersList=uController.findUsersEntities(); //retrieves all the users from the database
                    tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
                    return "AdminControlPanel.xhtml"; 
                case 1:
                     return "StudentControlPanel.xhtml";
                     
                default:
                    return "AdminControlPanel.xhtml";
            }
        
        }
    }
   
}
