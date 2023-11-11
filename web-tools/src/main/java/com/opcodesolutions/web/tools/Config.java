package com.opcodesolutions.web.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Config {

	private static final Properties appProperties = new Properties();
	static {
		String appPropertiesUrl = get("app.properties.url", "file:///opt/app.properties");
		try (InputStream inputStream = new URL(appPropertiesUrl).openStream()) {
			appProperties.load(inputStream);
		} catch (IOException e) {
			System.err.println("Failed to load appProperties from url: " + appPropertiesUrl);
			e.printStackTrace();
		}
	}

	public static final String get(String key) {
		return get(key, null);
	}

	public static final String get(String key, String defaultValue) {
		String value = System.getProperty(key);
		if (value == null) {
			value = System.getenv(key.replace('.', '_').toUpperCase());
		}
		if (value == null) {
			value = appProperties.getProperty(key);
		}
		return value != null ? value : defaultValue;
	}

}
