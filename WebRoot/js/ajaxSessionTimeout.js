// JavaScript Document
var token;

$(document).ready(function(){
	token=getTokenEl();
	
	$.ajaxSetup({
		cache:false,
	    contentType:"application/x-www-form-urlencoded;charset=utf-8", 
	    type: "POST",
	    async:false, 
	    complete:function(XMLHttpRequest,textStatus){
    		var token=getTokenEl();
    		var _val=XMLHttpRequest.getResponseHeader("csrftoken");
    		if(_val != null){
    			$(token).val(XMLHttpRequest.getResponseHeader("csrftoken"));
    		}
	    	
	        //通过XMLHttpRequest取得响应头，sessionstatus 
	    	var requesturl=this.url;
	    	if(requesturl.indexOf("/fileserver/")!=-1){
	    		return;
	    	}
            var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");  
            var isCas=XMLHttpRequest.getResponseHeader("isCas");  
            if(!sessionstatus || sessionstatus=="timeout" || sessionstatus==""){
            	alert("页面失效，操作失败！");
        	    var strPath = window.document.location.pathname;
		        var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
		        if(isCas == "0"){
		        	window.open (postPath+"/q/login.jsp",'_top');
		        }else{
		        	window.open (postPath+"/login.jsp",'_top');
		        }
            }  
	    }  
	});
});

function getTokenEl(){
	var token=$("#_csrftoken",window.parent.document);
	if(typeof(token) == "undefined"){
		token=$("#_csrftoken",window.parent.parent.document);
		if(typeof(token) == "undefined"){
			token=$("#_csrftoken",window.parent.parent.parent.document);
			if(typeof(token) == "undefined"){
				token=$("#_csrftoken",window.parent.parent.parent.parent.document);
			}
		}
	}
	return token;
}

function checkResponse(XMLHttpRequest,textStatus){
	var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");  
    var isCas=XMLHttpRequest.getResponseHeader("isCas");  
    if(!sessionstatus || sessionstatus=="timeout" || sessionstatus==""){
    	alert("页面失效，操作失败！");
	    var strPath = window.document.location.pathname;
        var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
        if(isCas == "0"){
        	window.open (postPath+"/q/login.jsp",'_top');
        }else{
        	window.open (postPath+"/login.jsp",'_top');
        }
    }  
}