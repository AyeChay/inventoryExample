package com.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

		public static Connection getConnection()
		{
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "Thandar@2018#!");
			} catch (ClassNotFoundException e) {
				System.out.println("Loading class: "+ e.getMessage());
			} catch (SQLException e) {
				System.out.println("Database Connection: "+ e.getMessage());
			}
			
			return con;
		}
		
}
