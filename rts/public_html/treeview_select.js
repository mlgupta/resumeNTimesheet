// Definition of class Folder 
// ***************************************************************** 
var openerControl=null;
var foldersOnly=true;

function Folder(folderDescription, hreference) //constructor 
{ 
   
  //constant data 
  this.desc = folderDescription; 
  this.hreference = hreference;
  this.id = -1;
  this.navObj = 0;
  this.iconImg = 0; 
  this.nodeImg = 0;
  this.isLastNode = 0;
  this.iconSrc = ICONPATH + "ftv2folderopen.gif";
  this.iconSrcClosed = ICONPATH + "ftv2folderclosed.gif";
  this.children = new Array;
  this.nChildren = 0;
  this.level = 0;
  this.leftSideCoded = "";
  this.isLastNode=false;
  this.parentObj = null;
  this.maySelect=true;
  this.prependHTML = ""

  this.isNextLevel=false;   //Added by Sudheer -- Checks whether next level data exist locally
  this.isChildExist=false;	//Added by Sudheer -- Checks whether childern exist for the folder in database
  this.path=""              //Added by Sudheer -- Path for the Folder
  
  //dynamic data 
  this.isOpen = false
  this.isLastOpenedFolder = false
  this.isRendered = 0
  this.isOpen4List =false   //Added by Sudheer -- Checks whether the folder is open for listing 
 
  //methods 
  this.initialize = initializeFolder 
  this.setState = setStateFolder 
  this.addChild = addChild 
  this.createIndex = createEntryIndex 
  this.escondeBlock = escondeBlock
  this.esconde = escondeFolder 
  this.folderMstr = folderMstr 
  this.renderOb = drawFolder 
  this.totalHeight = totalHeight 
  this.subEntries = folderSubEntries 
  this.linkHTML = linkFolderHTML
  this.blockStartHTML = blockStartHTML
  this.blockEndHTML = blockEndHTML
  this.nodeImageSrc = nodeImageSrc
  this.iconImageSrc = iconImageSrc
  this.getID = getID
  this.forceOpeningOfAncestorFolders = forceOpeningOfAncestorFolders
} 
 
function initializeFolder(level, lastNode, leftSide) 
{ 
  var j=0 
  var i=0       
  nc = this.nChildren 
   
  this.createIndex() 
  this.level = level
  this.leftSideCoded = leftSide

  if (browserVersion == 0 || STARTALLOPEN==1)
    this.isOpen=true;

  if (level>0)
    if (lastNode) //the last child in the children array 
		leftSide = leftSide + "0"
	else
		leftSide = leftSide + "1"

  this.isLastNode = lastNode
 
  if (nc > 0) 
  { 
    level = level + 1 
    for (i=0 ; i < this.nChildren; i++)  
    { 
      if (i == this.nChildren-1) 
        this.children[i].initialize(level, 1, leftSide)
      else 
        this.children[i].initialize(level, 0, leftSide)
    } 
  } 
} 
 
