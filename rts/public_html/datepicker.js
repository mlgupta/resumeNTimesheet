/*
Purpose: To Render a Date,Time and Timezone DatePicker Control.
*/

// Global Constants  
var ONE_DAY = 86400000;          
var ONE_MONTH = (ONE_DAY * 30);
var ONE_YEAR=(ONE_MONTH*12);
var CAL_DEFAULT_HEIGHT=17;
var CAL_DEFAULT_WIDTH=180;
var CAL_DEFAULT_WIDTH_NOTIMEZONE=150;

//Month Short Name Array
var MONTH_SHORT_NAME = new Array(12)
MONTH_SHORT_NAME[0] = "Jan"
MONTH_SHORT_NAME[1] = "Feb" 
MONTH_SHORT_NAME[2] = "Mar" 
MONTH_SHORT_NAME[3] = "Apr" 
MONTH_SHORT_NAME[4] = "May" 
MONTH_SHORT_NAME[5] = "Jun" 
MONTH_SHORT_NAME[6] = "Jul" 
MONTH_SHORT_NAME[7] = "Aug" 
MONTH_SHORT_NAME[8] = "Sep" 
MONTH_SHORT_NAME[9] = "Oct" 
MONTH_SHORT_NAME[10] = "Nov" 
MONTH_SHORT_NAME[11] = "Dec" 

/*
Contructor to Construct the calendar control object
@param calId - Acts as Id as well as control object(instance) - i.e. both should be same
@param calWidth - Width of the calendar control object
@param calHeight - Height of the calendar control object
*/

function Calendar(calId,calWidth,calHeight){
  /********** Properties ****************/

  // ## Private variables
  
  // General Properties
	this.calId=calId;
	this.calWidth=calWidth;
	this.calHeight=calHeight;

  // Boolean Properties
	this.noTime=false;
	this.noTimezone=false;
  
  this.isInitialized=false;

  this.timezoneDisabled=false;

  this.disabled=false;

  this.clearDisabled=false;

  //Timer related Properties 
  this.refreshTimer=null;
	this.blinkTimer=null;

  this.prevBlinkId=null;
  
  //Running Date Property used to navigate for the Month and Year internally
	this.runningDate=new Date();

  //local date properties
	this.localDate=new Date();
	this.localTime=new Date();
  this.localTimezoneName=null;
	this.localTimezoneIndex=null;
	this.localTimezoneHrs=null;

  //selected date properties
	this.selectedDate=null;
	this.selectedTimezoneName=null;
	this.selectedTimezoneIndex=null;
	this.selectedTimezoneHrs=null;

  //selected Month properties
	this.selectedMonth="";
	this.selectedMonthName="";

  //Hour, Minute, Second and Time zone combo data
  this.options24="";
	this.options60="";
	this.optionsTZ="";

  /****** Methods, Accessors, Mutators, Event Handlers  *****/

  // ## Private methods

  // Navigation Methods
	this.moveNextMonth=NextMonth;
	this.movePrevMonth=PrevMonth;
	this.moveNextYear=NextYear;
	this.movePrevYear=PrevYear;

	this.setToDay=SetToDay;
	this.doCalendar=DoCalendar;
	this.showCalendar=ShowCalendar;
	this.initOnShow=InitOnShowCalendar;
  
	this.pickDate=PickDate;
  this.ok=OkClick;
  this.cancel=CancelClick;
	this.tzOnChange=TZOnChange;

  //public methods 
	this.initialize=Initialize;
  this.render=drawDatePicker;

  //Accessors
  this.getBrowserTimezoneName=GetBrowserTimezoneName;
	this.getDay=GetDay;
	this.getMonth=GetMonth;
	this.getYear=GetYear;
	this.getHours=GetHours;
	this.getMinutes=GetMinutes;
	this.getSeconds=GetSeconds;
	this.getTimezone=GetTimezone;

  //Mutators
	this.setDateTime=SetDateTime;
	this.setTimezone=SetTimezone;

  //Event Handlers
	this.onclick=null;
  this.onclear=null;

  //Events
  this.click=ClickEvent;
  this.clear=ClearDate;

  //Tooltips
  this.tooltipClear="Click to Clear";
  this.tooltipNextMonth="Move to Next Month";
  this.tooltipPrevMonth="Move to Previous Month";
  this.tooltipNextYear="Move to Next Year";
  this.tooltipPrevYear="Move to Previous Year";
  this.tooltipTimezone="Choose Timezone";
  this.tooltipHour="Choose Hours (0-23)";
  this.tooltipMinute="Choose Minutes (0-59)";
  this.tooltipSecond="Choose Seconds (0-59)";
  this.tooltipOk="Submit Changes";
  this.tooltipCancel="Cancel Changes";
  this.tooltipDay="Choose Day";
  this.tooltipNow="Move to Now";
  this.tooltipCalendar="Click to Popup the Calendar";
}

