<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>后台管理系统登陆</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	 
	<link rel="stylesheet" type="text/css" href="css/admin.login.css">
	
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			document.forms[0].adminAccount.focus();
		});
		
		// 在被嵌套时就刷新上级窗口
		if(window.parent != window){
			window.parent.location.reload(true);
		}
	</script>
</head>
  
<body>
	<div id="container">
	
	<div id="header">
		<h2 id="logo">
			<a href="javascript:void(0)"><span class="title">JavaBack</span></a>
		</h2>
	</div>
	
	<s:form action="admin_login">
		<div id="content">
			<div class="text-field">
				<label for="accountInput">Username</label>
				<br/>
				<s:textfield id="accountInput" cssClass="text-field" type="text" size="30" name="adminAccount" />
			</div>
			<div class="text-field">
				<label for="passwordInput">Password</label>
				<br/>
				<s:textfield id="passwordInput" cssClass="text-field" type="password" size="30" name="adminPassword" />
			</div>
			<!-- 
			<div class="checkbox-field">
				<input id="rememberInput" type="checkbox" value="1" name="remember" checked="checked" />
				<label for="rememberInput">Keep me logged in</label>
			</div>
			-->
			<div class="submit-message">
				<s:submit cssClass="button" value="Login"/>
				<div id="messageReturn"><s:fielderror/></div>
			</div>
		  </div>
	</s:form>
	  
	</div>
</body>
</html>
