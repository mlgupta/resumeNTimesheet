/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: FaxB4Action.java,v 1.2 2005/05/31 07:06:42 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.fax;


/**
 *	Purpose: To populate faxing.jsp
 *  @author              Mishra Maneesh 
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
/* rts package references */
import rts.web.actionforms.fax.FaxForm;
import rts.web.actionforms.filesystem.DocFaxForm;
import rts.beans.*;
import rts.web.beans.user.*;
import rts.web.beans.utility.*;
import rts.web.beans.scheduler.*;
//Java API
import java.io.*;
import java.util.*;
//Servlet API
import javax.servlet.*;
import javax.servlet.http.*;
//Struts API
import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.apache.struts.validator.*;


public class FaxB4Action extends Action {
    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
         Logger logger = Logger.getLogger("DbsLogger");
         logger.info("Preforming FaxB4Action ...");
         ActionErrors errors=new ActionErrors();
         String[] faxFileNames=null;
         try{   
             DocFaxForm docFaxForm = (DocFaxForm)form;
             logger.debug("docFaxForm : " + docFaxForm);
             HttpSession httpSession = request.getSession(false);
             UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            
             DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
             Long[] documentIds = docFaxForm.getChkFolderDocIds();
             if(documentIds!=null){
                 logger.debug("documentId : " + documentIds);
                 faxFileNames =new String[documentIds.length];
                 for(int index=0; index < faxFileNames.length ; index++){
                    faxFileNames[index]= dbsLibrarySession.getPublicObject(documentIds[index]).getAnyFolderPath();
                    logger.info(faxFileNames[index]);
                 }
             }
         }catch(DbsException dbsException){
                logger.error(dbsException);
                ActionError editError=new ActionError("errors.catchall",dbsException.getErrorMessage());
                errors.add(ActionErrors.GLOBAL_ERROR,editError);
            }catch(Exception exception){
                logger.error(exception);
                ActionError editError=new ActionError("errors.catchall",exception);
                errors.add(ActionErrors.GLOBAL_ERROR,editError);
            }
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
                return (mapping.getInputForward());
            }
        FaxForm faxForm=new FaxForm();
        if(faxFileNames!=null){
            faxForm.setLstAttachment(faxFileNames);
        }
        request.setAttribute("FaxForm",faxForm);
        return mapping.findForward("success");
    }
}
