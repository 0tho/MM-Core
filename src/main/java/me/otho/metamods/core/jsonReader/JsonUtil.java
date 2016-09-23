package me.otho.metamods.core.jsonReader;

public class JsonUtil {
	
	public static Object notNullOrDefault(Object obj, Object defaultValue) {
		return obj == null ? defaultValue : obj;
	}
	
}