/**
Purpose : To show the calender for the specified Date
@param  : theDate
**/

function ShowCalendar(theDate){
	MM_findObj(this.calId + 'calendar').innerHTML="";
	var varCal=""
	var DateLoop = new Date(theDate);
	varCal ="<table border='0'  cellspacing='2' cellpadding='1' class='imgData borderClrLvl_2 bgClrLvl_4' width='"+ this.calWidth + "px' >";	
  //Timezone combo 
	if(!this.noTimezone){
    varCal += "<tr>";
    if (eval(this.timezoneDisabled)){
      varCal += "<th colspan='7' align='center' class='borderClrLvl_2' >";
      varCal += this.selectedTimezoneName
      varCal += "<select id='"+ this.calId + "cboTZ' name='"+ this.calId + "cboTZ' style='display:none; width:"+ (this.calWidth-10) + "px' onchange='"+ this.calId + ".tzOnChange()' class='borderClrLvl_2 componentstyle' title='"+ this.tooltipTimezone +"' >";
      varCal += this.optionsTZ;
      varCal += "</select>";
      varCal += "</th>";
    }else{
      varCal += "<td colspan='7' align='center' class='borderClrLvl_2' >";
      varCal += "<select id='"+ this.calId + "cboTZ' name='"+ this.calId + "cboTZ' style='width:"+ (this.calWidth-10) + "px' onchange='"+ this.calId + ".tzOnChange()' class='borderClrLvl_2 componentstyle' title='"+ this.tooltipTimezone +"' >";
      varCal += this.optionsTZ;
      varCal += "</select>";
      varCal += "</td>";
    }
    varCal += "</tr>";
	}
	varCal += "<tr>";
	varCal += "<td colspan='7' align='center'>" 
	varCal += "<table border='0' cellpadding='0' cellspacing='1' width='100%' class='borderClrLvl_2' >";
	varCal += "<tr width='100%'>";
	//Navigate Previous Month and Previous Year 
	varCal += "<td width='10%' onClick='" + this.calId + ".movePrevYear()' class='imgNavFirst '   title='"+ this.tooltipPrevYear +"' >&nbsp;</td>";
	varCal += "<td width='10%' onClick='" + this.calId + ".movePrevMonth()' class='imgNavPrev '   title='"+ this.tooltipPrevMonth +"' >&nbsp;</td>";
	
	varCal += "<td style='cursor:pointer;cursor: default' width='60%' align='center'>";
	varCal += this.selectedMonthName;
	varCal += "</td>";

	//Navigate Next Month and Next Year 
	varCal += "<td width='10%' onClick='" + this.calId + ".moveNextMonth()' class='imgNavNext '  title='"+ this.tooltipNextMonth +"' >&nbsp;</td>";
	varCal += "<td width='10%' onClick='" + this.calId + ".moveNextYear()' class='imgNavLast ' title='"+ this.tooltipNextYear +"' >&nbsp;</td>";
	varCal += "</tr>";
	varCal += "</table>";
	varCal += "</td>";
	varCal += "</tr>";

  //Week Days Row
	varCal += "<tr class='borderClrLvl_2'>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>S</th>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>M</th>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>T</th>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>W</th>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>T</th>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>F</th>";
	varCal += "<th style='cursor:pointer;cursor:default' align='center' class='borderClrLvl_2'>S</th>";
	varCal += "</tr>";

  //Day Rows  
	for ( var r = 1; r <= 6; r++ ) {
		varCal += "<tr>"
		for ( var c = 1; c <= 7; c++ ) {
			var cellPrefix="";
			var cellSuffix="";
      //Current Month Dates cells 
			if (DateLoop.getMonth()==this.selectedMonth){

				if(DateLoop.getDate()==this.localDate.getDate()||(DateLoop.getDay()==0)){	
						cellPrefix="<th " 	
						cellSuffix="</th>";		
				}else{
						cellPrefix= "<td class='borderClrLvl_2 bgClrLvl_4' ";
						cellSuffix="</td>";	
				}

        varCal +=cellPrefix;
        varCal += " id='" + (this.calId + r)+c +"' ";
        varCal += " style=' cursor:pointer; cursor:hand; visibility:visible' align='center'" ;
        varCal += " onclick='"+this.calId +".pickDate(\""+ DateLoop +"\",this.id)' ";
        varCal += " title='"+ this.tooltipDay +"' ";
        varCal += " >" + DateLoop.getDate() 
        varCal +=cellSuffix;

        if(DateLoop.getDate()==this.selectedDate.getDate()){
          BlinkSelected(this.calId,(this.calId + r)+c );      	
        }
      //Non-Current Month Dates cells 
			}else{
				varCal +="<td  style='cursor:pointer;cursor: default' align='center'>&nbsp;</td>"  
			}
      //Days Calculation
			varDays = DateLoop.getTime() + ONE_DAY;
			DateLoop.setTime(varDays);
		}
		varCal += "</tr>"
	}

  
  //Todays Row
	varCal += "<tr>"; 
	varCal += "<th id='"+ this.calId + "todayNow' onclick='"+this.calId +".setToDay()' style='cursor:pointer; cursor:hand;' colspan='7' align='center' class='borderClrLvl_2' title='"+ this.tooltipNow +"' >"
	varCal += "</th>";
	varCal += "</tr>";

  // Time combos and Ok Cancel
  varCal += "<tr>";
  varCal += "<td colspan='7' align='center' class='borderClrLvl_2' >";

  varCal += "<table border='0' cellpadding='0' cellspacing='1' width='100%'  >";
	varCal += "<tr width='100%'>";

	// Combos Hours , Minutes & Seconds
  if(!this.noTime){
    varCal += "<td  align='center'>";
    varCal += "<select id='"+ this.calId + "cboHH' name='"+ this.calId + "cboHH' style='width:38x' class='borderClrLvl_2 componentstyle' title='"+ this.tooltipHour +"' >";
    varCal += this.options24;
    varCal += "</select>:";
    varCal += "<select id='"+ this.calId + "cboMM' name='"+ this.calId + "cboMM' style='width:38px' class='borderClrLvl_2 componentstyle' title='"+ this.tooltipMinute +"' >";
    varCal += this.options60;
    varCal += "</select>:";
    varCal += "<select id='"+ this.calId + "cboSS' name='"+ this.calId + "cboSS' style='width:38px' class='borderClrLvl_2 componentstyle' title='"+ this.tooltipSecond +"' >";
    varCal += this.options60;
    varCal += "</select>";
    varCal += "</td>";
  }else{
    varCal += "<td align='center'>&nbsp;</td>";
  }
	// Buttons Ok and Cancel
	varCal += "<td class='imgDtPickerOk' onclick='"+this.calId +".ok()' style='width:17px' title='"+ this.tooltipOk +"' >&nbsp;</td>";
	varCal += "<td class='imgDtPickerCancel' onclick='"+this.calId +".cancel()' style='width:17px' title='"+ this.tooltipCancel +"' >&nbsp;</td>";
	varCal += "</tr>";
	varCal += "</table>";
  varCal += "</td>";

  varCal += "</tr>";
	varCal += "</table>";

  //Assigning Inner HTML 
	MM_findObj(this.calId +'calendar').innerHTML = varCal

  //Call for time refresher
	RefreshTime(this.calId);
}


