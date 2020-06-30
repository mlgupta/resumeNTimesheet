<%@ page import="rts.beans.*" %>
<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.actionforms.preferences.UserPreferencesProfileForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html> 
<head>
<title><bean:message key="title.UserPreferenceProfile" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script src="general.js"></script>
<html:javascript formName="userPreferencesProfileForm" dynamicJavascript="true" staticJavascript="false"/>
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
<script language="Javascript1.1" >

//function cancelActionPerformed(){
//    window.location.replace("folderDocMenuClickAction.do");
//}

function callPage(thisForm){ 
    
    if(validateUserPreferencesProfileForm(thisForm)){
      if(thisForm.txtPermittedTreeAccessLevel.value<=0){
        alert("<bean:message key="errors.userPreference.PermittedTreeAccessLevel"/>"); 
        return false;    
      }
      if(thisForm.txtItemsToBeDisplayedPerPage.value<=0){
        alert("<bean:message key="errors.userPreference.ItemsToBeDisplayedPerPage"/>");     
        return false;
      }
        thisForm.target="_self";
        thisForm.action="userPreferenceProfileAction.do";
        thisForm.submit();
    }
}

function setURLEncPass(){
        openWindow("","SetURLEncPass",200,500,0,0,true);
        document.forms[0].target= "SetURLEncPass";
        document.forms[0].action= "setURLEncryptPasswordB4Action.do";
        document.forms[0].submit();
}
</script>
</head>
<body style="margin:0">
<!-- This page contains 3 outermost tables, named 'headerIncluder', 'errorContainer' and 'tabContainer' -->
<table id="headerIncluder" width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="95px">
      <!--Header Starts-->
        <% if( ((UserPreferencesProfileForm)request.getAttribute("userPreferencesProfileForm")).getCboNavigationType() == UserPreferences.FLAT_NAVIGATION) {%>
          <jsp:include page="/header.jsp" /> 
        <%}else{%>
          <jsp:include page="/headerForTreeNav.jsp" />
        <%}%>
      <!--Header Ends-->
	</td>
  </tr>
