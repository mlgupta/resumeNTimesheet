<%@ page import="rts.web.beans.scheduler.SchedulerConstants" %>
<%@ page import="org.quartz.*" %>
<%@ page import="rts.web.beans.user.UserPreferences" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="jobs" name="jobs" type="java.util.ArrayList" />
<bean:define id="pageCount" name="SchedulerListForm" property="txtPageCount" />        
<bean:define id="pageNo" name="SchedulerListForm" property="txtPageNo" />    
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />

<%

//Variable declaration

boolean firstTableRow;

firstTableRow = true;

%>

<html:html>
<head>
<title><bean:message key="title.SchedulerList" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script src="general.js"></script>
<script src="datepicker.js"></script>
<script src="timezones.js"></script>
<script src="navigationbar.js" ></script>
<!-- Date Related Scripts-->
<script>

  var createDateFrom = new Calendar("createDateFrom",0,0);
  createDateFrom.onclick="selectedValues(createDateFrom,'txtCreateFromDate')";
  createDateFrom.onclear="clearValues('txtCreateFromDate')";
  createDateFrom.timezoneDisabled=true;
  createDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
  createDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
  createDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
  createDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';
  createDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
  createDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
  createDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
  createDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
  createDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';
  createDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
  createDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
  createDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
  createDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
  createDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

  var createDateTo = new Calendar("createDateTo",0,0);
  createDateTo.onclick="selectedValues(createDateTo,'txtCreateToDate')";
  createDateTo.onclear="clearValues('txtCreateToDate')";
  createDateTo.timezoneDisabled=true;
  createDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
  createDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
  createDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
  createDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';
  createDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
  createDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
  createDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
  createDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
  createDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';
  createDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
  createDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
  createDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
  createDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
  createDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

  var dispatchDateFrom = new Calendar("dispatchDateFrom",0,0);
  dispatchDateFrom.onclick="selectedValues(dispatchDateFrom,'txtDispatchFromDate')";
  dispatchDateFrom.onclear="clearValues('txtDispatchFromDate')";
  dispatchDateFrom.timezoneDisabled=true;
  dispatchDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
  dispatchDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
  dispatchDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
  dispatchDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';
  dispatchDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
  dispatchDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
  dispatchDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
  dispatchDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
  dispatchDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';
  dispatchDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
  dispatchDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
  dispatchDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
  dispatchDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
  dispatchDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

  var dispatchDateTo = new Calendar("dispatchDateTo",0,0);
  dispatchDateTo.onclick="selectedValues(dispatchDateTo,'txtDispatchToDate')";
  dispatchDateTo.onclear="clearValues('txtDispatchToDate')";
  dispatchDateTo.timezoneDisabled=true;
  dispatchDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
  dispatchDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
  dispatchDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
  dispatchDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';
  dispatchDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
  dispatchDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
  dispatchDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
  dispatchDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
  dispatchDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';
  dispatchDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
  dispatchDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
  dispatchDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
  dispatchDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
  dispatchDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

  function  selectedValues(dtPickerObj, txtDateTimeName){
    var dateString=dtPickerObj.getMonth();
    dateString+="/" +dtPickerObj.getDay();
    dateString+="/" +dtPickerObj.getYear();
    dateString+=" " +dtPickerObj.getHours();
    dateString+=":" +dtPickerObj.getMinutes();
    dateString+=":" +dtPickerObj.getSeconds();
    eval("document.forms[0]."+txtDateTimeName).value=dateString;
  }  

  function  clearValues(txtDateTimeName){
    eval("document.forms[0]."+txtDateTimeName).value="";
  }

</script>

