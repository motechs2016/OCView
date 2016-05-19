//普通用户登陆
var userLogin = function(){
	//获取请求参数
	var userAccount = $("#name").val();
	var userPassword = $("#psw").val();
	//异步请求
	$.get(
			"user_login",
			{ 
				"userAccount":userAccount, 
				"userPassword":userPassword
			} ,
			function(data){
				if(data.indexOf("OK")!=-1){
					loginFlag = false;
					//处理页面
					$("#login").fadeOut("slow");
					$("#wrapper").hide();
					$("#loginPage").html("");
					//$("#video").show();
					$("#top").prepend(
							'<span id="user_homepage"><a href="#">'
							+data.split("OK")[1]+'</a></span>\n'
							+'<span class="separation">|</span>\n'
							+'<span id="user_setting"><a href="#">设置</a></span>\n'
							+'<span class="separation">|</span>\n'
							+'<span id="user_exit"><a href="#" onclick="userExit();">退出</a></span>\n'
					);//“\n”让我好生费力的才把问题解决
					
					//刷新评论(在评论的页面才刷新！！)
					var pathnamestr = window.location.pathname;
					if(pathnamestr == "/OCView/series"||pathnamestr == "/OCView/media")
						showCommentReply(iPageNum);
					$("#fabiao").attr("href","javascript:void(0)");
					//$("#login").remove();//不应该remove掉
				}
				else{
					$("#messageReturn").text("温馨提示:"+data);
				}
			},
			//指定服务器相应为html
			"html"
	);
}

var userRegist = function(){
	//获取请求参数
	var userAccount = $("#name").val();
	var userPassword = $("#psw").val();
	var confirmPassword = $("#turepsw").val();
	var userEmail = $("#email").val();
	var userNickname = $("#nickName").val();
	//异步请求
	$.get(
			"user_regist",
			{ 
				"userAccount":userAccount, 
				"userPassword":userPassword,
				"confirmPassword":confirmPassword,
				"userEmail":userEmail,
				"userNickname":userNickname
			} ,
			function(data){
				if(data.indexOf("OK")!=-1){
					$("#login").fadeOut("slow");
					$("#wrapper").hide();
					$("#loginPage").html("");
					$("#video").show();
					$("#top").prepend('<a href="#">'+data.split("OK")[1]+'</a>');
					//刷新评论
					showCommentReply(iPageNum);
					$("#fabiao").attr("href","javascript:void(0)");
					$("#login").remove();
				}
				else{
					$("#messageReturn").text("温馨提示:"+data);
				}
			},
			//指定服务器相应为html
			"html"
	);
	
}

//退出后刷新页面
userExit = function(){
	$.get("user_exit");
	//window.location.reload();
	//退出后本不应该刷新，但是用户退出，表示用户不想看了，这里感觉并没有任何语义矛盾
	
	$("#top").html(
		'<span id="loginPage"><a href="#">登录</a></span>\n'
		+'<span>|</span>\n'
		+'<span><a href="index">首页</a></span>\n'
		+'<span class="separation">|</span>\n'
		+'<span class="span"><a href="about">关于</a></span>\n'
	);
	//重新加入html后，还要给按钮注册事件
	//ry.app.button();//这个是我针对media.jsp页面加的。并不通用所有页面
	registLoginPageOnclick();
	//wrapper在登陆后就被隐藏了，所有这里还需要展现出来，登陆关闭只是把hight设置为0
	$("#wrapper").show();
	document.getElementById('wrapper').style.height=0;
};

registLoginPageOnclick = function(){
	var oLoginPage=document.getElementById('loginPage');
	var oLogin=document.getElementById('login');
	var oWrapper=document.getElementById('wrapper');
	var oContent=document.getElementById('wrapperContent');
	var oTurepsw=document.getElementById('turepsw');
	var oEmail=document.getElementById('email');
	var oEmailValue=document.getElementById('email_value');
	var oPswTrue=document.getElementById('psw_true');
	var oHeight=oContent.offsetHeight;
	var oNick=document.getElementById('nickName');
	var oNickValue=document.getElementById('nickName_value');
	var oZhuce=document.getElementById('zhuce');
	var oDenglu=document.getElementById('denglu');
	var oSignInwrapper=document.getElementById('signInwrapper');
	if(oLoginPage!=null){
		oLoginPage.onclick=function(){
			oLogin.style.display="block";
			oTurepsw.style.display="none";
			oPswTrue.style.display='none';
			oNick.style.display='none';
			oNickValue.style.display='none';
			oEmail.style.display="none";
			oEmailValue.style.display='none';
			oZhuce.style.display='none';
			oDenglu.style.display="block";
			oSignInwrapper.style.display='block';
			oWrapper.className="wrapper";
			oWrapper.style.height=oHeight+'px';
		}
	}
};


/*designer:ldc4*/