<%@ page errorPage="error_handler.jsp" %>

<%@ page import="rts.web.beans.filesystem.*" %>

<%@ page import="java.util.*" %>



<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="folderDocCheckOutForm" name="folderDocCheckOutForm" type="rts.web.actionforms.filesystem.FolderDocCheckOutForm" />

<html> 

<head>

<title><bean:message key="title.CheckOut" /> </title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="general.js"></script>

<link href="themes/main.css" rel="stylesheet" type="text/css" >

<script language="JavaScript" type="text/JavaScript">



//This is to submit the request for copying

function folderDocCheckOut(){

    document.forms[0].target = opener.name;

    document.forms[0].action="folderDocCheckOutAction.do";

    document.forms[0].submit();

    window.location.replace("blank.html");

}



</script>

</head>



<body>

<html:form name="folderDocCheckOutForm" action="folderDocCheckOutAction"  type="rts.web.actionforms.filesystem.FolderDocCheckOutForm" >

  <logic:iterate id="chkFolderDocId" property="chkFolderDocIds" name="folderDocCheckOutForm" >    

     <html:hidden property="chkFolderDocIds" value="<%=chkFolderDocId%>" />

  </logic:iterate>



<html:hidden property="chkFolderDocIds" value="<%=folderDocCheckOutForm.getChkFolderDocIds()[0]%>" />

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

<tr><td height="12px"></td></tr>

<tr>

<td >

	<table id="tabParent" width="500px" align="center" border="0" cellpadding="0" cellspacing="0">

		<tr>

    	<td>

			<table id="tab" width="110px" border="0" cellpadding="0" cellspacing="0">

      	<tr>

            <td width="5px" class="imgTabLeft"></td>

            <td width="100px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.CheckOut" /></div></td>

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

        <td valign="top">

        <div align="left"><bean:message key="lbl.Comment" /> :

        </div>

        </td>

      </tr>

      <tr>

        <td>

        <html:textarea property="txaComment" rows="5" style="width:480" styleClass="bdrColor_336633 componentStyle"></html:textarea>

        </td>

      </tr>

      <tr>

        <td colspan="2" height="30px" align="right">

            <html:button property="btnOk" onclick="folderDocCheckOut();" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>

            <html:button property="btnCancel" styleClass="buttons"  onclick='window.close()' style="width:70px"><bean:message key="btn.Cancel" /></html:button>

            <html:button property="btnHelp" styleClass="buttons" style="width:70px" onclick="openWindow('help?topic=document_versioned_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>            

        </td>

      </tr>

      </table>

    </td>

  </tr>

</table>

<!-- bdrColor_336633 table ends above-->

</td>

</tr>

</table>

<!-- tabContainer table ends above-->      





</html:form>

</body>

</html>