<script>

  function window_onload(){
    var currentDate=null;
    if(document.forms[0].txtCreateFromDate.value!=""){
      currentDate=new Date(document.forms[0].txtCreateFromDate.value);
    }else{
      currentDate=new Date();
    }

    createDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());

    createDateFrom.setTimezone(document.forms[0].timezone.value);

    if(document.forms[0].txtCreateFromDate.value!=""){
      createDateFrom.click();
    }

    if(document.forms[0].txtCreateToDate.value!=""){
      currentDate=new Date(document.forms[0].txtCreateToDate.value);
    }else{
      currentDate=new Date();
    }

    createDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            

    createDateTo.setTimezone(document.forms[0].timezone.value);

    if(document.forms[0].txtCreateToDate.value!=""){
      createDateTo.click();
    }               

    if(document.forms[0].txtDispatchFromDate.value!=""){
      currentDate=new Date(document.forms[0].txtDispatchFromDate.value);
    }else{
      currentDate=new Date();
    }

    dispatchDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            

    dispatchDateFrom.setTimezone(document.forms[0].timezone.value);

    if(document.forms[0].txtDispatchFromDate.value!=""){
      dispatchDateFrom.click();
    }                   

    if(document.forms[0].txtDispatchToDate.value!=""){
      currentDate=new Date(document.forms[0].txtDispatchToDate.value);
    }else{
      currentDate=new Date();
    }  

    dispatchDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                                                        

    dispatchDateTo.setTimezone(document.forms[0].timezone.value);

    if(document.forms[0].txtDispatchToDate.value!=""){
      dispatchDateTo.click();
    }   
  }

  var navBar=new NavigationBar("navBar");
  navBar.setPageNumber(<%=pageNo%>);
  navBar.setPageCount(<%=pageCount%>);
  navBar.onclick="callPage('page_scheduler')";
  navBar.msgPageNotExist="<bean:message key="page.pageNumberNotExists" />";
	navBar.msgNumberOnly="<bean:message key="page.enterNumbersOnly"/>";    
	navBar.msgEnterPageNo="<bean:message key="page.enterPageNo"/>";
	navBar.msgOnFirst="<bean:message key="page.onFirst"/>";
	navBar.msgOnLast="<bean:message key="page.onLast"/>";
	navBar.lblPage="<bean:message key="lbl.Page"/>";
	navBar.lblOf="<bean:message key="lbl.Of"/>";
	navBar.tooltipPageNo="<bean:message key="tooltips.PageNo"/>";
	navBar.tooltipFirst="<bean:message key="tooltips.First"/>";
	navBar.tooltipNext="<bean:message key="tooltips.Next"/>";
	navBar.tooltipPrev="<bean:message key="tooltips.Previous"/>";
	navBar.tooltipLast="<bean:message key="tooltips.Last"/>";
	navBar.tooltipGo="<bean:message key="btn.Go"/>";

</script>

<html:javascript formName="userListForm" dynamicJavascript="true" staticJavascript="false"/>
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
<script language="Javascript1.1" >
  
  function callPage(relayOperation){
    var thisForm=document.forms[0];
    thisForm.txtPageNo.value=navBar.getPageNumber();
    if( (relayOperation!="search_scheduler") && (relayOperation!="scheduler_start_stop") && (relayOperation!="page_scheduler")){
      if(checkSelected(thisForm)){
        if(relayOperation=="job_delete"){
          if (confirm("<bean:message key="msg.delete.confirm" />")){
            thisForm.operation.value=relayOperation;
            thisForm.submit() ;
          }
        }else{
          thisForm.operation.value=relayOperation;
          thisForm.submit() ;
        }
      }
    }else{
      thisForm.operation.value=relayOperation;
      thisForm.submit() ;
    }
  }
  
  function checkSelected(thisForm){
    if (typeof thisForm.radSelect!="undefined"){
      if (typeof thisForm.radSelect.length !="undefined"){
        for(index = 0 ; index < thisForm.radSelect.length ;index++){  
          if(thisForm.radSelect[index].checked){   
            return true;
          }
        }
      }else{
        if(thisForm.radSelect.checked){   
          return true;
        }
      }
    }else{
      alert('<bean:message key="errors.radSelect.noitem"/>');         
      return false;
    }      
    alert('<bean:message key="errors.radSelect.required"/>');         
    return false;
  }
  
  function openList(thisForm){
    openWindow('relayAction.do?operation=page_user_select&control=txtSearchByUserName','schedulerUser',500,450,0,0,true);  
  }
  
  function startStopScheduler(relayOperation,action){
    var thisForm=document.forms[0]  ;   
    thisForm.txtPageNo.value=navBar.getPageNumber();
    thisForm.operation.value=relayOperation;
    thisForm.action="schedulerRelayAction.do?action="+action;
    if (action=="Stop"){
      if(confirm("<bean:message key="scheduler.stop.confirm" />")){              
        thisForm.submit() ;
      }
    }else{          
      thisForm.submit() ;
    }
  }
  
  
  
  function enter(thisField,e,relayOperation){
    var i;
    i=handleEnter(thisField,e);
    if (i==1) {
      return callPage(relayOperation);
    }
  }    

