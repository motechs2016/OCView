<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
    
	<title>后台管理系统退出</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="css/admin.login.css">
	<style type="text/css">
		#input_button{
			width:260px;
			margin:0 auto;
		}
		#exit{
			float:right;
		}
	</style>
</head>
  
<body>
<div id="container">
	
	<div id="header">
		<h2 id="logo">
			<a href="javascript:void(0)"><span class="title">JavaBack</span></a>
		</h2>
	</div>
	
	<div id="content">
		<div id="input_button">
			<input id="loginAgain" type="button" class="button" value="重新登陆" onclick="javascript:window.open('backend/admin_login.action','_self');"/>
			<input id="exit" type="button" class="button" value="退　　出" onclick="javascript:window.open('','_self');window.close();"/>
		</div>
	</div>
	
</div>
</body>
</html>
