      DEFAULT_PG_PATEL_WIDTH=1;
      DEFAULT_PGBAR_INTERVAL=1000;
      DEFAULT_PATEL_COLOR='blue';
      DEFAULT_PATEL_SEPERATION=1;
      
      /*
      Contructor to Construct the Progress Bar control object
      @param pgBarId - Acts as Id as well as control object(instance) - i.e. both should be same
      @param height - Height of the Color Palette control object
      @param width - Width of the Color Palette control object
      */
      function ProgessBar(pgBarId,width,height){
        this.pgBarTimer=null;	
        this.pgBarId=pgBarId;
        this.height=height;
        this.width=width;
        this.renderPgBar=drawPgBar;
        this.start=starPgBar;
        this.stop=endPgBar;
        this.pbBarClassName=null;
        this.patelClassName=null;
        this.pgPatelWidth=DEFAULT_PG_PATEL_WIDTH;
        this.progressAt=0;
        this.progressEnd=0;
        this.refresh=progress_refresh;
        this.patelColor=DEFAULT_PATEL_COLOR;
        this.pgBarInterval=DEFAULT_PGBAR_INTERVAL;
        this.continuous=false
        this.seperation=DEFAULT_PATEL_SEPERATION;

        /* Need To Work on these Properties 
        
        this.pgBarBorderColor=null;
        this.pgBarBorderStyle=null;
        this.pgBarBorderWeight=null;
        this.patelBorderColor=null;
        this.patelBorderStyle=null;
        this.patelBorderWeight=null;
        
        */
        

      }

      function drawPgBar(){
        var pgBarId=this.pgBarId;
        var height=this.height;
        var width=this.width;
        var pgPatelWidth=this.pgPatelWidth;
        var pgPatelHeight=height;
        var noOfPgPatels;
        var patelSeperation=this.seperation;
        var isContinuous=this.continuous;
        var pgBarClassName=this.pbBarClassName;
        var patelClassName=this.patelClassName;
        
        if(isContinuous){
          patelSeperation=0;
          this.seperation=0;
        }
        
        noOfPgPatels=(width/pgPatelWidth);
        pgPatelWidth=pgPatelWidth-patelSeperation;
        this.progressEnd=noOfPgPatels;

        
        
        document.write('<div id="' + pgBarId + '" style="margin:0px;height:' + height + 'px;width:' + width +'px; "');
        if(pgBarClassName!=null){
          document.write(' class="' + pgBarClassName + '"');
        }
        document.write(' >');
        for(var pgPatelCount=0; pgPatelCount<noOfPgPatels; pgPatelCount++){
          document.write('<div id="' + pgBarId + 'patel'+ (pgPatelCount+1)+'" style="height:' + pgPatelHeight + '; width:' + pgPatelWidth + '; margin-left:'+ patelSeperation + 'px;float:left; display:none;')
          if(patelClassName!=null){ 
            document.write('" class="' + patelClassName + '"');
          }else{
            document.write(' background-color:'+ this.patelColor + ';"');
          }
          document.write(' > </div>');
        }
        document.write('</div>');
      }

      function starPgBar(){
        runPgBar(this.pgBarId);
      }

      function runPgBar(varPgBarId){
        objPgBar=eval(varPgBarId); 
        objPgBar.progressAt++;
        if (objPgBar.progressAt > objPgBar.progressEnd){ 
          objPgBar.refresh();
        }else {
          var strPgBarPatel = objPgBar.pgBarId + 'patel'+ objPgBar.progressAt;
          var objPgBarPatel = MM_findObj(strPgBarPatel);
          
          if (objPgBarPatel!=null){
            objPgBarPatel.style.display='';
          }
        }
        if( this.seperation!=0 ){
          objPgBar.pgBarTimer = setTimeout('runPgBar(' + objPgBar.pgBarId + ')',objPgBar.pgBarInterval);
        }
      }

      function progress_refresh() {
        for (var i = 1; i <= this.progressEnd; i++){ 
          var strPgBarPatel = this.pgBarId+'patel'+i;
          var objPgBarPatel = MM_findObj(strPgBarPatel);
            if (objPgBarPatel!=null){
              objPgBarPatel.style.display= 'none';
            }
        }
        this.progressAt = 0;
      }


      function endPgBar(){
        clearTimeout(this.pgBarTimer);
        this.refresh();      
      }


