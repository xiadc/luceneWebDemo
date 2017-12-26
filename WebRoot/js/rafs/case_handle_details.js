var pageIndex = 0;
var pageSize = 15;
var contextPath = "";
$(function(){
	// 获取服务器目录
	contextPath = $('#contextPath').val();
});

function handleBtn(){
	token=$("#_csrftoken",window.parent.parent.parent.document);
	$.ajax({  
        type : "POST",  
        dataType : "json", 
        headers:{'_requestVerificationToken':$(token).val()},
        url : contextPath+'/rafsCaseManage/submitRiskCase.go',  
        data: eval("({'uuid':'"+uuid+ "'})"),   
        success : function(resp,textStatus, jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
        },
        error: function(){
   	       layer.msg("操作失败");
   	       $("#loadingimg").hide();
   	    },complete:function(){}
    });  
}
function flowBtn(){
	$("#addCaseDetailFrame").attr("src",(contextPath+ "/rafsCaseManage/toTransInfoList.go"));
}
//提交处理
function submitHandleCase(){
 	/*var recordNum = $("input[name='record_id']").length;
 	if(recordNum == 0){
 		layer.msg('请先登记处理结果!');
 		return;
 	}  
	//有且只有一条有效的登记处理结果
    var lastFlagNum = $("input[name='lastFlag'][value='1']").length;
 	if(lastFlagNum==0){
 		layer.msg('请先登记处理结果!');
 		return;
 	}
 	if(lastFlagNum > 1){
 		layer.msg('只能有一条有效的登记处理结果!');
 		return;
 	}
 	var workOver="0";
 	$("#dm_tbl1 tr .workState").each(function(){
 		if($(this).val() != "4"){
 			workOver="1";
 		}
 	});
 	if(workOver=="1"){
 		layer.msg("存在未完结工单,不允许提交!");
 		return;
 	}*/
 	
	if(window.confirm('确定要提交吗？')){
		submitHandle();
    }
	
}
function submitHandle(){
	var uuid = $("#uuid").val();
	token=$("#_csrftoken",window.parent.parent.parent.document);
	 $.ajax({
	        type: "post",
	        dataType: "json", 
	        headers:{'_requestVerificationToken':$(token).val()},
	        url: contextPath+'/rafsCaseManage/submitRiskCase.go',
	        data:{'uuid':uuid},
	        async:false,
	        success: function(strV , textStatus, jqXHR){
	        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
			    $("#loadingimg").hide();  
	        	 if(strV.flag=='success'){
		             layer.msg("操作成功"); 
	            }else{
		            layer.msg("操作失败: "+strV.msg);
	            }
		        window.parent.init_dateTable(pageIndex);
		        setTimeout(function(){
			    	cancelRiskInfoDetail();
				},1000);
	        },  
	        beforeSend:function(XMLHttpRequest){
	        $("#loadingimg").show(); 
         },
	  error: function(){
	        layer.msg("操作失败");
	        $("#loadingimg").hide(); 
	    } ,complete:function(){}
     });  
}
//提交审核
function submitReviewCase(){
 	/*var recordNum = $("input[name='record_id']").length;
 	if(recordNum == 0){
 		layer.msg('请先登记处理结果!');
 		return;
 	}  
	//有且只有一条有效的登记处理结果
    var lastFlagNum = $("input[name='lastFlag'][value='1']").length;
 	if(lastFlagNum==0){
 		layer.msg('请先登记处理结果!');
 		return;
 	}
 	if(lastFlagNum > 1){
 		layer.msg('只能有一条有效的登记处理结果!');
 		return;
 	}
 	var workOver="0";
 	$("#dm_tbl1 tr .workState").each(function(){
 		if($(this).val() != "4"){
 			workOver="1";
 		}
 	});
 	if(workOver=="1"){
 		layer.msg("存在未完结工单,不允许提交!");
 		return;
 	}*/
 	
	if(window.confirm('确定要提交吗？')){
		submitReview();
    }
	
}
function submitReview(){
	var uuid = $("#uuid").val();
	var auditRemark = $("#auditRemark").val();
	token=$("#_csrftoken",window.parent.parent.parent.document);
	 $.ajax({
	        type: "post",
	        dataType: "json", 
	        headers:{'_requestVerificationToken':$(token).val()},
	        url: contextPath+'/rafsCaseManage/submitRiskCase.go',
	        data:{'uuid':uuid,'auditRemark':auditRemark},
	        async:false,
	        success: function(strV , textStatus, jqXHR){
	        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
			    $("#loadingimg").hide();  
	        	 if(strV.flag=='success'){
		             layer.msg("操作成功"); 
	            }else{
		            layer.msg("操作失败: "+strV.msg);
	            }
		        window.parent.init_dateTable(pageIndex);
		        setTimeout(function(){
			    	cancelRiskInfoDetail();
				},1000);
	        },  
	        beforeSend:function(XMLHttpRequest){
	        $("#loadingimg").show(); 
         },
	  error: function(){
	        layer.msg("操作失败");
	        $("#loadingimg").hide(); 
	    } ,complete:function(){}
     });  
}
//驳回
function rejectRafsBySubmit(){
	var uuid = $("#uuid").val();
	var caseno = $("#caseno").val();
	
	
	if(window.confirm('确定要驳回吗？')){
		token=$("#_csrftoken",window.parent.parent.parent.document);
		//执行退回动作，刷新页面
		 $.ajax({  
		        type: "post",
		        dataType:"json",  
		        headers:{'_requestVerificationToken':$(token).val()},
		        url: contextPath+ "/rafsCaseManage/caseReject.go",  
		        data:{'uuid':uuid,'caseno':caseno},
		        async:false,
		        success: function(strV , textStatus, jqXHR){
		        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
				    $("#loadingimg").hide();  
		            if(strV.flag=='success'){
			             layer.msg("操作成功"); 
		            }else{
			             layer.msg("操作失败"+strV.msg); 
		            }
			        window.parent.init_dateTable(0);
			      	setTimeout(function(){
				    	cancelRiskInfoDetail();
					},1000);
		        },  
		        beforeSend:function(XMLHttpRequest){
		        	$("#loadingimg").show(); 
		        },
				error: function(){
			        layer.msg("操作失败");
			        $("#loadingimg").hide(); 
			    } ,complete:function(){}
			});  
	}
}

/**
 *取消按钮
 */
function cancelRiskInfoDetail() {
	try{
		parent.$.fancybox.close();
	}catch(e){layer.msg(e);}
	
}
