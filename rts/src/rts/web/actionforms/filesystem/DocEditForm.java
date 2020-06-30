package rts.web.actionforms.filesystem;

import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
  //Struts API
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.log4j.*;
import org.apache.struts.validator.ValidatorForm;

public class DocEditForm extends ValidatorForm  {
  private FormFile fileOne;                   /* form-file obj for file to be uploaded */
  private String txtPath;                     /* folder path for file upload */
  private String txtFileDesc;                 /* file description to be set */    
  private String txtName;                     /* name of resume holder */
  private String txtEmail;                    /* email-Id of resume holder */
  private String txtPhone1;                   /* phone(mandatory) number of resume holder */
  private String txtPhone2;                   /* phone(optional) number */
  private String txtCommunicationSkill;       /* comm skills of resume holder */
  private String txaAddress;                  /* address of resume holder */    
  private String txtAvailDate;                /* availability date */      
  private String txtBillingRate;              /* billing rate */
  private String txtCustom1Lbl;               /* custom label fetched from group */
  private String txtCustom2Lbl;               /* custom label fetched from group */
  private String txtCustom3Lbl;               /* custom label fetched from group */
  private String txtCustom4Lbl;               /* custom label fetched from group */
  private String txtCustom1Desc;              /* value of cust. label 1 */
  private String txtCustom2Desc;              /* value of cust. label 2 */
  private String txtCustom3Desc;              /* value of cust. label 3 */
  private String txtCustom4Desc;              /* value of cust. label 4 */
  private String[] lstAttachment;             /* list of folder paths to share resume */
  private String hdnTargetFolderPath;         /* control to be set for selecting folders */
  private String txaGrab;
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
      strTemp += "\n\ttxtName : " +txtName;
      strTemp += "\n\ttxtFileDesc : " +txtFileDesc;
      strTemp += "\n\ttxtEmail : " +txtEmail;
      strTemp += "\n\ttxtPhone1 : " +txtPhone1;
      strTemp += "\n\ttxaAddress : " +txaAddress;
      strTemp += "\n\ttxtCustom1Lbl : "+txtCustom1Lbl;
      strTemp += "\n\ttxtCustom2Lbl : "+txtCustom2Lbl;
      strTemp += "\n\ttxtCustom3Lbl : "+txtCustom3Lbl;
      strTemp += "\n\ttxtCustom4Lbl : "+txtCustom4Lbl;
      strTemp += "\n\thdnTargetFolderPath : "+hdnTargetFolderPath;
      /*if( lstAttachment== null || lstAttachment.length <= 1 ){
        strTemp += "\n\tlstAttachment : "+lstAttachment;  
      }else{
        for( int index = 0; index < lstAttachment.length; index++ ){
          strTemp += "\n\tlstAttachment["+index+"] : "+lstAttachment[index];
        }
      }*/
      
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
   * Purpose   :  Returns  txtEmail.
   * @return   :  String 
   */
  public String getTxtEmail()  {
    return txtEmail;
  }

  /**
   * Purpose   : Sets the value of txtEmail.
   * @param txtEmail Value of txtEmail from the form
   */
  public void setTxtEmail(String txtEmail) {
    this.txtEmail = txtEmail;
  }

  /**
   * Purpose   :  Returns  txtPhone1.
   * @return   :  String 
   */
  public String getTxtPhone1() {
    return txtPhone1;
  }

  /**
   * Purpose   : Sets the value of txtPhone1.
   * @param txtPhone1 Value of txtPhone1 from the form
   */
  public void setTxtPhone1(String txtPhone1) {
    this.txtPhone1 = txtPhone1;
  }

  /**
   * Purpose   :  Returns  txtPhone2.
   * @return   :  String 
   */
  public String getTxtPhone2() {
    return txtPhone2;
  }

  /**
   * Purpose   : Sets the value of txtPhone2.
   * @param txtPhone2 Value of txtPhone2 from the form
   */
  public void setTxtPhone2(String txtPhone2) {
    this.txtPhone2 = txtPhone2;
  }

  /**
   * Purpose   :  Returns  txtCommunicationSkill.
   * @return   :  String 
   */
  public String getTxtCommunicationSkill() {
    return txtCommunicationSkill;
  }

  /**
   * Purpose   : Sets the value of txtCommunicationSkill.
   * @param txtCommunicationSkill Value of txtCommunicationSkill from the form
   */
  public void setTxtCommunicationSkill(String txtCommunicationSkill) {
    this.txtCommunicationSkill = txtCommunicationSkill;
  }

  /**
   * Purpose   :  Returns  txaAddress.
   * @return   :  String 
   */
  public String getTxaAddress() {
    return txaAddress;
  }

  /**
   * Purpose   : Sets the value of txaAddress.
   * @param txaAddress Value of txaAddress from the form
   */
  public void setTxaAddress(String txaAddress) {
    this.txaAddress = txaAddress;
  }

