// JavaScript Document
$.fn.extend({ 
	"change_size":function(){
		$(this).jScrollPane({mouseWheelSpeed:80});
		var barObj = $(".jspHorizontalBar");//解决查询页面滚动条位置不对Bug
		var tbObj = $(".lay_wrap");
		if($(tbObj).attr("class")!=undefined){
			$(barObj).css("left",$(tbObj).offset().left+7);
			setTimeout(function(){
				$(barObj).css("left",$(tbObj).offset().left+7);
			},10);	
		}
	}
}); 

$.fn.serializeObject = function(){  
   var o = {};  
   var a = this.serializeArray();  
   $.each(a, function() {  
       if (o[this.name]) {  
           if (!o[this.name].push) {  
               o[this.name] = [o[this.name]];  
           }  
           o[this.name].push(this.value || '');  
       } else {  
           o[this.name] = this.value || '';  
       }  
   });  
   return o;  
};  
$(function(){
	
	//滚动条 
	$(".scroll-pane").change_size();//调用滚动条方法

	var popbottomObj = $(".popbottom");//审阅页面底部按钮绝对居底
	if($(popbottomObj).attr("class")!=undefined){
		$(popbottomObj).css({"position":"absolute","bottom":"0px","width":$("#inline1").width(),"padding-right":"0px"});
		if(null==$("#inline1").width() || undefined==$("#inline1").width()){
			var length = $("[id^=inline]").length;
			var inlineObj = $("[id^=inline]:eq("+(length-1)+")");
			$(popbottomObj).css({"position":"absolute","bottom":"0px","width":inlineObj.width(),"padding-right":"0px"});
			if(null==inlineObj.width() || undefined==inlineObj.width()){
				$(popbottomObj).css("width","100%");
			}
		}
		$(".popbottom .fancybox-close").css("margin-right","15px");
	}
	$("[id^=inline] iframe").css("height",window.screen.availHeight*0.75);
	//结束---------------------	 

    $(window).resize(function(){ 
		$(".scroll-pane").change_size();
	}); 
    
	//遮罩层
	$(".various1,.various2,.variousdel,.confirmsMark").fancybox({
		'overlayColor'      : '#444',
		'titlePosition'		: 'inside',
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'hideOnOverlayClick':false,
		'enableEscapeButton':false,
		enableEscapeButton	:true
	});
	$(".fancybox-close").click(function(){
		$("#fancybox-close").click();
	});
	$(".various1").click(function(){
		$(".scroll-pane").change_size();
	});
	//loading 加载图片
	$("body").append("<div id='loadingimg'><div></div></div>");
	/*var loadingimgindex=1,loadingimgTimer=setInterval(function(){
		$("#loadingimg div").css("top",loadingimgindex*-40+"px");
		loadingimgindex = (loadingimgindex + 1) % 12;
	},80);*/ 
    //删除
	var $prompt=$("#confirm .prompt").text();
	$(".variousdel").click(function(e){
		if($(this).parents("#wrapEdit").siblings(".wraptable").find(".table input:checkbox:checked").length>=1){
			if($(this).hasClass("reset")){
				$("#confirm .prompt").text("您确定要重置选中用户的密码吗？"); 
			}else if($(this).hasClass("enableUser")){
				$("#confirm .prompt").text("您确定要解锁选中的用户吗？"); 
			}else{
				$("#confirm .prompt").text($prompt); 
			}
			$("#confirm .wrapInput").css("display","block");
		}else{
			$("#confirm .prompt").text("至少选择一条数据");
			$("#confirm .wrapInput").css("display","none");
			setTimeout(function(){
				$("#fancybox-close").click();
			},500);
		}
	});
	//biunique
	$(".biunique").click(function(){
		var checkbox=$(this).parents("#wrapEdit").siblings(".wraptable").find(".table input:checkbox:checked").length;
		if(checkbox==0){
			$("#confirms .prompt").text("请选择一条数据");
			$(".confirmsMark").click();
			setTimeout(function(){
				$("#fancybox-close").click();
			},500);
		}else if(checkbox>1){
			$("#confirms .prompt").text("最多只能选择一条数据");
			$(".confirmsMark").click();
			setTimeout(function(){
				$("#fancybox-close").click();
			},500);
		}else{
			$(this).siblings("a").click();
		}
	}) ;
	//biunique2
	$(".biunique2").click(function(){
		var checkbox=$(this).parents("#wrapEdit2").siblings(".wraptable").find(".table input:checkbox:checked").length;
		if(checkbox==0){
			$("#confirms .prompt").text("请选择一条数据");
			$(".confirmsMark").click();
			setTimeout(function(){
				$("#fancybox-close").click();
			},500);
		}else if(checkbox>1){
			$("#confirms .prompt").text("最多只能选择一条数据");
			$(".confirmsMark").click();
			setTimeout(function(){
				$("#fancybox-close").click();
			},500);
		}else{
			$(this).siblings("a").click();
		}
	}) ;
	
	
	//leastOne
	$(".leastOne").click(function(){
		var checkbox=$(this).parents("#wrapEdit").siblings(".wraptable").find(".table input:checkbox:checked").length;
		if(checkbox==0){
			$("#confirms .prompt").text("请选择一条数据");
			$(".confirmsMark").click();
			setTimeout(function(){
				$("#fancybox-close").click();
			},500);
		}else{
			$(this).siblings("a").click();
		}
	}) ;
	//重置表单
	$(".formReset").click(function(){
		$(this).parents("#wrapEdit").siblings(".operate").find(".formbox")[0].reset();
	});
	//时间控件
	$( ".from_date" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true, 
	  changeYear: true,	 
      closeText: '关闭',
      currentText:'今天',
      numberOfMonths: 1,//显示 n个月的选择 
	  showButtonPanel: true,//按钮底部按钮显示
	  dateFormat:"yy-mm-dd",
	  showAnim:"slideDown",
	  selectCurrent:true,
      onClose: function( selectedDate ) {
       $( ".to_date" ).datepicker( "option", "minDate", selectedDate );
      }//起始日期
    });
    $( ".to_date" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
	  changeYear: true,
	  closeText: '关闭',
      currentText:'今天',
      numberOfMonths: 1,
	  showButtonPanel: true,//按钮底部按钮显
	  dateFormat:"yy-mm-dd",
	  showAnim:"slideDown",
	  selectCurrent:true,
      onClose: function( selectedDate ) {
        $( ".from_date" ).datepicker( "option", "maxDate", selectedDate );
      }//终止日期
    });
	$( ".from_date2" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true, 
	  changeYear: true,
	  closeText: '关闭',
      currentText:'今天',
      numberOfMonths: 1,//显示 n个月的选择 
	  showButtonPanel: true,//按钮底部按钮显示
	  dateFormat:"yy-mm-dd",
	  showAnim:"slideDown",
	  selectCurrent:true,
      onClose: function( selectedDate ) {
       $( ".to_date2" ).datepicker( "option", "minDate", selectedDate );
      }//起始日期
    });
    $( ".to_date2").datepicker({
      defaultDate: "+1w",
      changeMonth: true,
	  changeYear: true,
	  closeText: '关闭',
      currentText:'今天',
      numberOfMonths: 1,
	  showButtonPanel: true,//按钮底部按钮显
	  dateFormat:"yy-mm-dd",
	  showAnim:"slideDown",
	  selectCurrent:true,
      onClose: function( selectedDate ) {
        $( ".from_date2" ).datepicker( "option", "maxDate", selectedDate );
      }//终止日期
    });
	$( ".single_date" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
	  changeYear: true,
	  closeText: '关闭',
      currentText:'今天',
      numberOfMonths: 1,
      showAnim:"slideDown",
	  selectCurrent:true,
	  showButtonPanel: true,//按钮底部按钮显
	  dateFormat:"yy-mm-dd",
    }); 
	$( ".single_date_yymm" ).datepicker({
	      defaultDate: "+1w",
	      changeMonth: true,
		  changeYear: true,
		  closeText: '关闭',
	      currentText:'今天',
	      numberOfMonths: 1,
	      showAnim:"slideDown",
		  selectCurrent:true,
		  showButtonPanel: true,//按钮底部按钮显
		  dateFormat: 'yy-mm-dd',
		  onClose: function(dateText, inst) {
              var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
              var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
              $(this).datepicker('setDate', new Date(year, month, 1));
          }
	    }); 
	$(".quartz_date_yymm" ).datepicker({
	      defaultDate: "+1w",
	      changeMonth: true,
		  changeYear: true,
		  closeText: '关闭',
	      currentText:'今天',
	      numberOfMonths: 1,
	      showAnim:"slideDown",
		  selectCurrent:true,
		  showButtonPanel: true,//按钮底部按钮显
		  dateFormat: 'yy-mm',
		  onClose: function(dateText, inst) {
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker('setDate', new Date(year, month, 1));
        }
	}); 
	
	/**
	 * 日期控件【Backspace】删除选中日期，并且禁用回退功能
	 */
	$( ".from_date,.to_date,.from_date2,.to_date2,.single_date,.single_date_yymm,.quartz_date_yymm").keydown(function(event){
		if(event.keyCode == '8') {
			$(this).val('');
			return false;
		}
	});
	//全选按钮
	$(".table tr").live('click',function(){
		if($(this).find("input:checkbox").length==0) return false;
		$(this).find("input:checkbox").attr("checked",!$(this).find("input:checkbox").attr("checked"));
		$(this).toggleClass("on");
		var flag=true;
		$(this).parents(".table").find("input:checkbox").each(function() {
            if(!$(this).attr("checked")){
				flag=false;
				return false;
			}
        });
		var chooseAll=$(this).parents(".wraptable").siblings("#wrapEdit").find(".chooseAll");
		if(flag){
			chooseAll.attr("checked",true);
		}else{
			chooseAll.attr("checked",false);
		}
	});
	$(".chooseAll").click(function(e){
		var $tableBox=$(this).parents("#wrapEdit").siblings(".wraptable").find(".table");
		if($(this).attr("checked")){
			$tableBox.find("tr input:checkbox").attr("checked",true);
			$tableBox.find("tr").addClass("on");
		}else{
			$tableBox.find("tr input:checkbox").attr("checked",false);
			$tableBox.find("tr").removeClass("on");
		}
		e.stopPropagation();
	});
	$(".chooseAllCopy").click(function(){
		$(this).find(".chooseAll").attr("checked",!$(this).find(".chooseAll").attr("checked"));
		var $tableBox=$(this).parents("#wrapEdit").siblings(".wraptable").find(".table");
		if($(this).find(".chooseAll").attr("checked")){
			$tableBox.find("tr input:checkbox").attr("checked",true);
			$tableBox.find("tr").addClass("on");
		}else{
			$tableBox.find("tr input:checkbox").attr("checked",false);
			$tableBox.find("tr").removeClass("on");
		}
	});
	$(".table tr input:checkbox").live('click',function(e){
		$(this).parents("tr").toggleClass("on");
		e.stopPropagation();
	});
});

