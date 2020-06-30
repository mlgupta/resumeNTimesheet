<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%request.setAttribute("topic","job_reschedule_html");%>

<html:html>

<head>

<title><bean:message key="title.Reschedule" /></title>

<script src="general.js"></script>

<script src="datepicker.js"></script>

<script src="timezones.js"></script>

<!-- Date Related Scripts-->

<script>

  var createDatePicker = new Calendar("createDatePicker",0,0);

  createDatePicker.disabled=true;

  

  

  var dispatchDatePicker = new Calendar("dispatchDatePicker",0,0);

  dispatchDatePicker.onclick="setDateValues()";        

  dispatchDatePicker.onclear="clearDateValues()"; 

  dispatchDatePicker.clearDisabled="true";

  dispatchDatePicker.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';

  dispatchDatePicker.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';

  dispatchDatePicker.tooltipClear='<bean:message key="tooltips.cal.Clear" />';

  dispatchDatePicker.tooltipDay='<bean:message key="tooltips.cal.Day" />';

  dispatchDatePicker.tooltipHour='<bean:message key="tooltips.cal.Hour" />';

  dispatchDatePicker.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';

  dispatchDatePicker.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';

  dispatchDatePicker.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';

  dispatchDatePicker.tooltipNow='<bean:message key="tooltips.cal.Now" />';

  dispatchDatePicker.tooltipOk='<bean:message key="tooltips.cal.Ok" />';

  dispatchDatePicker.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';

  dispatchDatePicker.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';

  dispatchDatePicker.tooltipSecond='<bean:message key="tooltips.cal.Second" />';

  dispatchDatePicker.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

  

  function setDateValues(){

      document.forms[0].day.value=dispatchDatePicker.getDay();

      document.forms[0].month.value=dispatchDatePicker.getMonth();

      document.forms[0].year.value=dispatchDatePicker.getYear();

      document.forms[0].hours.value=dispatchDatePicker.getHours();

      document.forms[0].minutes.value=dispatchDatePicker.getMinutes();            

      document.forms[0].seconds.value=dispatchDatePicker.getSeconds();

      document.forms[0].timezone.value=dispatchDatePicker.getTimezone();

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

<!-- -->

<script>

  function window_onload(){

    var createDate = new Date(document.forms[0].txtCreateDate.value)

    createDatePicker.setDateTime(createDate.getYear(),createDate.getMonth(),createDate.getDate(),

                            createDate.getHours(),createDate.getMinutes(),createDate.getSeconds());

    createDatePicker.setTimezone(document.forms[0].timezone.value);

    createDatePicker.click();

      

    dispatchDatePicker.setDateTime(document.forms[0].year.value,document.forms[0].month.value,document.forms[0].day.value,

                            document.forms[0].hours.value,document.forms[0].minutes.value,document.forms[0].seconds.value);

    dispatchDatePicker.setTimezone(document.forms[0].timezone.value);



    dispatchDatePicker.click();

  }



</script>

</head>

<body style="margin:0" onload="window_onload();">

<html:form name="RescheduleForm" method="post" type="rts.web.actionforms.scheduler.RescheduleForm" action="/jobRescheduleAdminAction.do">

<html:hidden name="RescheduleForm" property="triggerName"  />

<html:hidden name="RescheduleForm" property="triggerType"  />

<html:hidden name="RescheduleForm" property="day"  />

<html:hidden name="RescheduleForm" property="month"  />

<html:hidden name="RescheduleForm" property="year"  />

<html:hidden name="RescheduleForm" property="hours"  />

<html:hidden name="RescheduleForm" property="minutes"  />

<html:hidden name="RescheduleForm" property="timezone"  />

<html:hidden name="RescheduleForm" property="seconds"  />

<html:hidden name="RescheduleForm" property="txtCreateDate" />



<!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->

<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="headerIncluder">

  <tr>

    <td height="95px">

      <!--Header Starts-->

      <jsp:include page="/header.jsp" />

      <!--Header Ends-->

    </td>

  </tr>

</table>

<br>

<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

  <tr>

    <td align="center">

      <table id="tabParent" width="550px"  border="0" cellpadding="0" cellspacing="0">

        <tr>

          <td>

            <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">

              <tr>

                <td width="5px" class="imgTabLeft"></td>

                <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.Reschedule" /></div></td>

                <td width="5px" class="imgTabRight"></td>              

              </tr>

            </table>

          </td>

        </tr>

      </table>

      <table width="550px" border="0" cellpadding="0" cellspacing="0" class="borderClrLvl_2 bgClrLvl_4" id="borderClrLvl_2">

      <tr>

        <td height="25px" align="center">

          <table width="100%"  border="0">

            <tr>

              <td>&nbsp;</td>

              <td>&nbsp;</td>

            </tr>

            <tr>

              <td width="25%"><div align="right"><bean:message key="lbl.JobName" /></div></td>

              <td width="75%">

                <html:text name="RescheduleForm" property="txtJobName" styleClass="borderClrLvl_2 componentStyle componentStyleDisable"  style="width:330px;" readonly="true" />

              </td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.JobType" /></div></td>

              <td>

                <html:text name="RescheduleForm" property="txtJobType" styleClass="borderClrLvl_2 componentStyle componentStyleDisable" style="width:330px;" readonly="true" />

              </td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.User" /> :</div></td>

              <td>

                <html:text name="RescheduleForm" property="txtUser" styleClass="borderClrLvl_2 componentStyle componentStyleDisable" style="width:330px;" readonly="true" />

              </td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.CreateDate" /> :</div></td>

              <td>

                <script>

                    createDatePicker.render();

                </script>                

                

              </td>

            </tr>

            <tr>

              <td><div align="right"><bean:message key="lbl.DispatchDate" /> :</div></td>

              <td>

                <script>

                    dispatchDatePicker.render();

                </script>                

              </td>

            </tr>

            <tr>

              <td><div align="right"></div></td>

              <td>&nbsp;</td>

            </tr>

            <tr>

              <td></td>

              <td>

                <div align="right">

                  <html:submit property="btnOk" styleClass="buttons" style="width:70px"  ><bean:message key="btn.Ok" /></html:submit>

                  <html:button property="btnCancel" styleClass="buttons" style="width:70px;margin-right:70px;" onclick="history.go(-1);" ><bean:message key="btn.Cancel" /></html:button>&nbsp;                    

                </div>                  

              </td>

            </tr>

          </table>

    </td>

  </tr>

  <tr>

		<td height="10px" >

		</td>

  </tr>

</table>

   <!-- borderClrLvl_2 table ends above-->

</td>

</tr>

<tr><td height="2px"></td></tr>

      <tr>

          <td align="center">

          <table class="imgStatusBar borderClrLvl_2 bgClrLvl_4 " width="550px" height="20px" border="0" cellpadding="0" cellspacing="0" id="statusBar">

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

<!-- tabContainer table ends above-->



</body>

</html:html>