<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>404 - 页面不存在</title>
</head>
	<style>
		.b_404Box{min-width: 600px; min-height: 520px; margin: 0; padding:0; text-align: center; padding-top: 150px;}
		.b_404Box .name{ color: #666; font-size: 18px; font-family: "微软雅黑";}
	</style>
	<body>
		<div class="b_404Box">
			<img class="ui-img-src" src="${ctx}/static/images/404.png">
			<p class="name">你访问的页面找不到了</p>
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