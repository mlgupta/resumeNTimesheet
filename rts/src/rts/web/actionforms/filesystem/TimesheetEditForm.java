package rts.web.actionforms.filesystem;

/* Java API */
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
  //Struts API
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.log4j.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

public class TimesheetEditForm extends ValidatorForm  {
  private FormFile fileOne;           /* form-file obj for file to be uploaded */
  private String txtPath;             /* folder path for file upload */
  private String txtFileDesc;         /* file description to be set */
  private String txtName;             /* name of timesheet creator */
  private String txaCSV;              /* corresponding CSV */
  private String[] lstAttachment;     /* list of folder paths to share timesheet */
  private String hdnTargetFolderPath; /* control to be set for selecting folders */
  /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
      super.reset(mapping, request);
  }

  /**
   * Validate all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   * @return ActionErrors A list of all errors found.
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    return super.validate(mapping, request);
  }

  /**
   * Purpose   :  Returns  txtPath.
   * @return   :  String 
   */
  public String getTxtPath() {
    return txtPath;
  }

  /**
   * Purpose   : Sets the value of txtPath.
   * @param newTxtPath Value of txtPath from the form
   */
  public void setTxtPath(String newTxtPath) {
    txtPath = newTxtPath;        
  }

  /**
   * Purpose   :  Returns  fileOne.
   * @return   :  String 
   */
  public FormFile getFileOne() {
    return fileOne;
  }

  /**
   * Purpose   : Sets the value of fileOne.
   * @param newFileOne Value of fileOne from the form
   */
  public void setFileOne(FormFile newFileOne) {
    fileOne = newFileOne;
  }
    
  public String toString(){
    String strTemp = "";
    Logger logger = Logger.getLogger("DbsLogger");
    if(logger.getLevel() == Level.DEBUG){
      strTemp += "\n\ttxtPath : " + txtPath;
    }
      return strTemp;
    }

  /**
   * Purpose   :  Returns  txtFileDesc.
   * @return   :  String 
   */
  public String getTxtFileDesc() {
    return txtFileDesc;
  }

  /**
   * Purpose   : Sets the value of txtFileDesc.
   * @param txtFileDesc Value of txtFileDesc from the form
   */
  public void setTxtFileDesc(String txtFileDesc) {
    this.txtFileDesc = txtFileDesc;
  }

  /**
   * Purpose   :  Returns  txtName.
   * @return   :  String 
   */
  public String getTxtName() {
    return txtName;
  }

  /**
   * Purpose   : Sets the value of txtName.
   * @param txtName Value of txtName from the form
   */
  public void setTxtName(String txtName) {
    this.txtName = txtName;
  }

  /**
   * Purpose   :  Returns  txaCSV.
   * @return   :  String 
   */
  public String getTxaCSV() {
    return txaCSV;
  }

  /**
   * Purpose   : Sets the value of txaCSV.
   * @param txaCSV Value of txaCSV from the form
   */
  public void setTxaCSV(String txaCSV) {
    this.txaCSV = txaCSV;
  }

  /**
   * Purpose   :  Returns  lstAttachment.
   * @return   :  String 
   */
  public String[] getLstAttachment() {
    return lstAttachment;
  }

  /**
   * Purpose   : Sets the value of lstAttachment.
   * @param lstAttachment Value of lstAttachment from the form
   */
  public void setLstAttachment(String[] lstAttachment) {
    this.lstAttachment = lstAttachment;
  }

  /**
   * Purpose   :  Returns  hdnTargetFolderPath.
   * @return   :  String 
   */
  public String getHdnTargetFolderPath() {
    return hdnTargetFolderPath;
  }

  /**
   * Purpose   : Sets the value of hdnTargetFolderPath.
   * @param hdnTargetFolderPath Value of hdnTargetFolderPath from the form
   */
  public void setHdnTargetFolderPath(String hdnTargetFolderPath) {
    this.hdnTargetFolderPath = hdnTargetFolderPath;
  }
}