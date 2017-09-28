<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/views/pub/header.jsp"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
<title>基本地图展示</title>
<script src="http://webapi.amap.com/maps?v=1.3&key=8bfa3db11c3f1839c078daba56ab0e2e"></script>
<script type="text/javascript" src="${ctx}/static/js/heatmapData3.js"></script>
<style type="text/css">
body,html,#container{
	height: 100%;
	margin: 0px;
}
</style>
</head>
<body>
<div id="container"></div>
<script type="text/javascript">
var map = new AMap.Map('container', {
    resizeEnable: true,
    zoom:11,
    center: [118.620752, 24.838300]
    //center: [116.418261, 39.921984]
});
var heatmap;
map.plugin(["AMap.Heatmap"], function() {
    //初始化heatmap对象
    heatmap = new AMap.Heatmap(map, {
        radius: 15, //给定半径
        opacity: [0, 1]
        /*,gradient:{
         0.5: 'blue',
         0.65: 'rgb(117,211,248)',
         0.7: 'rgb(0, 255, 0)',
         0.9: '#ffea00',
         1.0: 'red'
         }*/
    });
    //设置数据集：该数据为北京部分“公园”数据
    heatmap.setDataSet({
        data: heatmapData,
        max: 100
    });
});
</script>
</body>
</html>