package org.nawa.common.json;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class JsonSerializable {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public String toJson(){
		return toJsonObject().toString();
	} //toJson
	
	public JSONObject toJsonObject(){
		JSONObject json = new JSONObject();
			
		for(Field field : getClass().getDeclaredFields()){
			JsonColumn jsonCol = field.getAnnotation(JsonColumn.class);
			if(jsonCol==null)
				continue;
			field.setAccessible(true);
			
			try {
				String key = jsonCol.key();
				Object value = field.get(this);
				
				if(value instanceof Date)
					value = dateFormat.format(value);
				
				json.put(key, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			} //catch
		} //for field
		
		return json;
	} //toJsonObject
	
	@Override
	public String toString() {
		return toJson();
	}
} //class