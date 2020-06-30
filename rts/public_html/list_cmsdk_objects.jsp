<%@ page import="oracle.ifs.common.*" %>
<%@ page import="oracle.ifs.beans.*" %>
<%@ page import="oracle.ifs.search.*" %>
<%@ page import="rts.beans.DbsLibrarySession" %>
<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>
<%@ page import="rts.web.actionforms.filesystem.*" %>
<%@ page import="java.util.*" %>

<%
    PublicObject publicObject;
    DbsLibrarySession dbsLibrarySession = ((UserInfo)session.getAttribute("UserInfo")).getDbsLibrarySession();
    LibrarySession librarySession = dbsLibrarySession.getLibrarySession();
    List searchQualificationList = new ArrayList();     


    AttributeSearchSpecification attributeSearchSpec = new AttributeSearchSpecification();
    SearchClassSpecification searchClassSpec;

    String [] classes = new String[] {PublicObject.CLASS_NAME,AccessControlList.CLASS_NAME};
    searchClassSpec = 	new SearchClassSpecification(classes);
    
    searchClassSpec.addResultClass(PublicObject.CLASS_NAME);
    // join DbsDocument and DbsContentObject
    JoinQualification jq = new JoinQualification();
    jq.setLeftAttribute(PublicObject.CLASS_NAME, PublicObject.ACL_ATTRIBUTE);
    jq.setRightAttribute(AccessControlList.CLASS_NAME, null);
    searchQualificationList.add(jq);

    AttributeQualification folderDocNameAttrbQual = new AttributeQualification();
    String searchColumn = "NAME";
    folderDocNameAttrbQual.setCaseIgnored(true);
    folderDocNameAttrbQual.setAttribute(AccessControlList.CLASS_NAME,searchColumn);
    folderDocNameAttrbQual.setOperatorType(AttributeQualification.LIKE);
    String searchValue = "j%";
    folderDocNameAttrbQual.setValue(searchValue);
    searchQualificationList.add(folderDocNameAttrbQual);

    SearchQualification searchQual = null;
    Iterator iterator = searchQualificationList.iterator();
    while (iterator.hasNext()){
        SearchQualification nextSearchQualification = (SearchQualification) iterator.next();
        if (searchQual == null) {
            searchQual = nextSearchQualification;
        }else{
            searchQual = new SearchClause(searchQual, nextSearchQualification, SearchClause.AND);
        }
    }

    SearchSortSpecification searchSortSpec = new SearchSortSpecification();
    searchSortSpec.add(PublicObject.NAME_ATTRIBUTE, true);

    attributeSearchSpec.setSearchQualification(searchQual);
    attributeSearchSpec.setSearchClassSpecification(searchClassSpec);    
    attributeSearchSpec.setSearchSortSpecification(searchSortSpec);
                
    // create and run the search
    Search search= new Search(librarySession, attributeSearchSpec);
    search.open();
//    SearchResultObject[] arrSRO = search.getItems();
    

%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
Hello World
</title>
</head>
<body>

<table id="headerIncluder" width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="96px">
		<!--Header Starts-->
		<jsp:include page="/header.jsp" />
		<!--Header Ends-->
	</td>
  </tr>
</table>

<table  cellspacing="2" cellpadding="1" border="1" width="100%" align="left">
    <tr>
        <td align="center"><h3>Public Object Search Result</h3></td>
    </tr>
    
</table>
<br>

<table  cellspacing="2" cellpadding="1" border="1" width="100%" align="left">
    <tr>
        <td><h4>Total Items : </h4></td>
        <td><h4><%=search.getItemCount()%></h4></td>
    </tr>
    
</table>
<br>

<table  cellspacing="2" cellpadding="1" border="1" width="100%" align="left">
<%for(int index = 0; index < search.getItemCount(); index++){%>
<%publicObject = (PublicObject)search.next().getLibraryObject();%>
    <%// if(publicObject.getAnyFolderPath() == null) {%>
        <tr>
            <td><%=index%></td>
            <%if(publicObject.getOwner() != null){%>
                <td><%=publicObject.getOwner().getName()%></td>
            <%}else{%>
                <td></td>
            <%}%>
            <td><%=publicObject.getId()%></td>
            <td><%=publicObject.getName()%></td>
            <%if(publicObject.getAnyFolderPath() != null){%>
                <td><%=publicObject.getAnyFolderPath()%></td>
            <%}else{%>
                <td><%=publicObject.getClassname()%></td>
            <%}%>
            <td><%=publicObject.getAcl().getName()%></td>
        </tr>
    <%//}%>
<%}%>
</table>

</body>
</html>
