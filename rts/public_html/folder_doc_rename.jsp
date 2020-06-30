<%@ page errorPage="error_handler.jsp" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
 
<%@ page import="java.util.*" %>
<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>

<% 

//request.setAttribute("topic","folder_doc_introduction_html");

int rowsInList = 0;
ArrayList folderDocRenameLists = (ArrayList)request.getAttribute("folderDocRenameLists");
if(folderDocRenameLists != null){
  rowsInList = folderDocRenameLists.size();
}
%>

<html:html>
<head>
<title><bean:message key="title.RenameDocument" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css" >
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src="general.js" ></script>
<script name = "javascript">

function rename(){
  var emptyTxtName=validateRename();
  var nameStartsIllegally = validateName();
  
  if (emptyTxtName){
      alert("<bean:message key="msg.folderdoc.rename.required"/>" + " " + emptyTxtName.alt);  
      emptyTxtName.focus();
    }else if( nameStartsIllegally){
      alert("<bean:message key="msg.folderdoc.folderName.inappropriate"/>");
      nameStartsIllegally.focus();       
    }
    else{
      document.forms[0].target = opener.name;
      document.forms[0].submit();
      window.location.replace("blank.html");    
    }
}

function validateName(){
  var thisForm=document.forms[0];
  var rowsInList = <%=rowsInList%>;
  
  if (rowsInList == 1){
  if (thisForm.txtNewName.length != "undefined"){
    for(var i=0;i<thisForm.txtNewName.length;i++){
      if (thisForm.txtNewName[i].value.charCodeAt(thisForm.txtNewName[i].value.length-1) == 46){
        return thisForm.txtNewName[i]
      }
    } 
  }else{
    if (thisForm.txtNewName.value.charCodeAt(thisForm.txtNewName.value.length-1) == 46){
        return thisForm.txtNewName
    }
  }
  }
  if(rowsInList > 1){
  
  if (typeof thisForm.txtNewName.length != "undefined"){
  
    for(var i=0;i<thisForm.txtId.length;i++){
      if (thisForm.txtNewName[i].value.charCodeAt(thisForm.txtNewName[i].value.length-1) == 46){
  
        return thisForm.txtNewName[i]
      }
  
    } 
  }else{
  
    if (thisForm.txtNewName.value.charCodeAt(thisForm.txtNewName.value.length-1) == 46){
  
        return thisForm.txtNewName
    }
  }
  }
  return false;

}
function validateRename(){
  var thisForm=document.forms[0];
  var rowsInList = <%=rowsInList%>;
  
  if (rowsInList == 1){
  if (typeof thisForm.txtNewName.length != "undefined"){
    for(var i=0;i<thisForm.txtNewName.length;i++){
      if (trim(thisForm.txtNewName[i].value)==""){
        return thisForm.txtNewName[i]
      }
    } 
  }else{
    if (trim(thisForm.txtNewName.value)==""){
        return thisForm.txtNewName
    }
  }
  }
  if(rowsInList > 1){
  
  if (typeof thisForm.txtNewName.length != "undefined"){
  
    for(var i=0;i<thisForm.txtId.length;i++){
      if (trim(thisForm.txtNewName[i].value)==""){
        return thisForm.txtNewName[i]
      }
    } 
  }else{
    if (trim(thisForm.txtNewName.value)==""){
        return thisForm.txtNewName
    }
  }
  }
  return false;
}

function enter(thisField,e){
  var i;
  i=handleEnter(thisField,e);
 	if (i==1) {
    return rename();
  }
}

</script>
</head>
<body style="margin:15px" >
<html:form  action="/folderDocRenameAction" name="folderDocRenameForm" type="rts.web.actionforms.filesystem.FolderDocRenameForm" >

<table id="tabParent" align="center"  width="500px"  border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td>
	<table width="200px" border="0" cellpadding="0" cellspacing="0" id="tab">
      <tr>
        <td width="5px" class="imgTabLeft"></td>
        <td width="190px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.RenameDocumentFolder" /></div></td>
        <td width="5px" class="imgTabRight"></td>
      </tr>
    </table>
	</td>
  </tr>
</table>

<table width="500px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgClrLvl_4 borderClrLvl_2" id="borderClrLvl_2">
  <tr>
    <td>
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0" id="innerContainer">
  <tr>
    <td height="10px"></td>
  </tr>
  <tr>
    <td align="center">
    <div style="overflow:auto;width:100%;height:125px" class="bgClrLvl_4 borderClrLvl_2">
      <logic:iterate id="folderDocList" name="folderDocRenameLists" type="rts.web.beans.filesystem.FolderDocList" >
        <bean:define id="name" name="folderDocList" property="name" />
        <bean:define id="description" name="folderDocList" property="description" />
        <bean:define id="id" name="folderDocList" property="id" />

          <table class="bgClrLvl_F" width="100%"  border="0" cellspacing="0" cellpadding="1">
            <tr height="3px" class="bgClrLvl_4"><td colspan="2"></td></tr>
            <tr class="bgClrLvl_3">
              <td width="20%"><div align="right"><bean:message key="lbl.OldName" /></div></td>
              <td width="80%"><bean:write name="folderDocList" property="name" /></td>
            </tr>

            <tr class="bgClrLvl_3">
              <td><div align="right"><bean:message  key="txt.RenameTo" /></div></td>
              <td>
              <html:text property="txtNewName" styleClass="borderClrLvl_2 componentStyle" style="width:350px" value="<%=name%>" tabindex="1" alt="<%=name%>" onkeypress="return enter(this,event);" /> 
              <html:hidden property="txtId" value="<%=id%>" />
              </td>
            </tr>
            <tr class="bgClrLvl_3">
              <td><div align="right"><bean:message  key="txt.ItemDesc" /></div></td>
              <td>
                <html:text property="txtNewDesc" styleClass="borderClrLvl_2 componentStyle" style="width:350px" value="<%=description%>" tabindex="2" alt="<%=description%>" onkeypress="return enter(this,event);" />
              </td>
            </tr>

           <tr height="3px" class="bgClrLvl_4"><td colspan="2"></td></tr>
          </table>
      </logic:iterate>

      <input type="text" name="txtDummy" id="txtDummy" style="display:none">
    </div>  
  </td>
  <tr>
    <td align="center" height="30px" >
    <div align="right">
      <html:button property="btnOk" onclick="rename()" styleClass="buttons" style="width:70px" tabindex="2" ><bean:message key="btn.Ok" /></html:button>
      <html:button property="btnCancel" styleClass="buttons" onclick="window.close();" style="width:70px" tabindex="3" ><bean:message key="btn.Cancel" /></html:button> 
    </div>
    </td>
  </tr>
</table>
</td>
  </tr>
</table>
  <!-- borderClrLvl_2 table ends above-->
</html:form> 
</body>
</html:html>