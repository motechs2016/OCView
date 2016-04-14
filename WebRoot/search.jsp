<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>搜索结果</title>
	<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
	<script src="js/myjs.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
	<script type="text/javascript" src="js/search.js"></script>
</head>

<body>
	<div class="login" id="login">
	    <h3>登录重邮公开课</h3>
	    <span id="close"><img src="images/ocview/close.png"/></span>
	    <input type="text" id="name"/>
	    <label id="name_value">账号</label>
	    <input type="password" id="psw" class="password"/>
	    <label id="psw_value">密码</label>
	    <input type="password" id="turepsw" class="password"/>
	    <label id="psw_true">确定密码</label>
	    <input type="text" id="nickName" /> 
	    <label id="nickName_value">昵称</label>
	    <input type="text" id="email"/>
	    <label id="email_value">邮箱</label>
	       
	    <input class="button" type="button" value="登录" id="denglu" onclick="userLogin();"/>
	    <input class="button" type="button"  value="注册" id="zhuce" onclick="userRegist();"/>
	    
	    <p id="signInwrapper">没有账号？<a href="#" id="signIn">立即注册</a></p>
	    
	    <div id="messageReturn">　</div>
	</div>
	<div id="wrapper"></div>
	
	<div id="wrapperContent">
		<div class="topwrapper">
			<div id="top">
			   <s:if test="#session.user==null">
			   <span id="loginPage"><a href="#">登录</a></span>
			   </s:if>
			   <s:else>
			   <!-- userNickname 这里修改完 记得修改login.js -->
			   <span id="user_homepage"><a href="#">${user.userNickname}</a></span>
			   <span class="separation">|</span>
			   <span id="user_setting"><a href="#">设置</a></span>
			   <span class="separation">|</span>
			   <span id="user_exit"><a href="#" onclick="userExit();">退出</a></span>
			   </s:else>
			   <span class="separation">|</span>
			   <span class="span"><a href="about">关于</a></span>
			</div><!--end of top-->
		</div>
		<div id="header">
			<div id="logo"><img src="images/ocview/logo.png" class="logo"/><img src="images/ocview/ocview.png" class="couse"/></div>
			<div id="soso"><input type="text" id="text" value="搜索视频，公开课"/><button onclick="seriesSearch();"></button></div>
		</div>
		
		<div class="searchCourse">
			<div class="courseContent">
	         	<s:if test="#searchList.size()>0">
		            <h2>课程</h2>
		            <ul>
						<s:iterator value="#searchList">
							<li>
								<img src="${picturePath}" />
								<h3><s:a action="series?seriesId=%{seriesId}">${seriesName}</s:a></h3>
								<p><span>学院：${teacherCollege}</span></p>
								<p><span>讲师：${teacherName}</span></p>
								<p><span>类型：${navicationName}</span></p>
								<p>${seriesDescription}</p>
							</li>
						</s:iterator>
		            </ul>
				</s:if>
				<s:else>
	            		无相关课程
				</s:else>
			</div>
		</div>
		
		<div id="footer">
			<div id="knowledge">
				<img src="images/ocview/footer_img.png"/>
			</div>
		</div>
 
	</div>
	<script>
		var timer=null;
		ry.app.toText(); 
		ry.app.openLogin();
		ry.app.toName();
		ry.app.passsword();
		ry.app.closeLogin();
		ry.app.trueLogin();
	</script>
</body>

</html>
