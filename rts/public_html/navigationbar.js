var MOVE_FIRST=0;
var MOVE_PREV=1;
var MOVE_NEXT=2;
var MOVE_LAST=3;
var MOVE_TO=4;

function NavigationBar(barId){

	this.barId=barId;
	
	this.moveNext=MoveNext;
	this.movePrev=MovePrev;
	this.moveFirst=MoveFirst;
	this.moveLast=MoveLast;
	this.moveToPage=MoveToPage
	this.render=DrawBar;
	this.navigate=DoPageNavigation;
	
	this.onclick=null;
	this.pageCount=1;
	this.pageNumber=1;

	this.setPageCount=SetPageCount;
	this.setPageNumber=SetPageNumber;
	this.getPageCount=GetPageCount;
	this.getPageNumber=GetPageNumber;

	this.msgPageNotExist="No page exists with page number";
	this.msgNumberOnly="Enter numbers only.";    
	this.msgEnterPageNo="Please enter page number.";
	this.msgOnFirst="Already On First Page";
	this.msgOnLast="Already On Last Page";

	this.lblPage="Page";
	this.lblOf="Of";

	this.tooltipPageNo="Enter Page Number";
	this.tooltipFirst="First";
	this.tooltipNext="Next";
	this.tooltipPrev="Previous";
	this.tooltipLast="Last";
	this.tooltipGo="Go";
	
}

function DrawBar(){
	document.write("<table style='margin-top:2px;margin-bottom:2px' width='215px' border='0' cellpadding='0'  cellspacing='0' >");
	document.write("<tr>");
	document.write("<td width='35px' align='center' >");
	document.write("<a id='"+this.barId+"First' class='imgNavFirst' onclick='" + this.barId+ ".moveFirst();' title='"+this.tooltipFirst+"' ></a>");
	document.write("<a id='"+this.barId+"Prev' class='imgNavPrev' onclick='" + this.barId+ ".movePrev();' title='"+this.tooltipPrev+"' ></a>");
	document.write("</td>");
	document.write("<td width='35px' align='center' >");
	document.write(this.lblPage);
	document.write("</td>");
	document.write("<td width='30px' align='center' >");
	document.write("<input type='text'  id='" + this.barId+ "txtPageNo' name='" + this.barId+ "txtPageNo' class='borderClrLvl_2 componentStyle' style='text-align:right; width:100%'  size='3' onkeypress='return integerOnly(event);' value='" + this.pageNumber + "' title='"+this.tooltipPageNo+"' >");
	document.write("</td>");
	document.write("<td width='30px' align='center' >");
	document.write("<a onclick='" + this.barId+ ".moveToPage();' class='imgGo' title='"+this.tooltipGo+"' ></a>");
	document.write("</td>");
	document.write("<td width='20px' align='center'>");
	document.write(this.lblOf);
	document.write("</td >");
	document.write("<td width='30px' align='center'>");
	document.write(this.pageCount);
	document.write("</td>");
	document.write("<td width='35px' align='center'>");
	document.write("<a id='"+this.barId+"Next' class='imgNavNext' onclick='" + this.barId + ".moveNext();' title='"+this.tooltipNext+"' ></a>");
	document.write("<a id='"+this.barId+"Last' class='imgNavLast' onclick='" + this.barId + ".moveLast();' title='"+this.tooltipLast+"' ></a>");
	document.write("</td>");
	document.write("</tr>");
	document.write("</table>");

  var objFirst=MM_findObj(this.barId+"First");
  var objPrev=MM_findObj(this.barId+"Prev");
  var objNext=MM_findObj(this.barId+"Next");
  var objLast=MM_findObj(this.barId+"Last");
	var varPageNumber=parseInt(this.pageNumber);
  var varPageCount=parseInt(this.pageCount);

  if (varPageNumber==1 && varPageCount==varPageNumber){
    objFirst.className="imgNavFirstDisable";
    objFirst.onclick="";
    objPrev.className="imgNavPrevDisable";
    objPrev.onclick="";
    objNext.className="imgNavNextDisable";
    objNext.onclick="";
    objLast.className="imgNavLastDisable";
    objLast.onclick="";
  }else if (varPageNumber==1 && varPageCount!=varPageNumber){
    objFirst.className="imgNavFirstDisable";
    objFirst.onclick="";
    objPrev.className="imgNavPrevDisable";
    objPrev.onclick="";
    objNext.className="imgNavNext";
    objLast.className="imgNavLast";
  }else if (varPageNumber!=1 && varPageCount==varPageNumber){
    objFirst.className="imgNavFirst";
    objPrev.className="imgNavPrev";
    objNext.className="imgNavNextDisable";
    objNext.onclick="";
    objLast.className="imgNavLastDisable";
    objLast.onclick="";  
  }else if (varPageNumber!=1 && varPageCount!=varPageNumber){
    objFirst.className="imgNavFirst";
    objPrev.className="imgNavPrev";
    objNext.className="imgNavNext";
    objLast.className="imgNavLast";
  }

}

function MoveNext(){
	this.navigate(MOVE_NEXT);
}
function MovePrev(){
	this.navigate(MOVE_PREV);
}
function MoveFirst(){
	this.navigate(MOVE_FIRST);
}
function MoveLast(){
	this.navigate(MOVE_LAST);
}
function MoveToPage(){
	this.navigate(MOVE_TO);		
}
function SetPageCount(varPageCount){
	this.pageCount=varPageCount;
}
function SetPageNumber(varPageNumber){
	this.pageNumber=varPageNumber;
}
function GetPageCount(){
	return this.pageCount;
}
function GetPageNumber(){
	return this.pageNumber;
}

function DoPageNavigation(varAction){
    var objPageNumber=MM_findObj(this.barId+"txtPageNo");
		var varPageNumber=objPageNumber.value;
    //To get rid of 0 as the first digit of any number
    varPageNumber=((varPageNumber.substring(0,1)=='0')?varPageNumber.substring(1,varPageNumber.length):varPageNumber);
		
    //Check For Number Only
    if (isNaN(varPageNumber)){
      alert(this.msgNumberOnly);
      return false;
    }
    //Check For Blank Entry
    if(isNaN(parseInt(varPageNumber))){     
      alert(this.msgEnterPageNo);
      return false;
    } 

    varPageNumber=parseInt(varPageNumber);
    
    if (varAction==MOVE_FIRST){     
			if(varPageNumber==1){      
			  alert(this.msgOnFirst);
			  return false;
			}
      varPageNumber=1;
    }else if (varAction==MOVE_PREV){          
      varPageNumber-=1;
    }else if (varAction==MOVE_NEXT){    
      varPageNumber+=1;
    }else if (varAction==MOVE_LAST){                        
      if(varPageNumber==this.pageCount){
				alert(this.msgOnLast);
				return false;
			}
      varPageNumber=this.pageCount;
    } 
    
    if((varPageNumber<1)||(varPageNumber>this.pageCount)){      
      alert(this.msgPageNotExist +" "+varPageNumber+".");
      return false;
    }
        
    this.pageNumber=varPageNumber;
    objPageNumber.value=varPageNumber;
    if(this.onclick!=null){
			if(typeof this.onclick!="undefined"){
				eval(this.onclick);
			}
    }
    return true;
}