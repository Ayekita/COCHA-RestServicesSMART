package com.cocha.servicefasttwo.persistance;
//package com.cocha.all.persistance;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class SqlConnections {
//	public Connection getSqlConnection(String driverSql, String urlSql) throws ClassNotFoundException{
//		Connection con = null;
//		try{			
//			Class.forName(driverSql);
//			con = DriverManager.getConnection(urlSql);
//		}
//		catch(SQLException e){
//			System.err.println("Error contectando con la base de datos "+urlSql+e);
//		}
//		return con;
//	}
//}
