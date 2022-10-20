package com.cybage.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtility {

	private static Connection connection  = null;
	public static Connection getConnection() throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");
		return connection;
	}
}
