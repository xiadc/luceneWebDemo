var pageIndex = 0;
var pageSize = 10;
var contextPath = "";
$(function(){
	// 获取服务器目录
	contextPath = $('#contextPath').val();
});

function pageCallback(index, jq) {  
	init_dateTable(index);  
}  

//点击下载响应函数
function download(filename){
	location = contextPath + "/DownloadFileServlet?filename="+filename;
	
}

function initQueryCondition(pageIndex){
	//var indexPath = $("#indexPath").val();
	var title = $("#title").val();
	var contents = $("#contents").val();	
	var titleSuffix =  $("input[name='titleSuffix']:checked").val();
	//var ifAnd = $("#ifAnd").val();
	var sortStr=$("#sort_column").val();
	
    var data="{'pageNum':"+ (pageIndex)+ ",'numPerPage':"+pageSize+",'sortStr':'"+sortStr +"','title':'"+title+"','contents':'"+contents+"','titleSuffix':'"+titleSuffix
    	+"'}";
	return eval("(" + data + ")");
}


function init_dateTable(pageIndex) {
	/*token=$("#_csrftoken",window.parent.parent.parent.document);
	if(typeof(token) == "undefined"){
		token=$("#_csrftoken",window.parent.parent.parent.parent.document);
	}*/
	//var indexPath = $("#indexPath").val();
	var title = $("#title").val();
	var contents = $("#contents").val();
	var titleSuffix =  $("input[name='titleSuffix']:checked").val();	
	if((title===''||title===null)&&(contents===''||contents===null)){
		alert("搜索关键字不能全为空！");
		return;
	}

	$.ajax({
        type: "POST",  
        dataType: "json",  
      //  headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/SearchFileServlet',
        data: initQueryCondition(pageIndex),
        async:false,
        success: function(jsondata,textStatus,jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
        	if(jsondata.error_msg!=null&&jsondata.error_msg!=""){
        		 layer.msg("操作失败!"+jsondata.error_msg);
        	}else if (jsondata[0] == null || jsondata[0].totalCount==0) {
            	$("#caseTrans_table tr:gt(0)").remove();
				$("#Pagination").pagination(0, {
           	        callback: pageCallback,
           	        prev_show_always:false,
           	        next_show_always:false,
           	        current_page: 0
           	   });
				$("#loadingimg").hide();
            	return;
			} else {
				var tableData = jsondata[0].result;
				var html='';
				$.each(tableData, function (i, data) {
			   		html+='<tr>'
			   				+'<td>'+data.titleWithCss+'</td>'
				   			+'<td>'+data.path+'</td>' 
			   				+'<td>'+data.lastModifiedDesc+'</td>'			   		   						
			   				//+'<td><button href="/luceneWebDemo/DownloadFileServlet?filename='+ data.title+'">点击下载</button></td>'	 
			   				+'<td><button  onclick="download(\''+data.title +'\')">点击下载</button></td>'	 
			        	+'</tr>';
			   		/*$('<p/>').html('<a href="/luceneWebDemo/DownloadFileServlet?filename='+ data.title+'">点击下载</a>').appendTo(document.body);*/ 
				});
				
				$("#caseTrans_table tr:gt(0)").remove();   
				$("#caseTrans_table").append(html);
				
			 	var json = jsondata[0];
				$("#caseInfo_pageNum").val(jsondata.pageNum);
				
				$("#Pagination").pagination(json.totalCount, {
           	        callback: pageCallback,
           	        prev_text: "<< 上一页",
                    next_text: "下一页 >>",
           	        items_per_page:pageSize, // Show only one item per page
           	        num_edge_entries: 2,       //两侧首尾分页条目数
                    num_display_entries: 6,
           	        current_page: pageIndex,   //当前页索引
           	   });
				
				$("#Pagination").show();
				$(".wraptable .jspContainer").height($("#caseTrans_table").outerHeight());
               	$("#loadingimg").hide();
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

function toCreateIndexJsp(){
	location = contextPath + "/createIndex.jsp";
}


	

