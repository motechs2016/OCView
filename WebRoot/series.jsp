<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>重邮公开课</title>
	<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		//页面全局变量
		var loginFlag = ${loginFlag};//标记是否登录
		var iPageNum = 1;
		var iPageCount = 1;
		var seriesId = ${series.seriesId};
	</script>
	<script type="text/javascript" src="js/myjs.js"></script>
	<script type="text/javascript" src="js/comment.js"></script>
	<script type="text/javascript" src="js/paging.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
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
			   <span>|</span>
			   <span><a href="index">首页</a></span>
			   <span class="separation">|</span>
			   <span class="span"><a href="about">关于</a></span>
			</div><!--end of top-->
		</div>


		<div class="content1">
			<div class="information">
      			<div style="background:#F5F5F5"><h3>${navication.navicationName}:${series.seriesName}</h3></div>
 				<div class="course">
					<div class="video">
						<img src="<s:property value="#series.picturePath.split(\"\\\\.\")[0]+\"-new.\"+#series.picturePath.split(\"\\\\.\")[1]"/>"/>
						<h2>${series.seriesName}</h2>
						<p>本课程共有<span>${series.courseNum}</span>集，欢迎学习</p>
						<p class="pd26">课程介绍</p>
						<p>${series.seriesDescription}</p>
					</div>
				</div>
				
				<div style="background:#FFF;width:1349px;margin:0 auto;">
					<div class="list">
						<h2>课程列表</h2>
						<p><span>名称</span></p>
						<ul>
							<s:iterator value="#mediaList">
								<li><s:a href="media?mediaId=%{mediaId}">${mediaName}</s:a></li>
							</s:iterator>
						</ul>
						
						<div class="comment" id="content">
						
							<h2><img src="images/ocview/comment.png"/><span id="recordCount">0</span>跟贴&nbsp;|&nbsp;<span id="userCount">0</span>参与</h2>
							<ul id="opinion">
								<li style="background:none;">内容加载中...</li>
							</ul>
							<script type="text/javascript">
								showCommentReply(iPageNum);
							</script>
							<div class="page" id="page">
								<!-- 分页导航在comment.js中动态加载 -->
							</div>
							
							
							<div class="my_view">
								<p><img src="images/ocview/myview.png"/><textarea id="comment_textarea"></textarea></p>
								<a href="javascript:void(0);" id="fabiao"><button onclick="commentSubmit();"></button></a>
							</div>
          
						</div>
					</div>
       
					<div class="sublist1">
						<h4>讲师介绍</h4>
						<p><img src="${teacher.teacherPicture}"/></p>
						<ul>
							<li>讲师：${teacher.teacherName}</li>
							<li>职业：${teacher.teacherJob}</li>
							<li>学位：${teacher.teacherDegree}</li>
						</ul>
						<h4>其他介绍</h4>
						<ul>
							<li>${teacher.teacherDescription}</li>
						</ul>
					</div>
				</div>
			</div>
		</div><!--end of content-->
  
		<div id="footer">
			<div id="knowledge">
				<img src="images/ocview/footer_img.png"/>
			</div>
		</div><!--end of footer-->
	</div>
  
	<script>
		var timer=null;
		ry.app.toName();
		ry.app.passsword();
		ry.app.closeLogin();
		ry.app.trueLogin();
	</script>
</body>

</html>
