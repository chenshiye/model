<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/views/pub/header.jsp"%>
<%-- <%@include file="../pub/header.jsp"%> --%>
<style type="text/css">
body {
  background-color: #fcfcfc;
}
</style>
</head>
<body>
<div id="" class="divc" style="width:600px;height:400px">
	<h1>子页面</h1>
	<div style="width:400px;height:200px"><p>sdfsdfsdf</p></iframe></div>
	<input style="button" onclick="ctest()" value="click">
	<input style="button" onclick="cclose()" value="close">
	<input style="button" onclick="childSay()" value="childSay">
</div>

<script type="text/javascript">
function ctest(){
	parent.ptest();
}
function cclose(){
	this.$parentIframeBg = $("#cIframeBg",window.parent.document);
	this.$parentIframeBg.remove();
	this.$parentIframe = $("#cIframe",window.parent.document);
	this.$parentIframe.remove();
	
}
function childSay(){
	alert("我是子页面的say方法");
}
</script>
</body>
</html>