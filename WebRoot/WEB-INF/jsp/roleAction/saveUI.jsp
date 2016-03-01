<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" " http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <base href="<%=basePath%>">

    <title><s:property value="%{roleId > 0 ? '修改角色':'新增角色'}"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
    <link rel="stylesheet" type="text/css" href="css/admin.global.css" />
    <link rel="stylesheet" type="text/css" href="css/admin.content.css" />
    <link rel="stylesheet" type="text/css" href="css/admin.index.css" />
    
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
    
</head>
<body>
<div id="right">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td class="index-table-top-left"></td>
        <td class="index-table-top-center">
            <div class="index-table-welcome-left"></div>
            <div class="index-table-welcome-center" id="index-title">系统管理</div>
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

    <div class="location">当前位置：系统管理 -&gt;角色管理 -&gt; <s:property value="%{roleId > 0 ? '修改角色':'新增角色'}"/></div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3><s:property value="%{roleId > 0 ? '修改角色':'新增角色'}"/></h3>
            <div class="bar">
            	<input class="btn-lit" type="button" style="padding-left:0px;width:35px;border-radius:3px;" onclick="javascript:history.go(-1);" value="后退"/>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <s:form action="role_%{ roleId > 0 ? 'edit':'add' }">
                <s:hidden name="roleId"/>
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="row">角色名称：</th>
                            <td><s:textfield name="roleName" cssClass="input-normal"/> *</td>
                        </tr>
                        <tr>
                            <th scope="row">角色说明：</th>
                            <td><s:textarea name="roleDescription" cssClass="TextareaStyle" cols="40" rows="4"></s:textarea></td>
                        </tr>
                        
                        <tr>
                            <th scope="row">&nbsp;</th>
                            <td>
                            	<s:submit cssClass="btn-lit" value="保存" style="padding-left:0px;width:35px;border-radius:3px;"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </s:form>
            </div>
        </div>
    </div>

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