function drawFolder(insertAtObj) 
{ 
  var nodeName = ""
  var auxEv = ""
  var docW = ""

  var leftSide = leftSideHTML(this.leftSideCoded)

  if (browserVersion > 0) 
    auxEv = "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" href='javascript:clickOnNode(\""+this.getID()+"\")'>" 
  else 
    auxEv = "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" >" 

  nodeName = this.nodeImageSrc()
 
  if (this.level>0) 
    if (this.isLastNode) //the last child in the children array 
	    leftSide = leftSide + "<td valign=top>" + auxEv + "<img name='nodeIcon" + this.id + "' id='nodeIcon" + this.id + "' src='" + nodeName + "' width=16 height=22 border=0></a></td>"
    else 
      leftSide = leftSide + "<td valign=top background=" + ICONPATH + "ftv2vertline.gif>" + auxEv + "<img name='nodeIcon" + this.id + "' id='nodeIcon" + this.id + "' src='" + nodeName + "' width=16 height=22 border=0></a></td>"

  this.isRendered = 1

  if (browserVersion == 2) { 
    if (!doc.yPos) 
      doc.yPos=20 
  } 

  docW = this.blockStartHTML("folder");

  docW = docW + "<tr>" + leftSide + "<td valign=top>";
  if (USEICONS)
  {
    docW = docW + this.linkHTML(false) 
    docW = docW + "<img id='folderIcon" + this.id + "' name='folderIcon" + this.id + "' src='" + this.iconImageSrc() + "' border=0></a>"
  }
  else
  {
	  if (this.prependHTML == "")
        docW = docW + "<img src=" + ICONPATH + "ftv2blank.gif height=2 width=2>"
  }
  if (WRAPTEXT)
	  docW = docW + "</td>"+this.prependHTML+"<td valign=middle width=100%>"
  else
	  docW = docW + "</td>"+this.prependHTML+"<td valign=middle nowrap width=100%>"
  if (USETEXTLINKS) 
  { 

    docW = docW + this.linkHTML(true) 
    docW = docW + this.desc + "</a>"

  } 
  else 
    docW = docW + this.desc
  docW = docW + "</td>"

  docW = docW + this.blockEndHTML()

  if (insertAtObj == null)
  {
	  if (supportsDeferral) {
		  doc.write("<div id=domRoot></div>") //transition between regular flow HTML, and node-insert DOM DHTML
		  insertAtObj = getElById("domRoot")
		  insertAtObj.insertAdjacentHTML("beforeEnd", docW)
	  }
	  else
		  doc.write(docW)
  }
  else
  {
      insertAtObj.insertAdjacentHTML("afterEnd", docW)
  }
 
  if (browserVersion == 2) 
  { 
    this.navObj = doc.layers["folder"+this.id] 
    if (USEICONS)
      this.iconImg = this.navObj.document.images["folderIcon"+this.id] 
    this.nodeImg = this.navObj.document.images["nodeIcon"+this.id] 
    doc.yPos=doc.yPos+this.navObj.clip.height 
  } 
  else if (browserVersion != 0)
  { 
    this.navObj = getElById("folder"+this.id)
    if (USEICONS)
      this.iconImg = getElById("folderIcon"+this.id) 
    this.nodeImg = getElById("nodeIcon"+this.id)
  } 
} 
 
function setStateFolder(isOpen) 
{ 
  var subEntries 
  var totalHeight 
  var fIt = 0 
  var i=0 
  var currentOpen
  
 
  if (isOpen == this.isOpen) 
    return 
 
  if (browserVersion == 2)  
  { 
    totalHeight = 0 
    for (i=0; i < this.nChildren; i++) 
      totalHeight = totalHeight + this.children[i].navObj.clip.height 
      subEntries = this.subEntries() 
    if (this.isOpen) 
      totalHeight = 0 - totalHeight 
    for (fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) 
      indexOfEntries[fIt].navObj.moveBy(0, totalHeight) 
  }  
  this.isOpen = isOpen;

  if (this.getID()!=foldersTree.getID() && PERSERVESTATE && !this.isOpen) //closing
  {
     currentOpen = GetCookie("clickedFolder")
     if (currentOpen != null) {
         currentOpen = currentOpen.replace(this.getID()+cookieCutter, "")
         SetCookie("clickedFolder", currentOpen)
     }
  }
	
  if (!this.isOpen && this.isLastOpenedfolder)
  {
		lastOpenedFolder = null;
		this.isLastOpenedfolder = false;
  }
  propagateChangesInState(this) 
} 
 
function propagateChangesInState(folder) 
{   
  var i=0 

  //Change node
  if (folder.nChildren > 0 && folder.level>0)  //otherwise the one given at render stays
    folder.nodeImg.src = folder.nodeImageSrc()

  //Change icon
  if (USEICONS)                                  
    folder.iconImg.src = folder.iconImageSrc()  

  //Propagate changes
  for (i=folder.nChildren-1; i>=0; i--) 
    if (folder.isOpen) 
      folder.children[i].folderMstr(folder.navObj)
    else 
  	  folder.children[i].esconde() 
} 
 
function escondeFolder() 
{ 
  this.escondeBlock()
   
  this.setState(0) 
} 
 
function linkFolderHTML(isTextLink) 
{ 
  var docW = "";
   if (this.hreference) 
  { 
	if (USEFRAMES)
	  docW = docW + "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" href='" + this.hreference + "' TARGET=\"basefrm\" "
	else
	  docW = docW + "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" href='" + this.hreference + "' TARGET=_top "
        
    if (isTextLink) {
        docW += "id=\"itemTextLink"+this.id+"\" ";
    }

    if (browserVersion > 0) 
      docW = docW + "onClick='javascript:clickOnFolder(\""+this.getID()+"\")'"

    docW = docW + ">"
  } 
  else{
    //docW = docW + "<a class=\"tree\">"  //Commented by sudheer and below code is added to achieve click and dblclick on folder to list its contents 
    docW += "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" " 
    if (isTextLink)  
      docW += "id=\"itemTextLink"+this.id+"\" "
    docW += "onClick='javascript:clickOnFolder(\""+this.getID()+"\")'"
    docW += "onDblClick='javascript:clickOnNode(\""+this.getID()+"\")'" + ">"  
  }
  return docW;
} 
 
