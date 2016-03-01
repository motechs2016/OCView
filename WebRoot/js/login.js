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
					$("#video").show();
					$("#top").prepend('<span id="userNickname"><a href="#">'+data.split("OK")[1]+'</a></span>');
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
/*designer:ldc4*/