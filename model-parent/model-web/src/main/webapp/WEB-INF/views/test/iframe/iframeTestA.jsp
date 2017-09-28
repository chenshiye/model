<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/views/pub/header.jsp"%>
<%-- <%@include file="../pub/header.jsp"%> --%>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	alert("aaaa");
})
</script>
</head>
<body>
<h1>父页面</h1>
<div id="iframe1" style="width:600px;height:400px;display:none">
	<iframe id="cIframe" src="${ctx}/user/iframeTestB" width="100%" height="100%" style="z-index:9999" frameborder='no' marginheight='0' marginwidth='0' allowTransparency='true'></iframe>
</div>
<input style="button" onclick="ptest()" value="click">
<input style="button" onclick="openIframe()" value="openIframe">
<input style="button" onclick="cIframeFun()" value="调用子页面方法">
<script type="text/javascript">
iframeTestA.jsp
function ptest(){
	alert("aaaa");
}
function openIframe(){
	//$("#iframe1").show();
	var url=ctx+"/user/iframeTestB";	
	showIframe("cIframe",url,"100%","100%");
}
function cIframeFun(){
	//document.getElementById('cIframe').contentWindow.childSay();
	$("#cIframe")[0].contentWindow.childSay();
}
function showIframe(id,url,w,h){
    //添加iframe
    var if_w = w; 
    var if_h = h; 
    //allowTransparency='true' 设置背景透明
    $("<iframe width='" + if_w + "' height='" + if_h + "' id='"+id+"' name='"+id+"' style='position:absolute;z-index:9999;'  frameborder='no' marginheight='0' marginwidth='0' allowTransparency='true'></iframe>").prependTo('body');    
    var st=document.documentElement.scrollTop|| document.body.scrollTop;//滚动条距顶部的距离
    var sl=document.documentElement.scrollLeft|| document.body.scrollLeft;//滚动条距左边的距离
    var ch=document.documentElement.clientHeight;//屏幕的高度
    var cw=document.documentElement.clientWidth;//屏幕的宽度
    var ifrm=$("#"+id);
    var objH=ifrm.height();//浮动对象的高度
    var objW=ifrm.width();//浮动对象的宽度
    var objT=Number(st)+(Number(ch)-Number(objH))/2;
    var objL=Number(sl)+(Number(cw)-Number(objW))/2;
    ifrm.css('left',objL);
    ifrm.css('top',objT);
    if(url.indexOf("?")>0)url+="&";
    else url+="?";
    url+="pframe="+id;
    ifrm.attr("src", url)
    
    //添加背景遮罩
    $("<div id='"+id+"Bg' style='background-color: Gray;display:block;z-index:3;position:absolute;left:0px;top:0px;filter:Alpha(Opacity=30);-moz-opacity:0.4;opacity: 0.4; '/>").prependTo('body'); 
    var bgWidth = Math.max($("body").width(),cw);
    var bgHeight = Math.max($("body").height(),ch);
    var ifrmBg=$("#"+id+"Bg");
    ifrmBg.css({width:bgWidth,height:bgHeight});
 
    //点击背景遮罩移除iframe和背景
    ifrmBg.click(function() {
    	ifrm.remove();
    	ifrmBg.remove();
    });
}
</script>
</body>
</html>