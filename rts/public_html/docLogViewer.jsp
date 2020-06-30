<%@ page import="rts.web.beans.filesystem.DocLogConstants" %>

<%@ page import="rts.web.beans.filesystem.*" %>



<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<bean:define id="pageCount" name="DocLogListForm" property="txtPageCount"  />        

<bean:define id="pageNo" name="DocLogListForm" property="txtPageNo" />    

<%

//Variable declaration

boolean firstTableRow;

firstTableRow = true;

%>

<html:html>

<head>

<title><bean:message key="title.DocLogViewer" /></title>

<jsp:include page="style_sheet_include.jsp" />

<script src="general.js"></script>

<script src="datepicker.js"></script>

<script src="timezones.js"></script>

<script src="navigationbar.js" ></script>

<!-- Date Related Scripts-->

<script>

    var docLogDateFrom = new Calendar("docLogDateFrom",0,0);

    docLogDateFrom.onclick="selectedValues(docLogDateFrom,'txtDocLogFromDate')";

    docLogDateFrom.onclear="clearValues('txtDocLogFromDate')";

    docLogDateFrom.timezoneDisabled=true;

    docLogDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';

    docLogDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';

    docLogDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';

    docLogDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';

    docLogDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';

    docLogDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';

    docLogDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';

    docLogDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';

    docLogDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';

    docLogDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';

    docLogDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';

    docLogDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';

    docLogDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';

    docLogDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

    

    var docLogDateTo = new Calendar("docLogDateTo",0,0);

    docLogDateTo.onclick="selectedValues(docLogDateTo,'txtDocLogToDate')";

    docLogDateTo.onclear="clearValues('txtDocLogToDate')";

    docLogDateTo.timezoneDisabled=true;

    docLogDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';

    docLogDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';

    docLogDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';

    docLogDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';

    docLogDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';

    docLogDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';

    docLogDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';

    docLogDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';

    docLogDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';

    docLogDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';

    docLogDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';

    docLogDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';

    docLogDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';

    docLogDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

    



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

    if(document.forms[0].txtDocLogFromDate.value!=""){

        currentDate=new Date(document.forms[0].txtDocLogFromDate.value);

    }else{

        currentDate=new Date();

    }

    docLogDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),

                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());

    docLogDateFrom.setTimezone(document.forms[0].timezone.value);


    if(document.forms[0].txtDocLogFromDate.value!=""){

        docLogDateFrom.click();

    }
        

    if(document.forms[0].txtDocLogToDate.value!=""){

        currentDate=new Date(document.forms[0].txtDocLogToDate.value);

    }else{

        currentDate=new Date();

    }

    docLogDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),

                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            

    docLogDateTo.setTimezone(document.forms[0].timezone.value);

    if(document.forms[0].txtDocLogToDate.value!=""){

        docLogDateTo.click();

    }               

  }
  
</script>

<html:javascript formName="userListForm" dynamicJavascript="true" staticJavascript="false"/>

<script language="Javascript1.1" src="staticJavascript.jsp"></script>



<script language="Javascript1.1" >



function openList(thisForm){

// openWindow('relayAction.do?operation=user_select_for_log&control=txtSearchByUserName','listingUser',500,450,0,0,true);
  openWindow('relayAction.do?operation=page_user_select&control=txtSearchByUserName','listingUser',505,450,0,0,true);

}


function closewindow(){    
  window.location.replace("blank.html");
}



