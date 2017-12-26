<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>索引创建Demo页面</title>
<%
	String contextPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= contextPath%>/static_page/css/style.less">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/style.min.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/jquery.fancybox-1.3.4.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/themes/icon.css"> 
<link rel="stylesheet" type="text/css" href="<%= contextPath%>/css/powerFloat.css"/>

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

<script src="<%= contextPath%>/js/core/jquery.ui.widget.js"></script>
<script src="<%= contextPath%>/js/core/jquery.iframe-transport.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fileupload.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fileupload-process.js"></script>
<script src="<%= contextPath%>/js/core/jquery.fileupload-validate.js"></script>
<script src="<%= contextPath%>/js/ResizableColumns/jquery.resizableColumns.min.js"></script>
<script src="<%= contextPath%>/js/core/jquery-powerFloat-min.js"></script>
<script type="text/javascript" src="<%= contextPath%>/js/public.js"></script> 
<script type="text/javascript" src="<%= contextPath%>/js/index/createIndex.js"></script> 
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
.progressbar {
  height: 20px;
  background: #ebebeb;
  border-left: 1px solid transparent;
  border-color: #bfbfbf #b3b3b3 #9e9e9e;
  border-right: 1px solid transparent;
  border-radius: 10px;
}
.progressbar > div {
  position: relative;
  float: left;
  margin: 0 -1px;
  min-width: 30px;
  height: 18px;
  line-height: 16px;
  text-align: right;
  background: #cccccc;
  border: 1px solid;
  border-color: #bfbfbf #b3b3b3 #9e9e9e;
  border-radius: 10px;
  -webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.3), 0 1px 2px rgba(0, 0, 0, 0.2);
  box-shadow: inset 0 1px rgba(255, 255, 255, 0.3), 0 1px 2px rgba(0, 0, 0, 0.2);
}
.progressbar > div > span {
  padding: 0 8px;
  font-size: 11px;
  font-weight: bold;
  color: #404040;
  color: rgba(0, 0, 0, 0.7);
  text-shadow: 0 1px rgba(255, 255, 255, 0.4);
}
</style>
</head>
<body >
	<div class="framecontent">
		<div class="wrap_bottom_gradient">
			<div class="bottom_gradient"></div>
		</div>
		<div class="lay_wrap"style="left:-35px;height: 550px;top:-10px">
			<div class="heading">上传文件与创建索引</div>
			
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
								<!--  <tr>
								 	<td>索引存储目录：</td>
		                           	<td><input id="indexPath" >
									</td>
						            
								</tr> -->
								<tr>
									<td>上传要被索引的文件：</td>
									<td>
									<div  id="fileContainer">
										<input id="fileupload" type="file" name="uploadFiles" data-url="<%=contextPath%>/UploadFileServlet" multiple>   
									</div>
									</td>
									<!-- <td>
										<div >
											<input type="button" style="width:50px;" id="uploadBtn" value="上传"/> 
										</div>
									</td> -->
								</tr>
								
								<tr><td>上传进度：</td>
								  <td colspan="2">
									<div id="progress" class="progressbar ">
    									<div class="bar" style="width: 0%;"><span>0%</span></div>

									</div>
							      </td>
								</tr>
								<!-- <tr>
									 <td>索引文件（夹）：</td>
			                        <td>
				                        <input id="docsPath" >
			                        </td>								 	
								</tr> -->
								<tr>
									 <td>索引创建方式：</td>
			                        <td>
				                       <select id = "createType" style="width:280px;">
				                       		<option value = "0" selected>为当前上传文件添加索引</option>
				                       		<option value = "1">删除所有索引，并为当前上传文件重新创建索引</option>
				                       		<option value = "2">删除所有索引，为所有已上传文件重新创建索引</option>
				                       		
				                       </select>
			                        </td>								 	
								</tr>
							</table>
	                    </form>
	                </div>
	      	<div id="wrapEdit">
				<div class="searbox">
					<!-- <div class="editlist">
						<input type="button" value="重置" class="formReset" id="resetBtn_p"/>
					</div> -->
					<div class="editlist">
						<input style="width:90px;" type="button" value="创建索引" class="searchStart1" onclick = "init_dateTable(0)" disabled/>
					</div>
				</div>
			<!-- 	<div class="editbox"style="border:none">					
					<div class="editlist" style="display: none;">
						<a class="biunique" href="javascript:void(0)" id="queryDetail"><span class="icon eye"></span><span>查看</span></a>
						<a class="various1" href="#inline1" onclick="queryDetail()" style="display: none"></a>
					</div>
					<div class="editlist"style="display: none;">
							<a class="various1" href="#inline1" href="javascript:void(0)"><span
								class="icon add"></span><span>新增</span></a>
						</div>
					<div class="editlist"style="display: none;">
							<a name="updateAllot"onclick="editBtn()" class="biunique" 
								href="javascript:void(0)"><span class="icon edit"></span><span>修改</span></a>
							<a class="various1" href="#inline1" style="display: none"></a>
						</div>
						<div class="editlist"style="display: none;">
							<a  class="variousdel"
								href="#confirm"name="deleteBtn"><span class="icon del"></span><span>删除</span></a>
						</div>
				</div> -->
			</div>
		<!-- <div  class="scroll-pane wraptable" style="position:relative;top:0px;bottom:20px;overflow-y:hidden;overflow-x:auto;">
			<div id="table1">
	            <table id="caseTrans_table"  name="trans_check"cellpadding="0" cellspacing="0" border="0" class="table" style="width:800px">
	                    <tr>
	                 				
							<th width="100"><p>标题</p></th>
							<th width="300"><p>文件所在路径</p></th>													
						
	                    </tr>
	                </table>
	            </div>
	          </div> -->
	          <div style="margin-left:100px;">
	            
	          	<textarea  style="width:800px;height: 290px;" id="returnMsg" readonly></textarea>
	          </div>
            </div>
	        </div>
	        <div class="tableBottom">
				<div id="Pagination"></div>
			</div>
        
    </div>
     <div class="popbottom" >
	<!-- <span class="confirm saveinfo"  onclick="">文件搜索</span> --> 
	 <input type="button" id="saveCaseRecordBtn" class="confirm saveinfo" style="margin-right:50px;"  value="文件搜索" onclick="toSearchFileJsp();" /> 
	<!--<input type="button" id="addDispatchBtn" class="confirm saveinfo" value="创建工单" style="float: left; margin-left: 2%; width: 80px" onclick="javascript:$('#addCaseDispatch').trigger('click');" />
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