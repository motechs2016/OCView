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

    <title><s:property value="%{teacherId > 0 ? '修改讲师':'新增讲师'}"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
    <link rel="stylesheet" type="text/css" href="css/admin.global.css" />
    <link rel="stylesheet" type="text/css" href="css/admin.content.css" />
    <link rel="stylesheet" type="text/css" href="css/admin.index.css" />
    
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
    <script language="javascript"> 
		//以下两个方法预览图片  
		var picPath;  
		  
		function loadImage(ele) {  
		    var flag = checkExt(ele);  
		    if(!flag){
		    	ele.value="";
		    	return;
		    }
		    picPath = getPath(ele);  
		    document.getElementById("previewImage").src=picPath;
		}
		function getPath(obj){  
		    if(obj){  
		    	//纠结了半天的兼容性,居然没有兼容性
		        if(obj.files){  
		            return window.URL.createObjectURL(obj.files[0]);
		        }
		        return obj.value;  
		    }  
		}  
		  
		//检查文件类型  
		function checkExt(obj){  
		  var errMsg="";  
		  var FileExt="";  
		  var AllowExt=".jpg|.jpeg|.gif|.bmp|.png|"//全部图片格式类型  
		  if(obj.value=="") 
		  	return false;  
		  FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();  
		  if(AllowExt!=0&&AllowExt.indexOf(FileExt+"|")==-1) { //判断文件类型是否允许上传  
		    errMsg="\n该文件类型不允许上传。请上传 "+AllowExt+" 类型的文件，当前文件类型为"+FileExt;  
		    alert(errMsg); 
		    return false;  
		  }
		  return true;
		}  
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

    <div class="location">当前位置：基本管理 -&gt;讲师管理 -&gt; <s:property value="%{teacherId > 0 ? '修改讲师':'新增讲师'}"/></div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3><s:property value="%{teacherId > 0 ? '修改讲师':'新增讲师'}"/></h3>
            <div class="bar">
            	<input class="btn-lit" type="button" style="padding-left:0px;width:35px;border-radius:3px;" onclick="javascript:history.go(-1);" value="后退"/>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <s:form action="teacher_%{ teacherId > 0 ? 'edit':'add' }" method="post" enctype="multipart/form-data">
                <s:hidden name="teacherId"/>
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="row">讲师照片：</th>
                            <td>
                            	
                            	<div id="previewImageDiv">
                            		<s:if test="teacherId==0">
                            			<img id="previewImage" src="videos/default/teacher.jpg" width="122" height="92"/>
                            		</s:if>
                            		<s:else>
                            			<img id="previewImage" src="<s:property value="teacherPicture"/>" width="122" height="92"/>
                            		</s:else>
                            	</div>
                            	<s:file name="upload" id="upload" onchange="loadImage(this)"/> *
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">讲师姓名：</th>
                            <td><s:textfield name="teacherName" cssClass="input-normal"/> *</td>
                        </tr>
                        <tr>
                            <th scope="row">讲师职业：</th>
                            <td><s:textfield name="teacherJob" cssClass="input-normal"/></td>
                        </tr>
                        <tr>
                            <th scope="row">讲师学位：</th>
                            <td><s:textfield name="teacherDegree" cssClass="input-normal"/></td>
                        </tr>
                        <tr>
                            <th scope="row">讲师学院：</th>
                            <td><s:textfield name="teacherCollege" cssClass="input-normal"/></td>
                        </tr>
                        <tr>
                            <th scope="row">讲师描述：</th>
                            <td><s:textarea name="teacherDescription" cssClass="TextareaStyle" cols="40" rows="4"></s:textarea></td>
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