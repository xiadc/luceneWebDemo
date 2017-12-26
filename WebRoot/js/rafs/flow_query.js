var pageIndex = 0;
var pageSize = 15;
var contextPath = "";
$(function(){
	// 获取服务器目录
	contextPath = $('#contextPath').val();
});

function pageCallback(index, jq) {  
	init_dateTable(index);  
}  

function initQueryCondition(pageIndex){
	var payCustUserId=$("#payCustUserId").val();
	var payCustCardNo=$("#payCustCardNo").val();
	var createdate_min=$("#createdate_min").val();
	var createdate_max=$("#createdate_max").val();
	var sortStr=$("#sort_column").val();
    var data="{'pageNum':"+ (pageIndex)+ ",'numPerPage':"+pageSize+",'sortStr':'"+sortStr +"','payCustUserId':'"+payCustUserId
    	+"','payCustCardNo':'"+payCustCardNo+"','createdate_min':'"+createdate_min+"','createdate_max':'"+createdate_max
    	+"'}";
	return eval("(" + data + ")");
}


function init_dateTable(pageIndex) {
	/*token=$("#_csrftoken",window.parent.parent.parent.document);
	if(typeof(token) == "undefined"){
		token=$("#_csrftoken",window.parent.parent.parent.parent.document);
	}*/
	var indexPath = $("#indexPath").val();
	var docsPath = $("#docsPath").val();
	if(indexPath===''||indexPath===null){
		alert("索引存储目录不能为空！");
		return;
	}else if(docsPath===''||docsPath===null){
		alert("索引文件（夹）不能为空！");
		return;
	}
	var ifCreate = $("#ifCreate").val();
	$.ajax({
        type: "POST",  
        dataType: "json",  
      //  headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/CreateIndexServlet',
        data: {"indexPath":indexPath,"docsPath":docsPath,"ifCreate":ifCreate},
        async:false,
        success: function(strV,textStatus,jqXHR) {
        		if(strV.flag=='success'){
        			$("#returnMsg").val(strV.returnMsg);
        			layer.msg("操作成功");
        			
        		}else{
        			layer.msg("操作失败: "+strV.msg);
        			$("#returnMsg").val("");
        		}	
        		
        },
        beforeSend:function(XMLHttpRequest){
     	   $("#loadingimg").show();
     	},
        error: function(){
  	       layer.msg("操作失败");
  	       $("#loadingimg").hide();
  	    },complete:function(){}
    }); 
	$(".popcon").css("height",window.screen.availHeight*0.65-80);
	$(".scroll-pane").change_size();
	$(".jspHorizontalBar").css({"left":"1%","bottom":"10%"});
	$("#loadingimg").hide();
}

/**
 *取消按钮
 */
function cancelRiskInfoDetail() {
	try{
		parent.$.fancybox.close();
	}catch(e){layer.msg(e);}
	
}
/*排序*/
function sortData(el,columnVal){
	var sortType=$(el).find("span").html();
	$("#caseTrans_table").find(".sortColumn").find("span").each(function(index){
		$(this).html("&darr;&uarr;");
	});
	if(sortType=="↓↑"){
		$(el).find("span").html("&darr;");
		$("#sort_column").val(columnVal+" desc");
	}else if(sortType=="↓"){
		$(el).find("span").html("&uarr;");
		$("#sort_column").val(columnVal+" asc");
	}else if(sortType=="↑"){
		$(el).find("span").html("&darr;&uarr;");
		$("#sort_column").val("");
	}
	init_dateTable(pageIndex);
}
