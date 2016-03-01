var userVote = function(mediaId){
	//异步请求
	$.get(
			"media_vote",
			{ 
				"mediaId":mediaId, 
			} ,
			function(data){
				if(data.indexOf("err")==-1){
					$("#voteCount").html("<h5>投票总数："+data+"</h5>");
					$("#vote").hide();
				}
			},
			//指定服务器相应为html
			"html"
	);
}