package com.cybage.dao;

import java.sql.SQLException;

import com.cybage.bean.User;

public interface UserDao {
	public  boolean add(User user) throws SQLException, ClassNotFoundException;
	
//	public boolean check();
	public boolean check(String email, String password);
}
