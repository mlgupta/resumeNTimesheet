package rts.web.actionforms.preferences;

/**
 *	Purpose: To store the values of the html controls of
 *  userPreferenceProfileForm in user_preference_profile.jsp
 * 
 *  @author              Suved Mishra
 *  @version             1.0
 * 	Date of creation:    19-07-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */ 

//Struts API
import org.apache.struts.validator.ValidatorForm;
import org.apache.log4j.*;

public class UserPreferencesProfileForm extends ValidatorForm  {

  private int txtPermittedTreeAccessLevel;       /* tree level */
  private int txtItemsToBeDisplayedPerPage;      /* records displayed per page */ 
  private String[] cboGroup;                     /* group to which user belongs */
  private String custom1_lbl;                    /* 4 custom labels for group */
  private String custom2_lbl;
  private String custom3_lbl;
  private String custom4_lbl;
  private boolean isSystemAdmin;                 /* variable for user status */
  private int cboNavigationType;
  private String userName;
  private String emailId;
  private String homeFolder;
  
  /**
   * Purpose   : Returns the permittedTreeAccessLevel.
   * @return   : String
   */
  public int getTxtPermittedTreeAccessLevel() {
      return txtPermittedTreeAccessLevel;
  }

 /**
  * Purpose   : Sets the name of permittedTreeAccessLevel.
  * @param    : newPermittedTreeAccessLevel Value of permittedTreeAccessLevel
  */
  public void setTxtPermittedTreeAccessLevel(int newTxtPermittedTreeAccessLevel) {
      txtPermittedTreeAccessLevel = newTxtPermittedTreeAccessLevel;
  }

  /**
   * Purpose   : Returns the itemsToBeDisplayedPerPage.
   * @return   : String
   */
  public int getTxtItemsToBeDisplayedPerPage() {
      return txtItemsToBeDisplayedPerPage;
  }

 /**
  * Purpose   : Sets the name of itemsToBeDisplayedPerPage.
  * @param    : newItemsToBeDisplayedPerPage Value of itemsToBeDisplayedPerPage
  */
  public void setTxtItemsToBeDisplayedPerPage(int newTxtItemsToBeDisplayedPerPage) {
      txtItemsToBeDisplayedPerPage = newTxtItemsToBeDisplayedPerPage;
  }

  /**
   * Purpose   : Returns the group.
   * @return   : String Array
   */
  public String[] getCboGroup() {
      return cboGroup;
  }

 /**
  * Purpose   : Sets the name of group.
  * @param    : newGroup Value of group
  */
  public void setCboGroup(String[] newCboGroup) {
      cboGroup = newCboGroup;
  }
  



  /**
   * Purpose  : get custom1 label value
   * @return  : String custom1 label value.      
   */
  public String getCustom1_lbl() {
    return custom1_lbl;
  }

  /**
   * Purpose  : Sets custom1 label value
   * @param   : String custom1 label value.
   */
  public void setCustom1_lbl(String custom1_lbl) {
    this.custom1_lbl = custom1_lbl;
  }

  /**
   * Purpose  : get custom2 label value
   * @return  : String custom2 label value.      
   */
  public String getCustom2_lbl() {
    return custom2_lbl;
  }

  /**
   * Purpose  : Sets custom2 label value
   * @param   : String custom2 label value.
   */
  public void setCustom2_lbl(String custom2_lbl) {
    this.custom2_lbl = custom2_lbl;
  }

  /**
   * Purpose  : get custom3 label value
   * @return  : String custom3 label value.      
   */
  public String getCustom3_lbl() {
    return custom3_lbl;
  }

  /**
   * Purpose  : Sets custom3 label value
   * @param   : String custom3 label value.
   */
  public void setCustom3_lbl(String custom3_lbl) {
    this.custom3_lbl = custom3_lbl;
  }

  /**
   * Purpose  : get custom4 label value
   * @return  : String custom4 label value.      
   */
  public String getCustom4_lbl() {
    return custom4_lbl;
  }

  /**
   * Purpose  : Sets custom4 label value
   * @param   : String custom4 label value.
   */
  public void setCustom4_lbl(String custom4_lbl) {
    this.custom4_lbl = custom4_lbl;
  }

  /**
   * Purpose  : check if user is systemAdmin
   * @return  : boolean IsSystemAdmin.      
   */
  public boolean isIsSystemAdmin() {
    return isSystemAdmin;
  }

  /**
   * Purpose  : Sets isSystemAdmin status for user
   * @param   : boolean isSystemAdmin.
   */
  public void setIsSystemAdmin(boolean isSystemAdmin) {
    this.isSystemAdmin = isSystemAdmin;
  }

  /**
   * Purpose  : method to override toString() for verbal description of preferences
   * @return  : String with preferences details
   */
  public String toString(){
      
      String strTemp = "";
      Logger logger = Logger.getLogger("DbsLogger");
      
      if(logger.getLevel() == Level.DEBUG){

          String strArrayValues = "";
          
          strTemp += "\n\ttxtPermittedTreeAccessLevel : " 
                      + txtPermittedTreeAccessLevel;
                      
          strTemp += "\n\ttxtItemsToBeDisplayedPerPage : " 
                      + txtItemsToBeDisplayedPerPage;            

          if(cboGroup != null){
              strArrayValues = "{";
              for(int index = 0; index < cboGroup.length; index++){
                  strArrayValues += " " + cboGroup[index];
              }
              strArrayValues += "}";
              strTemp += "\n\tcboGroup : " + strArrayValues;
          }else{
              strTemp += "\n\tcboGroup : " + cboGroup;
          }
          
          strTemp += "\n\tcboNavigationType : "+cboNavigationType;
          strTemp += "\n\tCustom1_lbl : "+custom1_lbl;
          strTemp += "\n\tCustom2_lbl : "+custom2_lbl;
          strTemp += "\n\tCustom3_lbl : "+custom3_lbl;
          strTemp += "\n\tCustom4_lbl : "+custom4_lbl;
          strTemp += "\n\tIsSystemAdmin : "+isSystemAdmin;
          strTemp += "\n\tUserName  : "+userName;
          strTemp += "\n\tHomeFolder  : "+homeFolder;
          strTemp += "\n\tEmailId : "+emailId;
      }
      return strTemp;
  }



  public int getCboNavigationType() {
    return cboNavigationType;
  }

  public void setCboNavigationType(int cboNavigationType) {
    this.cboNavigationType = cboNavigationType;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getHomeFolder() {
    return homeFolder;
  }

  public void setHomeFolder(String homeFolder) {
    this.homeFolder = homeFolder;
  }


}