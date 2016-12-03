package org.nawa.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class UTF8MultipartRequest extends MultipartRequest{

	public UTF8MultipartRequest(HttpServletRequest request) throws IOException {
		super(request, System.getProperty("java.io.tmpdir"), Integer.parseInt(Conf.get("post.max.size", 10*1024*1024 + "")), "utf8", new DefaultFileRenamePolicy());
	} //INIT

	@Override
	public String getParameter(String name) {
		String parameter = super.getParameter(name);
		if(parameter == null)
			return null;
//		try {
//			return URLDecoder.decode(parameter, "utf8");
			return parameter;
//		} catch (UnsupportedEncodingException e) { return null; }
	} //getParameter
	
	public Integer getParameterAsInt(String name) throws NumberFormatException {
		String parameter = getParameter(name);
		if(parameter == null)
			return null;
		return Integer.parseInt(parameter);
	} //getParameterAsInt
	
	public Date getParameterAsDate(String name, String dateFormat) throws ParseException{
		String parameter = getParameter(name);
		if(parameter == null)
			return null;
		return new SimpleDateFormat(dateFormat).parse(parameter);
	} //getParameterAsDate
} //class