//Renders the Control
function drawDatePicker(){
  //Checking Calendar Height if less than default it will be set default
	this.calHeight=(this.calHeight<CAL_DEFAULT_HEIGHT)?CAL_DEFAULT_HEIGHT:this.calHeight;

  //Checking Calendar Width if less than default it will be set default
  // Default width is different if timezone and time measurement is not requried
	if(this.noTimezone &&	this.noTime){
    this.calWidth=(this.calWidth<CAL_DEFAULT_WIDTH_NOTIMEZONE)?CAL_DEFAULT_WIDTH_NOTIMEZONE:this.calWidth;
  }else{
    this.calWidth=(this.calWidth<CAL_DEFAULT_WIDTH)?CAL_DEFAULT_WIDTH:this.calWidth;
  }

	document.write("<table border='0' cellspacing='0' cellpadding='0' >");
	document.write("<tr>");
	document.write("<td>");
	document.write("<input type='text' style='cursor:pointer;cursor:default; width:" + (this.calWidth-15) + "px; height:" + this.calHeight + "px;' class='borderClrLvl_2 componentstyle componentstyleDisable' id='" + this.calId + "txtDate' name='" + this.calId + "txtDate' ")
	document.write(" readonly />");
  document.write("<input type='hidden' id='" + this.calId + "hdnDate' name='" + this.calId + "hdnDate' />")
	document.write("</td>");
  //Disabled will hide the popup icon
  if (!eval(this.disabled)){
    document.write("<td>");
    document.write("<div style='float:left' class='imgDtPickerCal' onclick='"+this.calId +".initOnShow()' title='"+ this.tooltipCalendar +"' ></div>");
    if (!eval(this.clearDisabled)){
      document.write("<a href='#' style='float:left;margin-left:2px' class='menu' onclick='"+this.calId +".clear()' title='"+ this.tooltipClear +"' ><b>x</b></a>");
    }
    document.write("</td>");
    document.write("</tr>");
    document.write("<tr><td height='2px' colspan='2' ></td></tr>");
  }
  document.write("<tr>");
  document.write("<td colspan='2' >");
  document.write("<div style='position:absolute;z-index:10;' id='" + this.calId + "calendar'></div>");
  document.write("</div>");
  document.write("</td>");
	document.write("</tr>");
	document.write("</table>");

}

