package com.cocha.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	public final static String CONFIG_FILE_NAME = "resources/SmartServices.properties";
	Properties propertiesEntry;

	public PropertiesUtil(){
		this.propertiesEntry = new Properties();
		try{
			propertiesEntry.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
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