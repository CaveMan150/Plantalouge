
import EntityBeans.Tasks;
import EntityBeans.Users;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import query.TasksController;

import query.UsersController;


@FacesConverter("TConverter")
public class TaskConvertor implements Converter {
     
     private TasksController tController = new TasksController();

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
               
               Tasks t=tController.findT(value);
                return t;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", value+" Not a valid Task."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
           
            return ((Tasks)object).getTaskID().toString();
        }
        else {
            return null;
        }
    }   

  
} 