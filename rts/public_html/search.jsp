<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="rts.web.beans.filesystem.*" %>

<bean:define id="folderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="advanceSearchForm" name="advanceSearchForm" type="rts.web.actionforms.filesystem.AdvanceSearchForm" />
<html>
  <head>
    <title>Search</title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    <script src="general.js"></script>
    <script language="JavaScript" type="text/JavaScript">
    <!--
    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }
    //-->
    
    /*********************MM_findObj starts***********************************/    
    function MM_findObj(n, d) { //v4.01
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
      d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && d.getElementById) x=d.getElementById(n); return x;
    }
    /************************ MM_findObj text ends ****************************/
    
    /************************MM_showHideLayers starts**************************/
    function MM_showHideLayers() { //v6.0
      var i,p,v,obj,args=MM_showHideLayers.arguments;
      for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
        if (obj.style) { obj=obj.style; v=(v=='show')?'':(v=='hide')?'none':v; }
        obj.display=v; }
    }
    /******************** MM_showHideLayers text ends *************************/
    
    var theNewDocLayerTimer;
    function newDocument_onclick() {
      My_showHideLayers('lyrNewDocument','','show')
    }
  
    function newdoclayer_onmouseover() {
      clearTimeout(theNewDocLayerTimer);
    }
  
    function newdoclayer_onmouseout() {
      theNewDocLayerTimer=setTimeout("My_showHideLayers('lyrNewDocument','','hide')",500);
    }
    
    //Called when Search Lookup button is clicked
    
    function lookuponclick(){
    
      openWindow("folderDocSelectB4Action.do?heading=<bean:message key="lbl.ChoosePath" />&foldersOnly=true&openerControl=txtLookinFolderPath&recreate=true","searchLookUp",495,390,0,0,true);
    
    }

    function folderDocSearch(){
        document.forms[0].target = "_top";
        document.forms[0].action = "advanceSearchAction.do";
        document.forms[0].submit();
    }

    function SearchReset(){    
        document.forms[0].target="_top";
        document.forms[0].action="b4ToggleFolderSearchAction.do";
        document.forms[0].submit();    
    }
    
    </script>
  </head>
  <body style="margin-left:10px;" class="bgColor_e8e8e8">
  <html:form action="/advanceSearchAction">
  <table id="searchShowHide" width="245px" border="0" align="left" cellpadding="0" cellspacing="2">
    
    <tr>
      <td height="385px" align="center" valign="top">
        
          <table width="243px"  border="0" align="center" cellpadding="0" cellspacing="1">
            
            <tr><td height="10px"></td></tr>
            
            <tr>
              <td>
                <bean:message key="lbl.SearchforFoldersAndDocuments" />:
              <td>
            </tr>
          
            <tr>
              <td>
                <html:text name="advanceSearchForm" property="txtFolderOrDocName" styleClass="bdrColor_336633 componentStyle" style="width:210px; height:18px" />
              </td>
            </tr>
            
            <tr>
              <td>
                <bean:message key="lbl.YouCanUseWildCard" />
              </td>
            </tr>

            <tr><td height="5px"></td></tr>                      
            
            <tr>
              <td><bean:message key="lbl.Description" /></td>
            </tr>
            
            <tr>
              <td>
                <html:text name="advanceSearchForm" property="txtDocDescription" styleClass="bdrColor_336633 componentStyle" style="width:210px; height:18px" />
              </td>
            </tr>
            
            <tr><td height="5px"></td></tr>
            
            <tr>
              <td><bean:message key="lbl.LookIn" /></td>
            </tr>
            
            <tr>
              <td>
                <html:text name="advanceSearchForm" property="txtLookinFolderPath" styleClass="bdrColor_336633 componentStyle" style="width:186px; height:18px" readonly="true" />
                <html:button onclick="lookuponclick();" property="btnLookUp" style="width:20px; height:18px; cursor:pointer; cursor:hand" styleClass="buttons" value="..." />
              </td>
            </tr>

            <tr><td height="5px"></td></tr>
            
            <tr>
              <td><bean:message key="lbl.ContainingText" />:</td>
            </tr>
            
            <tr>
              <td><html:text name="advanceSearchForm" property="txtContainingText" styleClass="bdrColor_336633 componentStyle" style="width:210px" onkeypress="return alphaNumericOnly(event);" /></td>
            </tr>

            <tr><td height="5px"></td></tr>
            
            <tr>
              <td>
                <div align="left">
                  <html:button property="btnSearch" onclick="folderDocSearch()" styleClass="buttons" style="width:60px" ><bean:message key="btn.Search" /></html:button>
                  <html:button property="btnStop" styleClass="buttons" style="width:60px" ><bean:message key="btn.Stop" /></html:button>     
                  <html:button property="btnReset" onclick="SearchReset()" styleClass="buttons" style="width:60px" ><bean:message key="btn.Reset" /></html:button>
                </div>
              </td>
            </tr>
            
            <!--<tr>
              <td width="10%" align="left">
                <a onclick="folderDocSearch()" class="imgGo"  title="<bean:message key="tooltips.Go" />"></a>
              </td>
            </tr> -->
        </table>
      </td>
    </tr>
  </table>
  </html:form>
  </body>
</html>
