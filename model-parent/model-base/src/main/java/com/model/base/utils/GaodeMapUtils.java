package com.model.base.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
/**
 * 高德地图工具类
 * @author csy
 */
public class GaodeMapUtils {

	public static final String KEY = "fd0a9447bedb6c73d5a41c80bd1158fc";
	public static final String service_id = "144256";
	
	
	/**
	 * 地理坐标转换
	 */
	public static Map<String, Object> coordinateConvert(Double longitude,Double latitude){
		Map<String, Object> map = null;
		try {
			String url = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations="+longitude+","+latitude+"&coordsys=gps&output=JSON&key="+KEY;
			JSONObject jsonObject = HttpUtil.httpClient(url, "GET", null);
			String status = jsonObject.get("status")!=null?jsonObject.getString("status").trim():"";
			if("1".equals(status)){
				String locations = jsonObject.getString("locations");
				String[] result = locations.split(",");
				Double lng = Double.parseDouble(result[0]);
				Double lat = Double.parseDouble(result[1]);
				map = new HashMap<String, Object>();
				map.put("lng", lng);
				map.put("lat", lat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		Double longitude = 118.569800;
		Double latitude = 24.806584;
		Map<String, Object> aa = coordinateConvert(longitude,latitude);
		System.out.println(aa.get("lng"));
		System.out.println(aa.get("lat"));
	}
	
}
