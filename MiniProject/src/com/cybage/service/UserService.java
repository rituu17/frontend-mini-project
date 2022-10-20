package com.cybage.service;

import java.sql.SQLException;
import java.util.List;

import com.cybage.bean.User;



public interface UserService {
	public boolean add(User user) throws ClassNotFoundException, SQLException;

	public boolean check(String email, String password);
}
