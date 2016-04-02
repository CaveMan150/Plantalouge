/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EntityBeans.Labels;
import EntityBeans.Plants;
import EntityBeans.Tasks;
import EntityBeans.Users;
import EntityBeans.WorkSchedule;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import query.LabelsController;
import query.PlantsController;
import query.TasksController;
import query.UsersController;
import query.WorkScheduleController;
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
    private int type = -10;
    private Plants selectedPlant;
    private long userID;

    

    private UsersController uController = new UsersController();
    private List<Users> usersList;
    private List<Plants> plantList;
    private PlantsController pController = new PlantsController();
    private List<Tasks> tasksList;
    private TasksController tController = new TasksController();
    private List<WorkSchedule> workList;
    private WorkScheduleController wController = new WorkScheduleController();
    private List<Labels> labelList;
    private LabelsController lController = new LabelsController();

    private String WaterAmount;
    private Date WaterTime;
    private String FertilizerAmount;
    private Date StartDate;
    private Date ExpectedEndDate;
    private String OtherNotes;
   
    private String NewUsername;
    private String NewPassword;
    private int NewType;
    private String NewEmail;
    private String NameOfUser;
    private String PictureID;
    private String PhoneNumber;
    
    private String newGen;
    private String newSpec;
    private String NewPic;
    private String newTableN;
    private String newTableP;
    private String newOtherN;
    private Users AssignUser;
    
    private String a;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    
    public String getNewGen() {
        return newGen;
    }

    public void setNewGen(String newGen) {
        this.newGen = newGen;
    }

    public String getNewSpec() {
        return newSpec;
    }

    public void setNewSpec(String newSpec) {
        this.newSpec = newSpec;
    }

    public String getNewPic() {
        return NewPic;
    }

    public void setNewPic(String NewPic) {
        this.NewPic = NewPic;
    }

    public String getNewTableN() {
        return newTableN;
    }

    public void setNewTableN(String newTableN) {
        this.newTableN = newTableN;
    }

    public String getNewTableP() {
        return newTableP;
    }

    public void setNewTableP(String newTableP) {
        this.newTableP = newTableP;
    }

    public String getNewOtherN() {
        return newOtherN;
    }

    public void setNewOtherN(String newOtherN) {
        this.newOtherN = newOtherN;
    }

    public Users getAssignUser() {
        return AssignUser;
    }

    public void setAssignUser(Users AssignUser) {
        this.AssignUser = AssignUser;
    }


    
    
    
    
    
    

   
    

    public String getNameOfUser() {
        return NameOfUser;
    }

    public void setNameOfUser(String NameOfUser) {
        this.NameOfUser = NameOfUser;
    }

    public String getPictureID() {
        return PictureID;
    }

    public void setPictureID(String PictureID) {
        this.PictureID = PictureID;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    

    
    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public int getNewType() {
        return NewType;
    }

    public void setNewType(int NewType) {
        this.NewType = NewType;
    }

    public String getNewEmail() {
        return NewEmail;
    }

    public void setNewEmail(String NewEmail) {
        this.NewEmail = NewEmail;
    }
    /**
     * Creates a new instance of Controller
     */
    
    
    
    public Controller() {

    }

    public List<WorkSchedule> getWorkList() {
        return workList;
    }

    public void setWorkList(List<WorkSchedule> workList) {
        this.workList = workList;
    }

    public List<Labels> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Labels> labelList) {
        this.labelList = labelList;
    }

    public void getUsernameByID(int ID, int index) {

        //Here where to get UID and return the username;
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

    public void removePlant(int ID, int index) {
        try {
            System.out.println("removeplant remove plant rempppppppfijdsjdkjss");

            pController.destroy(ID);
            plantList.remove(index);

            //   return ID;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeUser(int ID, int index){
        try {
            uController.destroy(ID);
            usersList.remove(index);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removeTask(int ID, int index) {
        try {
            System.out.println("remove task remove plant rempppppppfijdsjdkjss");

            tController.destroy(ID);
            tasksList.remove(index);

            //   return ID;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeWork(int ID, int index) {
        try {
            System.out.println("remove work remove plant rempppppppfijdsjdkjss");

            wController.destroy(ID);
            workList.remove(index);
            

            //   return ID;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNewUsername() {
        return NewUsername;
    }

    public void setNewUsername(String NewUser) {
        this.NewUsername = NewUser;
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

    public void createUser() {

        try{
        Users newUser = new Users();
        newUser.setUsername(NewUsername);
        newUser.setPassword(NewPassword);
        newUser.setType(NewType);
        newUser.setEmail(NewEmail);
        newUser.setPhone(PhoneNumber);
        newUser.setPictureID(PictureID);
        newUser.setName(NameOfUser);
        
        uController.create(newUser);
        workList = wController.findWorkScheduleEntities();
         tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
         usersList = uController.findUsersEntities(); //retrieves all the users from the database
         plantList = pController.findPlantsEntities();
        }catch (Exception ex) {
            StackTraceElement[] stackTrace = ex.getStackTrace();
        }
        
        
    }
  
    public void createPlant(){
        Plants newPlant = new Plants();
        newPlant.setGenus(newGen);
        newPlant.setSpecies(newSpec);
        newPlant.setPictureID(NewPic);
        newPlant.setOtherNotes(newOtherN);
        newPlant.setTableNumber(newTableN);
        newPlant.setTablePosition(newTableP);
        newPlant.setUserID(AssignUser);
        pController.create(newPlant);
         workList = wController.findWorkScheduleEntities();
         tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
         usersList = uController.findUsersEntities(); //retrieves all the users from the database
         plantList = pController.findPlantsEntities();
         
    
    }
    public void createTask() {
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
         workList = wController.findWorkScheduleEntities();
         tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
         usersList = uController.findUsersEntities(); //retrieves all the users from the database
         plantList = pController.findPlantsEntities();
        
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editWork(WorkSchedule w) {
        try {
            wController.edit(w);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editTask(Tasks t) {
        try {
            tController.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editPlant(Plants p) {

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

    public void editUser(Users u) {

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

    public String getType(int t) {
        switch (t) {
            case 0:
                return "Admin";
            case 1:
                return "Student";
            case 2:
                return "Donator";

        }
        return "Unknown";
    }

    public String login() {
        System.out.println("\n\n\n test test " + username + " -- " + password);
         workList = wController.findWorkScheduleEntities();
         tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
         usersList = uController.findUsersEntities(); //retrieves all the users from the database
         plantList = pController.findPlantsEntities();
        Users user = uController.login(username, password, type);
        if (user == null) {
            return null;
            
        } else {
            System.out.println("\n\n\n test test ");
           
           
           
            switch (type) {
                case 0:

                    return "AdminControlPanel.xhtml";
                case 1:
                    return "StudentControlPanel.xhtml";
                case 2:
                    return "dontatorHome.xhtml";

                default:
                    return "AdminControlPanel.xhtml";
            }

        }
    }
    
    public void accessPlantsInfo(){
         plantList = pController.findPlantsEntities();
        
        
    }
    public void accessUserInfo(){
        
         usersList = uController.findUsersEntities(); //retrieves all the users from the database
        
    }
    public void accessTasksInfo(){
        tasksList = tController.findTasksEntities(); // retrives all the tasks from the database

        
    }
    public void accessWorkInfo(){
        
         workList = wController.findWorkScheduleEntities();
    }

    public String logout() {

        username = null;
        password = null;
        type = -10;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

    }
    
    
    
    
}
