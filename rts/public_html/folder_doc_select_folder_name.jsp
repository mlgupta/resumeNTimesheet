<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title><bean:message key="title.Directory" /> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script src="general.js" ></script>
<script language="JavaScript" type="text/JavaScript">
//This is to submit the request for creating new folder
function folderNew(){
    document.forms[0].target = opener.name;
    window.location.replace("blank.html");
    document.forms[0].submit();
}
</script>
</head>
<body style="margin:15px" >
<bean:define id="folderDocSelectForm"  name="folderDocSelectForm" type="rts.web.actionforms.filesystem.FolderDocSelectForm" />
<html:form name="folderDocSelectForm" action="/folderDocSelectNewFolderAction"  type="rts.web.actionforms.filesystem.FolderDocSelectForm" focus="hdnFolderName" >
<html:hidden name="folderDocSelectForm" property="hdnFoldersOnly" value="<%=folderDocSelectForm.isHdnFoldersOnly()%>" />
<html:hidden name="folderDocSelectForm" property="hdnOpenerControl" value="<%=folderDocSelectForm.getHdnOpenerControl()%>"/>
<html:hidden name="folderDocSelectForm" property="hdnFolderDocument" value="<%=folderDocSelectForm.getHdnFolderDocument()%>" />

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
<td >
	<table id="tabParent" width="500px" align="center" border="0" cellpadding="0" cellspacing="0">
		<tr>
    	<td>
			<table id="tab" width="140px" border="0" cellpadding="0" cellspacing="0">
      	<tr>
            <td width="5px" class="imgTabLeft"></td>
            <td width="130px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.directory" /></div></td>
            <td width="5px" class="imgTabRight"></td>   
      	</tr>
    	</table>
		</td>
  		</tr>
	</table>
<table id="bdrColor_336633" align="center" width="500px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" >
  <tr>
    <td >
      <table width="97%" border="0" cellpadding="0" cellspacing="1" align="center" >
      <tr>
        <td height="10px" colspan="2" ></td>
      </tr>      
      <tr>
        <td height="5px" colspan="2" ></td>
      </tr>      
      <tr>
        <td valign="top" width="15%" >
        <div align="right">
          <bean:message key="lbl.Name" />
        </div>
        </td>
        <td align="left" width="85%" > 
          <html:text property="hdnFolderName" style="width:395px;leftt-margin:4px;" styleClass="bdrColor_336633 componentStyle"></html:text>
        </td>
      </tr>
      <tr>
        <td align="right">
          <bean:message key="txt.ItemDesc" />
        </td>
        <td> 
          <html:text property="hdnFolderDesc" style="width:400px;left-margin:4px" styleClass="bdrColor_336633 componentStyle" onkeypress="return enter(this,event);" ></html:text>
        </td>
      </tr>
      <tr>
        <td colspan="2" height="30px" align="right">
            <html:button property="btnOk" onclick="folderNew()" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
            <html:button property="btnCancel" styleClass="buttons"  onclick='window.close()' style="width:70px"><bean:message key="btn.Cancel" /></html:button>
            <html:button property="btnHelp" styleClass="buttons" style="width:70px;margin-right:15px" onclick="openWindow('help?topic=folder_new_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>                                                        
        </td>
      </tr>
      </table>
    </td>
  </tr>
</table>
<!-- borderClrLvl_2 table ends above-->
</td>
</tr>
</table>
<!-- tabContainer table ends above-->      
</html:form>
</body>
</html>
