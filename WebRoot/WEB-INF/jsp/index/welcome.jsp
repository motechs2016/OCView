<%@page import="org.blue.backend.permission.domain.Admin"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>欢迎登陆</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.global.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.content.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.index.css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin.js"></script>
</head>
  
<body>
<div id="right">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td class="index-table-top-left"></td>
        <td class="index-table-top-center">
            <div class="index-table-welcome-left"></div>
            <div class="index-table-welcome-center" id="index-title">欢迎登陆</div>
            <div class="index-table-welcome-right"></div>
            <div class="clear"></div>
        </td>
        <td class="index-table-top-right"></td>
    </tr>
    <tr>
        <td class="index-table-middle-left"></td>
        <td class="index-table-middle-center" valign="top" id="content-container">
<!------------------------------------------------------------------------------------------>
	<div class="container">
	    <div class="location">当前位置：欢迎登陆</div>
	
	    <div class="blank10"></div>
	    
	    <div class="box">
	        <div class="box-title txt-blue-b">角色情况</div>
	        <div class="box-content">

            	<%-- start struts2 tag --%>
	            <s:if test="#session.admin.roles.size()!=0">
		            <s:iterator value="#session.admin.roles" status="status" id="ssrole">
						<span class="txt-orange">${roleName}</span>
			    	</s:iterator>
		    	</s:if>
		    	<s:else>
		    		<span class="txt-orange">无</span>
		    	</s:else>
		    	<%-- end struts2 tag --%>

	        </div>
	    </div>
	    
	    <div class="blank10"></div>
	
	    <div class="box">
	        <div class="box-title txt-blue-b">登录情况</div>
	        <div class="box-content">
	            <span class="txt-green">登录总数：</span>${admin.loginTimes}<br/>
	            <span class="txt-green">本次登录IP：</span>${admin.loginIP}<br/>
	            <span class="txt-green">上次登录IP：</span>${admin.lastLoginIP}<br/>
	            <span class="txt-green">本次登录时间：</span><s:date name="#session.admin.loginDate"/><br/>
	            <span class="txt-green">上次登录时间：</span><s:date name="#session.admin.lastLoginDate"/><br/>
	        </div>
	    </div>
	    
	    <div class="blank10"></div>
	 	
	 	<div class="box">
	        <div class="box-title txt-blue-b">账号情况</div>
	        <div class="box-content">
	            <span class="txt-green">创建时间：</span>${admin.createDate}<br/>
	            <span class="txt-green">修改时间：</span>${admin.updateDate}<br/>
	        </div>
	 	</div>   
	    
	    <div class="blank10"></div>
	
	    <img src="${pageContext.request.contextPath}/images/backend/ts.gif" style="margin-bottom:-2px;" width="16" height="16" alt="tip" /> 提示：为了账号的安全，如果上面的登录情况有异常，建议马上修改密码。
	    
	    <div class="blank10"></div>
	    <div class="line"></div>
	    <div class="blank10"></div>
	
	    <img src="${pageContext.request.contextPath}/images/backend/icon-mail.gif" style="margin-bottom:-1px;" width="16" height="11" alt="mail" /> 联系：xxxx@blues.com<br/>
	    <img src="${pageContext.request.contextPath}/images/backend/icon-phone.gif" style="margin-bottom:-2px;" width="17" height="14" alt="phone" /> 网站：<a href="http://www.baidu.com" target="_blank">http://www.baidu.com</a>
	
	</div>

<!------------------------------------------------------------------------------------------>
		</td>
		<td class="index-table-middle-right"></td>
	</tr>
	<tr>
		<td class="index-table-bottom-left"></td>
		<td class="index-table-bottom-center"></td>
		<td class="index-table-bottom-right"></td>
	</tr>
</table>
</div>

</body>
</html>
