package com.model.base.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.base.domain.User;
import com.model.base.service.IUserService;

@Controller
@RequestMapping("/test")
public class TestController{	
	
	@RequestMapping(value = "/iframe/iframeTestA")
	public String iframeTestA() {
		return "test/iframe/iframeTestA";
	}
	
	@RequestMapping(value = "/iframe/iframeTestB")
	public String iframeTestB() {
		return "test/iframe/iframeTestB";
	}
	
	@RequestMapping(value = "/gaode/map")
	public String gaodeMap() {
		return "test/gaode/map";
	}
	
	@RequestMapping(value = "/websocket/websocket")
	public String websocket() {
		return "test/websocket/websocket";
	}
}
