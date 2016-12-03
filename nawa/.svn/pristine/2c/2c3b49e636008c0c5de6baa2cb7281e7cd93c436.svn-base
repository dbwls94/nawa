package org.nawa.common;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.dao.NotificationDao;
import org.nawa.dao.NotificationType1Dao;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;

public class TestUtil {
	public static JSONArray getDummyUsers(int count){
		JSONArray users = new JSONArray();
		for(int i=0; i<count; i++){
			JSONObject user = getDummyUser();
			if(user == null)
				return null;
			users.put(user);
		} //for i
		
		return users;
	} //getDummyUsers
	
	public static JSONObject getDummyUser(){
		Random rand = new Random();
		
		JSONObject user = new JSONObject();
		user.put("email", rand.nextInt() + "@test.com");
		user.put("passwd", Sha512.encrypt(rand.nextInt() + ""));
		user.put("gender", UserInfoDao.GENDER_FEMALE);
		user.put("name", rand.nextInt() + "");
		user.put("regdate", new Date());
		
		UserInfoDao.insert(user);
		return user;
	} //getDummyUser
	
	public static JSONObject getDummyProject(int entryTerm, String leadingUserEmail){
		Random rand = new Random();
		
		JSONObject projectInfo = new JSONObject();
		projectInfo.put("id", ProjectInfoDao.generateId());
		projectInfo.put("title", String.format("title %s", rand.nextInt()));
		projectInfo.put("description", String.format("desc %s", rand.nextInt()));
		projectInfo.put("leader_email", leadingUserEmail);
		projectInfo.put("regdate", new Date());
		projectInfo.put("place", String.format("place %s", rand.nextInt()));
		projectInfo.put("meeting_date", new Date());
		projectInfo.put("meeting_time", String.format("meeting_time %s", rand.nextInt()));
		projectInfo.put("max_user_count", rand.nextInt(10)+10);
		projectInfo.put("recruit_due_date", new Date(System.currentTimeMillis() + (7*24*60*60*1000)));
		projectInfo.put("long_project", rand.nextInt(10) > 5 ? ProjectInfoDao.LONG_PROJECT_LONG : ProjectInfoDao.LONG_PROJECT_SHORT);
		
		return projectInfo;
	} //getDummyProject
	
	public static NotificationType1Dao getDummyNotiType1(JSONObject ownerUser, JSONObject followingUser, JSONObject project){
		NotificationType1Dao noti = new NotificationType1Dao();
		noti.setRegdate(new Date());
		noti.setFollowingUserEmail(followingUser.getString("email"));
		noti.setIsRead(0);
		noti.setNotiId(UUID.randomUUID().toString());
		noti.setOwner(ownerUser.getString("email"));
		noti.setParticipatingProjectId(project.getString("id"));
		boolean result = NotificationDao.insert(noti);
		
		if(result == false)
			return null;
		
		return noti;
	} //getDummyNoti
} //class