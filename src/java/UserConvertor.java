
import EntityBeans.Users;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import query.UsersController;


@FacesConverter("UConverter")
public class UserConvertor implements Converter {
     private UsersController uController = new UsersController();

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
               Users u=uController.findU(value);
                return u;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", value+" Not a valid user."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
           
            return ((Users)object).getUsername();
        }
        else {
            return null;
        }
    }   

  
} 