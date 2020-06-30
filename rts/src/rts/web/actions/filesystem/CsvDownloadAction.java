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
/* Oracle API */
import oracle.ifs.beans.*;
/* Struts API */
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

/**
 * Purpose : To download CSV(s) of  timesheet(s) selected    
 * @author              Suved Mishra
 * @version             1.0
 * 	Date of creation:   16-08-2005
 * 	Last Modified by :     
 * 	Last Modified Date:    
*/

public class CsvDownloadAction extends Action  {

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
        logger.info("Downloading CSVs ...");
        
        //variable declaration
        ExceptionBean exceptionBean;
        String forward = "success";
        DbsDocument dbsDocument = null;
        DbsAttributeValue dbsAttrVal = null;
        
        try{
            FolderDocListForm folderDocListForm = (FolderDocListForm)form;
            logger.debug("folderDocListForm : " + folderDocListForm);
            HttpSession httpSession = request.getSession(false);
            UserInfo userInfo = (UserInfo)httpSession.getAttribute("UserInfo");
            FolderDocInfo folderDocInfo = (FolderDocInfo)httpSession.getAttribute("FolderDocInfo");
            
            DbsLibrarySession dbsLibrarySession = userInfo.getDbsLibrarySession();
            /*obtain selected doc-ids */
            Long []selectedDocIds= folderDocListForm.getChkFolderDocIds();
            logger.debug("selectedDocIds length: "+selectedDocIds.length);
            
            int selectedDocIdsLength = selectedDocIds.length;
            String csvToAppend = new String();
            /* the csv(s) of the timesheet(s) selected, would be appended to an
             * empty String */
            DbsPublicObject dbsPublicObject = null;
            for( int index =0; index < selectedDocIdsLength; index++ ){
              dbsPublicObject = dbsLibrarySession.getPublicObject(selectedDocIds[index]).getResolvedPublicObject();
              if( dbsPublicObject instanceof DbsDocument ){
                dbsDocument = (DbsDocument)dbsPublicObject;
                dbsAttrVal = dbsDocument.getAttribute("CSV");
                logger.debug("CSV: "+dbsAttrVal.getString(dbsLibrarySession));
                csvToAppend += dbsAttrVal.getString(dbsLibrarySession);
                logger.debug("csvToAppend: "+csvToAppend);
                csvToAppend +="\n";
              }
            }
            /* setup the corr. response */            
            String mimeType = "text/csv";
            logger.debug("dbsDocument.getFormat().getMimeType() : " + mimeType);
            /* set the response contentType to "text/csv" */
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition","filename=\""+ dbsLibrarySession.getUser().getName()+".csv" + "\"");           
            int contentSize = csvToAppend.length();
            logger.debug("Content Size : " + contentSize);
            response.setContentLength(contentSize);
            char[] content = new char[contentSize];
            content = csvToAppend.toCharArray();
            /* write the content in response */
            //OutputStream outputStream = response.getOutputStream();
            Writer writer = response.getWriter();
            writer.write(content);
            //outputStream.write(content);
            writer.close();
            //outputStream.close();
            response.flushBuffer();
            
            logger.info("Downloading CSVs Complete");
            
        }catch(DbsException dex){
            exceptionBean = new ExceptionBean(dex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }catch(Exception ex){
            exceptionBean = new ExceptionBean(ex);
            logger.error(exceptionBean.getMessage());
            saveErrors(request,exceptionBean.getActionErrors());
        }
        
        return null; //mapping.findForward(forward);
    }

}