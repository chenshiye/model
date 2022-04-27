package com.model.wx.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.wx.utils.SignUtil;



@Controller
@RequestMapping("/wxapi")
public class WxApiCtrl {

	
	/**
	 * GET请求：进行URL、Tocken 认证；
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 */
	@RequestMapping(value = "/message",  method = RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request) {
		
		System.out.println("进来消息处理");
		
		String tocken = "1a46b38dccfd4133a9b467c46c752869";//获取token，进行验证；
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		
		// 校验成功返回  echostr，成功成为开发者；否则返回error，接入失败
		if (SignUtil.validSign(signature, tocken, timestamp, nonce)) {
			return echostr;
		}
		
		return "error";


	}

	
}
