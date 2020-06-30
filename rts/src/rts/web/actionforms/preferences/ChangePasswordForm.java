package rts.web.actionforms.preferences;

//Struts API
import org.apache.struts.validator.ValidatorForm;

public class ChangePasswordForm extends ValidatorForm{
    private String txtUserName;             /* userName */
    private String txtPassword;             /* new password */
    private String txtConfirmPassword;      /* confirm password */  

    /**
     * Purpose : Returns txtUserName.
     * @return String
     */
    public String getTxtUserName() {
        return txtUserName;
    }

    /**
     * Purpose : Sets the value of txtUserName 
     * @param newTxtUserName Value of txtUserName from the form
     */
    public void setTxtUserName(String newTxtUserName) {
        txtUserName = newTxtUserName;
    }

    /**
     * Purpose : Returns txtPassword.
     * @return String
     */
    public String getTxtPassword() {
        return txtPassword;
    }

    /**
     * Purpose : Sets the value of txtPassword 
     * @param newTxtPassword Value of txtPassword from the form
     */
    public void setTxtPassword(String newTxtPassword) {
        txtPassword = newTxtPassword;
    }

    /**
     * Purpose : Returns txtConfirmPassword.
     * @return String
     */
    public String getTxtConfirmPassword() {
        return txtConfirmPassword;
    }

    /**
     * Purpose : Sets the value of txtConfirmPassword 
     * @param newTxtConfirmPassword Value of txtConfirmPassword from the form
     */
    public void setTxtConfirmPassword(String newTxtConfirmPassword) {
        txtConfirmPassword = newTxtConfirmPassword;
    }
}
