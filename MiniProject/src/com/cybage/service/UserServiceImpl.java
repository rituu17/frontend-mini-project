package com.cybage.service;

import java.sql.SQLException;

import com.cybage.bean.User;
import com.cybage.dao.UserDao;
import com.cybage.dao.UserDaoImpl;



public class UserServiceImpl implements UserService{
	private UserDao userDao =  new UserDaoImpl();

	@Override
	public boolean add(User user) throws ClassNotFoundException, SQLException {
		
		
		return userDao.add(user);
	}


	@Override
	public boolean check(String email, String password) {
		// TODO Auto-generated method stub
		return userDao.check(email, password);
	}

	

	
	
	
}
