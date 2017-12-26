<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CRYSTAL</title>
<%
	String contextPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/style.min.css">
<title>Insert title here</title>
<style type="text/css">
.error {
	text-align: center;
	width: 400px;
	color: red;
	font-size: 14px;
	margin-left: 25%;
	margin-top: 19%;
	border: 1px #dbdbdb solid;
	height: 100px;
	border-radius: 6px;
	padding-top: 8%;
}
</style>
</head>
<body>
	<div class="error">
		<span>${empty errorMsg ?"您访问的页面不存在，请联系管理员！":errorMsg}</span><br/>
		<span>电话:021-25654235</span>
	</div>
	<div>
		
	</div>
</body>
</html>