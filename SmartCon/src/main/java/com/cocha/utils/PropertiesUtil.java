package com.cocha.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public final static String CONFIG_FILE_NAME = "resources/SmartServices.properties";
	Properties propertiesEntry;

	public PropertiesUtil(){
		this.propertiesEntry = new Properties();
		try{
			//propertiesEntry.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
			InputStream is = new FileInputStream("C:\\Users\\avaldivia\\Documents\\Proyectos Java\\RestServicesSmart\\"
					+ "RestServicesSMART\\SmartLineaCreditoNemo\\src\\main\\webapp\\WEB-INF\\classes\\resources\\SmartServices.properties");
			propertiesEntry.load(is);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String retrunValueOfKey(String keyOfProperties){
		String value= "";
		value = (propertiesEntry.getProperty(keyOfProperties) == null) ? ""
				: propertiesEntry.getProperty(keyOfProperties).toString();				
		return value;		
	}
}