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
	
    <title>管理员列表</title>
    
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
    <link rel="stylesheet" type="text/css" href="css/admin.global.css"/>
    <link rel="stylesheet" type="text/css" href="css/admin.content.css"/>
    <link rel="stylesheet" type="text/css" href="css/admin.index.css" />
    
    <style type="text/css">
    	body{overflow:scroll;}
    </style>
    
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
    <script type="text/javascript">
        $(function(){
			$("#selectAll").click(function(){
				//当选中或取消一个权限时，也同时选中或取消所有的
				$("input[type=checkbox]").attr("checked",this.checked);
				//disabled的单选框不被选中
				$(".checkbox_disabled").attr("checked",false);
			});
			//使图标居中
			var icon_td = $(".icon");
			var opt_a = $(".opt");
			opt_a.css("left",(icon_td.width()-opt_a.width())/2);
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
    <div class="location">当前位置：系统管理 -&gt;管理员管理</div>
    
    <div class="blank10"></div>
    
    <%-- start sturts2 tag(s:form) --%>
	<s:form action="admin_delete" method="get">
    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>管理员列表</h3>
            <div class="bar">
            
            	<%-- start sturts2 tag | button采用a标签样式会有点问题，故在style进行修正  --%>
                <s:a cssClass="btn-lit" action="admin_addUI" namespace="/backend"><span>新增</span></s:a>
                
                <s:if test="#session.admin.hasPrivilegeByUrl(\"/backend/admin_delete\")">
                	<s:submit cssClass="btn-lit" value="删除选中" style="padding-left:0px;width:70px;border-radius:3px;" onClick="return window.confirm('您确定要删除选中的？')"></s:submit>
                </s:if>
                <%-- end sturts2 tag --%>
                
            </div>
        </div>
        
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col">
                            	<%-- 判断:超级管理员不能删除&不能删除自己&有删除权限 --%>
                            	<s:if test="(adminAccount!='admin')&&(adminAccount!=#session.admin.adminAccount)&&(#session.admin.hasPrivilegeByUrl(\"/backend/admin_delete\"))">
                            		<input type="checkbox" id="selectAll"/>
                            	</s:if>
                            	<s:else>
                            		<input type="checkbox" id="selectAll" disabled="disabled"/>
                            	</s:else>
                            </th>
                            <th scope="col">账号</th>
                            <th scope="col">姓名</th>
                            <th scope="col">备注信息</th>
                            <th scope="col">初始化密码</th>
                            <th scope="col">查看</th>
                            <th scope="col">修改</th>
                            <th scope="col">删除</th>
                        </tr>
                        
                        <%-- start sturts2 tag(s:iterator) --%>
                        <s:iterator value="recordList">
			        	<tr>
			        		<td class="chk">
				        		<%-- 判断:超级管理员不能删除&不能删除自己&有删除权限 --%>
				        		<s:if test="(adminAccount!='admin')&&(adminAccount!=#session.admin.adminAccount)&&(#session.admin.hasPrivilegeByUrl(\"/backend/admin_delete\"))">
				        			<input type="checkbox" name="adminIds" value="${adminId}" id="cb_${adminId}" />
				        		</s:if>
						    	<s:else>
				        			<input type="checkbox" class="checkbox_disabled" disabled="disabled"/>
						    	</s:else>
			        		</td>
					    	
					    	<td class="txt120 c">${adminAccount}</td>
					    	<td class="txt120 c">${adminName}</td>
					    	<td class="txt200 c">${adminInfo}</td>
						    <td class="txt80 c"><s:a action="admin_initPassword?adminId=%{adminId}" onClick="return window.confirm('您确定要初始化密码为123456吗？')">初始化密码</s:a></td>
					    	<td class="icon"><s:a cssClass="opt" title="查看" action="admin_details?adminId=%{adminId}"><span  class="icon-sprite icon-magnifier"></span></s:a></td>
					    	<td class="icon"><s:a cssClass="opt" title="修改" action="admin_editUI?adminId=%{adminId}"><span class="icon-sprite icon-edit"></span></s:a></td>
					    	
					    	<td class="icon tail">
						    	<%-- 判断:超级管理员不能删除&不能删除自己  - 删除权限在a标签中已经处理 --%>
						    	<s:if test="(adminAccount!='admin')&&(adminAccount!=#session.admin.adminAccount)">
						    		<s:a cssClass="opt" title="删除" action="admin_delete?adminIds=%{adminId}" onClick="return window.confirm('您确定要删除？')"><span class="icon-sprite icon-delete"></span></s:a>
						    	</s:if>
					    	</td>
					    </tr>
					    </s:iterator>
					    <%-- end sturts2 tag(s:iterator) --%>
					       
                    </tbody>
                </table>
            </div>
            
            <!-- 分页导航   -->
            <script type="text/javascript">
                var pagerUrl = 'backend/admin_list?pageNum={0}';
                Pager.Output(pagerUrl, '${pageSize}', '${pageNum}', '${pageCount}', '${recordCount}');//(urlFormat, pageSize, pageNum, pageCount, recordCount)
            </script>
        </div>
    </div>
	</s:form>
	<%-- start sturts2 tag(s:form) --%>
	
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