function addChild(childNode) 
{ 
	this.children[this.nChildren] = childNode 
	childNode.parentObj = this
	this.nChildren++ 
	return childNode 
} 
 
function folderSubEntries() 
{ 
  var i = 0 
  var se = this.nChildren 
 
  for (i=0; i < this.nChildren; i++){ 
    if (this.children[i].children) //is a folder 
      se = se + this.children[i].subEntries() 
  } 
 
  return se 
} 

function nodeImageSrc() {
  var srcStr = "";

  if (this.isLastNode) //the last child in the children array 
  { 
    if (this.nChildren == 0 )
      if (this.isChildExist)						          // Added By Sudheer
        srcStr = ICONPATH + "ftv2plastnode.gif"		// Added By Sudheer
      else											                  // Added By Sudheer
        srcStr = ICONPATH + "ftv2lastnode.gif"
    else
      if (this.isOpen)
        srcStr = ICONPATH + "ftv2mlastnode.gif"  
      else
        srcStr = ICONPATH + "ftv2plastnode.gif"  
  } 
  else 
  { 
    if (this.nChildren == 0  )
      if (this.isChildExist)					        // Added By Sudheer
        srcStr = ICONPATH + "ftv2pnode.gif"		// Added By Sudheer
      else										                // Added By Sudheer
          srcStr = ICONPATH + "ftv2node.gif"
    else
      if (this.isOpen)
        srcStr = ICONPATH + "ftv2mnode.gif"
      else
        srcStr = ICONPATH + "ftv2pnode.gif"
  }   
  return srcStr;
  
  
}

function iconImageSrc() {
//  if (this.isOpen)                    Commented By Sudheer
  if (this.isOpen4List)
    return(this.iconSrc)
  else
    return(this.iconSrcClosed)
} 
 
// Definition of class Item (a document or link inside a Folder) 
// ************************************************************* 
 
function Item(itemDescription, isVersioned, itemLink, target ) // Constructor 
{ 
  // constant data 
  this.desc = itemDescription 
  this.link = itemLink    
  this.id = -1 //initialized in initalize() 
  this.navObj = 0 //initialized in render() 
  this.iconImg = 0 //initialized in render() 
  if (eval(isVersioned)){
    this.iconSrc = ICONPATH + "ftv2docver.gif" 
  }else{
    this.iconSrc = ICONPATH + "ftv2doc.gif" 
  }

  this.isRendered = 0
  this.isLastNode = false
  this.level = 0
  this.leftSideCoded = ""
  this.nChildren = 0
  this.target = target
  this.parentObj = null
  this.maySelect=true
  this.prependHTML = ""

  this.path=""              //Added by Sudheer -- Path for the Item
 
  // methods 
  this.initialize = initializeItem 
  this.createIndex = createEntryIndex 
  this.escondeBlock = escondeBlock
  this.esconde = escondeBlock
  this.folderMstr = folderMstr 
  this.renderOb = drawItem 
  this.totalHeight = totalHeight 
  this.blockStartHTML = blockStartHTML
  this.blockEndHTML = blockEndHTML
  this.getID = getID
  this.forceOpeningOfAncestorFolders = forceOpeningOfAncestorFolders
  this.iconImageSrc = iconImageSrc
} 
 
function initializeItem(level, lastNode, leftSide) 
{  
  this.createIndex() 
  this.level = level
  this.leftSideCoded = leftSide
  this.isLastNode = lastNode
} 
 