function callPage(relayOperation){

  var thisForm = document.forms[0];
  
  thisForm.operation.value = relayOperation;
  thisForm.action="docViewLogAction.do";
  thisForm.submit();

}

  function enter(thisField,e,relayOperation){

    var i;

    i=handleEnter(thisField,e);

    if (i==1) {

      return callPage(relayOperation);

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

</head>

<body style="margin:7" onload="window_onload();">

<html:form action="/b4DocDoNothingAction.do" focus="txtSearchByUserName" name="DocLogListForm"  type="rts.web.actionforms.filesystem.DocLogListForm" >

<html:hidden name="DocLogListForm" property="operation" />

<html:hidden name="DocLogListForm" property="txtPageNo" />

<html:hidden name="DocLogListForm" property="timezone" />

<html:hidden name="DocLogListForm" property="chkFolderDocIds" />

<!-- This page contains 3 outermost tables, named 'headerIncluder', 'errorContainer' and 'tabContainer' -->


<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">

<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->

<tr>

<td align="center">

<table id="tabParent" width="640px"  height="10px" border="0" cellpadding="0" cellspacing="0" >

  <tr>

    <td>

      <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0" >

        <tr>

          <td width="5px" class="imgTabLeft"></td>

          <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.DocLogList" /></div></td>

          <td width="5px" class="imgTabRight"></td>

        </tr>

      </table>

    </td>

  </tr>

</table>

 

<table width="640px" border="0" cellpadding="0" cellspacing="0" class="borderClrLvl_2" id="borderClrLvl_2" >

    <tr><td height="4px" class="bgClrLvl_4" colspan="2"></tr>
    
    <tr class="imgToolBar bgClrLvl_3" >
          
      <td colspan="2">
          
        <a tabindex="6" class="imgGo" title='<bean:message key="btn.Go" />'  style="float:right;margin-right:5px" onclick="return callPage('search_log')" >        

      </td>
      
    </tr>    

    <tr><td height="4px" class="bgClrLvl_4" colspan="2"></tr>
    
		<tr class="bgClrLvl_4" >

	  <td colspan="2">

		<table width="98%"  border="0" align="center" cellpadding="1" cellspacing="0" class="borderClrLvl_2">

		  <tr>

			<td width="50%" valign="top">

			  <fieldset>

			  <legend align="left"><bean:message key="lbl.LogInfo" /></legend>

				<table width="100%"  border="0" cellpadding="0">

				  <tr>

					<td width="20%"><div align="right"><bean:message key="txt.SearchByUser" />:</div></td>

					<td width="80%">

					  <html:text name="DocLogListForm" property="txtSearchByUserName" styleClass="borderClrLvl_2 componentStyle" style="width:200px" maxlength="20" onkeypress="return enter(this,event,'search_log');" />

					  <html:button property="btnSearchByUserName" styleClass="buttons" style="width:20px; height:17px" value="..." onclick="openList(this.form)" ></html:button>

					</td>

				  </tr>

				  <tr>

				
				  </tr>

          <tr><td colspan="2" height="4px"></td></tr>

				  <tr>

					<td width="20%"><div align="right"><bean:message key="txt.SearchByLogType" /></div></td>

					<td width="80%">
            
            <bean:define id="selectedLogType" name="DocLogListForm" property="cboSearchByLogType" />
					  
            <html:select name="DocLogListForm" property="cboSearchByLogType" styleClass="borderClrLvl_2 componentStyle" style="width:200px" >
              
              <% 
                if (selectedLogType.equals(DocLogConstants.Log_From_Samhita)) {
              %>
                <option selected value="<%=DocLogConstants.Log_From_Samhita%>"><bean:message key="lbl.Samhita" /></option>
              <%
                }else{
              %>
                <option value="<%=DocLogConstants.Log_From_Samhita%>"><bean:message key="lbl.Samhita" /></option>              
              <%
                }
              %>

              <% 
                if (selectedLogType.equals(DocLogConstants.Log_From_Agent)) {
              %>
                <option selected value="<%=DocLogConstants.Log_From_Agent%>"><bean:message key="lbl.Agent" /></option>
              <%
                }else{
              %>
                <option value="<%=DocLogConstants.Log_From_Agent%>"><bean:message key="lbl.Agent" /></option>              
              <%
                }
              %>

              <% 
                if (selectedLogType.equals(DocLogConstants.Log_From_Both)) {
              %>
                <option selected value="<%=DocLogConstants.Log_From_Both%>"><bean:message key="lbl.Both" /></option>
              <%
                }else{
              %>
                <option value="<%=DocLogConstants.Log_From_Both%>"><bean:message key="lbl.Both" /></option>              
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

			<td width="50%">

			  <fieldset>

				<legend align="left"><bean:message key="lbl.Date" /></legend>

				<table width="100%" border="0" cellpadding="0">

				  <tr>

					<td width="26%"><div align="right"><bean:message key="lbl.From" /></div></td>

					<td width="74%">

					  <html:hidden name="DocLogListForm" property="txtDocLogFromDate" styleClass="borderClrLvl_2 componentStyle" style="width:120px"  />

					  <script>

						docLogDateFrom.render();

					  </script>

					</td>

				  </tr>

				  <tr>

					<td><div align="right"><bean:message key="lbl.To" />:</div></td>

					<td>

					  <html:hidden name="DocLogListForm" property="txtDocLogToDate" styleClass="borderClrLvl_2 componentStyle" style="width:120px"  />                    

					  <script>docLogDateTo.render()</script>

					</td>

				  </tr>

				</table>

			  </fieldset>

			</td>

			<td width="30%">&nbsp;


			</td>

		  </tr>

		  <tr><td height="3px"></td></tr>

		</table>

	  </td>

	</tr>

	<tr><td colspan="2" class="bgClrLvl_3" height="5px"></td></tr>

	  <tr>

		<td colspan="2" align="center" >		

		<div class="imgData bgClrLvl_4" style="overflow:auto; width:100%; height:287px">

		<table class="bgClrLvl_F" width="100%" border="0"  cellspacing="1" cellpadding="0" id="data">

		  <tr>

			<th align="center" width="20%"><bean:message key="tbl.head.TimeStamp" /></th>
      
      <th align="center" width="20%"><bean:message key="tbl.head.User" /></th>

			<th align="center" width="60%"><bean:message key="tbl.head.Action" /></th>

		  </tr>
      <logic:iterate id="docLogBean" name="eventArrayList" type="rts.web.beans.filesystem.DocLogBean" >          
          
        <tr class="bgClrLvl_3">          
          <td align="center"  nowrap="nowrap" style="margin-left:2px;" width="20%" ><bean:write name="docLogBean" property="timeStamp" /></td>
          <td align="center"  nowrap="nowrap" style="margin-left:2px;" width="20%" ><bean:write name="docLogBean" property="userId" /></td>
          <td align="left"  nowrap="nowrap" style="margin-left:2px;" width="60%" ><bean:write name="docLogBean" property="actionPerformed" /></td>
        </tr>
        <tr><td height="4" class="bgClrLvl_4" colspan="3"></tr>
          
       </logic:iterate>			

		</table>


	  </div> 

	</td>

  </tr>
  
  
  
  <tr class="bgClrLvl_4" >
    <td height="30px" width="70%">&nbsp;</td>
    <td align ="center" width="30%">
      <html:button property="btnOK" onclick="closewindow()" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
      <html:button property="btnHelp" onclick="openWindow('help?topic=document_view_log_html','Help',650,800,0,0,true);" styleClass="buttons" style="width:70px"><bean:message  key="btn.Help" /></html:button>
    </td>
  </tr>
  
  


</table>

<!-- borderClrLvl_2 table ends above-->


</td>

</tr>

	
</table>

</html:form>

<!--Content Ends-->

</body>

</html:html>