//Initializes the calendar items for values which set by mutators
//Combo(Hours, Minutes & Seconds and Timezone) items filled in one loop;
function Initialize(){
    //Timezone and Time is required
    if ((!this.noTimezone)&& (!this.noTime)){
		for(var i=0; i<timezoneName.length; i++){
			var iString=new String(i);
			iString=(iString.length==1)?"0" + iString:iString;
			if(i<24){
				this.options24+= "<option value='" + i + "'>" + iString + "</option>";
			}
			if(i<60){
				this.options60+="<option value='" + i + "'>" + iString + "</option>";
			}	
			this.optionsTZ+="<option value='" + i + "'>" + timezoneName[i] + "</option>";

			if((this.selectedTimezoneHrs==null)&&(this.selectedTimezoneName==null)){
				if(timezoneHrs[i]==SSToHH((new Date(this.localDate)).getTimezoneOffset()*60)){
						this.selectedTimezoneHrs=timezoneHrs[i];
						this.selectedTimezoneIndex=i;
						this.selectedTimezoneName=timezoneName[i];
				}
			}else if (this.selectedTimezoneName!=null){
				if(timezoneName[i]==this.selectedTimezoneName){
						this.selectedTimezoneIndex=i;
						this.selectedTimezoneHrs=timezoneHrs[i];
				}
			}

      if((this.localTimezoneHrs==null)&&(this.localTimezoneName==null)){
				if(timezoneHrs[i]==SSToHH((new Date(this.localDate)).getTimezoneOffset()*60)){
						this.localTimezoneHrs=timezoneHrs[i];
						this.localTimezoneIndex=i;
						this.localTimezoneName=timezoneName[i];
				}
			}else if (this.localTimezoneName!=null){
				if(timezoneName[i]==this.localTimezoneName){
						this.localTimezoneIndex=i;
						this.localTimezoneHrs=timezoneHrs[i];
				}
			}
      
		}

  //Timezone is not required and Time is required
	}else if((this.noTimezone)&& (!this.noTime)){
		for(var i=0; i<59; i++){
			var iString=new String(i);
			iString=(iString.length==1)?"0" + iString:iString;
			if(i<24){
				this.options24+= "<option value='" + i + "'>" + iString + "</option>";
			}
			if(i<60){
				this.options60+="<option value='" + i + "'>" + iString + "</option>";
			}	
		}

 //Timezone is required and Time is not required
	}else if((!this.noTimezone)&& (this.noTime)){
		var isLocalTimezoneset=false;
		for(var i=0; i<timezoneName.length; i++){
			this.optionsTZ+="<option value='" + i + "'>" + timezoneName[i] + "</option>";
			if((this.selectedTimezoneHrs==null)&&(this.selectedTimezoneName==null)){
				if(timezoneHrs[i]==SSToHH((new Date(this.localDate)).getTimezoneOffset()*60)){
						this.selectedTimezoneHrs=timezoneHrs[i];
						this.selectedTimezoneIndex=i;
						this.selectedTimezoneName=timezoneName[i];
				}
			}else if (this.selectedTimezoneName!=null){
				if(timezoneName[i]==this.selectedTimezoneName){
						this.selectedTimezoneIndex=i;
						this.selectedTimezoneHrs=timezoneHrs[i];
				}
			}
		}
	}

  //Initialize is One time Operation 
  //isInitialized flag is set.
  this.isInitialized=true;
}

