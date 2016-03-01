<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    
    <script type="text/javascript">
		// 在被嵌套时就刷新上级窗口
		if(window.parent != window){
			window.parent.location.reload(true);
		}
	</script>
    
  </head>

<frameset rows="64px,*,30px" frameborder="no" border="0" framespacing="0" bordercolor="red">
	<frame src="backend/home_head" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" />
	<frameset cols="185px,*" frameborder="no" border="0" framespacing="0">
		<frame src="backend/home_left" name="mainFrame" scrolling="No" noresize="noresize" id="mainFrame" />
		<frame src="backend/home_welcome" name="contentFrame" scrolling="No" noresize="noresize" id="contentFrame" />
	</frameset>
	<frame src="backend/home_foot" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" />
</frameset>

<noframes><body></body></noframes>

</html>
