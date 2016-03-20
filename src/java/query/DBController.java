/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;
import EntityBeans.Users;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author falbellaihi
 */

public class DBController implements Serializable {
   EntityManagerFactory emf; 
   EntityManager em ;

    public DBController() {
        emf =Persistence.createEntityManagerFactory("PlantaloguePU");
        em =emf.createEntityManager();
     
    }
   
   public Users login(String username, String password, int t)
   {
       try
       {
           Users loginUser=em.createNamedQuery("Users.findByUsernameAndPassword", Users.class).setParameter("username", username).setParameter("password", password).setParameter("type", t).getSingleResult();
           if(loginUser==null)
               return null;
            return loginUser;
       }
       catch (Exception e)
       {
           e.printStackTrace();
            return null;
       }
       
   }
     public String addUser(Users user)
   {
       try
       {
           Users loginUser=em.createNamedQuery("Users.findByUsername", Users.class).setParameter("username", user.getUsername()).getSingleResult();
           
             
         return null;
       } 
       catch (Exception e)
       {
     
          
          e.printStackTrace();
           return "index.xhtml";
       }
       
   }

}