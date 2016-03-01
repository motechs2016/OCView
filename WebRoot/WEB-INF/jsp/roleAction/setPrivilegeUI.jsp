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
   
    <title>设置权限</title>
    
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
    <link rel="stylesheet" type="text/css" href="css/admin.global.css" />
    <link rel="stylesheet" type="text/css" href="css/admin.content.css" />
    <link rel="stylesheet" type="text/css" href="css/admin.index.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery_treeview/jquery.treeview.css" />
    
    <style type="text/css">
    	body{overflow:scroll;}
    </style>
    
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
	<script type="text/javascript" src="js/jquery_treeview/jquery.treeview.js"></script>
	<script type="text/javascript">
		$(function(){
			$("[name=privilegeIds]").click(function(){
				//当选中或取消一个权限时，也同时选中或取消所有的下级权限
				$(this).siblings("ul").find("input[type=checkbox]").attr("checked",this.checked);
				if(this.checked == true){
					$(this).parents("li").children("input").attr("checked",true);
				}
			});
		});
		$(function(){
    		$("#tree").treeview();
    	});
	</script>
</head>
  
<body>
<div id="right">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td class="index-table-top-left"></td>
        <td class="index-table-top-center">
            <div class="index-table-welcome-left"></div>
            <div class="index-table-welcome-center" id="index-title">设置权限</div>
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

    <div class="location">当前位置：角色管理 -&gt; 设置权限</div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>设置权限</h3>
            <div class="bar">
            	<input class="btn-lit" type="button" style="padding-left:0px;width:35px;border-radius:3px;" onclick="javascript:history.go(-1);" value="后退"/>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <s:form action="role_setPrivilege">
			  	<s:hidden name="roleId"/>
				<ul id="tree">
					<s:iterator value="#application.topPrivilegeList">
						<li><input type="checkbox" name="privilegeIds" value="${privilegeId}" id="cb_${privilegeId}" <s:property value=" privilegeId in privilegeIds ? 'checked':''"/> />
							<label for="cb_${privilegeId}"><span class="folder">${privilegeName}</span></label>
							<ul>
								<s:iterator value="children">
									<li><input type="checkbox" name="privilegeIds" value="${privilegeId}" id="cb_${privilegeId}" <s:property value=" privilegeId in privilegeIds ? 'checked':''"/> />
										<label for="cb_${privilegeId}"><span class="folder">${privilegeName}</span></label>
										<ul>
											<s:iterator value="children">
												<li><input type="checkbox" name="privilegeIds" value="${privilegeId}" id="cb_${privilegeId}" <s:property value=" privilegeId in privilegeIds ? 'checked':''"/> />
													<label for="cb_${privilegeId}"><span class="folder">${privilegeName}</span></label></li>
											</s:iterator>
										</ul>
									</li>
								</s:iterator>
							</ul>
						</li>
					</s:iterator>
				</ul>
				<s:submit cssClass="btn-lit" value="保存" style="padding-left:0px;width:35px;border-radius:3px;"/>
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
