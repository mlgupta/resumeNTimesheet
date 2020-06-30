<%@ page import="rts.web.beans.user.UserPreferences" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="currentFolderPath" name="FolderDocInfo" property="currentFolderPath" type="String" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<html:html>
  <head>
    <title><bean:message key="title.Mailing" /></title>
   
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    
    <script src="general.js"></script>
    <script src="datepicker.js"></script>
    <script src="timezones.js"></script> 
    <script>
      function attachonclick(){
        openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.AttachFile" />&foldersOnly=false&openerControl=lstAttachment&recreate=true','attachMailLookup',510,400,0,0,true);
      }
    </script>
    
    <!-- Date Related Scripts-->
    
    <script>
    
      var datePicker = new Calendar("datePicker",0,0);
      datePicker.onclick="setDateValues()";
      datePicker.onclear="clearDateValues()";
      datePicker.clearDisabled="true";
      datePicker.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
      datePicker.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
      datePicker.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
      datePicker.tooltipDay='<bean:message key="tooltips.cal.Day" />';
      datePicker.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
      datePicker.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
      datePicker.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
      datePicker.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
      datePicker.tooltipNow='<bean:message key="tooltips.cal.Now" />';
      datePicker.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
      datePicker.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
      datePicker.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
      datePicker.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
      datePicker.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
      function setDateValues(){
        document.forms[0].day.value=datePicker.getDay();
        document.forms[0].month.value=datePicker.getMonth();
        document.forms[0].year.value=datePicker.getYear();
        document.forms[0].hours.value=datePicker.getHours();
        document.forms[0].minutes.value=datePicker.getMinutes();
        document.forms[0].timezone.value=datePicker.getTimezone();
        document.forms[0].seconds.value=datePicker.getSeconds();
      }
    
      function clearDateValues(){
        document.forms[0].day.value="";
        document.forms[0].month.value="";
        document.forms[0].year.value="";
        document.forms[0].hours.value="";
        document.forms[0].minutes.value="";
        document.forms[0].seconds.value="";
        document.forms[0].timezone.value="";
      }
    
    </script>
    
    <script>
      function window_onload(){
        var currentDate=new Date();
        datePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
        datePicker.initialize();
        datePicker.click();
      }
    </script>
    <script>
    <!--
    // Email Validation. Written by PerlScriptsJavaScripts.com
    function check_email(e) {
      var ok = "1234567890qwertyuiop[]asdfghjklzxcvbnm.@-_QWERTYUIOPASDFGHJKLZXCVBNM";
      for(i=0; i < e.length ;i++){
        if(ok.indexOf(e.charAt(i))<0){ 
          return (false);
        }       
      } 
      if (document.images) {        
        var re = /(@.*@)|(\.\.)|(^\.)|(^@)|(@$)|(\.$)|(@\.)/;
        var re_two = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        if (!e.match(re) && e.match(re_two)) {
            return (true);   
        }else{
            return false;
        }
      }
    }
    
    // -->
    
    </script>
    <html:javascript formName="mailForm" dynamicJavascript="true" staticJavascript="false"/>
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script language="Javascript1.1" >
    

    function callPage(thisForm){
      if(thisForm.txtTo.value.length==0){
        alert("<bean:message key="errors.toAddress.required"/>");
        thisForm.txtTo.focus();
        return false;
      }
      var toValidationOk=false;
      var ccValidationOk=false;
      var bccValidationOk=false;
      var toAddr=thisForm.txtTo.value.split(',');
      var ccAddr=thisForm.txtCc.value.split(',');
      var bccAddr=thisForm.txtBCc.value.split(',');
    
      for( index=0;index < toAddr.length ; index++){
          if (check_email(toAddr[index])) {
            toValidationOk = true;
          }else{        
            alert("<bean:message key="errors.toAddress"/>");
            thisForm.txtTo.focus();
            return false;
          }
      }
    
      if (thisForm.txtCc.value.length> 0){
        for( index=0;index < ccAddr.length ; index++){
          if (check_email(ccAddr[index])) {
            ccValidationOk = true;
          }else{
            alert("<bean:message key="errors.ccAddress"/>");
            thisForm.txtCc.focus();
            return false;
          }
        }
      }else{
        ccValidationOk = true;
      }
    
      if (thisForm.txtBCc.value.length > 0) {
        for( index=0;index < bccAddr.length ; index++){
          if (check_email(bccAddr[index])) {
            bccValidationOk = true;
          }else{
            alert("<bean:message key="errors.bccAddress"/>");
            thisForm.txtBCc.focus();
            return false;
          }
        }
      }else{
        bccValidationOk = true;
      }
    
      if (toValidationOk && ccValidationOk && bccValidationOk) {        
        for(index = 0 ; index < thisForm.lstAttachment.length ;index++){     
          thisForm.lstAttachment[index].selected = true;    
        }  
        thisForm.submit() ;
      }
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
    
    function encryptPage(thisForm){   
      for(index = 0 ; index < thisForm.lstAttachment.length ;index++){     
        thisForm.lstAttachment[index].selected=true;    
      }
      thisForm.submit() ;
    }
    
    function decryptPage(thisForm){   
      for(index = 0 ; index < thisForm.lstAttachment.length ;index++){     
        thisForm.lstAttachment[index].selected=true;    
      }
      thisForm.submit() ;
    }
    
    function cancelActionPerformed(type ){
      if( type == 'flat' ){
        window.location.replace("webtop.jsp");
      }else{
        window.location.replace("webtopForTreeNav.jsp");
      }
    }
    
    function lookuponclick(){
      openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.GrabIds" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true&hdnIsForMail=true',"emailIdsLookup",495,390,0,0,true);
    }
    
    function grabIds(){
      document.forms[0].target = "_self";
      document.forms[0].action = "mailB4Action.do?grabIds=true";
      document.forms[0].submit();
    }
    
    </script>
    
  </head>
  
  <body style="margin:0" onload="window_onload();">
  
    <html:form name="MailForm" method="post" type="rts.web.actionforms.mail.MailForm" action="/mailAction" onsubmit="return validateMailForm(this);" focus="txtTo">  
    
    <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="headerIncluder">
      <html:hidden name="MailForm" property="day"  />
      <html:hidden name="MailForm" property="month"  />
      <html:hidden name="MailForm" property="year"  />
      <html:hidden name="MailForm" property="hours"  />
      <html:hidden name="MailForm" property="minutes"  />
      <html:hidden name="MailForm" property="timezone"  />
      <html:hidden name="MailForm" property="seconds"  />
      <html:hidden name="MailForm" property="hdnTargetFolderPath" value="<%=currentFolderPath%>" />
    
      <tr class="bdrColor_336633">
        <td valign="top" height="95px">
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
    
    <!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->
    <table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
    <!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
      <br>
      <tr>
      <td align="center">
        <table id="tabParent" width="650px"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="120px">
              <table id="tab1" width="120px" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="5px" class="imgTabLeft"></td>
                  <td width="110px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Mailing" /></div></td>
                  <td width="5px" class="imgTabRight"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>

        <table width="650px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" >
          <tr>
            <td align="center">
              <table width="650px" border="0" align="center" >
              <tr height="30px">
                <td align="center">&nbsp</td>
                <td>
                  <table border="0" cellpadding="0" cellspacing="0" width="445px">
                    <tr>
                      <td  align="right">
                        <script>datePicker.render();</script>
                      </td>
                      <td  align="right" width="150px">
                        <html:hidden name="MailForm" property="txtSendTime" styleClass="bdrColor_336633 componentStyle" style="width:150px;"  />
                        <html:button property="btnSend" styleClass="buttons" style="width:70px;" onclick="return callPage(this.form)" tabindex="8" ><bean:message key="btn.Send" /></html:button>
                        <% if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ) {%>
                          <html:button property="btnCancel" styleClass="buttons" style="width:70px;" onclick="cancelActionPerformed('flat')" tabindex="10" ><bean:message key="btn.Cancel" /></html:button>
                        <%}else{%>
                          <html:button property="btnCancel" styleClass="buttons" style="width:70px;" onclick="cancelActionPerformed('tree')" tabindex="10" ><bean:message key="btn.Cancel" /></html:button>
                        <%}%>
                      </td>     
                    </tr>
                 </table>
                </td>
              </tr>
      
              <tr>
                <td width="125px" align="center">
                  <div align="right"><bean:message key="txt.To" /></div>
                </td>
                <td width="447px" align="center">
                  <div align="left">
                    <html:text name="MailForm" property="txtTo" styleClass="bdrColor_336633 componentStyle" style="width:420px;"  tabindex="1"/>
                    <html:button onclick="lookuponclick();" property="btnLookUp" style="width:20px; height:18px; cursor:pointer; cursor:hand" styleClass="buttons" value="..." />
                  </div>
                </td>
              </tr>

              <tr>
                <td align="center"><div align="right"><bean:message key="txt.Cc" /></div></td>
                <td align="center">
                  <div align="left">
                    <html:text name="MailForm" property="txtCc" styleClass="bdrColor_336633 componentStyle" style="width:445px;" tabindex="2"/>
                  </div>
                </td>
              </tr>
      
              <tr>
                <td align="center"><div align="right"><bean:message key="txt.BCc" /></div></td>
                <td align="center">
                  <div align="left">
                    <html:text name="MailForm" property="txtBCc" styleClass="bdrColor_336633 componentStyle" style="width:445px;" tabindex="3" />
                  </div>
                </td>
              </tr>
      
              <tr>
                <td align="center"><div align="right"><bean:message key="txt.Subject" /></div></td>
                <td align="center">
                  <div align="left">
                    <html:text name="MailForm" property="txtSubject" styleClass="bdrColor_336633 componentStyle" style="width:445px;" tabindex="4"/>          
                  </div>
                </td>
              </tr>
      
              <tr>
                <td align="center" valign="top"><div align="right"><bean:message key="txa.Mail" /></div></td>
                <td align="center">
                  <div align="left">
                    <textarea name="txaMail" class="bdrColor_336633 componentStyle" style="width:445px; height:180px" tabindex="5"></textarea>
                  </div>
                </td>
              </tr>
      
              <tr>
                <td align="center">&nbsp;</td>
                <td align="left"><bean:message key="txt.Attachments" /></td>          
              </tr>

              <tr>
                <td></td>
                <td align="center">
                  <div align="left">
                    <html:select name="MailForm" property="lstAttachment" size="4"   multiple="true" style="width:449px;" styleClass="bdrColor_336633 componentStyle" tabindex="6">
                      <logic:present name="MailForm" property="lstAttachment">
                        <html:options name="MailForm" property="lstAttachment"  />
                      </logic:present>
                    </html:select>
                  </div>
                </td>
              </tr>
     
              <tr>
                <td height="30px" align="center"> 
                  <div align="right">&nbsp; 
                  </div>
                </td>
      
                <td align="center">
                  <div align="right">
                    <html:button property="btnAttach" styleClass="buttons" onclick="attachonclick();" style="width:70px;" tabindex="7"><bean:message key="btn.Attach" /></html:button>
                    <html:button property="btnRemove" styleClass="buttons" style="width:70px; margin-right:70px;" onclick="removeFromList(this.form)" tabindex="11"><bean:message key="btn.Remove" /></html:button>            
                  </div>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- blueBorder table ends above-->
    </td>
    
  </tr>
    
    <tr><td height="2px"></td></tr>
    
      <tr>
    
          <td align="center">
    
          <table class="bgColor_e8e8e8 bdrColor_336633" width="650px" align="center" border="0" cellpadding="0" cellspacing="0" id="statusBar">
    
              <tr>
    
              <td height="20px" width="30px"><div class="imgStatusMsg"></div></td>
    
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
    
    <table align="center" width="1000px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
      </tr>
    </table>
    
    </html:form>
    
    <!-- tabContainer table ends above-->
  
  </body>

</html:html>