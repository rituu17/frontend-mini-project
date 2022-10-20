package com.cybage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cybage.bean.User;
import com.cybage.util.JDBCUtility;



public class UserDaoImpl implements UserDao{

	@Override
	public boolean add(User user) throws SQLException, ClassNotFoundException {
		
		Connection connection;
		try {
			connection = JDBCUtility.getConnection();
			System.out.println("Connection Established");
			
			String insertQuery= "insert into users(firstname ,lastname ,email ,mobile,password,city )values(?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(insertQuery);
			
			
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setString(3, user.getEmail());
			ps.setInt(4, user.getMobile());
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getCity());
			
			int rc = ps.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;	
		
		
		
	}

//	@Override
//	public boolean check() {
//		Connection connection;
//		try {
//			connection = JDBCUtility.getConnection();
//			System.out.println("Connection Established");
//			
////			String insertQuery= "insert into loginuser(firstname ,lastname, password )values(?,?,?)";
//			
//			String insertQuery= "select * from loginuser(fname ,lname, pass)values(?,?,?)";
//			PreparedStatement ps = connection.prepareStatement(insertQuery);
//			
//			// select name from hgch where fnam=? and lname=? and password=?
//			ps.setString(1, user.getFirstname());
//			ps.setString(2, user.getLastname());
//			
//			ps.setString(5, user.getPassword());
//			int rc = ps.executeUpdate();
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}

	@Override
	public boolean check(String email, String password) {

		Connection connection;
//		boolean success = false;
		User u = new User();
		try {
			connection = JDBCUtility.getConnection();
			System.out.println("Connection Established");
			String insertQuery = "select * from users where email= ? and password = ? ";
			
			
			PreparedStatement ps = connection.prepareStatement(insertQuery);
			
			ps.setString(1, email);
			ps.setString(2, password);
			

			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				u.setFirstname(rs.getString(1));
				u.setLastname(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setMobile(rs.getInt(4));
				u.setPassword(rs.getString(5));
				u.setCity(rs.getString(6));
				return true;
			}
//			if(rc > 0) {
//				success = true; 
//				System.out.println("login success");
//			}
//			else {
//				success = false;
//				System.out.println("login failed");
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
		
	
	
		
	}
}



