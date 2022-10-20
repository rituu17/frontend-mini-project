package com.cybage.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cybage.bean.User;
import com.cybage.service.UserService;
import com.cybage.service.UserServiceImpl;



@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserService userService =new UserServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		java.util.List<User>empList = userService.getAllUsers();
//		request.setAttribute("empList", empList);
		RequestDispatcher  requestdispatcher = request.getRequestDispatcher("register.jsp");
		requestdispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		User user = new User();
		user.setFirstname(request.getParameter("firstname"));
		user.setLastname(request.getParameter("lastname"));
		user.setEmail(request.getParameter("email"));
		user.setMobile(Integer.parseInt((request.getParameter("mobile"))));
		user.setPassword(request.getParameter("password"));
		user.setCity(request.getParameter("city"));
		try {
//			employeeService.add(employee);
			boolean flag =userService.add(user);
			if(flag)
			{
				
				System.out.println("recorded inserted successfully");
			}
			else {
				System.out.println("some error in insertion");
				
			}
		
		} catch ( SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