function InitOnShowCalendar(){
  //Check whether initialized or not 
  // if not initialize it
  if (!eval(this.isInitialized)){
    this.initialize();
  }

  // Call for the doCalendar for the Selected Date
	this.doCalendar(this.selectedDate);

  //Synchronization of combo with selectedTimezone
	if (!this.noTimezone){
		MM_findObj(this.calId +'cboTZ').value=this.selectedTimezoneIndex;
	}
  //Synchronization of combo with selectedTime (Hours, Minutes and Years)
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=this.selectedDate.getHours();
		MM_findObj(this.calId +'cboMM').value=this.selectedDate.getMinutes();
		MM_findObj(this.calId +'cboSS').value=this.selectedDate.getSeconds();
	}

  //Setting the Hidden date
  MM_findObj(this.calId + 'hdnDate').value=this.selectedDate;
}

//Creates the calender for the Given Date
function DoCalendar(TheDate)
{
	var RefDate=new Date(TheDate);
	varDays=RefDate.getTime() - (ONE_DAY * (RefDate.getDate()-1));
	RefDate.setTime(varDays);
	varDays=RefDate.getTime() - (ONE_DAY * (RefDate.getDay()));
	RefDate.setTime(varDays);
	var MnthDate=new Date(TheDate);
	varDays=RefDate.getTime() + (ONE_DAY * 15);
	MnthDate.setTime(varDays);
	this.selectedMonth=MnthDate.getMonth();
	this.selectedMonthName=MONTH_SHORT_NAME[MnthDate.getMonth()] + " " + ((MnthDate.getYear()<2000)?(1900 + MnthDate.getYear()):MnthDate.getYear());
	this.runningDate=TheDate;
	this.showCalendar(RefDate);	
	
	if (!this.noTimezone){
		MM_findObj(this.calId +'cboTZ').value=this.selectedTimezoneIndex;
	}
}

