<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件搜索Demo页面</title>
<%
	String contextPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= contextPath%>/static_page/css/style.less">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/style.min.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/jquery.fancybox-1.3.4.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/themes/icon.css"> 

<script src="<%= contextPath%>/js/core/jquery-1.8.3.min.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fancybox-1.3.4.pack.js"></script>
<script src="<%= contextPath%>/js/layer-v2.1/layer/layer.js" type="text/javascript"></script>
<script src="<%= contextPath%>/js/core/jquery.mousewheel.js"></script>
<script src="<%= contextPath%>/js/core/jquery.jscrollpane.min.js"></script>
<script src="<%= contextPath%>/js/core/jquery-ui.js"></script>
<script src="<%= contextPath%>/js/core/json2.js"></script>
<script src="<%= contextPath%>/js/easyui/jquery.easyui.min.js"></script>
<script src="<%=contextPath%>/js/core/Validform_v5.3.2.js"></script>
<script src="<%= contextPath%>/js/core/jquery.pagination.js"></script>
<script src="<%= contextPath%>/js/ajaxSessionTimeout.js"></script>
<script src="<%= contextPath%>/js/core/jquery.ui.widget.js"></script>
<script src="<%= contextPath%>/js/core/jquery.iframe-transport.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fileupload.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fileupload-process.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fileupload-validate.js"></script>
<script src="<%= contextPath%>/js/ResizableColumns/jquery.resizableColumns.min.js"></script>
<script type="text/javascript" src="<%= contextPath%>/js/public.js"></script> 
<script type="text/javascript" src="<%= contextPath%>/js/index/searchFile.js"></script> 
<style type="text/css">
.formTb td{
  	padding:2px 5px 2px 5px;
  }
  .formTb th{
  	padding:2px 5px 2px 5px;
  	text-align: right;
  	background: #F4F3F4;
  	 white-space:nowrap;
  }
  .formTb div{
  	text-align: left;
  }
  .formTb .title{
  	background: #3571A0;
  	color:#ffffff;
  }
  .fieldBox{
	margin:0px;
  }
  
  .inputField{
    border: 1px solid #DBDBDB;
    height: 26px;
    border-radius: 3px;
    box-shadow: 0px 0px 3px #EBEBEB inset;
  }
  
  .must {
    color: #F00;
  }
 .auth_arrow{height:16px;text-align:right;margin-top:7px}
