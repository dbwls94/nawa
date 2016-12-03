package org.nawa.common;

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;

import org.json.JSONObject;
import org.nawa.data.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletUtil {
	private static final Logger logger = LoggerFactory.getLogger(ServletUtil.class);
	
	public static void handleError(PrintWriter output, String msg, int responseCode){
		logger.error(msg);
		printOutputError(output, msg, responseCode);
	} //handleError
	
	public static void handleError(PrintWriter output, String msg, int responseCode, Exception e){
		logger.error(msg, e);
		printOutputError(output, msg, responseCode);
	} //handleError
	
	private static void printOutputError(PrintWriter output, String msg, int responseCode){
		output.println(new JSONObject()
			.put("success", "0")
			.put("rescode", responseCode)
			.put("msg", msg).toString());
	} //printOutputError
	
} //class