function drawItem(insertAtObj) 
{ 
  var leftSide = leftSideHTML(this.leftSideCoded)
  var docW = ""

  var fullLink = "href=\""+this.link+"\" target=\""+this.target+"\" onClick=\"clickOnLink('"+this.getID()+"\', '"+this.link+"','"+this.target+"');return false;\"";
  this.isRendered = 1

  if (this.level>0) 
    if (this.isLastNode) //the last 'brother' in the children array 
    { 
      leftSide = leftSide + "<td valign=top><img src='" + ICONPATH + "ftv2lastnode.gif' width=16 height=22></td>"
    } 
    else 
    { 
      leftSide = leftSide + "<td valign=top background=" + ICONPATH + "ftv2vertline.gif><img src='" + ICONPATH + "ftv2node.gif' width=16 height=22></td>"
    } 

  docW = docW + this.blockStartHTML("item")

  docW = docW + "<tr>" + leftSide + "<td valign=top>"
  if (USEICONS)
      docW = docW + "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" " + fullLink  + " id=\"itemIconLink"+this.id+"\">" + "<img id='itemIcon"+this.id+"' " + "src='"+this.iconSrc+"' border=0>" + "</a>"
  else
	  if (this.prependHTML == "")
        docW = docW + "<img src=" + ICONPATH + "ftv2blank.gif height=2 width=3>"

  if (WRAPTEXT)
    docW = docW + "</td>"+this.prependHTML+"<td valign=middle width=100%>"
  else
    docW = docW + "</td>"+this.prependHTML+"<td valign=middle nowrap width=100%>"

  if (USETEXTLINKS) 
    docW = docW + "<a class=\"tree\" style=\"cursor:pointer;cursor:hand\" " + fullLink + " id=\"itemTextLink"+this.id+"\">" + this.desc + "</a>"
  else 
    docW = docW + this.desc

  docW = docW + "</td>"

  docW = docW + this.blockEndHTML()
 
  if (insertAtObj == null)
  {
	  doc.write(docW)
  }
  else
  {
      insertAtObj.insertAdjacentHTML("afterEnd", docW)
  }

  if (browserVersion == 2) { 
    this.navObj = doc.layers["item"+this.id] 
    if (USEICONS)
      this.iconImg = this.navObj.document.images["itemIcon"+this.id] 
    doc.yPos=doc.yPos+this.navObj.clip.height 
  } else if (browserVersion != 0) { 
    this.navObj = getElById("item"+this.id)
    if (USEICONS)
      this.iconImg = getElById("itemIcon"+this.id)
  } 
} 
 
 
// Methods common to both objects (pseudo-inheritance) 
// ******************************************************** 
 
function forceOpeningOfAncestorFolders() {
  if (this.parentObj == null || this.parentObj.isOpen)
    return
  else {
    this.parentObj.forceOpeningOfAncestorFolders()
    clickOnNodeObj(this.parentObj)
  }
}

function escondeBlock() 
{ 
  if (browserVersion == 1 || browserVersion == 3) { 
    if (this.navObj.style.display == "none") 
      return 
    this.navObj.style.display = "none" 
  } else { 
    if (this.navObj.visibility == "hidden") 
      return 
    this.navObj.visibility = "hidden" 
  }     
} 
 
function folderMstr(domObj) 
{ 
  if (!this.isRendered)
     this.renderOb(domObj)
  else
    if (browserVersion == 1 || browserVersion == 3) 
      this.navObj.style.display = "block" 
    else 
      this.navObj.visibility = "show" 
} 

function blockStartHTML(idprefix) {
  var idParam = "id='" + idprefix + this.id + "'"
  var docW = ""

  if (browserVersion == 2) 
    docW = "<layer "+ idParam + " top=" + doc.yPos + " visibility=show>"
  else if (browserVersion != 0)
    docW = "<div " + idParam + " style='display:block; position:block;'>"
     
  docW = docW + "<table border=0 cellspacing=0 cellpadding=0 width=100% >"

  return docW
}

function blockEndHTML() {
  var docW = ""

  docW = "</table>"
   
  if (browserVersion == 2) 
    docW = docW + "</layer>"
  else if (browserVersion != 0)
    docW = docW + "</div>"

  return docW
}
 
function createEntryIndex() 
{ 
  this.id = nEntries 
  indexOfEntries[nEntries] = this 
  nEntries++ 
} 
 
// total height of subEntries open 
function totalHeight() //used with browserVersion == 2 
{ 
  var h = this.navObj.clip.height 
  var i = 0 
   
  if (this.isOpen) //is a folder and _is_ open 
    for (i=0 ; i < this.nChildren; i++)  
      h = h + this.children[i].totalHeight() 
 
  return h 
} 


function leftSideHTML(leftSideCoded) {
	var i;
	var retStr = "";

	for (i=0; i<leftSideCoded.length; i++)
	{
		if (leftSideCoded.charAt(i) == "1")
		{
			retStr = retStr + "<td valign=top background=" + ICONPATH + "ftv2vertline.gif><img src='" + ICONPATH + "ftv2vertline.gif' width=16 height=22></td>"
		}
		if (leftSideCoded.charAt(i) == "0")
		{
			retStr = retStr + "<td valign=top><img src='" + ICONPATH + "ftv2blank.gif' width=16 height=22></td>"
		}
	}
	return retStr
}

function getID()
{
  //define a .xID in all nodes (folders and items) if you want to PERVESTATE that
  //work when the tree changes. The value eXternal value must be unique for each
  //node and must node change when other nodes are added or removed
  //The value may be numeric or string, but cannot have the same char used in cookieCutter
  if (typeof this.xID != "undefined") 
    return this.xID
  else
    return this.id
}

 
// Events 
// ********************************************************* 
 
