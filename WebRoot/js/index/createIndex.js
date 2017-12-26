var pageIndex = 0;
var pageSize = 15;
var contextPath = "";
var fileStr = "";
$(function(){
	// 获取服务器目录
	contextPath = $('#contextPath').val();
	//批量上传文件
	$('#fileupload').fileupload({
	    url: contextPath + '/UploadFileServlet',
	    sequentialUploads: true,
	    autoUpload: true,
	    replaceFileInput:false,
		dataType: 'json',
		/*add: function (e, data) {
            data.context = $('#uploadBtn')
                .click(function () {
                   // data.context = $('<p/>').text('Uploading...').replaceAll($(this));
                    data.submit();
                    //设置按钮不可用
                    $(".searchStart1").attr("disabled", true);
                    $("#resetBtn_p").attr("disabled", true);
                    $("#uploadBtn").attr("disabled", true);
                    if($('#returnMsg').val().indexOf("Indexing to directory")!=-1){
                    	$('#returnMsg').val("");
                    }
                    
                });
        },*/
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
            $('#progress .bar span').text(progress+'%');
            if(progress == 100){ //文件上传完成后开始建立索引           	
            	//恢复按钮可用
            	 $(".searchStart1").attr("disabled", false);
            	/* $("#resetBtn_p").attr("disabled", false);
            	 $("#uploadBtn").attr("disabled", false);*/
            	 if($('#returnMsg').val().indexOf("Indexing to directory")!=-1){
                 	$('#returnMsg').val("");
                 }
            }
        },
	    done: function (e, data) {
	    	if(data.result.flag=="success"){
	    		$.each(data.result.files, function (index, file) {
	        	    	var msg = $('#returnMsg').val();
	        	    	$('#returnMsg').val(msg+file.fileName +"上传成功！\r\n");
	        	    	fileStr = fileStr + file.fileName+"*";
	            	});
	    		}else{
	    			var msg = $('#returnMsg').val();
        	    	$('#returnMsg').val(msg + data.result.error+"\r\n");
	    		}
	        }       
	});		 
	
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
	/*var indexPath = $("#indexPath").val();
	var docsPath = $("#docsPath").val();
	if(indexPath==''||indexPath==null){
		alert("索引存储目录不能为空！");
		return false;
	}else if(docsPath==''||docsPath==null){
		alert("索引文件（夹）不能为空！");
		return false;
	}*/
	var createType = $("#createType").val();
	$.ajax({
        type: "POST",  
        dataType: "json",  
      //  headers:{'_requestVerificationToken':$(token).val()},
        url: contextPath+'/CreateIndexServlet',
        data: {"createType":createType,"fileStr":fileStr},
        async:false,
        success: function(strV,textStatus,jqXHR) {
        		if(strV.flag=='success'){
        			$("#returnMsg").val(strV.returnMsg);
        			layer.msg("操作成功");
        		 	fileStr = "";
        			
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

function toSearchFileJsp(){
	if(fileStr !=""){
		 var msg = "上传文件还未建立索引，您确定要离开此页面吗？"; 
		 if (confirm(msg)==true){ 
			 location = contextPath + "/searchFile.jsp";
	     }
	}else{
		location = contextPath + "/searchFile.jsp";
	}
	
}

