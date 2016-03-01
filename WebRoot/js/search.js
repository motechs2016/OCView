var seriesSearch = function(){
	var keyword = $("#text").val();
	if(keyword=="搜索视频，公开课")
		keyword = "";
	var url = window.location.href;
	if(url.indexOf("index")!=-1){
		url = url.split("index")[0]+"search?keyword="+keyword;
	}else if(url.indexOf("search")!=-1){
		url = url.split("search")[0]+"search?keyword="+keyword;
	}else{
		return ;
	}
	window.location.href = url;
}