<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title><bean:message key="title.Directory" /> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src="general.js" ></script>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
//This is to submit the request for creating new folder

function folderNew(thisForm){
    //alert(thisForm.hdnFolderName.value.length);
    
    if(thisForm.hdnFolderName.value==null || trim(thisForm.hdnFolderName.value)==""){
      alert("<bean:message key="msg.folderdoc.folderName.required"/>");
      thisForm.hdnFolderName.focus();
    }else if(thisForm.hdnFolderName.value.charCodeAt(thisForm.hdnFolderName.value.length-1) == 46 ){
      alert("<bean:message key="msg.folderdoc.folderName.inappropriate"/>");
      thisForm.hdnFolderName.focus();   
    }
    else {
      document.forms[0].target = opener.name;
      document.forms[0].submit();
      window.location.replace("blank.html");
    }
}

function enter(thisField,e){
  var i;
  i=handleEnter(thisField,e);
 	if (i==1) {
    return folderNew(thisField.form);
  }
}
</script>
</head>
<body style="margin:15px">
<html:form name="folderNewForm" action="folderNewAction"  type="rts.web.actionforms.filesystem.FolderNewForm" focus="hdnFolderName">
<table id="tabContainer" width="100%" border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
<td>
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
      <table width="100%" border="0" cellpadding="0" cellspacing="1" align="center" >
      <tr>
        <td height="17px" colspan="2" ></td>
      </tr>      
      <tr>
        <td align="right" width="15%">
          <bean:message key="lbl.Name" />
        </td>
        <td width="85%"> 
          <html:text property="hdnFolderName" style="width:400px;left-margin:4px" styleClass="bdrColor_336633 componentStyle" onkeypress="return enter(this,event);" ></html:text>
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
          <html:button property="btnOk" onclick="folderNew(this.form)" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
          <html:button property="btnCancel" styleClass="buttons"  onclick='window.close()' style="width:70px; margin-right:21px"><bean:message key="btn.Cancel" /></html:button>
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
