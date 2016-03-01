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
    
    <title>权限不足</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="Refresh" content="5;url=<s:url action="home_index"/>" />
	
	<style type="text/css">
		body{margin:0 auto;background-color:#CAE4F1;}
		img{width:100%}
	</style>
	
  </head>
  
  <body>
    <img src="images/noPrivilege.jpg"/>
  </body>
</html>