//Sets Today
function SetToDay(){
	var varHH;
	var varMM;
	var varSS;
	var tDate = new Date(new Date());
	varpDays = tDate.getTime();
	tDate.setTime(varpDays);
	if (!this.noTime){
		var varHH=MM_findObj(this.calId +'cboHH').value;
		var varMM=MM_findObj(this.calId +'cboMM').value;
		var varSS=MM_findObj(this.calId +'cboSS').value;
	}
	this.doCalendar(tDate);
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=varHH;
		MM_findObj(this.calId +'cboMM').value=varMM;
		MM_findObj(this.calId +'cboSS').value=varSS;
	}	
	
}

//Navigating to previous Month 
function PrevMonth(){
	var pDate = new Date(this.runningDate);
	varpDays = pDate.getTime() - ONE_MONTH;
	pDate.setTime(varpDays);
	if (!this.noTime){
		var varHH=MM_findObj(this.calId +'cboHH').value;
		var varMM=MM_findObj(this.calId +'cboMM').value;
		var varSS=MM_findObj(this.calId +'cboSS').value;
	}
	this.doCalendar(pDate);
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=varHH;
		MM_findObj(this.calId +'cboMM').value=varMM;
		MM_findObj(this.calId +'cboSS').value=varSS;
	}	
}

//Navigating to next Month 
function NextMonth(){
	var nDate = new Date(this.runningDate);
	varnDays = nDate.getTime() + ONE_MONTH;
	nDate.setTime(varnDays);
	if (!this.noTime){
		var varHH=MM_findObj(this.calId +'cboHH').value;
		var varMM=MM_findObj(this.calId +'cboMM').value;
		var varSS=MM_findObj(this.calId +'cboSS').value;
	}
	this.doCalendar(nDate);
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=varHH;
		MM_findObj(this.calId +'cboMM').value=varMM;
		MM_findObj(this.calId +'cboSS').value=varSS;
	}	
}

//Navigating to previous Year
function PrevYear(){
	var pDate = new Date(this.runningDate);
	varpDays = pDate.getTime() - ONE_YEAR;
	pDate.setTime(varpDays);
	if (!this.noTime){
		var varHH=MM_findObj(this.calId +'cboHH').value;
		var varMM=MM_findObj(this.calId +'cboMM').value;
		var varSS=MM_findObj(this.calId +'cboSS').value;
	}
	this.doCalendar(pDate);
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=varHH;
		MM_findObj(this.calId +'cboMM').value=varMM;
		MM_findObj(this.calId +'cboSS').value=varSS;
	}	
}

//Navigating to next Year
function NextYear(){
	var nDate = new Date(this.runningDate);
	varnDays = nDate.getTime() + ONE_YEAR;
	nDate.setTime(varnDays);
	if (!this.noTime){
		var varHH=MM_findObj(this.calId +'cboHH').value;
		var varMM=MM_findObj(this.calId +'cboMM').value;
		var varSS=MM_findObj(this.calId +'cboSS').value;
	}
	this.doCalendar(nDate);
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=varHH;
		MM_findObj(this.calId +'cboMM').value=varMM;
		MM_findObj(this.calId +'cboSS').value=varSS;
	}	
}

//To Pick the Date on the click of Cell
function PickDate(theDate,cellId){
  MM_findObj(this.calId + 'hdnDate').value = theDate;
  BlinkSelected(this.calId,cellId);
}

//On Click of the ok button to admit the selected date
function OkClick(){
	this.selectedDate=new Date(MM_findObj(this.calId + 'hdnDate').value);
	if (!this.noTime){
		this.selectedDate.setHours(MM_findObj(this.calId +'cboHH').value);
		this.selectedDate.setMinutes(MM_findObj(this.calId +'cboMM').value);
		this.selectedDate.setSeconds(MM_findObj(this.calId +'cboSS').value);
	}
	if (!this.noTimezone){
		this.selectedTimezoneIndex=MM_findObj(this.calId +'cboTZ').value
	}
  this.click();
}

