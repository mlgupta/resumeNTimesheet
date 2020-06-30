<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="folderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<bean:define id="currentFolderPath" name="FolderDocInfo" property="currentFolderPath" type="String" />
<bean:define id="currentFolderId" name="FolderDocInfo" property="currentFolderId" />


<html:html>
<head>
<title><bean:message key="title.doc.generate.url" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css" >
<script src="general.js"></script>
<script>
  function closewindow(){
    document.forms[0].submit();
    window.location.replace("blank.html");
  }
</script>
</head>

<body style="margin:7px">

<html:form action="/docDoNothingAction" name="showContentForm" type="rts.web.actionforms.filesystem.ShowContentForm" >

<table id="tabContainer" align="center" width="95%" border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr><td height="5px"></td></tr>
<tr>
  <td >
    <table id="tabParent" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="5px" class="imgTabLeft"></td>
              <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Links" /></div></td>
              <td width="5px" class="imgTabRight"></td>            
            </tr>
          </table>
        </td>
  		</tr>
    </table>

<table id="bdrColor_336633" align="center" width="100%"  border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" >

<tr><td height="15px" colspan="2"></td></tr>
  
  <tr>
    
    <td align="center"  colspan="2">
    
    <div style="overflow:auto;width:90%;height:135px" align="center" class="bgColor_FFFFFF bdrColor_336633">
        
        <table width="95%"  style="margin:3px" align="left" border="0"  cellpadding="1" cellspacing="1" id="innerTabContainer">
          
          <logic:iterate id="folderDocList" name="DocLinkLists" type="rts.web.beans.filesystem.FolderDocList" > 
          
          <bean:define id="txtLinkGenerated" name="folderDocList" property="txtLinkGenerated" />
          
          <tr class="trD7EFD7" >            
              <td align="right" height="19px" width="20%" nowrap="nowrap" ><bean:message key="lbl.DocumentName" /></td> 
              <td width="80%"><a target="viewContent" class="menu" style="margin-left:2px;" href="<%=txtLinkGenerated%>"/><bean:write name="folderDocList" property="name" /></a></td>
          </tr>
          
          <tr class="trF7F7F7" >
              <td align="right" height="19px"><bean:message key="lbl.Link"/></td>
              <td><html:text property="txtLinkGenerated" styleClass="componentStyle bdrColor_336633" style="width:440px; margin-left:2px;" value="<%=txtLinkGenerated%>" readonly="true" /></td>
          </tr>
          
          </logic:iterate>
        
        </table>
    
    </div>
    
    </td>
  
  </tr>
  
  <tr>
    <td height="30px" width="68%">&nbsp;</td>
    <td align="right" height="30px" width="32%" >
      <html:button property="btnOK" onclick="closewindow()" styleClass="buttons" style="width:70px;margin-right:30px"><bean:message key="btn.Ok" /></html:button>
    </td>
  </tr>

</table>

</td>

</tr>

</table>

</html:form>

</body>

</html:html>