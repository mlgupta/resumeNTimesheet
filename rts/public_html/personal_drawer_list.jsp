<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="rts.web.beans.user.*" %>
<%@ page import="rts.web.beans.filesystem.*" %>
<%@ page import="rts.web.actions.filesystem.*" %>
<%@ page import="rts.web.actionforms.filesystem.*" %>
<%@ page import="org.apache.struts.action.*" %>
<%@ page import="java.util.*" %>

<bean:define id="folderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<bean:define id="currentFolderPath" name="FolderDocInfo" property="currentFolderPath" type="String" />
<bean:define id="currentFolderId" name="FolderDocInfo" property="currentFolderId" />
<bean:define id="userInfo" name="UserInfo" type="rts.web.beans.user.UserInfo" />

<% 

//request.setAttribute("topic","folder_doc_introduction_html");

int rowsInList = 0;
ArrayList folderDocLists = (ArrayList)request.getAttribute("folderDocLists");
if(folderDocLists != null){
  rowsInList = folderDocLists.size();
}
boolean isFirstSet = ((Boolean)request.getAttribute("isFirstSet")).booleanValue();
boolean isLastSet = ((Boolean)request.getAttribute("isLastSet") ).booleanValue();

//Variable declaration

boolean firstTableRow = true;
boolean upButtonDisabled = false;
boolean isRootFolder = ((Boolean)request.getAttribute("isRootFolder")).booleanValue();

if(currentFolderPath.equals("/")){
  upButtonDisabled = true;
}

ActionMessages actionMessages = (ActionMessages)request.getAttribute("actionMessages");

if(actionMessages != null)
  out.print(actionMessages.size());
%>
<html:html>
  <head>
    <title><bean:message key="title.Personal.Drawer.List" /></title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    <script src="navigationbar.js"></script>
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
    
    </script>
    
    <script>
    
      var navBar=new NavigationBar("navBar");

      navBar.setPageNumber(<%=folderDocInfo.getPageNumber()%>);    
      navBar.setPageCount(<%=folderDocInfo.getPageCount()%>);
      navBar.onclick="folderDocNavigate('nav')";
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
    
    <script>
    
      //Called When Navigation bar buttons Clicked
      
      function folderDocNavigate(param){
      
        var thisForm=document.forms[0]
      
        thisForm.hdnActionType.value=<%=FolderOperation.NAVIGATE%>
      
        if( param == 'nav'){
        
          thisForm.txtPageNo.value=navBar.getPageNumber();
          
        }
      
        else{
        
          thisForm.txtPageNo.value=navBar.getPageNumber()-1;
          
        }

        
        thisForm.target = "_self"
      
        thisForm.action = "folderDocNavigateAction.do";
      
        thisForm.submit();
      
      }
    
    
      //Called when up button is clicked
      
      function folderUp() { 
      
          document.forms[0].hdnActionType.value=<%=FolderOperation.UP_FOLDER%>;
      
          document.forms[0].target = "_self";
      
          document.forms[0].action = "folderDocParentAction.do";
      
          document.forms[0].submit();
      
      }
      
      
      
      //Called when back button is clicked
      
      function folderBack() {
      
          document.forms[0].hdnActionType.value=<%=FolderOperation.BACK%>;
      
          document.forms[0].target = "_self";    
      
          document.forms[0].action = "folderDocBackAction.do";
      
          document.forms[0].submit();
      
      }
      
      
      
      //Called when forward button is clicked
      
      function folderForward() { 
      
          document.forms[0].hdnActionType.value=<%=FolderOperation.FORWARD%>;
      
          document.forms[0].target = "_self";
      
          document.forms[0].action = "folderDocForwardAction.do";
      
          document.forms[0].submit();
      
      }

      //Called when refresh button is clicked
      
      function folderRefresh(){
      
          document.forms[0].hdnActionType.value=<%=FolderOperation.REFRESH%>;
      
          document.forms[0].target = "_self";    
      
          document.forms[0].action = "personalListAction.do";
      
          document.forms[0].submit();
      
      }
    
    //UnSelect All

    function unCheckChkAll(me){

      var thisForm=me.form;

      var totalCount =thisForm.chkFolderDocIds.length;

      var checkValue = (me.checked)?true:false;

      var isAllSelected=false;

      if(checkValue){        

        if (typeof totalCount != "undefined") {

          for (var count=0;count<totalCount;count++) {

            if(thisForm.chkFolderDocIds[count].checked){

              isAllSelected=true;           

            }else{

              isAllSelected=false;

              break;

            }

          }      

        }else{

            if(thisForm.chkFolderDocIds.checked){

              isAllSelected=true;           

            }else{

              isAllSelected=false;

            }

        }        

      }

      thisForm.chkAll.checked=isAllSelected;

    }
    
    //for addressbar
    
    function enter(thisField,e){
    
      var i;
    
      i=handleEnter(thisField,e);
    
      if (i==1) {
    
        return gotoFolder();
    
      }
    }
    
    //Called when go button is clicked
    
    function gotoFolder(){
    
        if (document.forms[0].txtAddress.value != "" ){
    
            document.forms[0].hdnActionType.value=<%=FolderOperation.GOTO%>;
    
            document.forms[0].target = "_self";
    
            document.forms[0].action = "folderDocGoToFolderAction.do";
    
            document.forms[0].submit();
    
       }
    
    }
    
    //Called when Search Lookup button is clicked
    
    function lookuponclick(){
    
      openWindow("folderDocSelectB4Action.do?heading=<bean:message key="lbl.ChoosePath" />&foldersOnly=true&openerControl=txtLookinFolderPath&recreate=true","searchLookUp",495,390,0,0,true);
    
    }

    function folderDocSearch(){
    
        document.forms[0].target = "_self";
    
        document.forms[0].action = "advanceSearchAction.do";
    
        document.forms[0].submit();
    
    }
    
    //Called when new drawer button is clicked
    
    function drawerNew() { 
    
        openWindow("","newDrawer",150,530,0,0,true);
    
        document.forms[0].target = "newDrawer";
    
        document.forms[0].action = "b4FolderNewAction.do";
    
        document.forms[0].submit();
    
    }
    

    /* called for resume link generation */
    function generateLinks(){
    
        var itemSelected = false;
    
        var folderSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
        var itemCount=0;
        
        if (rowsInList == 1){
    
          if(document.forms[0].chkFolderDocIds.checked){
    
            if(document.forms[0].hdnClassName.value =="FOLDER"){
          
              folderSelected=true;
            
            }else{
    
              itemSelected=true;
    
            }
    
            itemCount=1;
    
          }
    
        }
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length; counter++){
    
              if(document.forms[0].chkFolderDocIds[counter].checked && (document.forms[0].hdnClassName[counter].value == "FOLDER")){
    
               folderSelected=true; 
    
               break;
    
              }
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    itemSelected=true;
    
                    break;
    
                }
    
            itemCount=itemCount+1;
    
           }
    
        }
    
        if (itemSelected && !(folderSelected)){
    
            openWindow("","GenerateLink",230,700,0,0,true);
    
            document.forms[0].target = "GenerateLink";
    
            document.forms[0].action = "docGenerateLinkAction.do";
    
            document.forms[0].submit();
    
        }
    
        if(folderSelected){
    
            alert("<bean:message key="msg.folderdoc.select_doc_only" />");
    
        }
    
        
        if(!itemSelected && !folderSelected){
            
            alert("<bean:message key="msg.folderdoc.no_doc4link" />");
    
        }
    
    
    }