//On Click of the cancel button
function CancelClick(){
	MM_findObj(this.calId + 'hdnDate').value=this.selectedDate;
  this.click(true);
}

//Refreshes the time on today
function RefreshTime(calId){
	var calObj=eval(calId);
	var theDate=new Date(Date());
	if (!calObj.noTimezone){
		theDate=new Date (ZoneTime(calObj.localDate,calObj.selectedTimezoneIndex));
	}else{
		theDate=new Date(Date());
	}
	var todayNowObj=MM_findObj(calObj.calId+'todayNow');
	if (todayNowObj!=null){
		todayNowObj.innerHTML = dateWithTimezone(theDate,calObj.selectedTimezoneName);
	}
	clearTimeout(calObj.refreshTimer);
	calObj.refreshTimer=setTimeout("RefreshTime(" + calObj.calId + ")",1000);
}

//Blinking cell for selected Date
function BlinkSelected(calId,blinkId){
	var delay;
	var calObj=eval(calId);
  var blinkObj=MM_findObj(blinkId);
  var prevblinkObj=null;
	if (blinkObj!=null){
		if(blinkObj.style.visibility=='hidden'){
			(blinkObj.style.visibility='visible')
			delay=750;
		}else{
			(blinkObj.style.visibility='hidden')
			delay=250;
		}
	}
  if (calObj.prevBlinkId!=null){
    if (calObj.prevBlinkId!=blinkId){
      prevblinkObj=MM_findObj(calObj.prevBlinkId);
      if (prevblinkObj!=null){
        prevblinkObj.style.visibility='visible';
      }
    }
  }
  calObj.prevBlinkId=blinkId;
	clearTimeout(calObj.blinkTimer);
	calObj.blinkTimer=setTimeout("BlinkSelected(" + calObj.calId + ",'" + blinkId + "')",delay);	
}

//To convert the Hours to Seconds
function HHToSS(varHH){
	var varMM=(parseInt(varHH.substring(1,3)*60))+parseInt(varHH.substring(3));
	return((varHH.substring(0,1)+(varMM*60)));
} 

//To convert the Seconds to Hours 
function SSToHH(varSS){
	var varHH;
	var varMM;
	var varSign;
	varSign=((new String(varSS)).indexOf("-")>-1)?"+":"-";
	varSS=(parseInt(varSS)<0)?(parseInt(varSS)*-1):varSS;
	varMM=varSS/60;
	varHH=parseInt(varMM/60);
	varMM=new String(varMM/60);
	varMM=(varMM.indexOf(".")>-1)?(varMM.substring(varMM.indexOf("."))*60):"0";
	varMM=(varMM.length<2)?"0"+varMM:varMM;
	varHH=new String(varHH)+new String(varMM);
	varHH=varSign+((varHH.length<4)?"0"+varHH:varHH);
	return varHH;
}

//To conver the date with respect to the timezone
function ZoneTime(theDate,timezoneIndex){
		var timezone=timezoneHrs[timezoneIndex];
		var seconds = (new Date()).getTime(); 
		var diff=(theDate.getTimezoneOffset())*60000; 
    seconds=seconds + diff + (HHToSS(timezone)*1000); 
    theDate.setTime(seconds)
    return theDate;
}

//onchange of the timezone the calendar should reset
function TZOnChange(){
	this.selectedTimezoneIndex=[MM_findObj(this.calId +'cboTZ').value];
	this.selectedTimezoneName=timezoneName[this.selectedTimezoneIndex];
	var RefDate=new Date(this.localDate)
	RefDate=ZoneTime(RefDate,this.selectedTimezoneIndex);
	this.doCalendar(this.runningDate);
	if (!this.noTime){
		MM_findObj(this.calId +'cboHH').value=RefDate.getHours();
		MM_findObj(this.calId +'cboMM').value=RefDate.getMinutes();
		MM_findObj(this.calId +'cboSS').value=RefDate.getSeconds();
	}	
}

