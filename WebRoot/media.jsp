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
		var seriesId = ${media.series.seriesId};
	</script>
	<script type="text/javascript" src="js/core.js"></script>
	<script type="text/javascript" src="js/vote.js"></script>
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
			
				<div style="background:#F5F5F5">
					<h3>${media.mediaName}</h3>
				</div>
		     
				<div class="course1">
					<div class="video1">
						<div id="video">
							<div id="a1" style="position:absolute;width:860px;height:538px;z-index:2;"> <!-- 此处JS添加播放器 --> </div>
						</div>
						<script type="text/javascript" src="ckplayer/offlights.js"></script>
						<script type="text/javascript" src="ckplayer/ckplayer.js" charset="utf-8"></script>
						<!-- CKplayer -->
						<script type="text/javascript">
							var flashvars={
								f:'${pageContext.request.contextPath}${media.mediaPath}',
								c:0,//使用.js配置，1为使用.xml配置。默认.xml配置
								my_url:'${pageContext.request.requestURL}',
								p:2//点击播放才加载
							};
							var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always'};
							CKobject.embedSWF('ckplayer/ckplayer.swf','a1','ckplayer_a1','100%','100%',flashvars,params);
							
							//开关灯
							var box = new LightBox();
							function closelights(){//关灯
								box.Show();
							}
							function openlights(){//开灯
								box.Close();
							}
							//为什么全屏黑： 因为以前我弹出登录的时候 老是被视频挡住，我应该调低了显示层
							//offlights z-index = 50
						</script>
					</div>
				</div><!--end of course-->
		      
		   
				<div style="background:#FFF;width:1349px;margin:0 auto;">
					<div class="list1">
						<div class="comment">
						
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
								<p><img src="images/ocview/myview.png"/><textarea style="width:680px;"></textarea></p>
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
						<s:if test="#media.isVoted==1">
							<div id="voteCount"><h5>投票总数：${media.voteCount}</h5></div>
							<s:if test="#voteMark">
							<a href="#vote" id="vote"><button onclick="userVote(${media.mediaId});">我要投票</button></a>
							</s:if>
						</s:if>
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
		ry.app.opinion();
		ry.app.toName();
		ry.app.passsword();
		ry.app.closeLogin();
		ry.app.trueLogin();
		ry.app.button();
	</script>
</body>

</html>
