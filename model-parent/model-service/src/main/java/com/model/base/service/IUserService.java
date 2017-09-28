package com.model.base.service;

import com.model.base.domain.User;

/**
 *
 */
public interface IUserService {
	/**
	 * 登录并返回sessionid
	 * @param userName
	 * @param password
	 * @return
	 */
	public User selectUserById(Integer userId);
}
