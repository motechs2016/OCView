<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>服务器线程信息</title>
</head>

<body>
	<pre>
<% 
		for(Map.Entry<Thread, StackTraceElement[]> stackTrace:Thread.getAllStackTraces().entrySet()){
			Thread thread = stackTrace.getKey();
			StackTraceElement[] stack = stackTrace.getValue();
			if(thread.equals(Thread.currentThread())){
				continue;
			}
			out.print("\n线程："+thread.getName()+"\n");
			for(StackTraceElement element : stack){
				out.print("\t" + element + "\n");
			}
		}
%>
	
	</pre>
</body>
</html>
