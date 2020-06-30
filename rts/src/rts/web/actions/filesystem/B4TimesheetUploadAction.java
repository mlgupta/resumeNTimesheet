package rts.web.actions.filesystem;

import rts.beans.DbsAttributeValue;
import rts.beans.DbsDirectoryGroup;
import rts.beans.DbsException;
import rts.beans.DbsLibrarySession;
import rts.web.actionforms.filesystem.TimesheetUploadForm;
import rts.web.beans.filesystem.FolderDocInfo;
import rts.web.beans.user.*;

/* Java API */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* Struts API */
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.log4j.*;

/**
 * Purpose : To set specific values prior to timesheet uploading    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   09-08-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class B4TimesheetUploadAction extends Action  {

    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Initialize logger
        Logger logger = null;
        try {
          logger = Logger.getLogger("DbsLogger");
          logger.info("Serving JSP for Upload");
          TimesheetUploadForm timesheetUploadForm = new TimesheetUploadForm();
          HttpSession httpSession = request.getSession(false);
          FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute(
                                                                "FolderDocInfo");
          timesheetUploadForm.setTxtName("");
          timesheetUploadForm.setTxaCSV("");
          timesheetUploadForm.setTxtFileDesc("");

          String [] folderReferencesPath = new String[1];  
          folderReferencesPath[0] = folderDocInfo.getCurrentFolderPath();
          /* set the path where this doc is to be uploaded */
          timesheetUploadForm.setLstAttachment(null);  
          request.setAttribute("timesheetUploadForm",timesheetUploadForm);
        }catch (Exception ex) {
          ex.printStackTrace();
        }
        logger.info("Serving JSP for Upload Complete");
        return mapping.findForward("success");
    }



}