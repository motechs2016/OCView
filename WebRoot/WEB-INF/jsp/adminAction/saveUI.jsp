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

    <title><s:property value="%{adminId > 0 ? '修改管理员':'新增管理员'}"/></title>
    
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
    <div class="location">当前位置：系统管理 -&gt;管理员管理 -&gt; <s:property value="%{adminId > 0 ? '修改管理员':'新增管理员'}"/></div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3><s:property value="%{adminId > 0 ? '修改管理员':'新增管理员'}"/></h3>
            <div class="bar">
            	<input class="btn-lit" type="button" style="padding-left:0px;width:35px;border-radius:3px;" onclick="javascript:history.go(-1);" value="后退"/>
            </div>
        </div>
        
        <div class="cnt-wp">
            <div class="cnt form">
                
                <%-- start struts2 tag(s:form) --%>
                <s:form action="admin_%{ adminId > 0 ? 'edit':'add' }">
                <s:hidden name="adminId"/>
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="row">账号：</th>
                            <td>
                            	<s:if test="adminId>0">
                            	<s:textfield name="adminAccount" cssClass="input-normal" readonly="true"/>
                            	* （不能修改账号）
                            	</s:if>
                            	<s:else>
                            	<s:textfield name="adminAccount" cssClass="input-normal"/>
                            	* （账号要唯一，默认密码是123456）
                            	</s:else>
                            </td>
                        </tr><!-- 
                        <tr> 
                            <th scope="row">密码：</th>
                            <td><input value="" type="password" name="password" id="password" class="input-normal" /></td>
                        	
                        </tr>
                        <tr>
                            <th scope="row">确认密码：</th>
                            <td><input value="" type="password" name="passwordConfirm" id="passwordConfirm" class="input-normal" /></td>
                        </tr>-->
                        <tr>
                            <th scope="row">姓名：</th>
                            <td><s:textfield name="adminName" cssClass="input-normal"/> *</td>
                        </tr><!--  
                        <tr>
                            <th scope="row">性别：</th>
                            <td><select name="gender" id="gender"><option value="Male">男</option><option value="Female">女</option></select></td>
                        </tr>-->
                        <tr>
                            <th scope="row">Email：</th>
                            <td><s:textfield name="adminEmail" cssClass="input-normal"/></td>
                        </tr>
                        <tr>  
                            <th scope="row">手机号码：</th>
                            <td><s:textfield name="adminPhone" cssClass="input-normal"/></td>
                        </tr>
                        <tr>
                            <th scope="row">备注信息：</th>
                            <td><s:textarea name="adminInfo" cssClass="TextareaStyle" cols="40" rows="4"></s:textarea></td>
                        </tr><!--
                        <tr>
                            <th scope="row">状态：</th>
                            <td><select name="status" id="status"><option value="Natural">正常</option><option value="Disabled">禁用</option></select></td>
                        </tr>-->
                        <tr>
                        	<th scope="row">角色：</th>
                        	<td>
	                        	<s:select list="#roleList" name="roleIds" multiple="true" cssStyle="height:85px;"
	                  						listKey="roleId" listValue="roleName"></s:select>
	                        	
	                            按住Ctrl键可以多选或取消选择
	                        </td>
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
                <%-- end struts2 tag(s:form) --%>
                
            </div>
        </div>
    </div>

</div>

<div>
	<div>提示：</div>
	<div>1.账号不能为空，只能是字母和数字 ，且长度必须在4到25之间</div>
	<div>2.姓名不能为空，且长度必须在2到20之间</div>
	<div>3.备注信息不超过255个字符</div>
	<div>4.可以不填邮箱和电话，但不要乱填</div>
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