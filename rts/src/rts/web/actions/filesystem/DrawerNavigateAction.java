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
 * $Id: DrawerNavigateAction.java,v 1.2 2005/08/25 08:24:55 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.filesystem;
/* rts package references */
import rts.beans.*;
import rts.web.actionforms.filesystem.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;

public class DrawerNavigateAction extends Action  {
    /**
     * This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Initialize logger 
        Logger logger = Logger.getLogger("DbsLogger");
        logger.info("Navigate to different page");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = null;
        int pageNumber = 1;
        HttpSession httpSession = null;
        FolderDocInfo folderDocInfo = null;
        try{
            logger.debug("Initializing variables...");
            httpSession = request.getSession(false);
            DrawerListForm drawerListForm = (DrawerListForm)form;
            logger.debug("drawerListForm : " + drawerListForm);
            folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            pageNumber = drawerListForm.getTxtPageNo();
            logger.debug("pageNumber : " + pageNumber);
            folderDocInfo.setDrawerPageNumber(pageNumber);
            
            
            httpSession.setAttribute("IS_SIMPLE_NAVIGATION","TRUE");
/*            
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("msg.NextPageFetched");
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,actionMessage);
            httpSession.setAttribute("ActionMessages",actionMessages);
*/            

        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            logger.debug(exceptionBean.getErrorTrace());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        logger.info("Navigate to different page complete");
        //forward = ( folderDocInfo.getRtsType() == 1 )?"resume":"timesheet";
        switch (folderDocInfo.getRtsType()){
          case 3:
          forward= "resumeDrawer";
          break;
          
          case 4:
          forward= "timesheetDrawer";
          break;
          
          case 6:
          forward= "personalDrawer";
          break;

          default:
          break;
        }
        
        return mapping.findForward(forward);

    }
}