function clickOnFolder(folderId) 
{ 
    
  var folder = findObj(folderId)
  var state = 0 
  var currentOpen
  highlightObjLink(folder);
  document.forms[0].hdnFolderDocument.value=folder.path;
  document.forms[0].folderDocument.value=folder.path;
  if(typeof document.forms[0].btnNewFolder != 'undefined' ){
    document.forms[0].btnNewFolder.disabled=false;
  }
  if(!eval(foldersOnly)){
    if(typeof document.forms[0].btnSelect != 'undefined' ){
      document.forms[0].btnSelect.disabled=true;
    }
  }



// Below block comment is commented by sudheer to nullify node click
/*
    var clicked = findObj(folderId)

    if (!clicked.isOpen) {
      clickOnNodeObj(clicked) 
    }

    if (lastOpenedFolder != null && lastOpenedFolder != folderId)
      clickOnNode(lastOpenedFolder); //sets lastOpenedFolder to null

    if (clicked.nChildren==0) {
      lastOpenedFolder = folderId;
      clicked.isLastOpenedfolder = true
    }

    if (isLinked(clicked.hreference)) {
        highlightObjLink(clicked);
    }
*/



} 
 
function clickOnNode(folderId) 
{ 
  var folderObj = findObj(folderId)
  clickOnNodeObj(folderObj)
  // below code is added by sudheer to expand the node
  if ((folderObj.isOpen) )				
    if (!(folderObj.isNextLevel) ){
      window.location.replace("folderDocSelectB4Action.do?currentFolderDocId4Select="+ folderId + "&treeAppend=true&foldersOnly=" + foldersOnly + "&openerControl=" + openerControl  + "&hdnIsForMail=" + document.forms[0].hdnIsForMail.value);
    }
}

function clickOnNodeObj(folderObj) 
{ 
  var state = 0 
  var currentOpen
 
  state = folderObj.isOpen                      
  folderObj.setState(!state) //open<->close     

  if (folderObj.id!=foldersTree.id && PERSERVESTATE)
  {
    currentOpen = GetCookie("clickedFolder")
    if (currentOpen == null)
      currentOpen = ""

    if (!folderObj.isOpen) //closing
    {
      currentOpen = currentOpen.replace(folderObj.getID()+cookieCutter, "")
      SetCookie("clickedFolder", currentOpen)
    }
    else
      SetCookie("clickedFolder", currentOpen+folderObj.getID()+cookieCutter)
  }
}

function clickOnLink(clickedId, target, windowName) {  
    var docObj=findObj(clickedId);
    highlightObjLink(docObj);
    document.forms[0].hdnFolderDocument.value=docObj.path;
    document.forms[0].folderDocument.value=docObj.path;
    document.forms[0].btnNewFolder.disabled=true;
    document.forms[0].btnSelect.disabled=false;
}



// Auxiliary Functions 
// *******************
 
function findObj(id)
{
  var i=0;
  var nodeObj;
  if (typeof foldersTree.xID != "undefined") {
    nodeObj = indexOfEntries[i];
    for(i=0;i<nEntries&&indexOfEntries[i].xID!=id;i++) //may need optimization
      ;
    id = i
  }
  if (id >= nEntries)
    return null; //example: node removed in DB
  else
    return indexOfEntries[id];
}

function isLinked(hrefText) {
    var result = true;
    result = (result && hrefText !=null);
    result = (result && hrefText != '');
    result = (result && hrefText.indexOf('undefined') < 0);
    result = (result && hrefText.indexOf('parent.op') < 0);
    return result;
}

// Do highlighting by changing background and foreg. colors of folder or doc text
function highlightObjLink(nodeObj) {
  if (!HIGHLIGHT || nodeObj==null || nodeObj.maySelect==false) {//node deleted in DB 
    return;
  }

  if (browserVersion == 1 || browserVersion == 3) {
    var clickedDOMObj = getElById('itemTextLink'+nodeObj.id); 
    if (clickedDOMObj != null) {
        if (lastClicked != null) {
            var prevClickedDOMObj = getElById('itemTextLink'+lastClicked.id);  
            prevClickedDOMObj.style.color=lastClickedColor;
            prevClickedDOMObj.className=lastClickedClassName;
        }

        lastClickedColor    = clickedDOMObj.style.color;
        lastClickedClassName = clickedDOMObj.className;
        clickedDOMObj.style.color=HIGHLIGHT_COLOR;
        clickedDOMObj.className="bgClrLvl_2 tree";
        

        
    }
  }
  lastClicked = nodeObj;
  if (PERSERVESTATE)
    SetCookie('highlightedTreeviewLink', nodeObj.getID());
}

