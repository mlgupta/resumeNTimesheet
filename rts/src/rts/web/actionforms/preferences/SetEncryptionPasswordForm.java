package rts.web.actionforms.preferences;

//Struts API
import org.apache.struts.validator.ValidatorForm;

public class SetEncryptionPasswordForm extends ValidatorForm{
    private String txtPassword;             /* new password */
    private String txtConfirmPassword;      /* confirm password */  

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