//Returns the Day (1-31)of the selected date
function GetDay(){
  if (this.selectedDate!=null){
    return this.selectedDate.getDate();
  }else{
  return 0;
  }
}

//Returns the (1-12)Month of the selected date
function GetMonth(){
  if (this.selectedDate!=null){
    return this.selectedDate.getMonth()+1;
  }else{
    return 0;
  }
}

//Returns the Year of the selected date
function GetYear(){
  if (this.selectedDate!=null){
    return ((this.selectedDate.getYear()<2000)?(1900+this.selectedDate.getYear()):(this.selectedDate.getYear()));
  }else{
    return 0;
  }
}

//Returns the Hours(0-23) of the selected date
function GetHours(){
  if (this.selectedDate!=null){
    return this.selectedDate.getHours();
  }else{
    return 0;
  }
}

//Returns the Minutes(0-59) of the selected date
function GetMinutes(){
  if (this.selectedDate!=null){
    return this.selectedDate.getMinutes();
  }else{
    return 0;
  }
}

//Returns the Seconds(0-59) of the selected date
function GetSeconds(){
  if (this.selectedDate!=null){
    return this.selectedDate.getSeconds();
  }else{
    return 0;
  }
}

//Returns the Timezone name of the selected date
function GetTimezone(){
	return this.selectedTimezoneName;
}

//Sets the Year, Month, Day, Hours, Minutes and Seconds
function SetDateTime(varYear,varMonth,varDay,varHours,varMinutes,varSeconds){
  if (this.selectedDate==null){
    this.selectedDate= new Date();
  }
  varYear=((varYear<2000)?(1900+varYear):(varYear));
	this.selectedDate.setYear(varYear);
	this.selectedDate.setMonth(varMonth-1);
  this.selectedDate.setDate(varDay);
  this.selectedDate.setHours(varHours);
  this.selectedDate.setMinutes(varMinutes);
  this.selectedDate.setSeconds(varSeconds);
}

//Sets the Timezone name
function SetTimezone(varTimezoneName){
	 this.selectedTimezoneName=varTimezoneName;
}

//Click event on ok click or cancel click
function ClickEvent(isCancel){
  if (this.onclick!=null){
		if(typeof this.onclick != "undefined"){
			eval(this.onclick);
		}
	}
  MM_findObj(this.calId + 'calendar').innerHTML = ""
  if (isCancel){
    MM_findObj(this.calId + 'txtDate').value = MM_findObj(this.calId + 'txtDate').value ;
  }else{
    MM_findObj(this.calId + 'txtDate').value = dateWithTimezone(this.selectedDate,this.selectedTimezoneName);
  }
  MM_findObj(this.calId + 'txtDate').title= MM_findObj(this.calId + 'txtDate').value ;
}

//Clear Event the onclick of clear symbol 'X'
function ClearDate(){
  if (this.onclear!=null){
		if(typeof this.onclear != "undefined"){
			eval(this.onclear);
		}
	}
  MM_findObj(this.calId + 'txtDate').value = "";
}

//Retruns the Date concated with Timezone name
function dateWithTimezone(varDate,varTimezoneName){
  var varYear=new String((varDate.getYear()<2000)?(1900+varDate.getYear()):varDate.getYear())
  varDate= new String(varDate);
  var indexOfYear=varDate.lastIndexOf(varYear);
  var indexOfTime=varDate.indexOf(":")-2;
  if(((indexOfTime-5)!=indexOfYear)){
    varDate=varDate.substring(0,indexOfTime) + varYear + varDate.substring(indexOfTime-1);
  }
  var lastIndex=varDate.lastIndexOf(":")+3;
  varTimezoneName=(varTimezoneName==null)?"":varTimezoneName;
  return varDate.substring(0,lastIndex) + " " + varTimezoneName
}

//Returns the TimezoneName of the browser
function GetBrowserTimezoneName(){
 return this.localTimezoneName;
}