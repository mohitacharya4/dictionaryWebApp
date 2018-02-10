package com.dictionarywebapp.utilities;

import java.io.InputStream;
import java.util.Properties;

public class Details {
	
			InputStream in;

			public  InputStream getPropertyfile(String propertyfile)
			{
				try{
				in=Details.class.getResourceAsStream(propertyfile);
				
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return in;
			}

			public Properties getConstants(){
				Properties constants =new Properties();
				try{
				constants.load(getPropertyfile("/Constants.properties"));
				}catch (Exception e) {
					e.printStackTrace();
				}
				return constants;
			}
	
}