</script>
</head>
<body style="margin:0" onload="window_onload();">
<html:form name="SchedulerListForm"  type="rts.web.actionforms.scheduler.SchedulerListForm" action="/schedulerRelayAction.do" focus="txtSearchByUserName">

<html:hidden name="SchedulerListForm" property="operation" />
<html:hidden name="SchedulerListForm" property="isSchedulerStopped" />
<html:hidden name="SchedulerListForm" property="txtPageNo" /> 
<html:hidden name="SchedulerListForm" property="timezone" /> 
<!-- This page contains 3 outermost tables, named 'headerIncluder', 'errorContainer' and 'tabContainer' -->
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="headerIncluder">
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
<!--Content Starts-->
<br>
<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0" >
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
<td align="center">
<table id="tabParent" width="980px"  height="10px" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td>
      <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td width="5px" class="imgTabLeft"></td>
          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.SchedulerList" /></div></td>
          <td width="5px" class="imgTabRight"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="980px" border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" id="bdrColor_336633" >
  <tr class="bgColor_e8e8e8 bdrTopColor_FFFFFF">
    <td width="508px" height="32px">
      <% if( ((String)request.getAttribute("isUserDisabled")).equals("false")){ %>
        <%if(((Scheduler)application.getAttribute("scheduler")).isShutdown()){ %>
          <a style="margin-left:5px;" onclick="return startStopScheduler('scheduler_start_stop','Start');" class="imgSchedulerStart" onmouseout="this.className='imgSchedulerStart'" onmouseover="this.className='imgSchedulerStart'" title="<bean:message key="tooltips.SchedulerStart" />" tabindex="2"></a>
        <%}else{ %>
          <a style="margin-left:5px;" onclick="return startStopScheduler('scheduler_start_stop','Stop');" class="imgSchedulerStop" onmouseout="this.className='imgSchedulerStop'" onmouseover="this.className='imgSchedulerStop'" title="<bean:message key="tooltips.SchedulerStop" />" tabindex="2"></a>
        <%}%>
      <%}%>
      <a onclick="return callPage('job_reschedule')" class="imgSchedulerRe" onmouseout="this.className='imgSchedulerRe'" onmouseover="this.className='imgSchedulerRe'" style="margin-left:2px;" title="<bean:message key="tooltips.JobReschedule" />" tabindex="3"></a>
      <a onclick="return callPage('job_delete')" class="imgSchedulerDelete" onmouseout="this.className='imgSchedulerDelete'" onmouseover="this.className='imgSchedulerDelete'" style="margin-left:2px;" title="<bean:message key="tooltips.JobDelete" />" tabindex="4"></a>
    </td>
    <td width="480px" align="right" valign="bottom">
      <a tabindex="6" class="imgGo" title='<bean:message key="btn.Go" />'  style="float:right;margin-right:5px" onclick="return callPage('search_scheduler')" />        
    </td>
  </tr>
  <tr class="bgColor_e8e8e8" >
  <td colspan="2">
  <table width="98%"  border="0" align="center" cellpadding="1" cellspacing="0" class="bgColor_e8e8e8">
    <tr>
    <td width="35%" valign="top">
      <fieldset>
      <legend align="left"><bean:message key="lbl.JobInfo" /></legend>
      <table width="100%"  border="0" cellpadding="0">
        <tr>
          <td width="25%"><div align="right"><bean:message key="txt.SearchByUser" />:</div></td>
          <td width="75%">
          <% if( ((String)request.getAttribute("isUserDisabled")).equals("true")){    %>
            <html:text name="SchedulerListForm" property="txtSearchByUserName" styleClass="bdrColor_336633 componentStyle" style="width:200px" maxlength="20" readonly="true" onkeypress="return enter(this,event,'search_scheduler');" />					       
            <html:button property="btnSearchByUserName" styleClass="buttons" style="width:20px; height:17px" value="..." disabled="true" onclick="openList(this.form)" ></html:button>
            <% }else{ %>
              <html:text name="SchedulerListForm" property="txtSearchByUserName" styleClass="bdrColor_336633 componentStyle" style="width:200px" maxlength="20" onkeypress="return enter(this,event,'search_scheduler');" />
              <html:button property="btnSearchByUserName" styleClass="buttons" style="width:20px; height:17px" value="..." onclick="openList(this.form)" ></html:button>
            <% } %>
          </td>
        </tr>
        <tr>
          <td><div align="right"><bean:message key="txt.SearchByJobType" /></div></td>
          <td>
            <bean:define id="searchJobType" name="SchedulerListForm" property="jobType" />
            <html:select  name="SchedulerListForm" property="cboSearchByJobType" style="width:223px" styleClass="bdrColor_336633 componentStyle bgClrLvl_5">
              <%
              if(searchJobType.equals(SchedulerConstants.ALL_JOBS)){
              %>
              <option selected value="<%=SchedulerConstants.ALL_JOBS%>"><bean:message key="lbl.All" /></option>
              <%
              }else{
              %>
                <option value="<%=SchedulerConstants.ALL_JOBS%>"><bean:message key="lbl.All" /></option>
              <%
              }
              %>
              <%
              if(searchJobType.equals(SchedulerConstants.MAIL_JOB)){
              %>
              <option selected value="<%=SchedulerConstants.MAIL_JOB%>"><bean:message key="lbl.Mail" /></option>
              <%
              }else{
              %>
                <option value="<%=SchedulerConstants.MAIL_JOB%>"><bean:message key="lbl.Mail" /></option>
              <%
              }
              %>
              <%
              if(searchJobType.equals(SchedulerConstants.FAX_JOB)){
              %>
              <option selected value="<%=SchedulerConstants.FAX_JOB%>"><bean:message key="lbl.Fax" /></option>
              <%
              }else{
              %>
                <option value="<%=SchedulerConstants.FAX_JOB%>"><bean:message key="lbl.Fax" /></option>
              <%
              }
              %>
            </html:select>                    
          </td>
        </tr>
        <tr><td colspan="2" height="4px"></td></tr>
      </table>
      </fieldset>
    </td>
    <td width="30%">
      <fieldset>
      <legend align="left"><bean:message key="lbl.CreatedDate" /></legend>
      <table width="100%" border="0" cellpadding="0">
        <tr>
        <td width="26%"><div align="right"><bean:message key="lbl.From" /></div></td>
        <td width="74%">
          <html:hidden name="SchedulerListForm" property="txtCreateFromDate" styleClass="bdrColor_336633 componentStyle" style="width:120px"  />
          <script>
          createDateFrom.render();
          </script>
        </td>
        </tr>
        <tr>
        <td><div align="right"><bean:message key="lbl.To" />:</div></td>
        <td>
          <html:hidden name="SchedulerListForm" property="txtCreateToDate" styleClass="bdrColor_336633 componentStyle" style="width:120px"  />                    
          <script>createDateTo.render()</script>
        </td>
        </tr>
      </table>
      </fieldset>
    </td>
    <td width="30%">
      <fieldset>
      <legend align="left"><bean:message key="lbl.DispatchDate" /></legend>
      <table width="100%" border="0" cellpadding="0">
        <tr>
        <td width="26%">
          <div align="right">
          <bean:message key="lbl.From" />
          </div>
        </td>
        <td width="74%">
          <html:hidden name="SchedulerListForm" property="txtDispatchFromDate" styleClass="bdrColor_336633 componentStyle" style="width:120px"  />
          <script>dispatchDateFrom.render()</script>      
        </td>
        </tr>
        <tr>
        <td>
          <div align="right">
          <bean:message key="lbl.To" />:
          </div>
        </td>
        <td>
          <html:hidden name="SchedulerListForm" property="txtDispatchToDate" styleClass="bdrColor_336633 componentStyle" style="width:120px"  />
          <script>dispatchDateTo.render()</script>                          
        </td>
        </tr>
      </table>
      </fieldset>
    </td>
    </tr>
    <tr><td height="3px"></td></tr>
  </table>
  </td>
  </tr>
  <tr><td colspan="2" class="bgColor_e8e8e8" height="5px"></td></tr>
  <tr>
  <td colspan="2" align="center" >		
  <div class=" bgColor_e8e8e8" style="overflow:auto; width:100%; height:287px">
  <table class="bgColor_e8e8e8" width="100%" border="0"  cellspacing="1" cellpadding="0" id="data">
    <tr>
    <th align="center" height="18" width="3%" class="th669966"><bean:message key="tbl.head.Select" /></th>
    <th align="center" width="24%" class="th669966"><bean:message key="tbl.head.JobName" /></th>
    <th align="center" width="5%" class="th669966"><bean:message key="tbl.head.JobType" /></th>
    <th align="center" width="10%" class="th669966"><bean:message key="tbl.head.User" /></th>
    <th align="center" width="14%" class="th669966"><bean:message key="tbl.head.CreateDate" /></th>
    <th align="center" width="14%" class="th669966"><bean:message key="tbl.head.DispatchDate" /></th>
    <th align="center" width="16%" class="th669966"><bean:message key="tbl.head.SchedulerStatus" /></th>
    </tr>
    <% if(jobs.size()>0) {%>
    <logic:iterate name="jobs" id="job" >
     <%if (firstTableRow == true){ firstTableRow = false; %>
      <tr  class="trF7F7F7">
    <%}else{ firstTableRow = true; %>
      <tr  class="trD7EFD7">                  
    <%}%>
    <td>
      <div align="center">
      <input type="radio" name="radSelect" tabindex="7" value="<bean:write name="job" property="jobType"/> <bean:write name="job" property="jobName"/>" >
      </div>
    </td>
    <td align="left" title="<bean:write name="job" property="jobName"/>"> <bean:write name="job" property="trimJobName"/> </td>
    <td align="left"> <bean:write name="job" property="jobType"/> </td>
    <td align="left" title="<bean:write name="job" property="creatorName"/>"> <bean:write name="job" property="trimCreatorName"/> </td>
    <td align="left"> <bean:write name="job" property="createTime"/> </td>
    <td align="left"> <bean:write name="job" property="executionTime"/> </td>
    <td align="left"> <bean:write name="job" property="retrialCount"/>/<bean:write name="job" property="jobErrorMessage"/> </td>
    </tr>   
    </logic:iterate>
    <%}%> 
  </table>
  <% if(jobs.size()==0) {%>
    <div style="width:100%; position:relative; top:175px; text-align:center;" class="tabText"><bean:message key="info.no_item_found.no_item_found" /></div> 
   <%}%>
  </div> 
  </td>
  </tr>
</table>
<!-- bdrColor_336633 table ends above-->
</td>
</tr>
<tr><td height="2px"></td></tr>
<tr>
  <td align="center">
  <table class="bdrColor_336633 bgColor_e8e8e8 " width="980px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
    <tr>
    <td width="30px"><div class="imgStatusMsg"></div></td>
    <td width="747px">
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
    <td width="3px">
    <div style="float:left; width:1px;height:22px;" class="bgClrLvl_2"></div>
    <div style="float:left; width:1px;height:22px;" class="bgClrLvl_F"></div>
    </td>
    <td  width="200px" align="right">
      <script>navBar.render();</script>
    </td>
    </tr>
  </table>
  <!-- statusBar table ends above-->
  </td>
</tr>
</table>
<table align="center" width="980px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
  </tr>
</table>
</html:form>
<!--Content Ends-->
</body>
</html:html>