.auth_arrow span{display:inline-block;height:16px;width:16px;background-image:url(../images/icons/auth_arrow.png);border-radius:50%;background-color:#dfdede;margin-left:5px;behavior:url(js/pie.htc);position:relative}
.auth_arrow span.on:hover{background-color:#767676;cursor:pointer;transition:all .2s;-webkit-transition:all .2s;-ms-transition:all .2s;-moz-transition:all .2s;-o-transition:all .2s}
.auth_arrow span.up{background-position:0 0}
.auth_arrow span.down{background-position:-16px 0}
.morecase{display: none;line-height:25px;text-align: left}
.dispatch_tr {text-align: left;}
.dispatch_tr a{color:#3571A0;}
.jspCorner{
	width:0!important;
}
.jspTrack{
	top:-5px;
}
.table tr th:first-child{
	width:200px!important;
}
.table tr th:last-child{
	width:150px!important;
}
.jspHorizontalBar{
	z-index:99
}
</style>
</head>
<body>
	<div class="framecontent">
		<div class="wrap_bottom_gradient">
			<div class="bottom_gradient"></div>
		</div>
		<div class="lay_wrap"style="left:-35px;height: 550px;top:-10px">
			<div class="heading">全文搜索</div>
			
	        <div class="wrapScroll scroll-pane">
	            <div class="wrapTab">
	                <div class="operate">
	                    <form class="formbox" id="form1">
	                        <input type="hidden" value="0"  id="caseInfo_pageNum"/>
							<input type="hidden" id="sort_column"/>
							<input type="hidden" id="contextPath" value="<%=contextPath%>"/>
							<input type="hidden" id="userOrg" value="${user.organizeBm }"/>
							<input type="hidden" id="orglevelhidden" name="orglevelhidden" value="${user.orgLevel }" />
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tableInput">
							
								 <tr>
								 	<td>按标题搜索：</td>
		                           	<td><input id="title" />
									</td>
						            
								</tr>
								<tr>
									 <td>按内容搜索：</td>
			                        <td>
				                        <input id="contents" />
			                        </td>								 	
								</tr>
			                    
								<tr><td>文件类型：</td>
									<td><input type="radio" name="titleSuffix" value="all" checked/>全部 &nbsp;&nbsp;  
										<input type="radio" name="titleSuffix" value="doc" />doc/docx&nbsp;&nbsp;  
										<input type="radio" name="titleSuffix" value="pdf" />pdf&nbsp;&nbsp; 
								     	<input type="radio" name="titleSuffix" value="xls" />xls/xlsx&nbsp;&nbsp;
								     	<input type="radio" name="titleSuffix" value="ppt" />ppt/pptx&nbsp;&nbsp;
										<input type="radio" name="titleSuffix" value="txt" />txt&nbsp;&nbsp;
									</td>
								</tr>
								
								<!-- <tr>
									 <td>两者联合查询结果：</td>
			                        <td>
				                       <select id = "ifAnd" style="width:100px;">
				                       		<option value = "0">AND</option>
				                       		<option value = "1">OR</option>
				                       </select>
			                        </td>								 	
								</tr> -->
							</table>
	                    </form>
	                </div>
	      	<div id="wrapEdit">
				<div class="searbox">
					<div class="editlist">
						<input type="button" value="重置" class="formReset" id="resetBtn_p"/>
					</div>
					<div class="editlist">
						<input type="button" value="查询" class="searchStart" onclick="init_dateTable(0)" />
					</div>
				</div>
			</div>
		 <div  class="scroll-pane wraptable" style="position:relative;top:0px;bottom:20px;overflow-y:hidden;overflow-x:auto;">
			<div id="table1">
	            <table id="caseTrans_table"  name="trans_check" cellpadding="0" cellspacing="0" border="0" class="table" style="width:976px">
	                    <tr>
	                 				
							<th><p>文件标题</p></th>
							<th><p>文件所在路径</p></th>													
							<th><p>文件最后更新时间</p></th>
							<th><p>文件下载</p></th>
	                    </tr>
	                </table>
	            </div>
	          </div> 
	         <!--  <div style="margin-left:200px;">
	            
	          	<textarea  style="width:400px;height: 290px;" id="returnMsg" readonly></textarea>
	          </div> -->
            </div>
	        </div>
	        <div class="tableBottom">
				<div id="Pagination"></div>
			</div>
        
    </div>
    <div class="popbottom" > 
	 <input type="button" id="saveCaseRecordBtn" class="confirm saveinfo" style="float:left; margin-left:10px;"  value="创建索引" onclick="toCreateIndexJsp();" /> 
	<!-- <input type="button" id="saveCaseRecordBtn" class="confirm saveinfo" value="保存" onclick="saveCaseRecord();" /> 
	<input type="button" id="addDispatchBtn" class="confirm saveinfo" value="创建工单" style="float: left; margin-left: 2%; width: 80px" onclick="javascript:$('#addCaseDispatch').trigger('click');" />
	<a class="various1" href="#addCaseDispatchDiv"  id="addCaseDispatch"  onclick="addCaseDispatch()" style="display:none"><span>addCaseDispatch</span></a>
	<a class="various1" href="#addCaseDispatchDiv"  id="viewCaseDispatch"  onclick="viewCaseDispatch()" style="display:none"><span>viewCaseDispatch</span></a> -->
</div>
	</div>

	
<!-- 工单新增页面 -->	
<div style="display: none;">
    <div id="addCaseDispatchDiv" style="width: 1024px;background-color: white;">
      	<iframe id="addCaseDispatchFrame" src="" width='100%' height='600' frameBorder='0' scrolling='no'  marginheight='0' marginwidth='0'></iframe>
  </div>
</div>
<form method="post" id="fileDownloadForm" action="<%= contextPath%>/riskInvestManage/investManage!downloadFile.go">
	<input type="hidden" name="fileName"  id="downloadFileName" value="">
	<input type="hidden" name="filePath" id="downloadFilePath" value="">
</form>
</body>
 <script type="text/javascript">
	$(function(){
		
		$(".fieldBox").css({"line-height":"27px","text-align":"left"});
		$(".title").css({"padding":"5px"});
		$(".popcon").css("height",window.screen.availHeight*0.78-90);
		$(".popbottom").css({"width":"100%","position":"absolute","bottom":"0px"});
		$(".scroll-pane").change_size();
	
	});
</script>
</html>