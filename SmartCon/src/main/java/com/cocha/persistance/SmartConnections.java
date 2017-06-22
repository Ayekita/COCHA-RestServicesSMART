package com.cocha.persistance;

import java.sql.Connection;
import java.sql.DriverManager;

import com.cocha.utils.PropertiesUtil;
import com.unisys.jdbc.dmsql.SQLException;

public class SmartConnections {
	volatile static SmartConnections s;
	public static Connection con = null;

	private SmartConnections() throws SQLException{
		String urlSmart = null;
		try {
			PropertiesUtil prop = new PropertiesUtil();
			String driverSmart = prop.retrunValueOfKey("driverSmart");	
			urlSmart = prop.retrunValueOfKey("urlSmart");		
			Class.forName(driverSmart);
			con = DriverManager.getConnection(urlSmart);
		} catch(Exception e){
			e.printStackTrace();
			System.err.println("Error contectando con la base de datos " + urlSmart+"\n "+ e);
		}
	}

	public Connection getConnSmart() throws SQLException{
		return con;
	}

	public static SmartConnections getInstance() throws Exception {
		if (con == null || con.isClosed()){
			s = null;
		}
		if (s == null) {
			synchronized(SmartConnections.class) {
				if (s == null) {
					s = new SmartConnections(); 
					System.out.println("Primera Conexion Smart Creada."); 
				}
			}
		}
		con = s.getConnSmart();
		return s; 
	}

}