</table>
<html:form name="userPreferencesProfileForm" action="/userPreferenceProfileB4Action" type="rts.web.actionforms.preferences.UserPreferencesProfileForm"
            onsubmit="return validateUserPreferencesProfileForm(this);" > 

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'borderClrLvl_2' -->
<br>
<tr>
<td align="center">
	<table id="tabParent" width="720px"  border="0" cellpadding="0" cellspacing="0">
		<tr>
    	<td width="151px">
        <table id="tab1" width="150px" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="5px" class="imgTabLeft"></td>
            <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.UserPreference" /></div></td>
            <td width="5px" class="imgTabRight"></td>
          </tr>
        </table>
      </td>
  	</tr>
	</table>
	<table id="bdrColor_336633"  width="720px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633">
    <tr><td align="center" height="10px"></td></tr>
		<tr>
      <td align="center">
        <fieldset style="width:680px">
          <legend align="left" class="tabText"><bean:message key="lbl.MyProfile" /></legend>
          <table width="660px" border="0" id="profile" cellspacing="1" cellpadding="1">
            <tr>
              <td width="95px">&nbsp;</td>
              <td width="205px">
                 <html:button  property="btnChangeLoginPassword" styleClass="buttons" style="width:190px" onclick="openWindow('changePasswordB4Action.do','userPassword',200,500,0,0,true)"><bean:message key="btn.ChangeLoginPassword" /></html:button>
              </td>
              <td width="155px">&nbsp;</td>
              <%if(!(((Boolean)request.getAttribute("hideURLOption")).booleanValue())){ %>
                <td width="205px">
                 <%if(((Boolean)request.getAttribute("URLOptionVisibility")).booleanValue()) {%>
                    <html:button property="btnSetURLEncryptDecryptPassword" styleClass="buttons" style="width:205px;" onclick="setURLEncPass()" ><bean:message key="btn.SetURLEncryptDecryptPassword" /></html:button> 
                 <%} else {%>
                    <html:button property="btnSetURLEncryptDecryptPassword" styleClass="buttons" style="width:205px;" disabled="true" ><bean:message key="btn.SetURLEncryptDecryptPassword" /></html:button>
                 <% } %>
                </td>
            </tr> 
            <% } %>
            <tr>
              <td colspan="4">
                <table width="660px" border="0" cellpadding="0">
                  <tr>
                    <td height="10px" ></td>
                  </tr>
                  <tr>
                    <td class="bgClrLvl_2" height="1px"></td>
                  </tr>
                  <tr>
                    <td height="10px" ></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td><div align="right"><bean:message key="txt.UserName" /></div></td>
              <td><html:text name="userPreferencesProfileForm" property="userName" readonly="true" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" /></td>
              <td><div align="right"><bean:message key="txt.EmailAddress" /></div></td>
              <td><html:text name="userPreferencesProfileForm" property="emailId" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" /></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><div align="right"><bean:message key="txt.HomeFolder" /></div></td>
              <td><html:text name="userPreferencesProfileForm" property="homeFolder" readonly="true" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" /></td>
              <td><div align="right"><bean:message key="lbl.Group" />:</div></td>
              <td>
                <html:select  property="cboGroup" styleClass="borderClrLvl_2 componentStyle" style="width:195px">
                  <html:options name="userPreferencesProfileForm" property="cboGroup" />    
                </html:select>
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            
            <logic:equal name="userPreferencesProfileForm" property="isSystemAdmin" value="true" >
            <tr>
              <td><div align="right">Custom1:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom1_lbl" maxlength="20" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
              <td>
                <div align="right">Custom2:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom2_lbl" maxlength="20" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            
            <tr>
              <td><div align="right">Custom3:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom3_lbl" maxlength="20" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
              <td>
                <div align="right">Custom4:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom4_lbl" maxlength="20" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
            </tr>
           </logic:equal>
           
            <logic:equal name="userPreferencesProfileForm" property="isSystemAdmin" value="false" >
            <tr>
              <td><div align="right">Custom1:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom1_lbl" readonly="true" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
              <td>
                <div align="right">Custom2:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom2_lbl" readonly="true" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            
            <tr>
              <td><div align="right">Custom3:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom3_lbl" readonly="true" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
              <td>
                <div align="right">Custom4:</div></td>
              <td>
                <html:text name="userPreferencesProfileForm" property="custom4_lbl" readonly="true" style="width:195px;" styleClass="borderClrLvl_2 componentStyle" />
              </td>
            </tr>
           </logic:equal>
           
          </table>
          <table width="660px" border="0" cellpadding="0">
            <tr>
            <td height="10px" ></td>
            </tr>
            <tr>
            <td class="bgClrLvl_2" height="1px"></td>
            </tr>
            <tr>
            <td height="10px" ></td>
            </tr>

          </table>

          <table width="660px" border="0" cellpadding="0" cellspacing="1">            
            <tr>
              <td width="85px" align="right">
                <bean:message key="cbo.NavigationType" />:
              </td>
              <td width="230px" align="left">
                <html:select name="userPreferencesProfileForm" property="cboNavigationType" styleClass="borderClrLvl_2 componentStyle" style="width:200px;margin-left:3px;">
                  <html:option value="<%=UserPreferences.FLAT_NAVIGATION%>" ><bean:message key="cbo.FlatNavigation" /></html:option>
                  <html:option value="<%=UserPreferences.TREE_NAVIGATION%>" ><bean:message key="cbo.TreeNavigation" /></html:option>
                </html:select>
              </td>
              <td width="345px" align="right" >
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            
          </table>
          
          <table width="660px" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="330px" align="left">
                <bean:message key="txt.ItemsToBeDisplayedPerPage" />:
                <html:text name="userPreferencesProfileForm" property="txtItemsToBeDisplayedPerPage" styleClass="borderClrLvl_2 componentStyle" style="width:35px; text-align:right" maxlength="4" onkeypress="return integerOnly(event);"/>
              </td>
              <td width="330px" align="right" >
                <b>*</b>&nbsp;<bean:message key="txt.PermittedTreeAccessLevel" />
                <html:text name="userPreferencesProfileForm" property="txtPermittedTreeAccessLevel" styleClass="borderClrLvl_2 componentStyle" style="width:35px; text-align:right" maxlength="4" onkeypress="return integerOnly(event);" />             
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>
          
        </fieldset>
      </td>
    </tr>
    <tr>
      <td align="center" height="30px">
        <table width="710px" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              &nbsp;&nbsp;&nbsp;<bean:message key="info.userpreference" />
            </td>
            <td>
              <div align="right">
                <html:button property="btnOk" styleClass="buttons" style="width:70px" onclick="return callPage(this.form);"><bean:message key="btn.Ok" /></html:button>
                <html:button property="btnCancel" styleClass="buttons" style="width:70px; margin-right:7px;" onclick="history.go(-1);"><bean:message key="btn.Cancel" /></html:button>
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
</table>

<!-- borderClrLvl_2 table ends above-->
</td>
</tr>
	<tr><td height="2px"></td></tr>
	<tr>
      <td align="center">
      <table class="bgColor_e8e8e8 bdrColor_336633" height="20px" width="720px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
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
              <bean:write name="msg"/>
            </html:messages>
          </td>
          </tr>
      </table>
      <!-- statusBar table ends above-->
      </td>
  </tr>
</table>
<table align="center" width="720px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
  </tr>
</table>

<!-- tabContainer table ends above-->
</html:form> 
</body>


</html:html>