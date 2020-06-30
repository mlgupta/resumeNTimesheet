package rts.web.actions.resumes;

import rts.beans.DbsException;
import rts.web.actionforms.resumes.ResumeDrawerListForm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.log4j.*;



public class ResumeDrawerMenuClickAction extends Action  {

public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  Logger logger = null;
  String target = new String("success");
  try{
    logger = Logger.getLogger("DbsLogger");
    logger.debug("Entering "+this.getClass().getName());
    ResumeDrawerListForm resumeDrawerListForm = (ResumeDrawerListForm)form;
    logger.debug("resumeDrawerListForm: "+resumeDrawerListForm);
  }catch( Exception ex ){
    logger.error("Exception occured...");
    target = new String("failure");
    ex.printStackTrace();
  }
  logger.debug("Exiting "+this.getClass().getName());
  return mapping.findForward(target);
}
}