package rts.web.actionforms.user;
/**
 *	Purpose: To store the values of the html controls of 
 *  userListSelectForm in user_list_select.jsp
 * 
 * @author              Sudheer Pujar
 * @version             1.0
 * 	Date of creation:   05-01-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

//Struts API
import org.apache.struts.action.ActionForm;

public class UserListSelectForm extends ActionForm {
    private String txtSearchByUserName;
    private String[] chkSelect;  
    private String control;
    private String txtPageNo;
    private String txtPageCount;
    private String operation;
 
    /**
     * Purpose : Returns  chkSelect array.
     * @return : String[] 
     */
    public String[] getChkSelect() {
        return chkSelect;
    }

    /**
     * Purpose : Sets the value of chkSelect array.
     * @param  : newChkSelect Value of chkSelect from the form
     */
    public void setChkSelect(String[] newChkSelect) {
        chkSelect = newChkSelect;
    }

    /**
     * Purpose : Returns txtSearchByUserName.
     * @return : String
     */
    public String getTxtSearchByUserName() {
        return txtSearchByUserName;
    }

    /**
     * Purpose : Sets the value of txtSearchByUserName.
     * @param  : newTxtSearchByUserName Value of txtSearchByUserName from the form
     */
    public void setTxtSearchByUserName(String newTxtSearchByUserName) {
        txtSearchByUserName = newTxtSearchByUserName;
    }

    /**
     * Purpose : Returns control.
     * @return : String
     */
    public String getControl() {
        return control;
    }

    /**
     * Purpose : Sets the value of control.
     * @param  : newControl Value of control from the form
     */
    public void setControl(String newControl) {
        control = newControl;
    }

    /**
     * Purpose : Returns txtPageNo.
     * @return : String
     */
    public String getTxtPageNo() {
        return txtPageNo;
    }

    /**
     * Purpose : Sets the value of txtPageNo.
     * @param  : newTxtPageNo Value of txtPageNo from the form
     */
    public void setTxtPageNo(String newTxtPageNo) {
        txtPageNo = newTxtPageNo;
    }

    /**
     * Purpose : Returns txtPageCount.
     * @return : String
     */
    public String getTxtPageCount() {
        return txtPageCount;
    }

    /**
     * Purpose : Sets the value of txtPageCount.
     * @param  : newTxtPageCount Value of txtPageCount from the form
     */
    public void setTxtPageCount(String newTxtPageCount) {
        txtPageCount = newTxtPageCount;
    }

    /**
     * Purpose : Returns operation.
     * @return : String
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Purpose : Sets the value of operation.
     * @param  : newOperation Value of operation from the form
     */
    public void setOperation(String newOperation) {
        operation = newOperation;
    }
}
