<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.PrintWriter"%>
<%@ page import="com.kframe.core.exception.BusinessException"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<%
	String errorMessage="系统异常";
	if (exception != null){
		if(exception instanceof BusinessException){
			errorMessage=exception.getMessage();
		}
		//记录日志
		Logger logger = LoggerFactory.getLogger("500.jsp");
		logger.error(exception.getMessage(), exception);
	}
		
	

%>

<!DOCTYPE html>
<html>
<head>
	<title>500 -  <%= errorMessage%> </title>
</head>
<style>
		.b_500Box{min-width: 600px; min-height: 520px; margin: 0; padding:0; text-align: center; padding-top: 150px;}
		.b_500Box .name{ color: #666; font-size: 18px; font-family: "微软雅黑";}
</style>
<body>
	<div class="b_500Box">
		<img class="ui-img-src" src="${ctx}/static/images/500.png">
		<p class="name">页面出现错误了(<%=errorMessage%>)</p>
	</div>
</body>
</html>
<script type="text/javascript">
		var url = window.location.pathname;
		var app = url.split("/");
		if (app.length > 0) {
			if (app[1]=='kmanage-web') {
				var size = 0, link = "", href = "", src = "";
				//样式
				size = document.getElementsByTagName("link").length;
				for ( var i = 0; i < size; i++) {
					href = document.getElementsByTagName("link")[i].getAttribute("href");
	
					document.getElementsByTagName("link")[i].setAttribute("href", "/" + app[1] + href);
				}
				//链接
				size = document.getElementsByClassName("ui-a-href").length;
				for ( var i = 0; i < size; i++) {
					href = document.getElementsByClassName("ui-a-href")[i].getAttribute("href");
	
					document.getElementsByClassName("ui-a-href")[i].setAttribute("href", "/" + app[1] + href);
				}
				//图片
				size = document.getElementsByClassName("ui-img-src").length;
				for ( var i = 0; i < size; i++) {
					src = document.getElementsByClassName("ui-img-src")[i].getAttribute("src");
	
					document.getElementsByClassName("ui-img-src")[i].setAttribute("src", "/" + app[1] + src);
				}
			}
		}
	</script>
