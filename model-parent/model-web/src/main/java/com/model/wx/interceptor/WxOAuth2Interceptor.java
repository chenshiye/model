package com.model.wx.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.degal.police.common.LoginHelper;
import com.degal.police.common.bean.ResponseBean;
import com.degal.police.common.constant.GloableConstant;
import com.degal.police.common.enumitem.StateType;
import com.degal.police.common.factory.ResponseBeanFactory;
import com.degal.police.common.util.HttpUtil;
import com.degal.police.system.domain.AdminUser;
import com.degal.police.system.service.AdminUserService;
import com.model.base.service.IUserService;
import com.wxapi.process.OAuthScope;
import com.wxapi.process.WxApi;
import com.wxapi.process.WxApiClient;

/**
 * 微信客户端用户请求验证拦截器
 */
public class WxOAuth2Interceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	IUserService userService;
	
	/**
	 * 开发者自行处理拦截逻辑，
	 * 方便起见，此处只处理includes
	 */
	private String[] excludes;//不需要拦截的
	private String[] includes;//需要拦截的
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		
		boolean oauthFlag = true;//为方便展示的参数，开发者自行处理

		for(String s : excludes){
			if(uri.contains(s)){//如果包含，就不拦截
				oauthFlag = false;
				break;
			}
		}
		for(String s : includes){
			if(uri.contains(s)){//如果包含，就拦截
				oauthFlag = true;
				break;
			}
		}
		if(!oauthFlag){//如果不需要oauth认证
			return true;
		}
		
		//APP登录拦截
//		if(uri.contains("/app/")){
//			AdminUser adminUser = AppLoginHelper.getLoginUser();
//			if(adminUser==null){
//				ResponseBean responseBean = ResponseBeanFactory.getResponseBean(StateType.C111,"未登录或登录超时");
//				JSONObject jsonObject = JSONObject.fromObject( responseBean );
//				String json = jsonObject.toString();
//				returnJson(response, json);
//				return false;
//			}else{
//				return true;
//			}
//		}
		
		if(!LoginHelper.isLogin()){//没有，通过微信页面授权获取
			String code = request.getParameter("code");
			if(!StringUtils.isBlank(code)){//如果request中包括code，则是微信回调
				try {
					String openId = WxApiClient.getOAuthOpenId(code);
					if(!StringUtils.isBlank(openId)){
						
						AdminUser adminUser = adminUserService.findByOpenId(openId);
						if(adminUser!=null){
							LoginHelper.setOpenId(openId);//缓存openid
							LoginHelper.setLoginUser(adminUser);
							String redirectUrl = HttpUtil.getRequestFullUri(request);//请求code的回调url
							String params = OAuth2RequestParamHelper.getStateParamStr(request);
							HttpUtil.redirectHttpUrl(request, response, redirectUrl+"?"+params);
							return false;
						}else{
							//没有权限
							HttpUtil.redirectUrl(request, response, "/error/intercept.html");
							return false;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{//oauth获取code
				
				String redirectUrl = HttpUtil.getRequestFullUri(request);//请求code的回调url
				String state = OAuth2RequestParamHelper.prepareState(request);
				if(StringUtils.isNotBlank(GloableConstant.AppId)){
					String url = WxApi.getOAuthCodeUrl(GloableConstant.AppId, redirectUrl, OAuthScope.Base.toString(), state);
					HttpUtil.redirectHttpUrl(request, response, url);
					return false;
				}
			}
		}else{
			System.out.println("#### WxOAuth2Interceptor Session : openid = " + LoginHelper.getOpenId());
			return true;
		}
		HttpUtil.redirectUrl(request, response, "/error/101.html");
		return false;
	}
	
	private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        response.setStatus(999);
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
	
	 
	 
	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}
	
	
}

