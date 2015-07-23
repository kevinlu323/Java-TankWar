package com.linkui.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
	static Properties props = new Properties();
	static{
		try {
			props.load(TankClient.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//defined as private, others cannot new this class
	private PropertyMgr(){}
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}
}
