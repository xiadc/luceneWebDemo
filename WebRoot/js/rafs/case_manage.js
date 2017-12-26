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
	var userId=$("#userId").val();
	var userNo=$("#userNo").val();
	var userName=$("#userName").val();
	var createdate_min=$("#createdate_min").val();
	var createdate_max=$("#createdate_max").val();
	var caseStatus=$("#caseStatus").val();
	var controlStrategy=$("#controlStrategy").val();
	var riskType=$("#riskType").val();
	var checkResult=$("#checkResult").val();
	var callStatus=$("#callStatus").val();
	var sortStr=$("#sort_column").val();
    var data="{'pageNum':"+ (pageIndex)+ ",'numPerPage':"+pageSize+",'sortStr':'"+sortStr +"','userId':'"+userId
    	+"','userNo':'"+userNo+"','createdate_min':'"+createdate_min+"','createdate_max':'"+createdate_max
    	+"','userName':'"+userName+"','caseStatus':'"+caseStatus+"','controlStrategy':'"+controlStrategy
    	+"','riskType':'"+riskType+"','checkResult':'"+checkResult+"','callStatus':'"+callStatus+"'}";
	return eval("(" + data + ")");
}


function init_dateTable(pageIndex) {
	token=$("#_csrftoken",window.parent.parent.parent.document);
	$.ajax({
        type: "POST",  
        dataType: "json",  
        headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/rafsCaseManage/queryRafsManageCaseList.go',
        data: initQueryCondition(pageIndex),
        async:false,
        success: function(jsondata) {
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
			   					+'<input type="hidden" value="'+data.userNo+'" class="userNo" />'
			   					+'<input type="hidden" value="'+data.controlStrategy+'" id="controlStrategy" />'
			   				 +'<a href="#" id="detailOperationTrigger_'+i+'">详情<img src="'+contextPath+'/images/icons/select_arrow.png" style="margin-left:5px;width:15px;height:5px;border:0px;"/></a>'
			   					+'</div>'
			   				+'</td>'
			   				+'<td>'+data.userNo+'</td>'
			   				+'<td>'+data.userId+'</td>'
				   			+'<td>'+data.userName+'</td>' 
				   			+'<td>'+data.caseCount+'</td>'
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
        beforeSend:function(XMLHttpRequest){
     	   $("#loadingimg").show();
     	},
        error: function(){
  	       layer.msg("操作失败");
  	       $("#loadingimg").hide();
  	    }
    }); 
	$(".popcon").css("height",window.screen.availHeight*0.65-80);
	$(".scroll-pane").change_size();
	$(".jspHorizontalBar").css({"left":"1%","bottom":"10%"});
	rafsCaseTouchDiv();
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
				html.push({href: "javascript:detailCaseOperation("+index+")", text: "查看"});
				html.push({ href: "javascript:freezeBtn('1')", text: "冻结"  });
				html.push({ href: "javascript:freezeBtn('0')", text: "解冻"  });
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
//查看方法
function detailCaseOperation(i){
	$("input[name='trans_check']").each(function(){
		 $(this).attr("checked",false);              
	 });
	$("#trans_check"+i).attr("checked", true);
	$("#detailInvest").click();
}
function detailInvest(){
	var userNo=$("input[name='trans_check']:checked").siblings(".userNo").val();
	$("#typeinDetailIfram").attr("src",(contextPath+ "/rafsCaseManage/userManage.go?userNo="+userNo));
}
//冻结
function freezeBtn(controlType){
	var userNo=$("input[name='trans_check']:checked").siblings(".userNo").val();
	var controlStrategy = $("#controlStrategy").val();
	if(controlType == 0){
		if(confirm("确定要解冻此条数据？")){
			token=$("#_csrftoken",window.parent.parent.parent.document);
			$.ajax({
		        type: "POST",  
		        dataType: "json",  
		        headers:{'_requestVerificationToken':$(token).val()},
		        url: contextPath+'/rafsCaseManage/userControl.go',
		        data: eval("({'userNo':'"+userNo+ "','controlType':'"+controlType+"'})"),
		        async:false,
		        success: function(jsondata,textStatus, jqXHR) {
		        	$(token).val(jqXHR.getResponseHeader("csrftoken"));	
		        	if(jsondata.flag=='success'){
		        		layer.msg("解冻成功");
		            	$("#fancybox-close").click();
		            	init_dateTable(pageIndex);
					}else{
						layer.msg("解冻失败,"+jsondata.msg);
						$("#fancybox-close").click();
		            	init_dateTable(pageIndex);
					}
		        },
		        error: function(){
		   	       layer.msg("操作失败");
		   	       $("#loadingimg").hide();
		   	    },complete:function(){}
			});
		} else {
			$.fancybox.close();	
		}
	}else{
		if(confirm("确定要冻结此条数据？")){
			token=$("#_csrftoken",window.parent.parent.parent.document);
			$.ajax({
		        type: "POST",  
		        dataType: "json",  
		        headers:{'_requestVerificationToken':$(token).val()},
		        url: contextPath+'/rafsCaseManage/userControl.go',
		        data: eval("({'userNo':'"+userNo+ "','controlType':'"+controlType+"'})"),
		        async:false,
		        success: function(jsondata,textStatus, jqXHR) {
		        	$(token).val(jqXHR.getResponseHeader("csrftoken"));	
		        	if(jsondata.flag=='success'){
		        		layer.msg("冻结成功");
		            	$("#fancybox-close").click();
		            	init_dateTable(pageIndex);
					}else{
						layer.msg("冻结失败,"+jsondata.msg);
						$("#fancybox-close").click();
		            	init_dateTable(pageIndex);
					}
		        },
		        error: function(){
		   	       layer.msg("操作失败");
		   	       $("#loadingimg").hide();
		   	    },complete:function(){}
			});
		} else {
			$.fancybox.close();	
		}
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
