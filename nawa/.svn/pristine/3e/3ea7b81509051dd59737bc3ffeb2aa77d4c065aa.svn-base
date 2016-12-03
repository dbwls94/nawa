package org.nawa.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nawa.rdb.SchemaChecker;

public class DaoTest {
	protected void initDaoTest(){
		System.setProperty("is.unit.test", "true");
		SchemaChecker.dropAllTables();
		SchemaChecker.check();
	} //initDaoTest
	
	protected void finalize(){
		SchemaChecker.dropAllTables();
	} //finalize
	
	protected JSONObject getProjectInfoJsonObject(JSONObject userInfo) throws JSONException, ParseException{
		Random rand = new Random();
		
		String leaderEmail = userInfo.getString("email");
		
		JSONObject projectInfo = new JSONObject();
		projectInfo.put("id", UUID.randomUUID().toString());
		projectInfo.put("title", rand.nextInt() + "");
		projectInfo.put("description", rand.nextInt() + "");
		projectInfo.put("description_title", rand.nextInt() + "");
		projectInfo.put("leader_email", leaderEmail);
		projectInfo.put("regdate", new Date());
		projectInfo.put("place", rand.nextInt() + "");
		projectInfo.put("meeting_date", new SimpleDateFormat("yyyyMMdd").parse("20101001"));
		projectInfo.put("meeting_time", new Date());
		projectInfo.put("max_user_count", rand.nextInt(10));
		projectInfo.put("recruit_due_date", new Date());
		projectInfo.put("long_project", 1);
		return projectInfo;
	} //getProjectInfoJsonObject
	
	protected JSONObject getProjectInfoJsonObject() throws JSONException, ParseException{
		return getProjectInfoJsonObject(getUserInfoJsonObject());
	} //getProjectInfoJsonObject
	
	protected JSONObject getUserInfoJsonObject(){
		Random rand = new Random();
		JSONObject user = new JSONObject();
		user.put("email", rand.nextInt() + "@email.com");
		user.put("passwd", rand.nextInt()+"");
		user.put("name", rand.nextInt()+"");
		user.put("gender", 1);
		user.put("regdate", new Date());	
		return user;
	} //getUserInfoJsonObject
} //class