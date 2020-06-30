<%@ page import="rts.web.beans.user.UserPreferences" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="FolderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />

<html:html>
  <head>
    <title><bean:message key="title.AddTimesheet" /></title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    <html:javascript formName="timesheetUploadForm" dynamicJavascript="true" staticJavascript="false"/>
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script language="JavaScript" type="text/JavaScript">
    <!--
    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }
    
    //-->
    </script>
    <script src="general.js"></script>

    <script name="javascript">
      
      function timesheetUpload(){
        var thisForm=document.forms[0];
          if(validateTimesheetUploadForm(thisForm)){
            for(index = 0 ; index < thisForm.lstAttachment.length ;index++){     
              thisForm.lstAttachment[index].selected = true;    
            }  
            thisForm.submit();
          }
      }
      
      function cancelUpload(){
        document.forms[0].action = "timesheetListAction.do";
        document.forms[0].submit();
      }
      
      function cancelUploadForTree(){
        document.forms[0].action = "timesheetTreeListAction.do";
        document.forms[0].submit();
      }

      function removeFromList(thisForm){
        var index=0;
        var length=thisForm.lstAttachment.length;
        while(index <length){     
          if(thisForm.lstAttachment[index].selected){
            if(thisForm.lstAttachment[index].value!="WORLD [G]"){
             thisForm.lstAttachment.remove(index);
             length=thisForm.lstAttachment.length;   
            }else{
              alert("<bean:message key="msg.world.delete"/>");
              index++;
            }
          }else{
            index++;
          }
         }
      }
  
      function attachonclick(){
        openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.ShareWith" />&foldersOnly=true&openerControl=lstAttachment&recreate=true',"ShareWith",495,390,0,0,true);
      }
      
    </script>
  </head>
  <body>
    <html:form action="/newTimesheetUploadAction" enctype="multipart/form-data" focus="txtName" name ="timesheetUploadForm" type="rts.web.actionforms.filesystem.TimesheetUploadForm" >
    <html:hidden property="txtPath" value="<%=FolderDocInfo.getCurrentFolderPath()%>" />
      <table id="4outermostBorder" class="bdrColor_336633" width="1000px" height="650px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" height="94px">
            <!--Header Starts-->
            <%if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ){%>
              <jsp:include page="/header.jsp" />
            <%}else{%>
              <jsp:include page="/headerForTreeNav.jsp" />
            <%}%>
            <!--Header Ends-->
          </td>
        </tr>
        <tr>
          <td height="px" align="center" valign="top" class="bgColor_e8e8e8">
            <table width="750px" border="0" cellpadding="1" cellspacing="1" class="bdrColor_336633" style="margin-top:20px; margin-bottom:20px">
              <tr>
                <td colspan="3" align="right" valign="top">&nbsp;</td>
              </tr>
              <tr>
                <td width="110" align="right"><span class="textRed">* </span><bean:message key="txt.Name" />:</td>
                <td width="631">
                  <html:text name="timesheetUploadForm" property="txtName" styleClass="bdrColor_336633  componentStyle" style="width:560px; height:18px" tabindex="1" onkeypress="return alphanumeric(event);" />
                </td>
              </tr>
              <tr>
                <td align="right" valign="top"><bean:message key="txt.CSV" />:</td>
                <td>
                  <html:textarea name="timesheetUploadForm" property="txaCSV" styleClass="bdrColor_336633  componentStyle" style="width:560px; height:80px" tabindex="2" />
                </td>
              </tr>
              <tr>
                <td align="right" valign="top"><bean:message key="txt.Description" />:</td>
                <td>
                  <html:textarea name="timesheetUploadForm" property="txtFileDesc" styleClass="bdrColor_336633  componentStyle" style="width:560px; height:80px" tabindex="3" />
                </td>
              </tr>
              
              <tr>
                <td align="right" height="40px"><span class="textRed">* </span><bean:message key="txt.Timesheet" />:</td>
                <td colspan="2">
                  <html:file name="timesheetUploadForm" property="fileOne" size="95" styleClass="bdrColor_336633"  onkeypress="return false;" tabindex="4" ><bean:message key="btn.Browse" /></html:file>
                </td>
              </tr>
              <tr>
                <td></td>
                <td colspan="2"></td>          
              </tr>
              <tr>
                <td align="right" height="40px" valign="top"><bean:message key="lbl.ShareWith" />:</td>
                <td colspan="2" valign="top">
                  <table width="590px" height="50px" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="563px" rowspan="2" height="50px">
                        <html:select name="timesheetUploadForm" property="lstAttachment" size="4" multiple="true" style="width:563px;" styleClass="bdrColor_336633 componentStyle">
                          <logic:present name="timesheetUploadForm" property="lstAttachment">
                            <html:options name="timesheetUploadForm" property="lstAttachment" />
                          </logic:present>
                        </html:select>
                      </td>
                      <td width="27px" height="25px" valign="top">
                        <html:button property="btnAttach" onclick="attachonclick();" styleClass="buttons" style="width:20px;" tabindex="5" title="Attach">+</html:button>
                      </td>
                    </tr>
                    <tr>
                      <td valign="bottom" height="25px">
                        <html:button property="btnRemove" onclick="removeFromList(this.form);" styleClass="buttons" style="width:20px;" tabindex="6" title="Remove">-</html:button>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="right" height="30px"></td>
                <td colspan="2" align="right">
                  <html:button property="btnSave" onclick="timesheetUpload();" styleClass="buttons" style="width:70px" tabindex="7"><bean:message key="btn.Save" /></html:button>
                  <%if(userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION){%>
                    <html:button property="btnCancel" onclick="return cancelUpload();" styleClass="buttons" style="width:70px;margin-right:70px" tabindex="8"><bean:message key="btn.Cancel" /></html:button>
                  <%}else{%>
                    <html:button property="btnCancel" onclick="return cancelUploadForTree();" styleClass="buttons" style="width:70px;margin-right:70px" tabindex="8"><bean:message key="btn.Cancel" /></html:button>                
                  <%}%>
                </td>
              </tr>
            </table>		
          </td>
        </tr>
      </table>
      <table align="center" width="1000px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>