package com.model.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import net.sf.json.JSONObject;

/**
 * http工具类
 */
public class HttpUtil  extends WebUtils{
	
	private static Logger logger=Logger.getLogger(HttpUtil.class);
	
	public static String getDomain(HttpServletRequest request){
		return request.getServerName();
	}
	
	public static String getHttpDomain(HttpServletRequest request){
		return request.getScheme() + "://" + request.getServerName();
	}
	
	public static String getContextHttpUri(HttpServletRequest request){
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	public static String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	public static String getRequestFullUri(HttpServletRequest request){
		String port = "";
		if(request.getServerPort() != 80){
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port + request.getContextPath() + request.getServletPath();
	}
	
	public static String getRequestFullUriNoContextPath(HttpServletRequest request){
		String port = "";
		if(request.getServerPort() != 80){
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port + request.getServletPath();
	}
	
	//获取ip地址；
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			if(ip.indexOf("::ffff:")!=-1) ip = ip.replace("::ffff:", "");
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	//判断当前请求是否为Ajax
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return !StringUtils.isEmpty(header) && "XMLHttpRequest".equals(header);
	}
	
	/**
	 * 重定向
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param url
	 */
	public static void redirectUrl(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,String url){
		try {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重定向到http://的url
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param url
	 */
	public static void redirectHttpUrl(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,String url){
		try {
			httpServletResponse.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject httpClient(String requestUrl,String httpMethod,Map<String, Object> params){
		JSONObject jsonObject = null;
		@SuppressWarnings({ "resource", "deprecation" })
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			if("GET".equals(httpMethod.toUpperCase())){
				System.out.println(requestUrl);
				HttpGet httpGet = new HttpGet(requestUrl);
				response = httpClient.execute(httpGet);
			}else{
				HttpPost httpPost = new HttpPost(requestUrl);
				if(params != null){
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					Set<String> keySet = params.keySet();
					for(String key : keySet) {
						if(params.get(key) != null)
							nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
			        }
					httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				}
				
				response = httpClient.execute(httpPost);
			}
			
			HttpEntity entity = response.getEntity();
			logger.info("response status: " + response.getStatusLine());
			
			String body = URLDecoder.decode(EntityUtils.toString(entity), "utf-8");
			logger.info("获取到的body"+URLDecoder.decode(body, "utf-8"));
			if(StringUtils.isNotBlank(body)){
				jsonObject = JSONObject.fromObject(body);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static String http(String requestUrl,String httpMethod,Map<String, Object> params){
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			if("GET".equals(httpMethod.toUpperCase())){
				System.out.println(requestUrl);
				HttpGet httpGet = new HttpGet(requestUrl);
				response = httpClient.execute(httpGet);
			}else{
				HttpPost httpPost = new HttpPost(requestUrl);
				if(params != null){
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					Set<String> keySet = params.keySet();
					for(String key : keySet) {
						if(params.get(key) != null)
							nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
			        }
					httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				}
				response = httpClient.execute(httpPost);
				response.setHeader("P3P","CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
			}
			
			HttpEntity entity = response.getEntity();
			logger.info("response status: " + response.getStatusLine());
			
			String body = URLDecoder.decode(EntityUtils.toString(entity), "utf-8");
			logger.info("获取到的body:"+URLDecoder.decode(body, "utf-8"));
			if(StringUtils.isNotBlank(body)){
				return body;
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	//新建URL对象
            URL realUrl = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            //设置请求头参数
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("content-type", "application/json;charset=utf-8");
            conn.setRequestProperty("content-length", "256");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //得到输出流
            out = new PrintWriter(conn.getOutputStream());
            //往输出流写入请求体
            out.print(param);
            out.flush();
            //得到输入流
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            //拼接字符串
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入输出流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //返回结果
        return result;
    }
	
}