  /**
   * Purpose   :  Returns  txtAvailDate.
   * @return   :  String 
   */
  public String getTxtAvailDate() {
    return txtAvailDate;
  }

  /**
   * Purpose   : Sets the value of txtAvailDate.
   * @param txtAvailDate Value of txtAvailDate from the form
   */
  public void setTxtAvailDate(String txtAvailDate) {
    this.txtAvailDate = txtAvailDate;
  }

  /**
   * Purpose   :  Returns  txtBillingRate.
   * @return   :  String 
   */
  public String getTxtBillingRate() {
    return txtBillingRate;
  }

  /**
   * Purpose   : Sets the value of txtBillingRate.
   * @param txtBillingRate Value of txtBillingRate from the form
   */
  public void setTxtBillingRate(String txtBillingRate) {
    this.txtBillingRate = txtBillingRate;
  }

  /**
   * Purpose   :  Returns  txtCustom1Lbl.
   * @return   :  String 
   */
  public String getTxtCustom1Lbl() {
    return txtCustom1Lbl;
  }

  /**
   * Purpose   : Sets the value of txtCustom1Lbl.
   * @param txtCustom1Lbl Value of txtCustom1Lbl from the form
   */
  public void setTxtCustom1Lbl(String txtCustom1Lbl) {
    this.txtCustom1Lbl = txtCustom1Lbl;
  }

  /**
   * Purpose   :  Returns  txtCustom2Lbl.
   * @return   :  String 
   */
  public String getTxtCustom2Lbl() {
    return txtCustom2Lbl;
  }

  /**
   * Purpose   : Sets the value of txtCustom2Lbl.
   * @param txtCustom2Lbl Value of txtCustom2Lbl from the form
   */
  public void setTxtCustom2Lbl(String txtCustom2Lbl) {
    this.txtCustom2Lbl = txtCustom2Lbl;
  }

  /**
   * Purpose   :  Returns  txtCustom3Lbl.
   * @return   :  String 
   */
  public String getTxtCustom3Lbl() {
    return txtCustom3Lbl;
  }

  /**
   * Purpose   : Sets the value of txtCustom3Lbl.
   * @param txtCustom3Lbl Value of txtCustom3Lbl from the form
   */
  public void setTxtCustom3Lbl(String txtCustom3Lbl) {
    this.txtCustom3Lbl = txtCustom3Lbl;
  }

  /**
   * Purpose   :  Returns  txtCustom1Desc.
   * @return   :  String 
   */
  public String getTxtCustom1Desc() {
    return txtCustom1Desc;
  }

  /**
   * Purpose   : Sets the value of txtCustom1Desc.
   * @param txtCustom1Desc Value of txtCustom1Desc from the form
   */
  public void setTxtCustom1Desc(String txtCustom1Desc) {
    this.txtCustom1Desc = txtCustom1Desc;
  }

  /**
   * Purpose   :  Returns  txtCustom2Desc.
   * @return   :  String 
   */
  public String getTxtCustom2Desc() {
    return txtCustom2Desc;
  }

  /**
   * Purpose   : Sets the value of txtFolderOrDocName.
   * @param txtCustom2Desc Value of txtCustom2Desc from the form
   */
  public void setTxtCustom2Desc(String txtCustom2Desc) {
    this.txtCustom2Desc = txtCustom2Desc;
  }

  /**
   * Purpose   :  Returns  txtCustom3Desc.
   * @return   :  String 
   */
  public String getTxtCustom3Desc() {
    return txtCustom3Desc;
  }

  /**
   * Purpose   : Sets the value of txtCustom3Desc.
   * @param txtCustom3Desc Value of txtCustom3Desc from the form
   */
  public void setTxtCustom3Desc(String txtCustom3Desc) {
    this.txtCustom3Desc = txtCustom3Desc;
  }

  /**
   * Purpose   :  Returns  txtCustom4Lbl.
   * @return   :  String 
   */
  public String getTxtCustom4Lbl() {
    return txtCustom4Lbl;
  }

  /**
   * Purpose   : Sets the value of txtCustom4Lbl.
   * @param txtCustom4Lbl Value of txtCustom4Lbl from the form
   */
  public void setTxtCustom4Lbl(String txtCustom4Lbl) {
    this.txtCustom4Lbl = txtCustom4Lbl;
  }

  /**
   * Purpose   :  Returns  txtCustom4Desc.
   * @return   :  String 
   */
  public String getTxtCustom4Desc() {
    return txtCustom4Desc;
  }

  /**
   * Purpose   : Sets the value of txtCustom4Desc.
   * @param txtCustom4Desc Value of txtCustom4Desc from the form
   */
  public void setTxtCustom4Desc(String txtCustom4Desc) {
    this.txtCustom4Desc = txtCustom4Desc;
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

  public String getTxaGrab() {
    return txaGrab;
  }

  public void setTxaGrab(String txaGrab) {
    this.txaGrab = txaGrab;
  }
}