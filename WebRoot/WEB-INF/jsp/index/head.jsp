<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.index.css">
<style type="text/css">
	body{margin:0px;}
</style>

<div id="header">
    <div id="header-logo"><img src="${pageContext.request.contextPath}/images/backend/logo.gif" alt="logo" width="59" height="64" border="0" /></div>
    <div id="header-title">后台管理系统</div>
    <div id="header-info">
        <span style="margin-right:50px;color:#fff;">管理员：<b><s:property value="#session.admin.adminName"/></b> 您好，欢迎登陆使用！</span>
        <span style="margin-right:50px;color:#fff;">当前版本：1.0.2</span>
        <s:a action="home_index" target="_parent" style="margin-right:10px;color:#fff; font-weight:bold;">后台首页</s:a>
        <s:a action="admin_changePasswordUI?adminId=%{#session.admin.adminId}" target="contentFrame" style="margin-right:10px;color:#fff; font-weight:bold;">修改密码</s:a>
        <s:a action="admin_logout" target="_parent"><img src="${pageContext.request.contextPath}/images/backend/out.gif" class="middle" alt="安全退出" width="46" height="20" border="0" /></s:a>
    </div>
</div>
