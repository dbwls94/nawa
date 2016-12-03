package org.nawa.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieBox {
	private Map<String, String> map = new HashMap<String, String>();
	
	public CookieBox(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				String key = cookie.getName();
				String value = cookie.getValue();
				if(key==null || value==null)
					continue;
				try {
					String decodedValue = URLDecoder.decode(value, "utf-8");
					map.put(key, decodedValue);
				} catch (UnsupportedEncodingException e) { } //catch
			} //for cookie
		} //if
	} //INIT
	
	public String get(String key){
		return map.get(key);
	} //get
	
	public static String get(HttpServletRequest request, String key){
		if(key == null)
			return null;
		
		Cookie[] cookies = request.getCookies();
		if(cookies == null)
			return null;
		
		String value = null;
		for (Cookie cookie : cookies) {
			if(key.equals(cookie.getName())){
				value = cookie.getValue();
				break;
			} //if
		} //for cookie
		
		if(value == null)
			return null;
		
		try {
			return URLDecoder.decode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		} //catch
	} //get
	
	public static boolean set(HttpServletResponse response, String key, String value, int maxAge){
		try {
			Cookie cookie = new Cookie(key, URLEncoder.encode(value, "utf-8"));
			cookie.setPath("/");
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
			return true;
		} catch (UnsupportedEncodingException e) {
			return false;
		} //catch
	} //set
} //class