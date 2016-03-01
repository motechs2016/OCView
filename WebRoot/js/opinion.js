var opinionSubmit = function(){
	//获取请求参数
	var opinionContent = $("#col2Views").val();
	var contactInfo = $("#phone").val();
	//异步请求
	$.get(
			"opinion_submit",
			{
				"opinionContent":opinionContent,
				"contactInfo":contactInfo
			},
			function(data){
				if(data.indexOf("OK")!=-1){
					$(".col2").html(data.split("OK")[1]);
				}else{
					$("#messageReturn").text("温馨提示:"+data);
				}
			},
			//指定服务器相应为html
			"html"
	);
}