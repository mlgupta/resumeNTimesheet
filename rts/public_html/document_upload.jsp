<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="FolderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />

<html:html>
<head>
<title><bean:message key="title.UploadDocument" /></title>
<link href="themes/main.css" rel="stylesheet" type="text/css">
<script src="general.js"></script>
<script src="progressbar.js"></script>
<script name="javascript">
// ProgressBar Definition
  varPGBar = new ProgessBar('varPGBar',120,10);
  varPGBar.pgPatelWidth=5;
  varPGBar.seperation=1;
  varPGBar.patelClassName='bgClrLvl_2';  
</script>

<script>
  var intFileUploadIndex=0;
  var blnNotFirstTime= false;
  var fileIdPrefix='fleFile';
  var textAreaIdPrefix='txaFileDesc';

  function init(){
    addFiles();
    blnNotFirstTime=true;
  }

  function upload(){
    var thisForm = document.forms[0];
    
    if (thisForm.lstFileUpload.options.length>0){
    
      var fileId=fileIdPrefix+'[' + (intFileUploadIndex - 1) +']';
      var textAreaId=textAreaIdPrefix+'[' + (intFileUploadIndex - 1) +']';

  
      var objFileId=MM_findObj(fileId);
      var objTextAreaId=MM_findObj(textAreaId) ;
      
      objFileId.disabled=true;
      objTextAreaId.disabled=true;

      thisForm.submit();
      varPGBar.start();
      MM_findObj('waitmsg').style.display='';
           
    }else{
      alert('<bean:message key="msg.AddFiles" />');
    }
    
  }
  
  function addFiles(){

    var fileId=fileIdPrefix+'[' + intFileUploadIndex +']';
    var textAreaId=textAreaIdPrefix+'[' + intFileUploadIndex +']';
    
    var objLyrFileUpload=MM_findObj('lyrFileUpload');
    var objLyrFileDesc=MM_findObj('lyrFileDesc');

    if (blnNotFirstTime){
      var index2Hide= intFileUploadIndex-1;
      var objListFileUpload=MM_findObj('lstFileUpload');
           
      
      //Code For File Control
      var fileId2Hide=fileIdPrefix+'[' + index2Hide +']';
      var objFileId2Hide=MM_findObj(fileId2Hide) ;
      var fileName=objFileId2Hide.value;
      //alert(fileName);
      if (fileName.length==0){
        alert('<bean:message key="msg.NothingToAdd" />');
        return false;
      }

      //Code For Text Area Control
      var textAreaId2Hide=textAreaIdPrefix+'[' + index2Hide +']';
      var objTextAreaId2Hide=MM_findObj(textAreaId2Hide) ;
      var fileDesc=objTextAreaId2Hide.value;

      
      
      for(var openerIndex=0;openerIndex<objListFileUpload.options.length; openerIndex++){
          if(objListFileUpload.options[openerIndex].id==fileName){                  
            alert('<bean:message key="msg.FileAlreadyOnList" />');
            return false;
          } 
      }

      //To set display none for File control
      objFileId2Hide.style.display='none';
      
      //To set display none for Text Area control
      objTextAreaId2Hide.style.display='none';
    
      var objOption = document.createElement('OPTION'); 
      objOption.value=fileId2Hide; 
      objOption.id=fileName;
      objOption.title=fileDesc;
      objOption.name=textAreaId2Hide;
      
      
      //To Get File Name Alone  
      while(fileName!=(fileName=fileName.replace("\\","/")));
      var strArray=fileName.split('/');
      fileName=strArray[strArray.length-1];

      objOption.text=fileName;

      var newIndex=objListFileUpload.options.length ;
      objListFileUpload.options[newIndex]=objOption;
      
    }

  
    //Following Piece of Code Generates File Control
    var objFileUpload= document.createElement ('INPUT'); 
    objFileUpload.id=fileId;
    objFileUpload.name=fileId;
    objFileUpload.type='file';
    objFileUpload.size='46'; 
    objFileUpload.className='bdrColor_336633 componentStyle';
    objFileUpload.onkeypress=fleOnKeypress;
    objLyrFileUpload.appendChild(objFileUpload);

    //Following Piece of Code Generates Text Area Control
    var objTextArea= document.createElement ('TEXTAREA'); 
    objTextArea.id=textAreaId;
    objTextArea.name=textAreaId;
    objTextArea.cols='57'; 
    objTextArea.rows='4'; 
    objTextArea.className='bdrColor_336633 componentStyle';
    objLyrFileDesc.appendChild(objTextArea);

    intFileUploadIndex++; 
  
  }
  
  function removeFiles(){
    var thisForm = document.forms[0];
    var index=0;
    var length=thisForm.lstFileUpload.length;
    var fileId;
    var textAreaId;
    var objLyrFileUpload=MM_findObj('lyrFileUpload');
    var objLyrFileDesc=MM_findObj('lyrFileDesc');

    
    var objFileUpload;
    var objFileDesc;
    while(index <length){     
      if(thisForm.lstFileUpload[index].selected){

        //To Remove File Control  
        fileId=thisForm.lstFileUpload[index].value;
        objFileUpload=MM_findObj(fileId);
        objLyrFileUpload.removeChild(objFileUpload) ;
        
        //To Remove Text Area Control
        textAreaId=thisForm.lstFileUpload[index].name
        objFileDesc=MM_findObj(textAreaId);
        objLyrFileDesc.removeChild(objFileDesc) ;
        
        //To Remove list item 
        thisForm.lstFileUpload.remove(index);
        
        length=thisForm.lstFileUpload.length;   
        
      }else{
        index++;
      }
    }
  }

  function fleOnKeypress(){
    return false;
  }

