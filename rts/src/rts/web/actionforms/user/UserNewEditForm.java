package rts.web.actionforms.user;

/**
 *	Purpose: To store and retrive the values of the html controls of 
 *  userNewEditForm in user_new.jsp and user_edit.jsp.
 * 
 *  @author              Sudheer Pujar
 *  @version             1.0
 * 	Date of creation:   05-01-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
//Struts API
import org.apache.struts.validator.ValidatorForm;
import org.apache.log4j.*;

public class UserNewEditForm extends ValidatorForm {
    private String txtUserName;
    private String txaDescription;      
    private String txtPassword;         
    private String txtConfirmPassword;  
    private String txtEmailAddress;      

    /**
     * Purpose : Returns txaDescription.
     * @return : String
     */
    public String getTxaDescription() {
        return txaDescription;
    }

    /**
     * Purpose : Sets the value of txaDescription.
     * @param  : newTxaDescription Value of txaDescription from the form
     */
    public void setTxaDescription(String newTxaDescription) {
        txaDescription = newTxaDescription;
    }

    /**
     * Purpose : Returns txtConfirmPassword.
     * @return : String
     */
    public String getTxtConfirmPassword() {
        return txtConfirmPassword;
    }
    
    /**
     * Purpose : Sets the value of txtConfirmPassword.
     * @param  : newTxtConfirmPassword Value of txtConfirmPassword from the form
     */
    public void setTxtConfirmPassword(String newTxtConfirmPassword) {
        txtConfirmPassword = newTxtConfirmPassword;
    }

    /**
     * Purpose : Returns txtPassword.
     * @return : String
     */
    public String getTxtPassword() {
        return txtPassword;
    }

    /**
     * Purpose : Sets the value of txtPassword.
     * @param  : newTxtPassword Value of txtPassword from the form
     */
    public void setTxtPassword(String newTxtPassword) {
        txtPassword = newTxtPassword;
    }

    /**
     * Purpose : Returns txtUserName.
     * @return : String
     */

    public String getTxtUserName() {
        return txtUserName;
    }

    /**
     * Purpose : Sets the value of txtUserName.
     * @param  : newTxtUserName Value of txtUserName from the form
     */
    public void setTxtUserName(String newTxtUserName) {
        txtUserName = newTxtUserName;
    }
    
    /**
     * Purpose : Returns txtEmailAddress.
     * @return : String
     */
    public String getTxtEmailAddress() {
        return txtEmailAddress;
    }

    /**
     * Purpose : Sets the value of txtEmailAddress.
     * @param  : newTxtEmailAddress Value of txtEmailAddress from the form
     */
    public void setTxtEmailAddress(String newTxtEmailAddress) {
        txtEmailAddress = newTxtEmailAddress;
    }
     
    public String toString(){
        String strTemp = "";
        Logger logger = Logger.getLogger("DbsLogger");
        if(logger.getLevel() == Level.DEBUG){
            String strArrayValues = "";
            strTemp += "\n\ttxtUserName : " + txtUserName;
            strTemp += "\n\ttxaDescription : " + txaDescription;
            strTemp += "\n\ttxtPassword : " + txtPassword;

            strTemp += "\n\ttxtConfirmPassword : " + txtConfirmPassword;

            strTemp += "\n\ttxtEmailAddress : " + txtEmailAddress;
        }
        return strTemp;
    }
}
