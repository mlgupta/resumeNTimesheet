<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<%@ page import="org.apache.struts.action.*" %>
<%@ page import="java.util.ArrayList" %>

<%
  ArrayList drawerLists = (ArrayList)request.getAttribute("drawerLists");
  boolean isFirstSet = ((Boolean)request.getAttribute("isFirstSet")).booleanValue();
  boolean isLastSet = ((Boolean)request.getAttribute("isLastSet") ).booleanValue();
%>
<script>

</script>

<html>
  <head>
    <title>Drawer Lisiting</title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
  </head>
  <body style="margin:0px">
  <table id="drawerList" width="1000px" border="0" align="center" cellpadding="1" cellspacing="0" style="margin-top:1px" class="bgColor_e8e8e8">
    <tr>
      <td width="20px" valign="middle">
        <%if( !isFirstSet ){%>
          <a href="drawerListAction.do?oper=prev" class="imgMoreLeftDrawers" onmouseout="this.className='imgMoreLeftDrawers'" onmouseover="this.className='imgMoreLeftDrawers'" title="More Drawers"></a>
        <%}%>
      </td>
        
      <td width="960px" height="20px" valign="middle">
        <% if( drawerLists.size() > 0 ) {%>
          <logic:iterate id="folderDocList" name="drawerLists" type="rts.web.beans.filesystem.FolderDocList" > 
            <a class="drawerListLink" target="_top" href="folderDocFolderClickAction.do?currentFolderId=<bean:write name="folderDocList" property="id" />" title="<bean:write name="folderDocList" property="path" />" style="float:left">&nbsp;[<bean:write name="folderDocList" property="name" />]&nbsp;</a>          
          </logic:iterate>      
        <%}%>
      </td>
        
      <td width="20px" valign="middle">
        <%if( !isLastSet ){%>
          <a href="drawerListAction.do?oper=next" class="imgMoreRightDrawers" onmouseout="this.className='imgMoreRightDrawers'" onmouseover="this.className='imgMoreRightDrawers'" title="More Drawers"></a>
        <%}%>
      </td>  
    </tr>
  </table>
  </body>
</html>
