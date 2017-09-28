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
@RequestMapping("/user")
public class UserController{
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		User user = userService.selectUserById(10);
		model.addAttribute("user", user);
		
		return "user/userList";
	}
	
}
