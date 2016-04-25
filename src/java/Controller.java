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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.primefaces.event.FileUploadEvent;
import query.LabelsController;
import query.PlantsController;
import query.TasksController;
import query.UsersController;
import query.WorkScheduleController;
import query.exceptions.NonexistentEntityException;
import query.exceptions.PreexistingEntityException;

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
    private Users fusername;

    private Tasks tasks = new Tasks();

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
    private String PictureID = "Unknown";
    private String PhoneNumber;

    private String newGen;
    private String newSpec;
    private String NewPic;
    private String newTableN;
    private String newTableP;
    private String newOtherN;
    private Users AssignUser;
    private Tasks AssignTask;
    private Users TaskToUser;
    private String ForgotEmail;
    private String ForUser;
    private String returnPass;
    private Users SelectedUser;

    public String getForUser() {
        return ForUser;
    }

    public void setForUser(String ForUser) {
        this.ForUser = ForUser;
    }

    
    
    
    public String getForgotEmail() {
        return ForgotEmail;
    }

    public void setForgotEmail(String ForgotEmail) {
        this.ForgotEmail = ForgotEmail;
    }

    public Users getSelectedUser() {
        return SelectedUser;
    }

    public void setSelectedUser(Users SelectedUser) {
        this.SelectedUser = SelectedUser;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");

        FacesContext.getCurrentInstance().addMessage(null, message);
        Path uploadFolder = Paths.get("D:\\wamp64/www");
        String fn = event.getFile().getFileName();
        int index = fn.indexOf(".");
        if (index < 0) {
            return;
        }
        String ext = fn.substring(index);
        String fileName = fn.substring(0, index);
        Path file = null;
        try {
            file = Files.createTempFile(uploadFolder, fileName, ext);
            InputStream input = event.getFile().getInputstream();
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int userIndex = Integer.parseInt(event.getComponent().getAttributes().get("user").toString());
        SelectedUser = this.usersList.get(userIndex);
        SelectedUser.setPictureID("http://localhost/" + file.getFileName());
        System.out.println(SelectedUser.getPictureID());

    }

    public void h() {

        Users u = new Users();
        u= uController.findU(ForUser);
        
        System.out.println(u.getEmail());
        if(u.getEmail().equals(ForgotEmail)){
                
        
        System.out.println("  Called email forgotpass ");
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("falbellaihi12345", "Faisal!1"));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("falbellaihi12345@gmail.com");
            email.setSubject("Reset Plantalouge Password!");
            email.setMsg(u.getName()+" Hello!, Here is your login information "+u.getUsername()+" "+u.getPassword());
            email.addTo("falbellaihi@hotmail.com");
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
        else {
            System.out.println("Email not matched with user record");
        }
    }

    public Tasks getAssignTask() {
        return AssignTask;
    }

    public void setAssignTask(Tasks AssignTask) {
        this.AssignTask = AssignTask;
    }

    public Users getTaskToUser() {
        return TaskToUser;
    }

    public void setTaskToUser(Users TaskToUser) {
        this.TaskToUser = TaskToUser;
    }

    public Users getFusername() {
        return fusername;
    }

    public void setFusername(Users fusername) {
        this.fusername = fusername;
    }

    private List<Plants> result = new LinkedList();
    private List<Users> userresult = new LinkedList();
    private String a;//a search variable for the plants
    private String SU; //A search variable for the users
    private List<Tasks> wtask = new LinkedList();

    public List<Users> getUserresult() {
        return userresult;
    }

    public void setUserresult(List<Users> userresult) {
        this.userresult = userresult;
    }

    public String getSU() {
        return SU;
    }

    public void setSU(String SU) {
        this.SU = SU;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void searchUser() {

        //Plants p = new Plants();
        System.out.println("searching ...");

        result.clear();
        for (Users str : usersList) {
            if (str.toString().contains(SU)) {
                System.out.println(str);
                userresult.add(str);
                //    S = str.getGenus();
                //  return S;
            }
        }

        //return "Could not find plant";
    }

    public void searchPant() {

        //Plants p = new Plants();
        System.out.println("searching ...");

        result.clear();
        for (Plants str : plantList) {
            if (str.toString().contains(a)) {
                System.out.println(str);
                result.add(str);
                //    S = str.getGenus();
                //  return S;
            }
        }

        //return "Could not find plant";
    }

    public List<Plants> getResult() {
        return result;
    }

    public void setResult(List<Plants> result) {
        this.result = result;
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
        System.out.println("this is pic id" + PictureID);
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
            System.out.println("removeplant remove plant ");

            pController.destroy(ID);
            plantList.remove(index);

            //   return ID;
        } catch (NonexistentEntityException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUser(int ID, int index) {
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
           ex.printStackTrace();
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
        System.out.println("called create user");
        try {
            Users newUser = new Users();
            newUser.setUsername(NewUsername);
            newUser.setPassword(NewPassword);
            newUser.setType(NewType);
            newUser.setEmail(NewEmail);
            newUser.setPhone(PhoneNumber);
            PictureID = "Unknown";
            newUser.setPictureID(PictureID);
            newUser.setName(NameOfUser);

            uController.create(newUser);
            workList = wController.findWorkScheduleEntities();
            tasksList = tController.findTasksEntities(); // retrives all the tasks from the database
            usersList = uController.findUsersEntities(); //retrieves all the users from the database
            plantList = pController.findPlantsEntities();
        } catch (Exception ex) {
            StackTraceElement[] stackTrace = ex.getStackTrace();
        }

    }

    public String getReturnPass() {
        return returnPass;
    }

    public void setReturnPass(String returnPass) {
        this.returnPass = returnPass;
    }

    public void createPlant() {
        System.out.println("creating plant!");
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
            System.out.println("creating task!");

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
            ex.printStackTrace();
        }

    }

    public void createWork() {

        WorkSchedule work = new WorkSchedule();

        for (Tasks task : tasksList) {
            if (task.toString().contains((CharSequence) AssignTask)) {
                System.out.println(task);
                wtask.add(task);
                //    S = str.getGenus();
                //  return S;
            }
        }
        //work.setComments();
        work.setDate(StartDate);
        work.setTimeOut(StartDate);
        work.setTasks(AssignTask);

        //System.out.println("this is " + );
        work.setUserID(TaskToUser);
        try {
            wController.create(work);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public void accessPlantsInfo() {
        plantList = pController.findPlantsEntities();

    }

    public void accessUserInfo() {

        usersList = uController.findUsersEntities(); //retrieves all the users from the database

    }

    public void accessTasksInfo() {
        tasksList = tController.findTasksEntities(); // retrives all the tasks from the database

    }

    public void accessWorkInfo() {

        workList = wController.findWorkScheduleEntities();
    }

    public String logout() {
        System.out.println("bye ");
        username = null;
        password = null;
        type = -10;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

    }

}
