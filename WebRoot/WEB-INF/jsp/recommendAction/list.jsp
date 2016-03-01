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
	
    <title>前台推荐</title>
    
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
    <link rel="stylesheet" type="text/css" href="css/admin.global.css"/>
    <link rel="stylesheet" type="text/css" href="css/admin.content.css"/>
    <link rel="stylesheet" type="text/css" href="css/admin.index.css"/>
    
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
            <div class="index-table-welcome-center" id="index-title">基本管理</div>
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
    <div class="location">当前位置：基本管理 -&gt; 推荐管理</div>
    
    <div class="blank10"></div>
    
    <%-- start sturts2 tag(s:form) --%>
	<s:form action="recommend_delete" method="post">
    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>推荐列表</h3>
            <div class="bar">
            
            	<%-- start sturts2 tag | button采用a标签样式会有点问题，故在style进行修正  --%>
                <s:a cssClass="btn-lit" action="recommend_addUI"><span>新增</span></s:a>
            	<s:if test="#session.admin.hasPrivilegeByUrl(\"/backend/recommend_delete\")">
                	<s:submit cssClass="btn-lit" value="删除选中" style="padding-left:0px;width:70px;border-radius:3px;" onClick="return window.confirm('所有的下级栏目都会被删除，您确定要删除选中的？')"></s:submit>
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
                            	<%-- 判断:有删除权限 --%>
                            	<s:if test="#session.admin.hasPrivilegeByUrl(\"/backend/recommend_delete\")">
                            		<input type="checkbox" id="selectAll"/>
                            	</s:if>
                            	<s:else>
                            		<input type="checkbox" id="selectAll" disabled="disabled"/>
                            	</s:else>
                            </th>
                            <th scope="col">序号</th>
                            <th scope="col">图片路径</th>
                            <th scope="col">背景颜色</th>
                            <th scope="col">修改</th>
                            <th scope="col">删除</th>
                            <th scope="col">上移</th>
                            <th scope="col">下移</th>
                        </tr>
                        
                        <%-- start sturts2 tag(s:iterator) --%>
                        <s:iterator value="#recList" status="status">
			        	<tr>
			        		<td class="chk">
			        			
			        			<%-- 判断:有删除权限 --%>
				        		<s:if test="#session.admin.hasPrivilegeByUrl(\"/backend/recommend_delete\")">
				        			<input type="checkbox" name="recommendIds" value="${recommendId}" id="cb_${recommendId}" />
				        		</s:if>
						    	<s:else>
				        			<input type="checkbox" class="checkbox_disabled" disabled="disabled"/>
						    	</s:else>
			        		</td>
					    	<td class="txt160 c">${status.count}</td>
					    	<td class="txt160 c">${picturePath}</td>
					    	<td class="txt160 c">${backgroundColor}</td>
					    	<td class="icon"><s:a cssClass="opt" title="修改" action="recommend_editUI?recommendId=%{recommendId}"><span  class="icon-sprite icon-edit"></span></s:a></td>
					    	<td class="icon"><s:a cssClass="opt" title="删除" action="recommend_delete?recommendIds=%{recommendId}"><span class="icon-sprite icon-delete"></span></s:a></td>
					    	<td class="txt40 c"><s:a action="recommend_moveUp?recommendId=%{recommendId}">↑↑↑</s:a></td>
					    	<td class="txt40 c tail"><s:a action="recommend_moveDown?recommendId=%{recommendId}">↓↓↓</s:a></td>
					    </tr>
					    </s:iterator>   
					    <%-- end sturts2 tag(s:iterator) --%>
					    
                    </tbody>
                </table>
            </div>
            
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
