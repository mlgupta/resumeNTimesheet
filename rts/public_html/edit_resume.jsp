<%@ page import="rts.web.beans.user.UserPreferences" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="FolderDocInfo" name="FolderDocInfo" type="rts.web.beans.filesystem.FolderDocInfo" />
<bean:define id="userPreferences" name="UserPreferences" type="rts.web.beans.user.UserPreferences" />
<bean:define id="communicationSkill" name="communicationSkill" type="java.lang.String" />

<%

  long resumeId = ((Long)request.getAttribute("resumeId")).longValue();

%>
<html:html>
  <head>
    <title><bean:message key="title.edit.resume" /></title>
    <link href="themes/main.css" rel="stylesheet" type="text/css">
    <html:javascript formName="docEditForm" dynamicJavascript="true" staticJavascript="false"/>
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script language="JavaScript" type="text/JavaScript">
    <!--
    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }
    
    //-->
    </script>
    <script src="general.js"></script>

    <script name="javascript">
      
      function editResume(thisForm){
        if(validateDocEditForm(thisForm)){
          for(index = 0 ; index < thisForm.lstAttachment.length ;index++){     
            thisForm.lstAttachment[index].selected = true;    
          }  
          thisForm.action="docEditAction.do";
          thisForm.submit();
        }
      }
      
      function cancelEdit(){
        document.forms[0].action = "folderDocListAction.do";
        document.forms[0].submit();
      }

      function cancelEditForTree(){
        document.forms[0].action = "folderDocTreeListAction.do";
        document.forms[0].submit();
      }
      
      function parse(thisForm){
       var name="";
       var address = "";
       var phone1 = "";
       var phone2 = "";
       var email = "";
       var email1 = "";
       var email2 = "";
       var email3 = "";
       var nameIndex=0;
       var phone1Index=-1;
       var phone2Index=-1;
       var phone3Index=-1;
       var phone4Index=-1;
       var phone5Index=-1;
       var mailIndex=-1;
       var mail1Index=-1;
       var mail2Index=-1;
       var mail3Index=-1;
       var text=thisForm.txaGrab.value ;
       thisForm.txtName.value="";
       thisForm.txtEmail.value="";
       thisForm.txtPhone1.value="";
       thisForm.txtPhone2.value= "";
       thisForm.txaAddress.value="";

       text=trimString(text);
       var lineText = text.split("\n");
       for(var index=0 ; index < lineText.length ; index++){
         if((lineText[index]).indexOf("@")!=-1 && ((lineText[index]).indexOf(".com")!=-1 || lineText[index].indexOf(".edu")!=-1 || lineText[index].indexOf(".org")!=-1 || lineText[index].indexOf(".co.")!=-1)){
           if(mailIndex==-1){
              mailIndex=index;
            }else if(mail1Index==-1){
              mail1Index=index;
            }else if(mail2Index==-1){
              mail2Index=index;
            }else if(mail3Index==-1){
              mail3Index=index;
            }
         }else if(lineText[index].length>=10){
           var phCounter=0;

           for(var phIndex = 0 ; phIndex < lineText[index].length ; phIndex++){
            if(!(lineText[index].charCodeAt(phIndex) == 42 || lineText[index].charCodeAt(phIndex) == 44 || 
                   (lineText[index].charCodeAt(phIndex)  > 45 && lineText[index].charCodeAt(phIndex) <48) ||
                   (lineText[index].charCodeAt(phIndex)  > 57 && lineText[index].charCodeAt(phIndex) <65) ||
                   (lineText[index].charCodeAt(phIndex)  > 64 && lineText[index].charCodeAt(phIndex) <91) || 
                   (lineText[index].charCodeAt(phIndex)  > 90 && lineText[index].charCodeAt(phIndex) <97) ||
                   (lineText[index].charCodeAt(phIndex)  > 96 && lineText[index].charCodeAt(phIndex) <123) || 
                   (lineText[index].charCodeAt(phIndex)  > 122 && lineText[index].charCodeAt(phIndex) <=127))){

               if(lineText[index].charCodeAt(phIndex) >=48 && lineText[index].charCodeAt(phIndex) <=57){
                   phCounter++;
                }
             }else{
               phCounter=0;
               break;
             }
           } // end for

           if(phCounter==10){
             if(phone1Index == -1){
                phone1Index=index;
             }else if(phone2Index == -1){
                phone2Index=index;
             }else if(phone3Index == -1){
                phone3Index=index;
             }else if(phone4Index == -1){
                phone4Index=index;
             }else if(phone5Index == -1){
                phone5Index=index;
             }
           } // end if
         } // end else if

       } // end for
       
       for(var index=0 ; index < lineText.length ; index++){

         if(index==nameIndex){
           thisForm.txtName.value=trimString(lineText[index]);
         }else if(index==mailIndex){
           thisForm.txtEmail.value=trimString(lineText[index]);
         }else if(index==mail1Index){
           thisForm.txtEmail.value=thisForm.txtEmail.value+", "+trimString(lineText[index]);
         }else if(index==mail2Index){
           thisForm.txtEmail.value=thisForm.txtEmail.value+", "+trimString(lineText[index]);
         }else if(index==mail3Index){
           thisForm.txtEmail.value=thisForm.txtEmail.value+", "+trimString(lineText[index]);
         }else if(index==phone1Index){
           thisForm.txtPhone1.value=trimString(lineText[index]);
         }else if(index==phone2Index){
           thisForm.txtPhone2.value=trimString(lineText[index]);
         }else if(index==phone3Index){
           thisForm.txtPhone2.value=thisForm.txtPhone2.value+", "+trimString(lineText[index]);
         }else if(index==phone4Index){
           thisForm.txtPhone2.value=thisForm.txtPhone2.value+", "+trimString(lineText[index]);
         }else if(index==phone5Index){
           thisForm.txtPhone2.value=thisForm.txtPhone2.value+", "+trimString(lineText[index]);
         }else{
           if(address.length==0){
              address=lineText[index];
           }else{
              address=address+"\n"+lineText[index];
           }
           thisForm.txaAddress.value=trimString(address);
         }
       } // end for
     } // end parse
     
    function removeFromList(thisForm){
      var index=0;
      var length=thisForm.lstAttachment.length;
      while(index <length){     
        if(thisForm.lstAttachment[index].selected){
          if(thisForm.lstAttachment[index].value!="WORLD [G]"){
           thisForm.lstAttachment.remove(index);
           length=thisForm.lstAttachment.length;   
          }else{
            alert("<bean:message key="msg.world.delete"/>");
            index++;
          }
        }else{
          index++;
        }
       }
    }

      function attachonclick(){
        openWindow('folderDocSelectB4Action.do?heading=<bean:message key="lbl.ShareWith" />&foldersOnly=true&openerControl=lstAttachment&recreate=true',"ShareWith",495,390,0,0,true);
      }
     
    </script>
    
  </head>
  
  <body>
    <html:form action="/docEditAction" enctype="multipart/form-data" focus="txaGrab" name="docEditForm" type="rts.web.actionforms.filesystem.DocEditForm"  >
    <html:hidden property="txtPath" value="<%=FolderDocInfo.getCurrentFolderPath()%>" />
    <html:hidden property="hdnTargetFolderPath" value="<%=FolderDocInfo.getCurrentFolderPath()%>" />
    
      <table id="4outermostBorder" class="bdrColor_336633" width="1000px" height="580px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" height="94px">
            <!--Header Starts-->
            <%if( userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION ){%>
              <jsp:include page="/header.jsp" />
            <%}else{%>
              <jsp:include page="/headerForTreeNav.jsp" />
            <%}%>
            <!--Header Ends-->
          </td>
        </tr>
        <tr>
          <td height="px" align="center" valign="top" class="bgColor_e8e8e8">
            <table width="770px" border="0" cellpadding="1" cellspacing="1" class="bdrColor_336633" style="margin-top:20px; margin-bottom:20px">
              <tr>
                <td colspan="3" align="right" valign="top">&nbsp;</td>
              </tr>
              <tr>
                <td width="115" align="right" valign="top">Contact Grabber : </td>
                <td width="646" colspan="2" valign="top">
                  <html:textarea property="txaGrab" styleClass="bdrColor_336633  componentStyle" style="width:568px; height:100px" title="Enter your block of text in the here and click on 'grab' button to parse the block of text into name, address, contact nos. , and email address etc..." tabindex="1">
                  </html:textarea>
                  <img align="top" style="margin-top:1px;" src="themes/images/icon_tip.gif" width="20" height="18" onClick="openWindow('grab_tip.htm','grabtip',185,430,0,0,true,'status=no');">
                </td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td colspan="2" align="right" height="30px">
                  <input name="Submit3" type="button" class="buttons" value="Grab" style="width:75px; height:18px; margin-right:78px" onclick="return parse(this.form);" tabindex="2"></td>
              </tr>
              <tr>
                <td height="30px" colspan="3">
                  <table width="100%"  border="0" cellspacing="2" cellpadding="2">
                    <tr>
                      <td width="15%" align="right"><span class="textRed">* </span><bean:message key="txt.Name" />:</td>
                      <td width="30%"><html:text name="docEditForm" property="txtName" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="3" onkeypress="return charOnly(event);" /></td>
                      <td width="18%" align="right"><span class="textRed">* </span><bean:message key="txt.CommunicationSkills" />:</td>
                      <td width="37%">
                        <html:select name="docEditForm"  property="txtCommunicationSkill" styleClass="bdrColor_336633  componentStyle" style="width:200px;height:18px;" tabindex="8" >
                          <% if(communicationSkill.equals("GOOD")) {%>
                            <option selected value="GOOD">Good</option>
                          <%}else{%>
                            <option value="GOOD">Good</option>
                          <%}%>
                          <% if(communicationSkill.equals("MEDIUM")) {%>
                            <option selected value="MEDIUM">Medium</option>
                          <%}else{%>
                            <option value="MEDIUM">Medium</option>
                          <%}%>
                          <% if(communicationSkill.equals("BAD")){%>
                            <option selected value="BAD">Bad</option>
                          <%}else{%>
                            <option value="BAD">Bad</option>
                          <%}%>
                        </html:select>
                      </td>
                    </tr>
                    <tr>
                      <td align="right" valign="top"><span class="textRed">* </span><bean:message key="txt.address" />:</td>
                      <td><html:textarea name="docEditForm" property="txaAddress" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:80px" tabindex="4" /></td>
                      <td align="right" valign="top"><bean:message key="txt.Description" />:</td>
                      <td><html:textarea name="docEditForm" property="txtFileDesc" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:80px" tabindex="9" /></td>
                    </tr>
                    <tr>
                      <td align="right"><span class="textRed">* </span><bean:message key="txt.Phone1" />:</td>
                      <td><html:text name="docEditForm" property="txtPhone1" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" onkeypress="return phoneNosOnly(event);" tabindex="5" /></td>
                      <td align="right">Avail Date:</td>
                      <td><html:text name="docEditForm" property="txtAvailDate" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="10" /></td>
                    </tr>
                    <tr>
                      <td align="right">Phone 2:</td>
                      <td><html:text name="docEditForm" property="txtPhone2" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" onkeypress="return phoneNosOnly(event);" tabindex="6" /></td>
                      <td align="right"><span class="textRed">* </span><bean:message key="lbl.BillingRate" />:</td>
                      <td><html:text name="docEditForm" property="txtBillingRate" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="11" /></td>
                    </tr>
                    <tr>
                      <td align="right"><span class="textRed">* </span><bean:message key="txt.Email" />:</td>
                      <td><html:text name="docEditForm" property="txtEmail" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="7" /></td>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
      			  <tr>
                <td height="30px" colspan="3">
          				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                    <tr valign="top">
                      <td width="15%" align="right"><bean:write name="docEditForm" property="txtCustom1Lbl" />:</td>
                      <td width="30%"><html:text name="docEditForm" property="txtCustom1Desc" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="12" /></td>
                      <td width="18%" align="right"><bean:write name="docEditForm" property="txtCustom3Lbl" />:</td>
                      <td width="37%"><html:text name="docEditForm" property="txtCustom3Desc" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="14" /></td>
                    </tr>
                    <tr valign="top">
                      <td align="right"><bean:write name="docEditForm" property="txtCustom2Lbl" />:</td>
                      <td><html:text name="docEditForm" property="txtCustom2Desc" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="13" /></td>
                      <td align="right"><bean:write name="docEditForm" property="txtCustom4Lbl" />:</td>
                      <td><html:text name="docEditForm" property="txtCustom4Desc" styleClass="bdrColor_336633  componentStyle" style="width:200px; height:18px" tabindex="15" /></td>
                    </tr>
                </table>
      				</td>
            </tr>
            <tr>
              <td align="right" height="40px"><bean:message key="txt.Resume" />:</td>
              <td colspan="2">
                <html:file name="docEditForm" property="fileOne" size="96" styleClass="bdrColor_336633"  onkeypress="return false;" tabindex="16" ><bean:message key="btn.Browse" /></html:file>
              </td>
            </tr>
            <tr>
              <td></td>
              <td colspan="2"></td>          
            </tr>
            <tr>
              <td align="right" height="40px" valign="top"><bean:message key="lbl.ShareWith" />:</td>
              <td colspan="2" valign="top">
                <table width="590px" height="50px" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="567px" rowspan="2" height="50px">
                      <html:select name="docEditForm" property="lstAttachment" size="4" multiple="true" style="width:567px;" styleClass="bdrColor_336633 componentStyle">
                        <logic:present name="docEditForm" property="lstAttachment">
                          <html:options name="docEditForm" property="lstAttachment" />
                        </logic:present>
                      </html:select>
                    </td>
                    <td width="23px" height="25px" valign="top">
                      <html:button property="btnAttach" onclick="attachonclick();" styleClass="buttons" style="width:20px;" tabindex="17" title="Attach">+</html:button>
                    </td>
                  </tr>
                  <tr>
                    <td valign="bottom" height="25px">
                      <html:button property="btnRemove" onclick="removeFromList(this.form);" styleClass="buttons" style="width:20px;" tabindex="18" title="Remove">-</html:button>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td align="right" height="30px">&nbsp;</td>
              <td colspan="2" align="right">
                <a target=newwin href="b4DocDownloadAction.do?documentId=<%=resumeId%>" tabindex="21">Open Current Resume</a>              
                <html:button property="btnSave" onclick="editResume(this.form);" styleClass="buttons" style="width:70px" tabindex="19"><bean:message key="btn.Save" /></html:button>
                <%if(userPreferences.getNavigationType() == UserPreferences.FLAT_NAVIGATION){%>
                  <html:button property="btnCancel" onclick="return cancelEdit();" styleClass="buttons" style="width:70px;margin-right:80px" tabindex="20"><bean:message key="btn.Cancel" /></html:button>
                <%}else{%>
                  <html:button property="btnCancel" onclick="return cancelEditForTree();" styleClass="buttons" style="width:70px;margin-right:80px" tabindex="20"><bean:message key="btn.Cancel" /></html:button>
                <%}%>
              </td>
            </tr>
          </table>		
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