function gFld(description, hreference) 
{ 

  folder = new Folder(description, hreference);
  return folder;
} 
 
function gLnk(optionFlags, description, isVersioned,linkData) 
{ 
  var fullLink = "";
  var targetFlag = "";
  var target = "";
  var protocolFlag = "";
  var protocol = "";

  if (optionFlags>=0) //is numeric (old style) or empty (error)
  {
    return oldGLnk(optionFlags, description, isVersioned, linkData)
  }

  targetFlag = optionFlags.charAt(0)
  if (targetFlag=="B")
    target = "_blank"
  if (targetFlag=="P")
    target = "_parent"
  if (targetFlag=="R")
    target = "basefrm"
  if (targetFlag=="S")
    target = "_self"
  if (targetFlag=="T")
    target = "_top"

  if (optionFlags.length > 1) {
    protocolFlag = optionFlags.charAt(1)
    if (protocolFlag=="h")
      protocol = "http://"
    if (protocolFlag=="s")
      protocol = "https://"
    if (protocolFlag=="f")
      protocol = "ftp://"
    if (protocolFlag=="m")
      protocol = "mailto:"
  }

  fullLink = "'" + protocol + linkData + "' target=" + target

  linkItem = new Item(description, isVersioned, protocol+linkData,  target)
  return linkItem 
} 

//Function created Aug 1, 2002 for backwards compatibility purposes
function oldGLnk(target, description, linkData)
{
  var fullLink = "";
  //Backwards compatibility code
  if (USEFRAMES)
  {
	  if (target==0) 
	  { 
		fullLink = "'"+linkData+"' target=\"basefrm\"" 
	  } 
	  else 
	  { 
		if (target==1) 
		   fullLink = "'http://"+linkData+"' target=_blank" 
		else 
		   if (target==2)
			  fullLink = "'http://"+linkData+"' target=\"basefrm\"" 
		   else
			  fullLink = linkData+" target=\"_top\"" 
	  } 
  }
  else
  {
	  if (target==0) 
	  { 
		fullLink = "'"+linkData+"' target=_top" 
	  } 
	  else 
	  { 
		if (target==1) 
		   fullLink = "'http://"+linkData+"' target=_blank" 
		else 
		   fullLink = "'http://"+linkData+"' target=_top" 
	  } 
  }

  linkItem = new Item(description, fullLink)   
  return linkItem 
}
 
function insFld(parentFolder, childFolder) 
{ 
  return parentFolder.addChild(childFolder) 
} 
 
function insDoc(parentFolder, document) 
{ 
  return parentFolder.addChild(document) 
} 

function preLoadIcons() {
	var auxImg
	auxImg = new Image();
	auxImg.src = ICONPATH + "ftv2vertline.gif";
	auxImg.src = ICONPATH + "ftv2mlastnode.gif";
	auxImg.src = ICONPATH + "ftv2mnode.gif";
	auxImg.src = ICONPATH + "ftv2plastnode.gif";
	auxImg.src = ICONPATH + "ftv2pnode.gif";
	auxImg.src = ICONPATH + "ftv2blank.gif";
	auxImg.src = ICONPATH + "ftv2lastnode.gif";
	auxImg.src = ICONPATH + "ftv2node.gif";
	auxImg.src = ICONPATH + "ftv2folderclosed.gif";
	auxImg.src = ICONPATH + "ftv2folderopen.gif";
	auxImg.src = ICONPATH + "ftv2doc.gif";
  auxImg.src = ICONPATH + "ftv2docver.gif";
}

//Open some folders for initial layout, if necessary
function setInitialLayout() {
  if (browserVersion > 0 && !STARTALLOPEN)
    clickOnNodeObj(foldersTree);
  
  if (!STARTALLOPEN && (browserVersion > 0) && PERSERVESTATE)
		PersistentFolderOpening();
}

//Used with NS4 and STARTALLOPEN
function renderAllTree(nodeObj, parent) {
  var i=0;
  nodeObj.renderOb(parent)
  if (supportsDeferral)
    for (i=nodeObj.nChildren-1; i>=0; i--) 
      renderAllTree(nodeObj.children[i], nodeObj.navObj)
  else
    for (i=0 ; i < nodeObj.nChildren; i++) 
      renderAllTree(nodeObj.children[i], null)
}

