
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<head>
<title><bean:message key="title.SetURLEcryptionDecryptionPassword" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<html:javascript formName="setEncryptionPasswordForm" dynamicJavascript="true" staticJavascript="false"/>
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
<script language="Javascript1.1" >

function cancelActionPerformed(){
    window.close();
}

function callPage(thisForm){ 
  if(validateSetEncryptionPasswordForm(thisForm)){
    if(thisForm.txtPassword.value!=thisForm.txtConfirmPassword.value){
       alert("<bean:message key="errors.password.mismatch"/>");  
       return false;
     }
    document.forms[0].target = opener.name;
    document.forms[0].submit();
    window.location.replace("blank.html");    
  }
}

function rename(){
    document.forms[0].target = opener.name;
    document.forms[0].submit();
    window.location.replace("blank.html");    
}
</script>
</head>
<body style="margin:0">
<!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->
<table id="errorContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
<logic:messagesPresent >
   <bean:message key="errors.header"/>
   <ul>
   <html:messages id="error">
      <li><bean:write name="error"/></li>
   </html:messages>
   </ul>
</logic:messagesPresent>
	</td>
  </tr>
</table>

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<br>
<tr>
<td align="center">
	<table id="tabParent" width="400px"  border="0" cellpadding="0" cellspacing="0">
		<tr>
    	<td>
			<table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">
      		<tr>
           <td width="5px" class="imgTabLeft"></td>
          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.SetEncryptionDecryptionPassword" /></div></td>
          <td width="5px" class="imgTabRight"></td>
      		</tr>
    		</table>
		</td>
  		</tr>
	</table>
	<table width="400px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" id="bdrColor_336633">
  <html:form action="/setURLEncryptPasswordAction" onsubmit="return validateSetEncryptionPasswordForm(this);">  
    <tr>
      <td width="398px" height="25px" align="center">
        <table width="95%"  border="0">
          <tr>
            <td width="33%">&nbsp;</td>
            <td width="67%">&nbsp;</td>
          </tr>
          <tr>
            
          </tr>
          <tr>
            <td ><div align="right"><bean:message key="txt.Password" /></div></td>
            <td><html:password name="setEncryptionPasswordForm" property="txtPassword" styleClass="bdrColor_336633 componentStyle " style="width:100%" redisplay="false" /></td>
          </tr>
          <tr>
            <td><div align="right"><bean:message key="txt.ConfirmPassword" /></div></td>
            <td><html:password name="setEncryptionPasswordForm" property="txtConfirmPassword" styleClass="bdrColor_336633 componentStyle" style="width:100%" redisplay="false" /></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="25px" align="center"> 
        <div align="right" style="width:95%">  
          <html:button property="btnOk" styleClass="buttons" style="width:70px" onclick="return callPage(this.form);" ><bean:message key="btn.Ok" /></html:button>
          <html:button property="btnCancel" styleClass="buttons" style="width:70px" onclick="cancelActionPerformed();" ><bean:message key="btn.Cancel" /></html:button>&nbsp; 
        </div>
      </td>
    </tr>
  </html:form>
</table>
<!-- borderClrLvl_2 table ends above-->
</td>
</tr>
	<tr><td height="2px"></td></tr>
	<tr>
      <td align="center">
      <table class="imgStatusBar bgColor_e8e8e8 bdrColor_336633 " height="20px" width="400px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
          <tr>
          <td><div class="imgStatusMsg"></div></td>
          <td>
            <html:messages id="msg" message="true">
            <bean:write  name="msg"/>
            </html:messages>
          </td>
          </tr>
      </table>
      <!-- statusBar table ends above-->
      </td>
  </tr>
</table>
<!-- tabContainer table ends above-->
</body>
</html:html>