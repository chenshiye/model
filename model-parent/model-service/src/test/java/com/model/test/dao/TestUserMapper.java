/*
 * Copyright(c) 2016 kuaigaoding.com All rights reserved.
 * distributed with this file and available online at
 * http://www.kuaigaoding.com/
 */
package com.model.test.dao;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.model.base.dao.UserMapper;
import com.model.base.domain.User;


/**
 * @version 1.0
 * @author 
 * 单元测试 gis系统的登录用户: GisUserDao
 */
public class TestUserMapper {
    
    Logger logger=Logger.getLogger(TestUserMapper.class);
    
    private ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    private UserMapper userMapper;
    
    @Before
    public void setUp(){
    	userMapper = (UserMapper) ac.getBean("userMapper");
    }
    @After
    public void tearDown(){
    	userMapper = null;
        // 此处可以做一些清理操作
    }
    
    @Test
    public  void testListBy(){
        logger.debug("开始测试 gisUserDao.listPage(params)");
        User user = userMapper.selectByPrimaryKey(10);  
        logger.debug("查找结果" + user.getUserName());  
        logger.debug("查找结果" + user);  
        logger.info("查找结果" + user.getUserName());
    }
    
//    @Test
//    public  void testListBy1(){
//        logger.debug("开始测试 gisUserDao.listPage(params)");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
//        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
//        logger.debug("查找结果" + user.getUserName());  
//        logger.debug("查找结果" + user);  
//        logger.info("查找结果" + user.getUserName());
//    }


}
