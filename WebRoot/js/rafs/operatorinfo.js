var pageIndex = 0;
var pageSize = 15;
var contextPath = "";
$(function(){
	// 获取服务器目录
	contextPath = $('#contextPath').val();
	init_dateTable(pageIndex);
});

function pageCallback(index, jq) {  
	init_dateTable(index);  
}  

function initQueryCondition(pageIndex){
	var  operatorNo = $('#operatorNo').val(); 
    var status = $('#status').val(); 
    var role = $('#role').val()
    var data="{'pageNum':"+ (pageIndex)+ ",'numPerPage':"+pageSize+",'operatorNo':'"+operatorNo +"','status':'"+status
    	+"','role':'"+role+"'}";
	return eval("(" + data + ")");
}

function init_dateTable(pageIndex) {
	var data = initQueryCondition(pageIndex);
	if(!data){return;}
	token=$("#_csrftoken",window.parent.parent.parent.document);
	
	$.ajax({
        type: "POST",  
        dataType: "json",  
        headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/rafsOperatorInfo/queryRafsOperatorInfo.go',
        data: data,
        async:false,
        success: function(jsondata) {
        	//查询结构为0条 记录
        	if (jsondata[0] == null || jsondata[0].totalCount==0 || jsondata.error_msg!=null) {
            	$("#Result tr:gt(0)").remove();
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
			   		html+='<tr><td>'
			   					+'<input type="checkbox" value="'+data.operatorNo+'" name="rafsOperatorCheck" id="rafsOperatorCheck'+i+'"/>'
			   				+'</td>'
			   				+'<td>'+data.operatorNo+'</td>'
				   			+'<td>'+data.statusDesc+'</td>' 
			   				+'<td>'+data.role+'</td>'
			        	+'</tr>';
				});
				$("#Result tr:gt(0)").remove();   
				$("#Result").append(html);
				
			 	
			  	var json = jsondata[0];
			  	$("#pageIndexvv").val(json.pageNum);
				
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
				$(".wraptable .jspContainer").height($("#Result").outerHeight());
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
}

function saveBtn(){
	var operatorNo = $("#_operatorNo").val();
   // var status = $("#_status").val();
    var role = $("#_role").val();
    var editUuid=$("#editUuid").val();
    if (operatorNo == null || operatorNo == "") 
    {
    	 alert("请输入客服工号");
    	 return;
    }
    
   /* if (status == null || status == "")
    {
	   alert("请输入客服状态");
	   return;
    }*/
   
    if (role == null)
    {
	   role = "";
    }
   
    token=$("#_csrftoken",window.parent.parent.parent.document);
	 $.ajax({  
        type: "post",//使用post方法访问后台  
        dataType: "text", 
        headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/rafsOperatorInfo/updateRafsOperator.go',  
       // data:"operatorNo="+operatorNo+"&status="+status+"&role="+role+"&editUuid="+editUuid,
        data:"operatorNo="+operatorNo+"&role="+role+"&editUuid="+editUuid,
        async:false,
        success : function(resp , textStatus, jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
        	resp = eval('(' + resp + ')');
        	var flag = resp.flag;
            if(flag == "success"){
            	$("#fancybox-close").click();
            	init_dateTable(pageIndex);
			}
            if(resp.flag == "fail")
            {
				$("#fancybox-close").click();
            	init_dateTable(pageIndex);
			}
        },  
        beforeSend:function(XMLHttpRequest)
        {
        	$("#loadingimg").show();
        },
        error: function()
        {//data为返回的数据，在这里做数据绑定  
	        alert("操作失败");
	        $("#loadingimg").hide();
	    }, complete:function(){}
    });
	$("#_operatorNo").val("");
 	//$("#_status").val("");
 	$("#_role").val("");
	$("#editUuid").val("");
}

/*编辑*/
function editBtn(){
	var operatorNo=$("input[name='rafsOperatorCheck']:checked").val();
	
	token=$("#_csrftoken",window.parent.parent.parent.document);
	$.ajax({  
        type : "POST",  
        dataType : "json", 
        headers:{'_requestVerificationToken':$(token).val()},
        url : contextPath+'/rafsOperatorInfo/toeditRafsOperatorInfo.go',  
        data: eval("({'operatorNo':'"+operatorNo+ "'})"),   
        success : function(resp,textStatus, jqXHR) {
        	$(token).val(jqXHR.getResponseHeader("csrftoken"));
        	$("#_operatorNo").val(resp[0].operatorNo);
        	//$("#_status").val(resp[0].status);
        	$("#_role").val(resp[0].role);
        	$("#editUuid").val(operatorNo);
        },
        error: function(){
   	       $("#loadingimg").hide();
   	    },complete:function(){}
    });  
}

/**
 *删除
 */
/*function deleteBtn(){
	var operatorNo=$("input[name='rafsOperatorCheck']:checked").val();
	token=$("#_csrftoken",window.parent.parent.parent.document);
			$.ajax({  
				type: "POST",  
				headers:{'_requestVerificationToken':$(token).val()},
				url: contextPath + "/rafsOperatorInfo/deleteRafsOperatorInfo.go",  
				data: eval("({'operatorNo':'"+operatorNo +"'})"),  
				dataType: 'json',  
				success: function(strV , textStatus, jqXHR){
					$(token).val(jqXHR.getResponseHeader("csrftoken"));
					$("#loadingimg").hide();  
					var str = strV[0];
					var flag = str.flag;
					if(flag =='success'){
						//layer.msg("操作成功"); 
						init_dateTable(pageIndex);
						$.fancybox.close();	
					}else{
						//layer.msg("操作失败"+strV.msg); 
						$.fancybox.close();	
					}
				},  
				beforeSend:function(XMLHttpRequest){
					$("#loadingimg").show(); 
				},
				error: function(){
					//layer.msg("操作失败");
					$("#loadingimg").hide(); 
				} ,complete:function(){}
			});  
	
}*/