package com.howard.investment.service.impl;

import com.howard.investment.dao.UserDao;
import com.howard.investment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Howard on 2017/11/23
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

	@Override
	public Map getUserByKey(String userName, String password) {
		// TODO Auto-generated method stub
		return userDao.getUserByKey(userName, password);
	}

}
