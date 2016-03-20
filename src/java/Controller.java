/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EntityBeans.Users;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import query.DBController;

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
    private DBController DBController = new DBController(); 
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
        
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

    public DBController getDBController() {
        return DBController;
    }

    public void setDBController(DBController DBController) {
        this.DBController = DBController;
    }

    
    
    
    
    public String login(){
        System.out.println("\n\n\n test test "+username + " -- "+ password);
        
        Users user = DBController.login(username, password,type);
        if(user == null){
            return null;
        }
        else{
            System.out.println("\n\n\n test test ");
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
    public String loginS(){
        System.out.println("\n\n\n test test "+username + " -- "+ password);
        Users user = DBController.login(username, password,1);
        if(user == null){
            return null;
        }
        else{
            System.out.println("\n\n\n test test ");
         return "StudentControlPanel.xhtml";   
        
        }
    }
    public String loginD(){
        System.out.println("\n\n\n test test "+username + " -- "+ password);
        Users user = DBController.login(username, password,2);
        if(user == null){
            return null;
        }
        else{
            System.out.println("\n\n\n test test ");
         return "AdminControlPanel.xhtml";   
        
        }
    }
}
