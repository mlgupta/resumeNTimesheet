<%@ page import="rts.web.beans.filesystem.*" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="docHistoryListForm" name="docHistoryListForm" type="rts.web.actionforms.filesystem.DocHistoryListForm" />
<bean:define id="documentHistoryDetails" name="documentHistoryDetails" type="ArrayList" />
<bean:define id="txtHistoryPageCount" name="docHistoryListForm" property="txtHistoryPageCount" />        
<bean:define id="txtHistoryPageNo" name="docHistoryListForm" property="txtHistoryPageNo" />

<%
  boolean firstTableRow = true;
  boolean isCheckedOut;
  if(((DocumentHistoryDetail)documentHistoryDetails.get(0)).getDocId() == null){
    isCheckedOut = true;
  }else{
    isCheckedOut = false;
  }
  int rtsType = ((Integer)request.getAttribute("rtsType")).intValue();
%> 

<html:html>
  <head>
  <title><bean:message key="title.DocumentHistory" /></title>

  <link href="themes/main.css" rel="stylesheet" type="text/css">

  <script src="general.js"></script>
  <script src="navigationbar.js" ></script>
  <script language="JavaScript" type="text/JavaScript">
    var navBar=new NavigationBar("navBar");
    navBar.setPageNumber(<%=txtHistoryPageNo%>);
    navBar.setPageCount(<%=txtHistoryPageCount%>);
    navBar.onclick="historyNavgation()";
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
  
    function historyNavgation(){
      document.forms[0].action="docHistoryListAction.do";
      document.forms[0].txtHistoryPageNo.value=navBar.getPageNumber();
      document.forms[0].submit();
    }

    function initialize(){
      if (typeof document.forms[0].radDocId[0] !="undefined") { 
        document.forms[0].radDocId[0].checked=true;
      }else{
        if (typeof document.forms[0].radDocId !="undefined") { 
          document.forms[0].radDocId.checked=true;
        }
      }
    }

    //Called when delete action is performed
    function docHistoryDelete(){
      var thisForm = document.forms[0];
      var execute = true;
      var isCheckedOut = <%=isCheckedOut%>
      if(!isCheckedOut){
        if (typeof document.forms[0].radDocId[0] !="undefined") { 
          if(document.forms[0].radDocId[0].checked == true){
            execute = false;
            alert("<bean:message key="msg.latestversion.deletedenied" />");
          }
        }else{
          if (typeof document.forms[0].radDocId !="undefined") { 
            if(document.forms[0].radDocId.checked == true){
              execute = false;
              alert("<bean:message key="msg.latestversion.deletedenied" />");
            }
          }
        }
      }else{
        if(document.forms[0].radDocId[0].checked == true ){
          execute = false;
          alert("<bean:message key="msg.latestversion.deletedenied" />");
        }
        if(document.forms[0].radDocId[1].checked == true ){
          execute = false;
          alert("<bean:message key="msg.latestversion.deletedenied" />");
        }
      }    
      if(execute){
        if (confirm("<bean:message key="msg.delete.confirm" />")){
          thisForm.target = "_self";
          thisForm.action = "docHistoryDeleteAction.do";
          thisForm.submit();
        }
      }
    }
  
    //Called when rollback action is performed
  
    function docHistoryRollback(){
      var thisForm = document.forms[0];
      if (confirm("<bean:message key="msg.rollback.confirm" />")){
        thisForm.target = "_self";
        thisForm.action = "docHistoryRollbackAction.do";
        thisForm.submit();
      }
    }

    function docHistoryDetail(){
      var thisForm = document.forms[0];
      openWindow("","DocHistoryDetail",375,375,0,0,true);
      thisForm.target = "DocHistoryDetail";
      thisForm.action = "docHistoryDetailAction.do";
      thisForm.submit();
    }
    
    function docTmsHistoryDetail(){
      var thisForm = document.forms[0];
      openWindow("","DocHistoryDetail",300,375,0,0,true);
      thisForm.target = "DocHistoryDetail";
      thisForm.action = "docHistoryDetailAction.do";
      thisForm.submit();
    
    }
    
  </script>

  </head>
  <body onload="initialize();">
    <html:form action="/docHistoryListAction" >
    
      <html:hidden property="hdnActionType" value="" />
      <html:hidden property="chkFolderDocIds" value="<%=docHistoryListForm.getChkFolderDocIds()[0]%>" />
      <html:hidden property="documentName" value="<%=docHistoryListForm.getDocumentName()%>" />
      <html:hidden property="txtHistoryPageNo" value="<%=txtHistoryPageNo%>" /> 
    
      <table id="tabParent" align="center"  width="500px"  border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td style="height:10px;"></td>
        </tr>
        
        <tr>
          <td>
            <table width="160px" border="0" cellpadding="0" cellspacing="0" id="tab">
              <tr>
                <td width="5px" class="imgTabLeft"></td>
                <td width="150px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.DocumentHistory" /></div></td>
                <td width="5px" class="imgTabRight"></td>
              </tr>
            </table>
          </td>
        </tr>
      
      </table>
      
      <table width="500px" border="0" align="center" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633" id="bdrColor_336633">

        <tr>
          <td>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="innerContainer">
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="1">
                    <tr class="bgColor_e8e8e8 bdrTopColor_FFFFFF"> 
                      <td>
                        <%if( rtsType == 1 ){%>
                          <a onclick="docHistoryDetail();" class="imgVersionDetail" onmouseout="this.className='imgVersionDetail'" onmouseover="this.className='imgVersionDetail'" style="margin-left:5px;" title="<bean:message key="tooltips.VersionDetail" /> "></a>
                        <%}else{%>
                          <a onclick="docTmsHistoryDetail();" class="imgVersionDetail" onmouseout="this.className='imgVersionDetail'" onmouseover="this.className='imgVersionDetail'" style="margin-left:5px;" title="<bean:message key="tooltips.VersionDetail" /> "></a>
                        <%}%>
                        <a onclick="docHistoryDelete();" class="imgVersionDelete" onmouseout="this.className='imgVersionDelete'" onmouseover="this.className='imgVersionDelete'" style="margin-left:2px;" title="<bean:message key="tooltips.VersionDelete" /> "></a>
                      </td>
                      <td align="right">
                        <bean:message key="lbl.Family" />&nbsp;:&nbsp;
                        <bean:write name="docHistoryListForm" property="documentName" />&nbsp;
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>    
        
              <tr>
                <td>
                  <div class=" bgColor_e8e8e8" style="overflow:auto; width:100%; height:324px" >
                    <table class="bgColor_e8e8e8" id="data" width="100%" border="0" cellpadding="0" cellspacing="1">
                      <tr> 
                        <th width="8%" height="18px" class="th669966"><bean:message key="tbl.head.Select" /></th>
                        <th width="10%" class="th669966"><bean:message key="tbl.head.VersionNo" /></th>
                        <th width="25%" class="th669966"><bean:message key="tbl.head.User" /></th>
                        <th width="42%" class="th669966"><bean:message key="tbl.head.VersionDate" /></th>
                        <th width="14%" class="th669966"><bean:message key="tbl.head.Action" /></th>
                      </tr>
          
                      <logic:iterate id="documentHistoryDetail" name="documentHistoryDetails" type="rts.web.beans.filesystem.DocumentHistoryDetail" >

                        <%if (firstTableRow == true){ firstTableRow = false; %>
                          <tr class="trF7F7F7">
                        <%}else{ firstTableRow = true; %>
                          <tr class="trD7EFD7">                  
                        <%}%>
      
                        <logic:empty name="documentHistoryDetail" property="docId" > 
                          <td align="center"><html:radio property="radDocId" value=""  /></td>
                          <td><bean:write name="documentHistoryDetail" property="versionNumber" /></td>
                        </logic:empty>
      
                        <logic:notEmpty name="documentHistoryDetail" property="docId" > 
                          <td align="center">
                            <bean:define  id="docId" name="documentHistoryDetail" property="docId" />
                            <html:radio property="radDocId" value="<%=docId%>"  />
                          </td>
                          <td>
                            <a target="newWindow" href="docDownloadAction.do?documentId=<bean:write name="documentHistoryDetail" property="docId" />" class="menu">
                              <bean:write name="documentHistoryDetail" property="versionNumber" />...
                            </a>
                          </td>
                        </logic:notEmpty>
      
                        <td><bean:write name="documentHistoryDetail" property="userName" /></td>
                        <td><bean:write name="documentHistoryDetail" property="versionDate" /></td>
                        <td><bean:write name="documentHistoryDetail" property="actionType" /></td>
                      </tr>
                    </logic:iterate>
                  </table>
                </div>
              </td>
              </tr>
              <table width="500px" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right" height="25px" >
                    <html:button property="btnClose" onclick="window.close()" styleClass="buttons" style="width:70px; margin-right:5px;"  ><bean:message key="btn.Close" /></html:button>
                  </td>
                </tr>
              </table>
            </table>
          </td>
        </tr>
      </table>

      <!-- status bar-->
      <table class="bdrColor_336633 bgColor_e8e8e8" align="center" width="500px" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0" id="statusBar">
        <tr>
          <td width="30px"><div class="imgStatusMsg"></div></td>
          <td width="367px"></td>
          <td width="3px">
            <div style="float:left; width:1px;height:22px;" class="bgClrLvl_2"></div>
            <div style="float:left; width:1px;height:22px;" class="bgClrLvl_F"></div>
          </td>
          <td width="100px" align="right">
            <script>navBar.render();</script>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>