//Called when upload button is clicked

function docUpload(){
    openWindow("","Upload",360,520,0,0,true);
    document.forms[0].target = "Upload";
    document.forms[0].action = "b4DocumentUploadAction.do";
    document.forms[0].submit();
}
    
//Called when rename button is clicked

function folderDocRename(){
    var itemSelected = false;
    var rowsInList = <%=rowsInList%>;
    var counter

    if (rowsInList == 1){
        itemSelected = document.forms[0].chkFolderDocIds.checked
    }        

    if (rowsInList > 1){
      for(counter=0; counter < document.forms[0].chkFolderDocIds.length; counter++){
         if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
            break;
        }    
      }
    }

    if (itemSelected){
        openWindow("","Rename",220,530,0,0,true);
        document.forms[0].target = "Rename";
        document.forms[0].action = "folderDocB4RenameAction.do";
        document.forms[0].submit();
    }else{
        alert("<bean:message key="msg.folderdoc.notselected" />");
    }
}

//Called when Download button is clicked

function docDownload(){
    var itemSelected = false;
    var rowsInList = <%=rowsInList%>;
    var counter;
    var itemCount = 0;
    var selectedItemValue=0
    thisForm = document.forms[0];
    
    if (rowsInList == 1){
        if(document.forms[0].chkFolderDocIds.checked){
            if(thisForm.hdnClassName.value != "FOLDER"){
                itemSelected = true;
                selectedItemValue = document.forms[0].chkFolderDocIds.value;
            }
            itemCount = 1;
        }
    }        

    if (rowsInList > 1){
        for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
            if(document.forms[0].chkFolderDocIds[counter].checked){
                if(thisForm.hdnClassName[counter].value != "FOLDER"){                
                    itemSelected = true;
                    selectedItemValue = document.forms[0].chkFolderDocIds[counter].value;
                }
                itemCount = itemCount + 1;
            }
       }
    }

    if (itemSelected && itemCount == 1){
        window.location.replace("docDownloadAction.do?blnDownload=true&documentId=" + selectedItemValue);
    }else{
        if(itemCount > 1){
            alert("<bean:message key="msg.folderdoc.select.one.document" />");
        }else{
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
        }
    }
}

    // function to popup window to select target folder for link.
    function linkPopUp(){
      var itemSelected = false;
      var counter
      var folderSelected = false;
      
      if (typeof document.forms[0].chkFolderDocIds!="undefined"){
        if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
                    if(document.forms[0].hdnClassName[counter].value =="FOLDER"){
                      folderSelected = true;
                    }
                    break;
                }    
            }
        }else{
          itemSelected = document.forms[0].chkFolderDocIds.checked;
        }
      }    
  
      if ( itemSelected && !folderSelected ){
        document.forms[0].hdnActionType.value = <%=FolderOperation.LINK%>;
        openWindow('folderDocLinkB4Action.do?heading=<bean:message key="lbl.SharedLink" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true',"copyToLookup",495,390,0,0,true);
      }
      if(folderSelected){
        alert("<bean:message key="msg.folderdoc.select_doc_only" />");
      }
      if(!itemSelected && !folderSelected){
        alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
      }
    }
    
    // function to link document in target folder
    function linkForPOs(){
        var itemSelected = false;
        var counter
        if (typeof document.forms[0].chkFolderDocIds!="undefined"){
          if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
              for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
                  if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
                      break;
                  }    
              }
          }else{
            itemSelected = document.forms[0].chkFolderDocIds.checked
          }
        }
    
        if (itemSelected){
            document.forms[0].target = "_self";
            document.forms[0].action = "folderDocLinkAction.do";
            document.forms[0].submit();
        }else{
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
        }
    }


    //Called when copyTo or moveTo button is clicked
    
    function b4CopyMoveFolderDocTo(actionType){
    
        var itemSelected = false;
    
        var counter
    
        if (typeof document.forms[0].chkFolderDocIds!="undefined"){
    
          if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
    
              for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                  if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                      break;
    
                  }    
    
              }
    
          }else{
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
          }
    
        }    
    
        if (itemSelected){
    
            if(actionType == 'COPY'){
    
                document.forms[0].hdnActionType.value = <%=FolderOperation.COPY%>;
    
                openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.CopyFolderDoc" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true',"copyToLookup",495,390,0,0,true);
    
            }else{
    
                document.forms[0].hdnActionType.value = <%=FolderOperation.MOVE%>;
    
                openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.MoveFolderDoc" />&foldersOnly=true&openerControl=hdnTargetFolderPath&recreate=true',"moveToLookup",495,390,0,0,true);
    
            }
    
        }else{
    
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
        }
    
    }
    
    
    
    //Called when copyTo or moveTo ok button is clicked
    
    function copyMoveFolderDocTo(){
    
        var itemSelected = false;
    
        var counter
    
        if (typeof document.forms[0].chkFolderDocIds!="undefined"){
    
          if (typeof document.forms[0].chkFolderDocIds.length!="undefined"){
    
              for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                  if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                      break;
    
                  }    
    
              }
    
          }else{
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
          }
    
        }
    
        if (itemSelected){
    
            document.forms[0].target = "_self";
    
            var conf = confirm("<bean:message key="msg.overwrite.confirm" />");
    
            document.forms[0].hdnOverWrite.value=conf;
    
            document.forms[0].action = "folderDocCopyMoveAction.do";
    
            document.forms[0].submit();
    
        }else{
    
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
        }
    
    }
    
    
    
    //Called when copy button is clicked
    
    function folderDocCopy(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter
    
        if (rowsInList == 1){
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    break;
    
                }    
    
           }
    
        }
    
        
    
        if (itemSelected){
    
            document.forms[0].target="_self";
    
            document.forms[0].action = "folderDocCopyAction.do" ;
    
            document.forms[0].submit();
    
        }else{
    
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
        }
    
    }
    
    
    
    
    
    
    
    //Called when copy button is clicked
    
    function folderDocCut(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter
    
        if (rowsInList == 1){
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    break;
    
                }    
    
           }
    
        }
    
        
    
        if (itemSelected){
    
            document.forms[0].target="_self";
    
            document.forms[0].action = "folderDocCutAction.do" ;
    
            document.forms[0].submit();
    
        }else{
    
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
        }
    
    }
    
    
    
    
    
    
    
    //Called when paste button is clicked
    
    function folderDocPaste(){
    
        document.forms[0].target="_self";
    
        document.forms[0].action = "folderDocB4PasteAction.do" ;
    
        document.forms[0].submit();
    
    }
    
    
    //Called when delete button is clicked
    
    function folderDelete(){
    
        var itemSelected = false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
        var itemsToBeDeleted = 0;
    
        var itemName;
    
        
    
        if (rowsInList == 1){
    
            itemSelected = document.forms[0].chkFolderDocIds.checked
    
            itemsToBeDeleted = 1;
    
            itemName = document.forms[0].name1.value;
    
        }else if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(itemSelected = document.forms[0].chkFolderDocIds[counter].checked){
    
                    itemsToBeDeleted++;
    
                    itemName = document.forms[0].name1[counter].value;
    
                }
    
           }
    
        }
    
        if (itemsToBeDeleted >= 1){
    
            if (itemsToBeDeleted == 1){
    
                if (confirm("<bean:message key="msg.delete.confirm" /> \n\"" + itemName + "\" ?" )){
    
                    document.forms[0].target = "_self";
    
                    document.forms[0].action = "folderDocDeleteAction.do";
    
                    document.forms[0].submit();
    
                }
    
            }else if (itemsToBeDeleted > 1){
    
                if (confirm("<bean:message key="msg.delete.confirm" /> " + " <bean:message key="lbl.these" /> " + itemsToBeDeleted + " <bean:message key="lbl.items" />" + " ?" )){
    
                    document.forms[0].target = "_self";
    
                    document.forms[0].action = "folderDocDeleteAction.do";
    
                    document.forms[0].submit();
    
                }
    
            }
    
        }else{
    
            alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
        }
    
    }
    
    //Called when mail button is clicked
    
    function docMailAction(){    
    
        var itemSelected = false;
    
        var folderSelected =false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
        var itemCount = 0;
    
        thisForm = document.forms[0];
    
        
    
        if (rowsInList == 1){
    
            if(document.forms[0].chkFolderDocIds.checked){
    
                if(thisForm.hdnClassName.value != "FOLDER"){
    
                    itemSelected = true;
    
                }else{
    
                    folderSelected=true;
    
                }
    
                itemCount = 1;
    
            }
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
    
                    if(thisForm.hdnClassName[counter].value != "FOLDER"){                
    
                        itemSelected = true;
    
                    }else{
    
                        folderSelected=true;
    
                    }
    
                    itemCount = itemCount + 1;
    
                }
    
           }
    
        }
    
            
    
        if (itemSelected && (itemCount > 0) ){
    
            if (!folderSelected){
    
                document.forms[0].target = "_self"
    
                document.forms[0].action = "mailB4Action.do";
    
                document.forms[0].submit();
    
            }else{
    
                alert("<bean:message key="msg.folderdoc.select_doc_only" />");
    
            }
    
        }else{        
    
                alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
           
    
        }
    
    }
    
    
    
    //Called when fax button is clicked
    
    function docFaxAction(){
    
        var itemSelected = false;
    
        var folderSelected =false;
    
        var rowsInList = <%=rowsInList%>;
    
        var counter;
    
    
    
        var itemCount = 0;
    
        thisForm = document.forms[0];
    
        
    
        if (rowsInList == 1){
    
            if(document.forms[0].chkFolderDocIds.checked){
    
                if(thisForm.hdnClassName.value != "FOLDER"){
    
                    itemSelected = true;
    
                }else{
    
                    folderSelected=true;
    
                }
    
                itemCount = 1;
    
            }
    
        }        
    
        if (rowsInList > 1){
    
            for(counter=0; counter < document.forms[0].chkFolderDocIds.length;counter++){
    
                if(document.forms[0].chkFolderDocIds[counter].checked){
    
                    if(thisForm.hdnClassName[counter].value != "FOLDER"){                
    
                        itemSelected = true;
    
                    }else{
    
                        folderSelected=true;
    
                    }
    
                    itemCount = itemCount + 1;
    
                }
    
           }
    
        }
    
            
    
        if (itemSelected && (itemCount > 0) ){
    
            if (!folderSelected){
    
                document.forms[0].target = "_self"
    
                document.forms[0].action = "faxB4Action.do";
    
                document.forms[0].submit();
    
            }else{
    
                alert("<bean:message key="msg.folderdoc.select_doc_only" />");
    
            }
    
        }else{        
    
                alert("<bean:message key="msg.folderdoc.no_doc_selected" />");
    
           
    
        }
    
    }
    
    
    //Select All

    function checkAll(menu){

      var thisForm = document.forms[0];

      var checkValue =menu?(thisForm.chkAll.checked=true):((thisForm.chkAll.checked)?true:false);

      if(typeof thisForm.chkFolderDocIds != "undefined"){

        var totalRows = thisForm.chkFolderDocIds.length;

        if (typeof totalRows != "undefined") {

          var count=0;                

          for (count=0;count<totalRows;count++) {

            thisForm.chkFolderDocIds[count].checked=checkValue;

          }

        }else{

            thisForm.chkFolderDocIds.checked=checkValue;

        }

      }

    }
    
    //Called When Search / Folder Button is Clicked
    
    function folderSearchFolders(me){
    
        document.forms[0].target="_self";
    
        document.forms[0].action = "toggleFolderSearchAction.do";
    
        document.forms[0].submit();
    
    }
    
    
    
  
  </script>
  
  </head>
  
  
  
  
  <body>
    <html:form name="folderDocListForm" action="/personalListAction" type="rts.web.actionforms.filesystem.FolderDocListForm" >
    
      <html:hidden property="hdnActionType" value="" />
      <!-- hidden values to be passed for following operation
          1. UP_FOLDER
          2. REFRESH
      -->
      <html:hidden property="currentFolderId" value="<%=currentFolderId %>" />
  
      <!-- hidden values to be passed to the copy or move operation -->
      <html:hidden property="hdnTargetFolderPath" value="<%=currentFolderPath%>" />
      <html:hidden property="hdnPropertyType" value="" />
  
      <!-- hidden values to be passed to the folder copyTo moveTo and paste operation -->
      <html:hidden property="hdnOverWrite" value="true" />
      
      <!-- hidden values to be passed to encrypt operation -->
      <html:hidden property="hdnEncryptionPassword" value="" />
      <html:hidden property="txtPageNo" value="<%=folderDocInfo.getPageNumber()%>" /> 

      <table id="4outermostBorder" class="bdrColor_336633" width="1000px" height="570px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" height="155px">
          
            <!--Header Starts-->
            <%if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ){%>
              <jsp:include page="/header.jsp" />
            <%}else{%>
              <jsp:include page="/headerForTreeNav.jsp" />
            <%}%>
            <!--Header Ends-->
          
            <table id="toolBar" width="1000px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px" class="bgColor_e8e8e8 bdrBottomColor_336633 bdrTopColor_FFFFFF">
            
              <tr>
              
                <td height="30px" colspan="2">
                
                  <logic:equal name="folderDocInfo" property="listingType" value="<%=FolderDocInfo.SIMPLE_LISTING%>" >
                  
                    <a onclick="drawerNew();" class="imgNewDrawer" onmouseout="this.className='imgNewDrawer'" onmouseover="this.className='imgNewDrawer'" title="<bean:message key="tooltips.DrawerNew" /> " style="margin-left:2px"></a>
                    
                    <%if( !((Boolean)request.getAttribute("isRootFolder")).booleanValue() ){ %>

                      <a  onclick="folderDocRename();" class="imgRename" onmouseout="this.className='imgRename'" onmouseover="this.className='imgRename'" title="<bean:message key="tooltips.FolderRename" />" style="margin-left:2px"></a>

                      <a  onclick="docUpload();" class="imgUpload" onmouseout="this.className='imgUpload'" onmouseover="this.className='imgUpload'" title="<bean:message key="tooltips.DocumentUpload" />" style="margin-left:2px"></a>
            
                      <a  onclick="docDownload();" class="imgDownload" onmouseout="this.className='imgDownload'" onmouseover="this.className='imgDownload'" title="<bean:message key="tooltips.DocumentDownload" />" style="margin-left:2px"></a>
                    
                      <a onclick="generateLinks();" class="imgGenerateLink" onmouseout="this.className='imgGenerateLink'" onmouseover="this.className='imgGenerateLink'" title="<bean:message key="tooltips.DocumentGenerateLink" />" style="margin-left:2px"></a>

                      <a  onclick="folderDocCut()" class="imgCut" onmouseout="this.className='imgCut'" onmouseover="this.className='imgCut'" title="<bean:message key="tooltips.FolderDocCut" />" style="margin-left:2px"></a>
    
                      <a  onclick="folderDocCopy()" class="imgCopy" onmouseout="this.className='imgCopy'" onmouseover="this.className='imgCopy'" title="<bean:message key="tooltips.FolderDocCopy" />" style="margin-left:2px"></a>

                      <a  onclick="folderDocPaste()" class="imgPaste" onmouseout="this.className='imgPaste'" onmouseover="this.className='imgPaste'" title="<bean:message key="tooltips.FolderDocPaste" />" style="margin-left:2px"></a>
            
                      <a  onclick="folderDelete();" class="imgDeleteFolder" onmouseout="this.className='imgDeleteFolder'" onmouseover="this.className='imgDeleteFolder'" title="<bean:message key="tooltips.FolderDocDelete" />" style="margin-left:2px"></a>
            
                      <a  onclick="b4CopyMoveFolderDocTo('COPY')" class="imgCopyTo" onmouseout="this.className='imgCopyTo'" onmouseover="this.className='imgCopyTo'" title="<bean:message key="tooltips.FolderDocCopyTo" />" style="margin-left:2px"></a>
            
                      <a  onclick="b4CopyMoveFolderDocTo('MOVE')" class="imgMoveTo" onmouseout="this.className='imgMoveTo'" onmouseover="this.className='imgMoveTo'" title="<bean:message key="tooltips.FolderDocMoveTo" />" style="margin-left:2px"></a>

                      <a onclick="linkPopUp();" class="imgShareDoc" onmouseout="this.className='imgShareDoc'" onmouseover="this.className='imgShareDoc'" title="<bean:message key="tooltips.ShareDocument" />" style="margin-left:2px"></a>
          
                      <a  onclick="docMailAction();" class="imgMailTo" onmouseout="this.className='imgMailTo'" onmouseover="this.className='imgMailTo'" title="<bean:message key="tooltips.MailTo" />" style="margin-left:2px"></a>
            
                      <a  onclick="docFaxAction();" class="imgFaxTo" onmouseout="this.className='imgFaxTo'" onmouseover="this.className='imgFaxTo'" title="<bean:message key="tooltips.FaxTo" />" style="margin-left:2px"></a>          
                    
                    <% }else{ %>

                      <div class="imgRenameDisable" style="margin-left:2px"></div>  
            
                      <div class="imgUploadDisable" style="margin-left:2px"></div>  
            
                      <div class="imgDownloadDisable" style="margin-left:2px"></div>  
                    
                      <div class="imgGenerateLinkDisable" style="margin-left:2px"></div>  
                      
                      <div class="imgCutDisable" style="margin-left:2px"></div>  

                      <div class="imgCopyDisable" style="margin-left:2px"></div>  

                      <div class="imgPasteDisable" style="margin-left:2px"></div>  

                      <div class="imgDeleteFolderDisable" style="margin-left:2px"></div>  

                      <div class="imgCopyToDisable" style="margin-left:2px"></div>  

                      <div class="imgMoveToDisable" style="margin-left:2px"></div>  

                      <div class="imgShareDocDisable" style="margin-left:2px"></div>

                      <div class="imgMailToDiasble" style="margin-left:2px"></div>  

                      <div class="imgFaxToDiasble" style="margin-left:2px"></div>  
                    
                    <%}%>
                      
                  </logic:equal>
                  
                  <logic:equal name="folderDocInfo" property="listingType" value="<%=FolderDocInfo.SEARCH_LISTING%>" >

                    <div class="imgNewDrawerDisable" style="margin-left:2px"></div>  

                    <a  onclick="folderDocRename();" class="imgRename" onmouseout="this.className='imgRename'" onmouseover="this.className='imgRename'" title="<bean:message key="tooltips.FolderRename" />" style="margin-left:2px" ></a>
          
                    <div class="imgUploadDisable" style="margin-left:2px"></div>  
          
                    <a  onclick="docDownload();" class="imgDownload" onmouseout="this.className='imgDownload'" onmouseover="this.className='imgDownload'" title="<bean:message key="tooltips.DocumentDownload" />" style="margin-left:2px" ></a>
          
                    <a onclick="generateLinks();" class="imgGenerateLink" onmouseout="this.className='imgGenerateLink'" onmouseover="this.className='imgGenerateLink'" title="<bean:message key="tooltips.DocumentGenerateLink" />" style="margin-left:2px"></a>
  
                    <a  onclick="folderDocCut()" class="imgCut" onmouseout="this.className='imgCut'" onmouseover="this.className='imgCut'" title="<bean:message key="tooltips.FolderDocCut" />" style="margin-left:2px"></a>
  
                    <a  onclick="folderDocCopy()" class="imgCopy" onmouseout="this.className='imgCopy'" onmouseover="this.className='imgCopy'" title="<bean:message key="tooltips.FolderDocCopy" />" style="margin-left:2px"></a>
          
                    <div class="imgPasteDisable" style="margin-left:2px"></div>  
          
                    <a  onclick="folderDelete();" class="imgDeleteFolder" onmouseout="this.className='imgDeleteFolder'" onmouseover="this.className='imgDeleteFolder'" title="<bean:message key="tooltips.FolderDocDelete" />" style="margin-left:2px"></a>
          
                    <a  onclick="b4CopyMoveFolderDocTo('COPY')" class="imgCopyTo" onmouseout="this.className='imgCopyTo'" onmouseover="this.className='imgCopyTo'" title="<bean:message key="tooltips.FolderDocCopyTo" />" style="margin-left:2px"></a>
          
                    <a  onclick="b4CopyMoveFolderDocTo('MOVE')" class="imgMoveTo" onmouseout="this.className='imgMoveTo'" onmouseover="this.className='imgMoveTo'" title="<bean:message key="tooltips.FolderDocMoveTo" />" style="margin-left:2px"></a>

                    <a onclick="linkPopUp();" class="imgShareDoc" onmouseout="this.className='imgShareDoc'" onmouseover="this.className='imgShareDoc'" title="<bean:message key="tooltips.ShareDocument" />" style="margin-left:2px"></a>
          
                    <a  onclick="docMailAction();" class="imgMailTo" onmouseout="this.className='imgMailTo'" onmouseover="this.className='imgMailTo'" title="<bean:message key="tooltips.MailTo" />" style="margin-left:2px"></a>
          
                    <a  onclick="docFaxAction();" class="imgFaxTo" onmouseout="this.className='imgFaxTo'" onmouseover="this.className='imgFaxTo'" title="<bean:message key="tooltips.FaxTo" />" style="margin-left:2px"></a>          
                                    
                  </logic:equal>
                  
                </td>
                
              </tr>
              
            </table>
  
            <table id="addressBar" width="1000px" border="0" align="center" cellpadding="0" cellspacing="0" class="bdrBottomColor_336633">
              <tr>
                <td width="185px" height="30px" class="bgColor_e8e8e8 bdrTopColor_FFFFFF bdrRightColor_828282">
                
                  <% if(!folderDocInfo.isBackButtonDisabled()){ %>
            
                    <a onclick="folderBack();" class="imgBack" onmouseout="this.className='imgBack'" onmouseover="this.className='imgBack'" style="margin-left:2px;" title="<bean:message key="tooltips.Back" />" ></a>
            
                  <%}else{%>
            
                    <div class="imgBackDisable" style="margin-left:2px;" ></div>		
            
                  <%}%>
            
                  <% if(!folderDocInfo.isForwardButtonDisabled()){ %>
            
                    <a onclick="folderForward();" class="imgForward" onmouseout="this.className='imgForward'" onmouseover="this.className='imgForward'" style="margin-left:2px;" title="<bean:message key="tooltips.Forward" />" ></a>
            
                  <%}else{%>
            
                    <div class="imgForwardDisable" style="margin-left:2px;" ></div>  
            
                  <%}%>
            
                  <% if(!upButtonDisabled && !isRootFolder ){ %>
            
                    <a onclick="folderUp();" class="imgGoUp" onmouseout="this.className='imgGoUp'" onmouseover="this.className='imgGoUp'" style="margin-left:2px;" title="<bean:message key="tooltips.Up" />" ></a>
            
                  <%}else{%>
            
                    <div  class="imgGoUpDisable" style="margin-left:2px;" ></div>
            
                  <%}%>
                
                  <a class="imgSearch" onmouseout="this.className='imgSearch'" onmouseover="this.className='imgSearch'" title="<bean:message key="tooltips.FolderDocSearch" />" 
                     style="margin-left:2px;" onclick="folderSearchFolders(this);"></a>
                  
                  <a onclick="folderRefresh();" class="imgRefresh" onmouseout="this.className='imgRefresh'" onmouseover="this.className='imgRefresh'" style="margin-left:2px;" title="<bean:message key="tooltips.Refresh" />" ></a>
                  
                </td>
                <td width="30px" class="bdrLeftColor_FFFFFF bgColor_e8e8e8 bdrTopColor_FFFFFF" align="left">
                  <% if( !isFirstSet ){%>
                  <a href="drawersNextAction.do?oper=prev" class="imgMoreLeftDrawers" onmouseout="this.className='imgMoreLeftDrawers'" onmouseover="this.className='imgMoreLeftDrawers'" title="More Drawers"></a>
                  <% } %>
                </td>
                <td width="750px" class="bgColor_e8e8e8 bdrTopColor_FFFFFF">
                  <%
                      String folderId;
                      String folderName;
                      ArrayList listOfParents = folderDocInfo.getListOfParents();
                      ArrayList listOfParentsId = folderDocInfo.getListOfParentsId();
                      int listOfParentsLength = listOfParents.size();
                      folderId = (String)listOfParentsId.get(0);
                      folderName = (String)listOfParents.get(0);
                  %>
                    <%if(folderDocInfo.getHierarchySetNo() == 1){%>
                      <a class="drawerListLink" href="folderDocFolderClickAction.do?currentFolderId=<%=folderId%>"><%=folderName%></a><span style="margin-right:5px; margin-left:5px">></span>
                    <%}else{%>
                      <a class="drawerListLink" href="folderDocFolderClickAction.do?currentFolderId=<%=folderId%>" style="margin-right:5px" ><%=folderName%></a>
                    <%}%>
                  <%
                      if(listOfParentsLength > 1){
                          folderId = (String)listOfParentsId.get(1);
                          folderName = (String)listOfParents.get(1);
                  %>
                        <%if(folderDocInfo.getHierarchySetNo() == 1){%>
                          <a class="drawerListLink" href="folderDocFolderClickAction.do?currentFolderId=<%=folderId%>" >
                          <%=folderName%></a>
                        <%}else{%><span style="margin-right:5px">></span>
                          <a class="drawerListLink" href="folderDocFolderClickAction.do?currentFolderId=<%=folderId%>" >
                          <%=folderName%></a>                        
                        <%}%>
                      <%}%>
        
                  <%
                      for(int index = 2; index < listOfParentsLength; index++){
                          folderId = (String)listOfParentsId.get(index);
                          folderName = (String)listOfParents.get(index);
                  %>
                      <span style="margin-left:5px; margin-right:5px">></span>
					  <a class="drawerListLink" href="folderDocFolderClickAction.do?currentFolderId=<%=folderId%>" >
                      <%=folderName%></a>
                  <%}%>
                  
                </td>
                <td width="30px" class="bgColor_e8e8e8 bdrTopColor_FFFFFF" align="right" >
                <% if(!isLastSet){ %>
                  <a href="drawersNextAction.do?oper=next" class="imgMoreRightDrawers" onmouseout="this.className='imgMoreRightDrawers'" onmouseover="this.className='imgMoreRightDrawers'" title="More Drawers"></a>
                <% } %>
                </td>
              </tr>
            </table>
            
            <%if(!folderDocInfo.isTreeVisible()){%>
            
              <bean:define id="advanceSearchForm" name="advanceSearchForm" type="rts.web.actionforms.filesystem.AdvanceSearchForm" />
              
              <script>
                function showHideSearchLayer(){
                  MM_findObj('searchShowHide').style.display=='none'?MM_showHideLayers('searchShowHide','','show'):MM_showHideLayers('searchShowHide','','hide');
                }
              </script>
              
              <table id="searchShowHide" style="display:'';" width="1000px" border="0" align="center" cellpadding="0" cellspacing="0" class="bdrBottomColor_336633">
                <tr>
                  <td height="70px" class="bgColor_e8e8e8 bdrTopColor_FFFFFF" align="center">
                  <fieldset style="width:980px;">
                    <legend align="left" class="tabText"><bean:message key="lbl.Search" /></legend>
                    <table width="934"  border="0" align="center" cellpadding="1" cellspacing="2">
                      <tr>
                        <td width="16%" align="right"><bean:message key="lbl.Name" /></td>
                        <td width="22%">
                          <html:text name="advanceSearchForm" property="txtFolderOrDocName" styleClass="bdrColor_336633" style="width:200px; height:18px" />
                        </td>
                        <td width="14%" align="right"><bean:message key="lbl.Description" /></td>
                        <td width="38%">
                          <html:text name="advanceSearchForm" property="txtDocDescription" styleClass="bdrColor_336633" style="width:350px; height:18px" />
                        </td>
                        <td width="10%" align="left">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="16%" align="right"><bean:message key="lbl.ContainingText" />:</td>
                        <td width="22%">
                          <html:text name="advanceSearchForm" property="txtContainingText" styleClass="bdrColor_336633 componentStyle" style="width:200px" onkeypress="return alphaNumericOnly(event);" />
                        </td>
                        <td width="14%" align="right"><bean:message key="lbl.LookIn" /></td>
                        <td>
                          <html:text name="advanceSearchForm" property="txtLookinFolderPath" styleClass="bdrColor_336633" style="width:325px; height:18px" readonly="true" />
                          <html:button onclick="lookuponclick();" property="btnLookUp" style="width:20px; height:18px; cursor:pointer; cursor:hand" styleClass="buttons" value="..." />
                        </td>
                        <td>
                          <a onclick="folderDocSearch()" class="imgGo"  title="<bean:message key="tooltips.Go" />"></a>
                        </td>
                      </tr>                        
                    </table>
                    </fieldset>
                  </td>
                </tr>
              </table>
            
            <%}%>
            <!-- Drawer Listing iframe-->
            <table id="drawerList" width="1000px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px" class="bgColor_e8e8e8 bdrBottomColor_336633">
              <tr>
                <td valign="middle">
                  <iframe name="ifDrawerList" src="drawerListAction.do?oper=list"  height="20px" width="100%" frameborder="0" scrolling="no"></iframe>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        
        <!-- Document Listing -->
        <tr>
          <td align="center" valign="top" class="bgColor_e8e8e8">
            <table width="100%"  border="0" cellspacing="1" cellpadding="1" class="bgColor_FFFFFF">
              <tr>
                <th width="2%" align="center" class="th669966">
                  <input type="checkbox" onclick="checkAll()" name="chkAll" title="SelectAll">
                </th>
                <th width="30%" align="center" class="th669966"><bean:message key="tbl.head.Name" /></th>
                <th width="11%" align="center" class="th669966"><bean:message key="tbl.head.Size" /></th>
                <th width="24%" align="center" class="th669966"><bean:message key="tbl.head.Type" /></th>
                <th width="23%" align="center" class="th669966"><bean:message key="tbl.head.ModifiedDate" /></th>
                <th width="10%" align="center" class="th669966"><bean:message key="tbl.head.Document" /></th>
              </tr>             
              
              <%if(folderDocLists.size() > 0 ){%>
              
                <logic:iterate id="folderDocList" name="folderDocLists" type="rts.web.beans.filesystem.FolderDocList" >
                
                  <%if (firstTableRow == true){ firstTableRow = false; %>
                    <tr class="trF7F7F7">
                  <%}else{ firstTableRow = true; %>
                    <tr class="trD7EFD7">
                  <%}%>
                  
                    <td valign="top">
                      <div align="center">
                        <html:multibox property="chkFolderDocIds" onclick="unCheckChkAll(this);" >
                          <bean:write name="folderDocList"  property="id" />
                        </html:multibox>
                      </div>
                      <bean:define id="className" property="className" name="folderDocList" />
                      <html:hidden property="hdnClassName" value="<%=className%>" />
                    </td>
                    
                    <td valign="top">
                      <html:hidden property="name1" value="<%=folderDocList.getName1()%>" />
                      <a class="imgDocIcon" target=newwin href="b4DocDownloadAction.do?documentId=<bean:write name="folderDocList" property="id" />" title="<bean:write name="folderDocList" property="name" />" />
                      <a target=newwin href="b4DocDownloadAction.do?documentId=<bean:write name="folderDocList" property="id" />" class="recordLink" title="<bean:write name="folderDocList" property="name" />">
                      <bean:write name="folderDocList" property="name1" />
                      <logic:equal name="folderDocList" property="className" value="FAMILY" >
                        (v)
                      </logic:equal>
                      </a>
                    </td>
                    <td valign="top">
                      <bean:write name="folderDocList" property="size" />
                    </td>
                    <td valign="top">
                      <bean:write name="folderDocList" property="type" />
                    </td>
                    <td valign="top">
                      <bean:write name="folderDocList" property="modifiedDate" />
                    </td>
                    <td valign="top">
                      <a target=newwin href="b4DocDownloadAction.do?documentId=<bean:write name="folderDocList" property="id" />" class="dataLink" ></a>
                      <a target=newwin href="b4DocDownloadAction.do?documentId=<bean:write name="folderDocList" property="id" />">dnld link</a>
                    </td>
                    </tr>
                  </logic:iterate>
                <%}%>
            </table>
            <%if(folderDocLists.size() == 0){%>
              <div style="position:relative; top:175px; text-align:center;" class="tabText">
                <bean:message key="info.no_item_found.no_item_found" />
              </div> 
            <%}%>  
          </td>
        </tr>
      </table>
     
      <table class="bdrColor_336633 bgColor_e8e8e8" align="center" width="1000px" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0" id="statusBar">
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
                <td width="200px" align="right">
                  <script>navBar.render();</script>
                </td>
              </tr>
            </table>
            <table align="center" width="1000px" class="bdrColor_336633 bgColor_e8e8e8" style="margin-top:3px;" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="10px" align="center" class="bgColor_669966 textCopyRight"><bean:message key="lbl.CopyRight" /></td>
              </tr>
            </table>
            </html:form>
  </body>
</html:html>