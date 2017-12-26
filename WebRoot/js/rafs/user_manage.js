var pageIndex = 0;
var pageSize = 15;
var contextPath = "";
$(function(){
	// 获取服务器目录
	contextPath = $('#contextPath').val();
	$(".lowScoreTab span").click(
			function() {
				var index = $(this).index();
				$(this).addClass("on").siblings().removeClass("on");
				$(this).parent().siblings(".lowWrapTab").eq(index).css({"display":"block"}).siblings(".lowWrapTab").css("display", "none");
				$(".scroll-pane").change_size();
		});

	initUser();
	$("#index3").on("click",function(){
		init_dateTable(pageIndex);
	});
	$("#index4").on("click",function(){
		init_record_dateTable(pageIndex);
	});
});
function pageCallback(index, jq) {  
	init_dateTable(index);  
}  
function initUser(){
	var userNo=$("#userNo").val();
	token=$("#_csrftoken",window.parent.parent.parent.document);
	$.ajax({  
        type : "POST",  
        dataType : "json", 
        headers:{'_requestVerificationToken':$(token).val()},
        url : contextPath+'/rafsCaseManage/queryRafsUserInfo.go',  
        data: eval("({'userNo':'"+userNo+ "'})"),   
        success : function(resp,textStatus, jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
        },
        error: function(){
   	       layer.msg("操作失败");
   	       $("#loadingimg").hide();
   	    },complete:function(){}
    });  
}

function init_dateTable(pageIndex) {
	var userNo=$("#userNo").val();
	token=$("#_csrftoken",window.parent.parent.parent.document);
	$.ajax({
        type: "POST",  
        dataType: "json",  
        headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/rafsCaseManage/queryRafsUserCaseInfo.go',
        data: eval("({'userNo':'"+userNo+ "'})"),
        async:false,
        success: function(jsondata,textStatus, jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
			if (jsondata[0] == null || jsondata[0].totalCount==0 || jsondata.error_msg!=null) {
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
			   		html+='<tr><td><div>'
			   			+'<input type="checkbox" style="display:none"value="'+data.uuid+'" name="trans_check" id="trans_check'+i+'"/>'
		   				 +'<a href="#" id="detailOperationTrigger_'+i+'">详情<img src="'+contextPath+'/images/icons/select_arrow.png" style="margin-left:5px;width:15px;height:5px;border:0px;"/></a>'
			   					+'</div>'
			   				+'</td>'
			   				+'<td>'+data.caseno+'</td>'
			   				+'<td>'+data.userNo+'</td>'
			   				+'<td>'+data.userId+'</td>'
			   				+'<td>'+data.userName+'</td>'
			   				+'<td>'+data.createDate+'</td>'
			   				+'<td>'+data.status+'</td>'
				   			+'<td>'+data.overDate+'</td>' 
			        	+'</tr>';
				});
				$("#caseTrans_table tr:gt(0)").remove();   
				$("#caseTrans_table").append(html);
				
			 	
			  	var json = jsondata[0];
			  	$("#caseInfo_pageNum").val(json.pageNum);
				
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
        error: function(){
  	       layer.msg("操作失败");
  	       $("#loadingimg").hide();
  	    },complete:function(){}
    }); 
	$(".popcon").css("height",window.screen.availHeight*0.65-80);
	$(".scroll-pane").change_size();
	$(".jspHorizontalBar").css({"left":"1%","bottom":"10%"});
	rafsCaseTouchDiv();
}
function init_record_dateTable(pageIndex) {
	var userNo=$("#userNo").val();
	token=$("#_csrftoken",window.parent.parent.parent.document);
	$.ajax({
        type: "POST",  
        dataType: "json",  
        headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/rafsCaseManage/queryUserControls.go',
        data: eval("({'userNo':'"+userNo+ "'})"),
        async:false,
        success: function(jsondata,textStatus, jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
			if (jsondata[0] == null || jsondata[0].totalCount==0 || jsondata.error_msg!=null) {
            	$("#caseTrans_table1 tr:gt(0)").remove();
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
			   		html+='<tr><td><div>'
			   			+'<input type="checkbox" style="display:none"value="'+data.uuid+'" name="trans_check" id="trans_check'+i+'"/>'
			   					+'</div>'
			   				+'</td>'
			   				+'<td>'+data.userId+'</td>'
			   				+'<td>'+data.userNo+'</td>'
			   				+'<td>'+data.userName+'</td>'
			   				+'<td>'+data.controlStrategy+'</td>'
			   				+'<td>'+data.operTime+'</td>'
				   			+'<td>'+data.operator+'</td>' 
			        	+'</tr>';
				});
				$("#caseTrans_table1 tr:gt(0)").remove();   
				$("#caseTrans_table1").append(html);
				
			 	
			  	var json = jsondata[0];
			  	$("#caseInfo_pageNum").val(json.pageNum);
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
				$(".wraptable .jspContainer").height($("#caseTrans_table1").outerHeight());
               	$("#loadingimg").hide();
			}
			
        },
        error: function(){
  	       layer.msg("操作失败");
  	       $("#loadingimg").hide();
  	    },complete:function(){}
    }); 
	$(".popcon").css("height",window.screen.availHeight*0.65-80);
	$(".scroll-pane").change_size();
	$(".jspHorizontalBar").css({"left":"1%","bottom":"10%"});
}

function rafsCaseTouchDiv(){	
	$("#caseTrans_table").find("a[id^=detailOperationTrigger_]").each(function(index){
		$(this).powerFloat({ 
			width: 100, 
			offsets: {
	            x: 0,
	            y: -1    
	        },
			target: function(){
				var html=[];
				html.push({href: "javascript:detailCaseOperation("+index+")", text: "案例查看"});
				html.push({href: "javascript:detailCaseOperation("+index+")", text: "案例处理"});
				html.push({href: "javascript:detailCaseReview("+index+")", text: "案例审核"});
				return html;
			},
          targetMode: "list" });
	});
	$("#caseTrans_table").find("a[id^=investRiskTypeTrigger_]").each(function(index){
		if("" != $(this).text().trim()){
			$(this).powerFloat({
				targetMode:"remind",
				target:$("#investRiskTypeBox_"+index).html(),
				width: 150,
				position: "5-5"
			});
		}
	});
	
}
function detailCaseOperation(i){
	$("input[name='trans_check']").each(function(){
		 $(this).attr("checked",false);              
	 });
	$("#trans_check"+i).attr("checked", true);
	$("#detailInvest").click();
}
function detailInvest(){
	var uuid=$("input[name='trans_check']:checked").val();
	$("#typeinDetailIfram").attr("src",(contextPath+ "/rafsCaseManage/caseHandleDetails.go?caseUuid="+uuid));
}
function detailCaseReview(i){
	$("input[name='trans_check']").each(function(){
		 $(this).attr("checked",false);              
	 });
	$("#trans_check"+i).attr("checked", true);
	$("#detailReview").click();
}
function detailReview(){
	var uuid=$("input[name='trans_check']:checked").val();
	$("#reviewDetailIfram").attr("src",(contextPath+ "/rafsCaseManage/caseAudit.go"));
}
function flowBtn(){
	$("#addCaseDetailFrame").attr("src",(contextPath+ "/rafsCaseManage/toTransInfoList.go"));
}
/**
 *取消按钮
 */
function cancelRiskInfoDetail() {
	try{
		parent.$.fancybox.close();
	}catch(e){layer.msg(e);}
	
}
