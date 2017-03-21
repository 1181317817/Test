package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static String getPropertiesValue(File file, String key) throws IOException {
		try {
			// 获取读取流
			InputStream in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			// 获取端口号
			return properties.getProperty(key);
		} catch (IOException e) {
			String path = file.getPath();
			path = path.replace("\\", "");
			file = new File(path);
			
			// 获取读取流
			InputStream in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			return properties.getProperty(key);
		}
	}
}
