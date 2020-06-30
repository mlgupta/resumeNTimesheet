<%@ page import="rts.web.actionforms.filesystem.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<bean:define id="docHistoryListForm" name="docHistoryListForm" type="rts.web.actionforms.filesystem.DocHistoryListForm" />
<%
DocHistoryListForm docHistoryListForm = (DocHistoryListForm)request.getAttribute("docHistoryListForm");
Long chkFolderDocId = docHistoryListForm.getChkFolderDocIds()[0];
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
Hello World
</title>
</head>
<body>
<html:form action="/docHistoryListAction"  >
    <html:hidden name="docHistoryListForm" property="radDocId"  />
    <html:hidden property="chkFolderDocIds" value="<%=chkFolderDocId%>" />    
    <html:hidden name="docHistoryListForm" property="documentName"  />
    <html:hidden name="docHistoryListForm" property="txtHistoryPageNo"  />
    <html:hidden name="docHistoryListForm" property="txtHistoryPageCount"  />
</html:form>

<script language="" >
    document.forms[0].submit();
</script>

</body>
</html>
