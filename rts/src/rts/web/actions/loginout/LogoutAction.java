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
 * $Id: LogoutAction.java,v 1.1.1.1 2005/05/26 12:32:37 suved Exp $
 *****************************************************************************
 */
package rts.web.actions.loginout; 
/* rts package references */
import rts.beans.*;
import rts.web.beans.exception.*;
import rts.web.beans.filesystem.*;
import rts.web.beans.user.*;
/* Java API */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
 
public class LogoutAction extends Action  {
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
        logger.info("Entering : " + this.getClass().getName() + "." + "execute");
        
        //variable declaration
        ExceptionBean exceptionBean = null;        
        HttpSession httpSession = null;        
        try{
            httpSession = request.getSession(false);
            logger.debug("httpSession id to be invalidated is: "+httpSession.getId());
            ServletContext context=request.getSession().getServletContext();
            logger.debug("httpSession id  removed from Context= "+httpSession.getId());
            context.removeAttribute(((UserInfo)httpSession.getAttribute("UserInfo")).getUserID());
            httpSession.invalidate();
            
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.info(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        
        logger.info("Exitting : " + this.getClass().getName() + "." + "execute");
        return mapping.findForward("success");
    }

}
    
