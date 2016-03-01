<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.index.css">
<style type="text/css">
	body{margin:0px;}
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.utils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin.js"></script>
<script type="text/javascript">
    // 初始化下面的变量
    Admin.IsIndexPage = true;
</script>
    
<div id="left">
	<div id="menu-container">
		<%-- 显示一级菜单  --%>
    	<s:iterator value="#application.topPrivilegeList">
    	<s:if test="#session.admin.hasPrivilegeByName(privilegeName)">
		<div class="menu-tit">${privilegeName}</div>
		<div class="menu-list hide">
			<div class="top-line"></div>
			<ul class="nav-items">
				<%-- 显示二级菜单  --%>
                <s:iterator value="children">
                <s:if test="#session.admin.hasPrivilegeByName(privilegeName)">
				<li><s:a action="%{privilegeUrl}_list" target="contentFrame">${privilegeName}</s:a></li>
				</s:if>
                </s:iterator>
			</ul>
		</div>
		</s:if>
		</s:iterator>
	</div>
	<div id="menu-bottom"></div>
</div>