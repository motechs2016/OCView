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

    <title><s:property value="%{navicationId > 0 ? '修改导航':'新增导航'}"/></title>
    
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
            <div class="index-table-welcome-center" id="index-title">导航管理</div>
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

    <div class="location">当前位置：导航管理 -&gt; 前台导航 -&gt; <s:property value="%{navicationId > 0 ? '修改导航':'新增导航'}"/></div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3><s:property value="%{navicationId > 0 ? '修改导航':'新增导航'}"/></h3>
            <div class="bar">
            	<input class="btn-lit" type="button" style="padding-left:0px;width:35px;border-radius:3px;" onclick="javascript:history.go(-1);" value="后退"/>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <s:form action="navication_%{ navicationId > 0 ? 'edit':'add' }">
                <s:hidden name="navicationId"/>
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                    	
                        <tr>
                            <th scope="row">上级导航：</th>
                            <td>
                            
	                            <s:select name="parentId" cssClass="SelectStyle" 
	                        		list="#navList" listKey="navicationId" listValue="navicationName" 
	                        		headerKey="0" headerValue="==请选择上级导航=="/>
                        		<%-- 修改导航时不能修改上级导航  --%>
	                        	<s:if test="navicationId>0">
	                        		<script type="text/javascript">
	                        			$("select[name='parentId']").attr("disabled",true);
	                        			$("option[value=0]").text("无上级栏目");
	                        		</script>
	                        	</s:if>
                        	
                        	</td>
                        </tr>
                        <tr>
                            <th scope="row">导航名称：</th>
                            <td><s:textfield name="navicationName" cssClass="input-normal"/> *</td>
                        </tr>
                        <tr>
                            <th scope="row">导航链接：</th>
                            <td><s:textfield name="navicationUrl" cssClass="input-big"/> *</td>
                        </tr>
                        <tr>
                            <th scope="row">打开方式：</th>
                            <td><s:select name="navicationTarget" cssClass="SelectStyle" 
                            	list="#{'_self':'在当前窗口/框架中打开（_self）','_parent':'在父窗口/框架中打开（_parent）','_top':'在整个窗口中打开（_top）','_bank':'在新窗口/选项卡中打开（_bank）'}"
                            	/> *</td>
                        </tr>
                        <tr>
                            <th scope="row">导航说明：</th>
                            <td><s:textarea name="navicationDescription" cssClass="TextareaStyle" cols="40" rows="4"></s:textarea></td>
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