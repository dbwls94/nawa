package org.nawa.service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.exception.NotExistsException;

public class AdminService {
	private static AdminService INSTANCE=null;
	
	private AdminService(){
	} //INIT
	
	public static AdminService getInstance(){
		synchronized (AdminService.class) {
			if(INSTANCE==null)
				INSTANCE=new AdminService();
			return INSTANCE;
		} //sync
	} //getInstance
	
	public void insertRandomUserInfo(int count){
		for (int i = 0; i < count; i++)
			UserInfoDao.insert(createRandomUser());
	} //insertRandomUserInfo
	
	private JSONObject createRandomUser(){
		JSONObject userInfo = new JSONObject();
		Random rand = new Random();

		String email=null;
		do{
			email="test" + rand.nextInt() + "@test.com";
		} while(UserInfoDao.isEmailExists(email));

		userInfo.put("email", email);
		userInfo.put("regdate", new Date());
		userInfo.put("gender", rand.nextInt(10) > 5 ? UserInfoDao.GENDER_MALE : UserInfoDao.GENDER_FEMALE);
		userInfo.put("name", rand.nextInt()+"");
		userInfo.put("passwd", email + "-passwd");
		return userInfo;
	} //createRandomUser
	
	public void insertRandomProjectInfo(int count){
		Random rand = new Random();

		JSONObject leaderUser = createRandomUser();
		UserInfoDao.insert(leaderUser);
		
		for (int i = 0; i < count; i++)
			ProjectInfoDao.insert(createRandomProject(leaderUser));
	} //insertRandomProjectInfo
	
	private JSONObject createRandomProject(JSONObject user){
		Random rand = new Random();
		
		JSONObject projectInfo = new JSONObject();
		projectInfo.put("id", ProjectInfoDao.generateId());
		projectInfo.put("title", String.format("title %s", rand.nextInt()));
		projectInfo.put("description_title", String.format("desc %s", rand.nextInt()));
		projectInfo.put("description", String.format("desc %s", rand.nextInt()));
		projectInfo.put("leader_email", user.getString("email"));
		projectInfo.put("regdate", new Date());
		projectInfo.put("place", String.format("place %s", rand.nextInt()));
		projectInfo.put("meeting_date", new Date());
		projectInfo.put("meeting_time", String.format("meeting_time %s", rand.nextInt()));
		projectInfo.put("max_user_count", rand.nextInt(10)+10);
		projectInfo.put("recruit_due_date", new Date(System.currentTimeMillis() + (7*24*60*60*1000)));
		projectInfo.put("long_project", rand.nextInt(10) > 5 ? ProjectInfoDao.LONG_PROJECT_LONG : ProjectInfoDao.LONG_PROJECT_SHORT);
		
		return projectInfo;
	} //createRandomProjec
	
	public void insertRandomComments(int count) throws JSONException, NotExistsException{
		Random rand=new Random();
		
		JSONObject user = createRandomUser();
		UserInfoDao.insert(user);
		
		JSONObject project=createRandomProject(user);
		ProjectInfoDao.insert(project);
		
		for (int i = 0; i < count; i++) {
			Long parentSeq=null;
			if(rand.nextInt(10)>7){
				JSONArray replyList = ProjectReplyService.getInstance().getReplyByProjectId(project.getString("id"));
				if(replyList!=null && replyList.length()!=0){
					parentSeq = replyList.getJSONObject(replyList.length()-1).getLong("seq");
				} //if
			} //if
			ProjectReplyService.getInstance().insert(user.getString("email"), project.getString("id"), UUID.randomUUID().toString(), parentSeq, new Date());
		} //for i
	} //insertRandomComments
} //class