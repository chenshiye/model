package com.model.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.base.dao.UserMapper;
import com.model.base.domain.User;
import org.apache.log4j.Logger;

/**
 * 通过httpClient方式调用webgis服务
 * 需要先登录后发命令
 * @author 可思
 *
 */
@Service
public class UserService implements IUserService{
	private Logger logger=Logger.getLogger(UserService.class);
	@Autowired  
    private UserMapper userMapper;  

	@Override
	public User selectUserById(Integer userId) {
		return userMapper.selectByPrimaryKey(userId); 
	}
	
}
