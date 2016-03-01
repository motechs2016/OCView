//提交评论
var commentSubmit = function(){
	//获取参数
	var commentContent = $("#comment_textarea").val();
	//校验
	if(commentContent==null||commentContent==""){
		return ;
	}
	//异步请求
	$.get(
			"series_comment",
			{
				"seriesId":seriesId,
				"commentContent":commentContent
			},
			function(data){
				$("#comment_textarea").val("");
				showCommentReply(iPageCount);
			},
			"html"
	);
}
//提交回复
var replySubmit = function(commentId){
	//获取参数
	var replyContent = $("#reply_textarea"+commentId).val();
	//校验
	if(replyContent==null||replyContent==""){
		return ;
	}
	//异步请求
	$.get(
			"series_reply",
			{
				"commentId":commentId,
				"replyContent":replyContent
			},
			function(data){
				$("#reply_textarea"+commentId).val("");
				showCommentReply(iPageNum);
			},
			"html"
	);
}

//显示评论和回复信息
var showCommentReply = function(pageNum){

	var pageSize = 5;//控制每页显示多少
	
	//异步请求
	$.get(
			"show_comment_reply",
			{
				"pageNum":pageNum, 
				"pageSize":pageSize,
				"seriesId":seriesId//页面全局变量
			} ,
			function(data){
				var obj = $.parseJSON(data);
				if(obj!=null&&obj.comments!=""){
					//清空内容
					$("#content").html();
					//设置跟帖数
					$("#recordCount").text(obj.recordCount);
					//设置回帖人数
					$("#userCount").text(obj.userCount);
					//获取到评论，并迭代
					var oComments = obj.comments;
					$("#opinion").html("");
					for(var index1 in oComments){
						//评论编号
						var commentId = oComments[index1].commentId;
						//html字符串
						var str = "";
						str += "<li class='opinionLi'>";
						str += "<b class='pdl60'>"+oComments[index1].user.userNickname+"</b><b class='Btime'>"+oComments[index1].commentTime+"</b>";
						str += "<p>"+oComments[index1].commentContent+"</p>";
						str += "<span><label>回复</label></span>";
						str += "<div class='replay'><textarea id='reply_textarea"+commentId+"'></textarea><a href='javascript:void(0);'><button id='reply_button"+commentId+"'>发表</button></a></div>";
						str +="<ul>";
						var oReplies = oComments[index1].replies;
						for(var index2 in oReplies){
							str += "<li><b class='pdl60'>"+oReplies[index2].user.userNickname+"</b><b class='Btime'>"+oReplies[index2].replyTime+"</b>";
							str += "<p>"+oReplies[index2].replyContent+"</p></li>";
						}
						str +="</ul></li>";
						//添加上去
						$("#opinion").append(str);
						
						$("#reply_button"+commentId).click(function(i){
							return function(){
								replySubmit(i);
							};
						}(commentId));
					}
					//---------------
					ry.app.opinion();
					ry.app.button2();
					//---------------
					
					//输出分页导航
					$("#page").html(getPageHtml(obj.pageNum,obj.pageCount,{goFuncName:'showCommentReply',showPageNum:5}));
					//设置页面的变量
					iPageNum = obj.pageNum;
					if(iPageCount!=1&&iPageCount!=obj.pageCount){
						//特殊处理。如果回复后新增加了一页，就向后移动一页
						showCommentReply(iPageCount+1);
					}
					iPageCount = obj.pageCount;
					
				}else if(obj!=null&&obj.comments==""){
					$("#opinion").html("<li style='font-size:20px;background:none;'>暂时还没有评论</li>");
					ry.app.button2();
				}else{
					alert("非法操作！！！");
				}
			},
			//指定服务器相应为html
			"html"
	);
}

/*powered by ldc4*/