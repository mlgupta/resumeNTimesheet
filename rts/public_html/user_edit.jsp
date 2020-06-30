<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="rts.web.beans.user.UserPreferences" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />

<html:html> 

<head>

<title><bean:message key="title.UserEdit" /></title>

<link href="themes/main.css" rel="stylesheet" type="text/css" />

<script src="general.js"></script>

<html:javascript formName="userNewEditForm" dynamicJavascript="true" staticJavascript="false"/>

<script language="Javascript1.1" src="staticJavascript.jsp"></script>

<script language="Javascript1.1" >

  function callPage(thisForm){ 

    /*for(index = 0 ; index < thisForm.lstGroupOrUserList.length ;index++){     

      thisForm.lstGroupOrUserList[index].selected=true;    

    }*/

    if(validateUserNewEditForm(thisForm)){

      if(thisForm.txtPassword.value!=thisForm.txtConfirmPassword.value){

        alert("<bean:message key="errors.password.mismatch"/>"); 

        thisForm.txtPassword.value=""; 

        thisForm.txtConfirmPassword.value="";

        return false;

      }     

      /*if ((thisForm.radQuota[0].checked==true) && (thisForm.txtQuota.value=="")) {

        alert("<bean:message key="errors.user.contentQuota"/>"); 

        return false;

      }     */

      thisForm.submit() ;

    }

  }



  function removeFromList(thisForm){

    var index=0;

    var length=thisForm.lstGroupOrUserList.length;

    while(index <length){     

      if(thisForm.lstGroupOrUserList[index].selected){

        thisForm.lstGroupOrUserList.remove(index);

        length=thisForm.lstGroupOrUserList.length;   

      }else{

        index++;

      }

    }

  }



  function cancelActionPerformed(thisForm){

     thisForm.action="relayAction.do?operation=cancel_user";

     thisForm.submit();  

  }


</script>

</head>

<body style="margin:0">

<html:form action="/userEditAdminAction" focus="txtUserName"

         onsubmit="return validateUserNewEditForm(this);" >

<!-- This page contains 3 outermost tables, named 'headerIncluder', 'errorContainer' and 'tabContainer' -->

<table id="headerIncluder" width="100%"  border="0" cellspacing="0" cellpadding="0">

  <tr>

    <td height="95px">

      <!--Header Starts-->
      <%if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ){%>
        <jsp:include page="/header.jsp" />
      <%}else{%>
        <jsp:include page="/headerForTreeNav.jsp" />
      <%}%>
      <!--Header Ends-->

	</td>

  </tr>

</table>

<br>

<!--Content Starts-->

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'borderClrLvl_2' -->

<tr>

<td align="center">



<table id="tabParent" width="725px"  border="0" cellpadding="0" cellspacing="0" >

  <tr>

    <td>

      <table width="150" border="0" cellpadding="0" cellspacing="0" id="tab">

        <tr>

          <td width="5px" class="imgTabLeft"></td>

          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.EditUser" /></div></td>

          <td width="5px" class="imgTabRight"></td>

        </tr>

      </table>

    </td>

  </tr>

</table>



<table id="borderClrLvl_2" width="725px" border="0" cellpadding="0" cellspacing="0" class="bgClrLvl_4 borderClrLvl_2" >

      <tr>

        <td align="center" height="10px"></td>

      </tr>

      <tr>

        <td align="center" >


          <table width="700px" border="0" id="general" cellpadding="2" cellspacing="2">

            <tr>

              <td width="25%"><div align="right"><bean:message key="txt.UserName" /></div></td>

              <td width="25%"><html:text  property="txtUserName" styleClass="borderClrLvl_2 componentStyle componentStyleDisable" style="width:200px" maxlength="20" tabindex="1" readonly="true" /></td>


              <td width="15%"><div align="right"><bean:message key="txa.Description" /></div></td>
              <td width="35%" rowspan="4">
                <html:textarea name="userNewEditForm" property="txaDescription" styleClass="borderClrLvl_2 componentStyle" style="width:250px; height:100px" tabindex="5" />
              </td>
              
            </tr>
            
            <tr>
              <td width="14%"><div align="right"><bean:message key="txt.Password" /></div></td>

              <td width="20%"><html:password  property="txtPassword" styleClass="borderClrLvl_2 componentStyle" style="width:200px"  redisplay="false" maxlength="20" tabindex="2"  value="PasswordNotChanged" /></td>
            
              <td width="20%">&nbsp;</td>
              </tr>

            <tr>
              <td width="14%"><div align="right"><bean:message key="txt.ConfirmPassword" /></div></td>

              <td width="20%"><html:password  property="txtConfirmPassword" styleClass="borderClrLvl_2 componentStyle" style="width:200px"  redisplay="false" maxlength="20" tabindex="3"  value="PasswordNotChanged" /></td>
            
              <td width="20%">&nbsp;</td>
              </tr>
            
            <tr>

              <td width="14%">

                <div align="right"><bean:message key="txt.EmailAddress" /> </div></td>

              <td valign="top">

                  <html:text name="userNewEditForm" property="txtEmailAddress" styleClass="borderClrLvl_2 componentStyle" style="width:200px" tabindex="4" />

              </td>

              <td valign="top">&nbsp;</td>
              </tr>


        </table>

      </td>

      </tr>

      <tr>
  
        <td height="10px">
  
        </td>
  
      </tr>


      <tr>

        <td height="35px" align="right" valign="top">

          <html:button property="btnSave" styleClass="buttons" style="width:70px" onclick="return callPage(this.form)" tabindex="6" ><bean:message key="btn.Save" /></html:button>

          <html:button property="btnCancel" styleClass="buttons" style="width:70px; margin-right:15px" onclick="history.go(-1);" tabindex="7"><bean:message key="btn.Cancel" /></html:button>

        </td>

      </tr>

</table>

<!-- borderClrLvl_2 table ends above-->

</td>

</tr>

	<tr><td height="2px"></td></tr>

	<tr>

    <td align="center">

      <table class="borderClrLvl_2 imgStatusBar bgClrLvl_4 " width="725px" height="20px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

        <tr>

          <td width="30px"><div class="imgStatusMsg"></div></td>

          <td>

            <logic:messagesPresent >

              <bean:message key="errors.header"/>

                <html:messages id="error">

                <font color="red"><bean:write name="error"/></font>

              </html:messages>

            </logic:messagesPresent>

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

</html:form>

<!--Content Ends-->

</body>

</html:html>
