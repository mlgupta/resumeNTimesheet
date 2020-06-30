<%@ page import="rts.web.actions.filesystem.*" %>
<%@ page import="rts.web.actionforms.filesystem.*" %>
<%@ page import="org.apache.struts.action.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<% ActionMessages actionMessages =(ActionMessages)request.getAttribute("actionMessages"); 
    if(actionMessages!=null){
      out.print(actionMessages.size());
    }
%>
<html:html>
<head>
<title><bean:message key="title.UploadDocument" /></title>
<jsp:include page="/style_sheet_include.jsp" />
<script src="general.js"></script>
<script>
    
  function closewindow(){
    document.forms[0].target=opener.name;
    document.forms[0].submit();
    window.location.replace("blank.html");
  }

  
</script>
</head>
<html:form action="folderDocListAction.do">
<body>
<table id="tabContainer" align="center" height="100%" width="100%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
  <td >
    <table id="tabParent" align="center" width="350px"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <table id="tab" width="150px" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="5px" class="imgTabLeft"></td>
              <td width="140px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.UploadDocuments" /></div></td>
              <td width="5px" class="imgTabRight"></td>            
            </tr>
          </table>
        </td>
  		</tr>
    </table>
<table id="borderClrLvl_2" align="center" width="350px"  border="0" cellpadding="0" cellspacing="0" class="imgData bgClrLvl_4 borderClrLvl_2"  >
  <tr>
    <td >
        <table width="100%"  align="left" border="0"  cellpadding="0" cellspacing="2" align="center">
          <tr><td height="25px" ></td></tr>
          <tr>
                <td align="center" >

              <%  if(((Integer)request.getAttribute("errorIndex")).intValue()== -1){%>
                    <div class="tabText"><b><bean:message key="msg.success" /></b></div>
              <%
                  }else{
                    if(((Integer)request.getAttribute("errorIndex")).intValue()== 0){
              %>        
                      <table>
                        <tr>
                          <td align="center" class="tabText" style="color:#ff0000;" > 
                          <b><bean:message key="msg.error" /></b>
                          </td>
                        </tr>
                        <tr>
                          <td align="center" class="tabText" >
                          <b><bean:message key="msg.failure" /></b>
                          </td>
                        </tr>                      
                      </table>
              <%    }
                  }
              %>
              
            </td>
          </tr>
          <tr><td height="15px" ></td></tr>
          <tr>
            <td align="center">
            <html:button property="btnOK" onclick="closewindow()" styleClass="buttons" style="width:70px"><bean:message key="btn.Ok" /></html:button>
            </td>
          </tr>
          <tr><td height="25px" ></td></tr>
      </table>
    </td>
  </tr>
</table>
</td>
</tr>
</table>
</html:form>
</html:html>