function validateIdCode(code) {
	var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
	var tip = "";
	var pass= true;
	
	 if(!code || !/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(code)){
		 tip = "身份证号格式错误";
		 pass = false;
	 }else if(!city[code.substr(0,2)]){
		 tip = "地址编码错误";
		 pass = false;
	 }else{
		 //18位身份证需要验证最后一位校验位
		if(code.length == 18){
			 code = code.split('');
			 //∑(ai×Wi)(mod 11)
			 //加权因子
			 var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			 //校验位
			 var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			 var sum = 0;
			 var ai = 0;
			 var wi = 0;
			 for (var i = 0; i < 17; i++){
				 ai = code[i];
				 wi = factor[i];
				 sum += ai * wi;
			 }
			 //var last = parity[sum % 11];
			 if(parity[sum % 11] != code[17]){
				 tip = "校验位错误";
				 pass =false;
			 }
		}
	}
	// if(!pass) alert(tip);
	 return pass;
 }

function strTrimToLen(string,length){
	if(typeof(string) == "undefined"){
		return "";
	}else{
		return (string.length>length)?(string.substring(0,length-1)+"..."):string;
	}
}


function Reg(str, rStr){
    var reg = new RegExp(rStr);
    if(reg.test(str)){
    	return true;
    }else{
    	return false;
    }
}
function validatePasswd(str){
   var rC = {lW:'[a-z]',uW:'[A-Z]',nW:'[0-9]',sW:'[~!@#$^*()=|{}:;+,\\[\\].<>/?~！@#￥……&*（）—|{}【】‘；：”“。，、？]'};
   if(str.length < 8 || str.length>16){
        alert("密码长度为8-16位!");
        return false;
    }else{
        var tR = {
            l:Reg(str, rC.lW),
            u:Reg(str, rC.uW),
            n:Reg(str, rC.nW),
            s:Reg(str, rC.sW)
        };
        if((tR.l && tR.u && tR.n) || (tR.l && tR.u && tR.s) || (tR.s && tR.u && tR.n) || (tR.s && tR.l && tR.n) || (tR.u && tR.s && tR.l && tR.n)){
        	return true;
        }else{
        	alert('您的密码必须含有（数字、大写字母、小写字母和特殊字符）中的任意三种!');
            return false;
        }
    }
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
           // + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
    return currentdate;
} 
