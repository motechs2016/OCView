<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>重邮公开课</title>
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
	    <input class="button" type="button" value="注册" id="zhuce" onclick="userRegist();"/>
	    
	    <p id="signInwrapper">没有账号？<a href="#" id="signIn">立即注册</a></p>
	    
	    <div id="messageReturn">　</div>
	</div>
	<div id="wrapper"></div>
 
	<div id="wrapperContent">
		<div id="top">
		   <s:if test="#session.user==null">
		   <span id="loginPage"><a href="#">登录</a></span>
		   </s:if>
		   <s:else>
		   <span id="userNickname"><a href="#">${user.userNickname}</a></span>
		   </s:else>
		   <span>|</span>
		   <span class="span"><a href="about">关于</a></span>
		</div><!--end of top-->
		<div id="header">
			<div id="logo"><img src="images/ocview/logo.png" class="logo"/><img src="images/ocview/ocview.png" class="couse"/></div>
			<div id="soso"><input type="text" id="text" value="搜索视频，公开课"/><button onclick="seriesSearch();"></button></div>
		</div><!--end of header-->

		<div id="nav">
			<ul style="width:1263px;margin:0 auto;">
				<s:iterator value="#topList" status="st">
				<li>
					<s:if test="!navicationUrl.equals(#topicUrl)">
						<a href="${navicationUrl}" target="${navicationTarget}">${navicationName}</a>
					</s:if>
					<s:else>
						<s:set value="#st.index" name="showIndex"/>
						<a href="${navicationUrl}" target="${navicationTarget}" class="active">${navicationName}</a>
					</s:else>
				</li>
				</s:iterator>
			</ul>
		
		    <s:iterator value="#secondList" status="status">
				<s:if test="#status.count == 1">
					<div style="display:block;">
						<p>
							<s:iterator value="navList">
								<a href="${navicationUrl}" target="${navicationTarget}">${navicationName}</a>
							</s:iterator>
						</p>
					</div>
				</s:if>
				<s:else>
					<div>
						<p>
							<s:iterator value="navList">
								<a href="${navicationUrl}" target="${navicationTarget}">${navicationName}</a>
							</s:iterator>
						</p>
					</div>
				</s:else>
			</s:iterator>
		</div>
		
		<div class="content">
			<div style="width:1349px;margin:0 auto;position:relative;background:#FFF;">
				<div id="img">
	      			<ul>
						<s:iterator value="#recList" status="status">
							<s:if test="#status.count == 1">
								<li style="opacity:1;filter:alpha(opacity=100)">
									<s:a action="series?seriesId=%{series.seriesId}">
										<img src="${picturePath}"/>
									</s:a>
								</li>
							</s:if>
							<s:else>
								<li>
									<s:a action="series?seriesId=%{series.seriesId}">
										<img src="${picturePath}"/>
									</s:a>
								</li>
							</s:else>
						</s:iterator>
	      			</ul>
				</div><!--end of img-->
				<div id="hot">
					<div id="hotnews"><span class="span_active"><a href="#">热门排行</a></span><span><a href="#">最新课程</a></span></div>
					<ul style = "display:block;">
						<s:iterator value="#hotList">
				        	<li><s:a action="media?mediaId=%{mediaId}">${mediaName}</s:a></li>
				        </s:iterator>
					</ul> 
			        <ul>
				        <s:iterator value="#newList">
				        	<li><s:a action="media?mediaId=%{mediaId}">${mediaName}</s:a></li>
				        </s:iterator>
					</ul> 
				</div><!--end of hot-->
			</div>
			
			<s:iterator value="#secondList.get(#showIndex).navList">
				<div class="col1" id=<s:property value="navicationUrl.split(\"#\")[1]"/>>
					<ul>
						<li><div class="part" id="bg1">${navicationName}</div></li>
						<s:iterator value="seriesList">
							<li>
								<s:a action="series?seriesId=%{seriesId}"><img src="${picturePath}"/></s:a>
								<span>${seriesName}</span>
								<span class="span">${seriesInfo}</span>
							</li>
						</s:iterator>
					</ul>
				</div><!--end of col1-->
			</s:iterator>
			
  		</div>		
		
   
		<div id="footer">
			<div id="knowledge">
			   <img src="images/ocview/footer_img.png"/>
			</div>
		</div><!--end of footer-->
	</div>
	
	<script>
		var timer=null;
		ry.app.toText(); 
		ry.app.toPlay();
		ry.app.toDisplay();
		ry.app.toNews();
		ry.app.openLogin();
		ry.app.toName();
		ry.app.passsword();
		ry.app.closeLogin();
		ry.app.trueLogin();
	</script>
</body>
</html>
