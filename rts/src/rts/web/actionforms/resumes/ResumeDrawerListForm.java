package rts.web.actionforms.resumes;


import org.apache.struts.action.ActionForm;
import org.apache.log4j.*;

public class ResumeDrawerListForm extends ActionForm  {

  private boolean forwardToListing= true;
  
  public void setForwardToListing(boolean newforwardToListing){
    this.forwardToListing = newforwardToListing;
  }
  
  public boolean isForwardToListing(){
    return forwardToListing;
  }
  
  public String toString(){
    String strTemp = "";
    strTemp += "\n\tforwardToListing: "+forwardToListing;
    return strTemp;
  }
}