function hideWholeTree(nodeObj, hideThisOne, nodeObjMove) {
  var i=0;
  var heightContained=0;
  var childrenMove=nodeObjMove;

  if (hideThisOne)
    nodeObj.escondeBlock()

  if (browserVersion == 2)
    nodeObj.navObj.moveBy(0, 0-nodeObjMove)

  for (i=0 ; i < nodeObj.nChildren; i++) {
    heightContainedInChild = hideWholeTree(nodeObj.children[i], true, childrenMove)
    if (browserVersion == 2) {
      heightContained = heightContained + heightContainedInChild + nodeObj.children[i].navObj.clip.height
      childrenMove = childrenMove + heightContainedInChild
	}
  }

  return heightContained;
}

 
// Simulating inserAdjacentHTML on NS6
// Code by thor@jscript.dk
// ******************************************

if(typeof HTMLElement!="undefined" && !HTMLElement.prototype.insertAdjacentElement){
	HTMLElement.prototype.insertAdjacentElement = function (where,parsedNode)
	{
		switch (where){
		case 'beforeBegin':
			this.parentNode.insertBefore(parsedNode,this)
			break;
		case 'afterBegin':
			this.insertBefore(parsedNode,this.firstChild);
			break;
		case 'beforeEnd':
			this.appendChild(parsedNode);
			break;
		case 'afterEnd':
			if (this.nextSibling) 
				this.parentNode.insertBefore(parsedNode,this.nextSibling);
			else this.parentNode.appendChild(parsedNode);
			break;
		}
	}

	HTMLElement.prototype.insertAdjacentHTML = function(where,htmlStr)
	{
		var r = this.ownerDocument.createRange();
		r.setStartBefore(this);
		var parsedHTML = r.createContextualFragment(htmlStr);
		this.insertAdjacentElement(where,parsedHTML)
	}
}

function getElById(idVal) {
  if (document.getElementById != null)
    return document.getElementById(idVal)
  if (document.all != null)
    return document.all[idVal]
  
  alert("Problem getting element by id")
  return null
}


// Functions for cookies
// Note: THESE FUNCTIONS ARE OPTIONAL. No cookies are used unless
// the PERSERVESTATE variable is set to 1 (default 0)
// The separator currently in use is ^ (chr 94)
// *********************************************************** 

function PersistentFolderOpening()
{
  var stateInCookie;
  var fldStr=""
  var fldArr
  var fldPos=0
  var id
  var nodeObj
  
  stateInCookie = GetCookie("clickedFolder");
  SetCookie("clickedFolder", "") //at the end of function it will be back, minus null cases

  
  if(stateInCookie!=null)
  {
    fldArr = stateInCookie.split(cookieCutter)
    for (fldPos=0; fldPos<fldArr.length; fldPos++)
    {
      fldStr=fldArr[fldPos]
      if (fldStr != "") {
        nodeObj = findObj(fldStr)
        if (nodeObj!=null) //may have been deleted
          if (nodeObj.setState) {
            nodeObj.isNextLevel=true;                //Added by Sudheer to say next level is already existing 
            nodeObj.forceOpeningOfAncestorFolders()
            clickOnNodeObj(nodeObj);
          }
          else
            alert("Internal id is not pointing to a folder anymore. Consider using external IDs")
      }
    }
  }  
}

function storeAllNodesInClickCookie(treeNodeObj)
{
  var currentOpen
  var i = 0

  if (typeof treeNodeObj.setState != "undefined") //is folder
  {
    currentOpen = GetCookie("clickedFolder")
    if (currentOpen == null)
      currentOpen = ""

    if (treeNodeObj.getID() != foldersTree.getID())
      SetCookie("clickedFolder", currentOpen+treeNodeObj.getID()+cookieCutter)

    for (i=0; i < treeNodeObj.nChildren; i++) 
        storeAllNodesInClickCookie(treeNodeObj.children[i])
  }
}

function CookieBranding(name) {
  if (typeof foldersTree.treeID != "undefined")
    return name+foldersTree.treeID //needed for multi-tree sites. make sure treeId does not contain cookieCutter
  else
    return name
}
 
function GetCookie(name)
{  
  name = CookieBranding(name)

	var arg = name + "=";  
	var alen = arg.length;  
	var clen = document.cookie.length;  
	var i = 0;  

	while (i < clen) {    
		var j = i + alen;    
		if (document.cookie.substring(i, j) == arg)      
			return getCookieVal (j);    
		i = document.cookie.indexOf(" ", i) + 1;    
		if (i == 0) break;   
	}  
	return null;
}

function getCookieVal(offset) {  
	var endstr = document.cookie.indexOf (";", offset);  
	if (endstr == -1)    
	endstr = document.cookie.length;  
	return unescape(document.cookie.substring(offset, endstr));
}

