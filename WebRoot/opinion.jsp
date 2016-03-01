<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>意见反馈</title>
	<link href="css/aboutUsCss.css" rel="stylesheet" type="text/css" />
	<script src="js/myjs.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/opinion.js"></script>
</head>

<body>
	<div class="top">
		<div class="top_content">
			<img src="images/ocview/logo.png" width='60' height="60"/>
			<h1>OC View</h1>
			<a href="index">首页</a>
			<a href="about">关于</a>
		</div>
	</div>
	
	<div class="content_wrapper">
		<div class="content">
		
			<div class="col1">
				<ul>
					<li><a href="about">关于我们</a></li>
					<li><a href="contact">联系我们</a></li>
					<li class="active"><a href="opinion">意见反馈</a></li>
				</ul>
			</div>
	       

			<div class="col2">
				<h2>意见反馈</h2>
				<s:textarea cssClass="views" id="col2Views" name="opinionContent"></s:textarea>
				<label id="col2ViewsValue">提出你宝贵的意见</label>
				<s:textfield id="phone" name="contactInfo"/>
				<label id="textValue">留下联系方式</label>
				<button type="submit" onclick="opinionSubmit();">提交</button>
				<div id="messageReturn">　</div>
			</div>

			
		</div>
	</div>
	
	<div class="footer">
		<div class="footer_content">
			<img src="images/ocview/logo.png" width='60' height='60'/><h1>OC View</h1>
			<p style="margin-top:40px;">我们的使命传播互联网最前沿技术，帮助更多的人实现梦想！</p>
			<p>Copyright © iyang</p>
		</div>
	</div>
	
	<script>
		ry.app.textareaHover();
	</script>
</body>

</html>
