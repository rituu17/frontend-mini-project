package com.cybage.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybage.bean.User;
import com.cybage.service.UserService;
import com.cybage.service.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	 
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		UserService us = new UserServiceImpl();
//		user.setFirstname(request.getParameter("firstname"));
		String email = request.getParameter("email");
		String pw = request.getParameter("password");
//		user.setLastname(request.getParameter("lastname"));
//		user.setPassword(request.getParameter("password"));
//		
		try {
//			employeeService.add(employee);
			boolean flag =us.check(email, pw);
			if(flag)
			{
				
				System.out.println("login successfully");
			}
			else {
				System.out.println("login faileddddd");
				
			}
		
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
		
	}

}