function SetCookie(name, value) 
{  
	var argv = SetCookie.arguments;  
	var argc = SetCookie.arguments.length;  
	var expires = (argc > 2) ? argv[2] : null;  
	//var path = (argc > 3) ? argv[3] : null;  
	var domain = (argc > 4) ? argv[4] : null;  
	var secure = (argc > 5) ? argv[5] : false;  
	var path = "/"; //allows the tree to remain open across pages with diff names & paths

  name = CookieBranding(name)

	document.cookie = name + "=" + escape (value) + 
	((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + 
	((path == null) ? "" : ("; path=" + path)) +  
	((domain == null) ? "" : ("; domain=" + domain)) +    
	((secure == true) ? "; secure" : "");
}

function ExpireCookie (name) 
{  
	var exp = new Date();  
	exp.setTime (exp.getTime() - 1);  
	var cval = GetCookie (name);  
  name = CookieBranding(name)
	document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
}


//To customize the tree, overwrite these variables in the configuration file (demoFramesetNode.js, etc.)
var USETEXTLINKS = 0 
var STARTALLOPEN = 0
var USEFRAMES = 0
var USEICONS = 1
var WRAPTEXT = 0
var PERSERVESTATE = 1
var ICONPATH = ''
var HIGHLIGHT = 0
var HIGHLIGHT_COLOR = 'white';
var HIGHLIGHT_BG    = 'blue';
var BUILDALL = 0


//Other variables
var lastClicked = null;
var lastClickedColor;
var lastClickedClassName;
var indexOfEntries = new Array 
var nEntries = 0 
var browserVersion = 0 
var selectedFolder=0
var lastOpenedFolder=null
var doc = document
var supportsDeferral = false
var cookieCutter = '^' //You can change this if you need to use ^ in your xID or treeID values

doc.yPos = 0

// Main function
// ************* 

// This function uses an object (navigator) defined in
// ua.js, imported in the main html page (left frame).
function initializeDocument() 
{ 
  preLoadIcons();
  switch(navigator.family)
  {

    case 'ie4':
      browserVersion = 1 //Simply means IE > 3.x
      break;
    case 'opera':
      browserVersion = (navigator.version > 6 ? 1 : 0); //opera7 has a good DOM
      break;
    case 'nn4':
      browserVersion = 2 //NS4.x 
      break;
    case 'gecko':
      browserVersion = 3 //NS6.x
      break;
    case 'safari':
      browserVersion = 1 //Safari Beta 3 seems to behave like IE in spite of being based on Konkeror
      break;
	default:
      browserVersion = 0 //other, possibly without DHTML  
      break;
  }

  supportsDeferral = ((navigator.family=='ie4' && navigator.version >= 5 && navigator.OS != "mac") || browserVersion == 3);
  supportsDeferral = supportsDeferral & (!BUILDALL)
  if (!USEFRAMES && browserVersion == 2)
  	browserVersion = 0;

  //If PERSERVESTATE is on, STARTALLOPEN can only be effective the first time the page 
  //loads during the session. For subsequent (re)loads the PERSERVESTATE data stored 
  //in cookies takes over the control of the initial expand/collapse
  if (PERSERVESTATE && GetCookie("clickedFolder") != null)
    STARTALLOPEN = 0

  //foldersTree (with the site's data) is created in an external .js (demoFramesetNode.js, for example)
  foldersTree.initialize(0, true, "") 
  if (supportsDeferral && !STARTALLOPEN)
	  foldersTree.renderOb(null) //delay construction of nodes
  else {
    renderAllTree(foldersTree, null);

    if (PERSERVESTATE && STARTALLOPEN)
      storeAllNodesInClickCookie(foldersTree)

    //To force the scrollable area to be big enough
    if (browserVersion == 2) 
      doc.write("<layer top=" + indexOfEntries[nEntries-1].navObj.top + ">&nbsp;</layer>") 

    if (browserVersion != 0 && !STARTALLOPEN)
      hideWholeTree(foldersTree, false, 0)
  }

  setInitialLayout()

  if (PERSERVESTATE && GetCookie('highlightedTreeviewLink')!=null  && GetCookie('highlightedTreeviewLink')!="") {
    var nodeObj = findObj(GetCookie('highlightedTreeviewLink'))
    if (nodeObj!=null){
      nodeObj.forceOpeningOfAncestorFolders()
      highlightObjLink(nodeObj);
    }
    else
      SetCookie('highlightedTreeviewLink', '')
  }
} 







 