</script>

</head>
<body topmargin="10px"; leftmargin="5px" >

<html:form action="/newDocumentUploadAction" enctype="multipart/form-data" >
<html:hidden property="txtPath" value="<%=FolderDocInfo.getCurrentFolderPath()%>" />

<table id="tabContainer" align="center" width="100%"  border="0" cellspacing="0" cellpadding="0" style="margin-top:15px;">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
  <td >
    <table id="tabParent" align="center" width="490px"  border="0" cellpadding="0" cellspacing="0">
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
<table id="bdrColor_336633" align="center" width="490px"  border="0" cellpadding="0" cellspacing="0" class="bgColor_e8e8e8 bdrColor_336633"  >
  <tr>
    <td >
      <table width="95%" border="0"  cellpadding="0" cellspacing="2" align="center">
          <tr><td height="20px" colspan="2" >&nbsp;</td></tr>
          <tr>
            <td width="200px" height="20px" valign="top" align="right">
              <bean:message key="lbl.UploadingLocation" />
            </td>   
            <td height="20px" valign="top" align="left">&nbsp;
              <bean:write name="FolderDocInfo" property="currentFolderPath" />
            </td>
          </tr>
          <tr><td colspan="2" height="10px"></td></tr>
          <tr>
            <td height="20px" valign="middle" align="right">
                <bean:message key="txt.LocateFile" />
             </td>   
             <td>
              <div id="lyrFileUpload" ></div>
             </td>
          </tr>
          <tr><td colspan="2" height="2px"></td></tr>
          <tr>
            <td height="20px" valign="top" align="right">
                <bean:message key="lbl.Description" />
             </td>   
             <td>
             <div id="lyrFileDesc" ></div>               
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" height="30px" >
              <html:button property="btnAdd" styleClass="buttons" style="width:70px" onclick="return addFiles();" ><bean:message key="btn.Add" /></html:button>
              <html:button property="btnRemove" styleClass="buttons" style="width:70px"  onclick="return removeFiles();" ><bean:message key="btn.Remove" /></html:button>              
            </td>
          </tr>
          
          <tr>
            <td valign="top" align="right"><bean:message key="lbl.SelectedFiles" /> </td>
            <td>
              <select id="lstFileUpload" name="lstFileUpload" size="5" multiple style="width:365px;" class="bdrColor_336633 componentStyle" ></select>                     
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" height="30px" valign="middle">
                <bean:message key="msg.Note"/>
                <html:button property="btnUpload" onclick="return upload();" styleClass="buttons" style="width:70px" ><bean:message key="btn.Upload" /></html:button>
                <html:button property="btnCancel" onclick="window.close();" styleClass="buttons" style="width:70px"><bean:message key="btn.Cancel" /></html:button>                                
            </td>
          </tr>
        </table>
      </td>
    </tr>
</table>
</td>
</tr>
</table>
<table cellpadding="0" cellspacing="0">
<tr><td height="2px"></td></tr>
</table>
<table align="center" class="bgColor_e8e8e8 bdrColor_336633" width="490px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
  <tr>
    <td height="20px" width="30px"><div class="imgStatusMsg"></div></td>
     <td width="275px">
       <div id="waitmsg" style="display:none"><bean:message key="msg.processing" /></div>
     </td>
     <td  width="125px" align="right">
       <div style="margin-right:3px" >
          <script>
            varPGBar.renderPgBar();
          </script>
       </div>  
     </td>
  </tr>
</table>
     <!-- statusBar table ends above-->
</html:form>
<script>init();</script>
</body>

</html:html>
