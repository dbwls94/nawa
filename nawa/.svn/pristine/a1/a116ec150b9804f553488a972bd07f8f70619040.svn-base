package org.nawa.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.nawa.dao.DaoTest;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.dao.UserParticipatedProjectDao;
import org.nawa.rdb.ConnectionSource;

public class ProjectServiceTest  extends DaoTest{

	@Test
	public void list_column_check(){
		try{
			initDaoTest();
			
			JSONObject userInfo = getUserInfoJsonObject();
			UserInfoDao.insert(userInfo);
			
			JSONObject projectInfo = getProjectInfoJsonObject(userInfo);
			ProjectInfoDao.insert(projectInfo);
			
			JSONArray results = ProjectService.getInstance().list(1);
			assertTrue(results.length() != 0);
			JSONObject result = results.getJSONObject(0);
			assertNotNull(result.get("id"));
			assertNotNull(result.get("title"));
			assertNotNull(result.get("description"));
			assertNotNull(result.get("description_title"));
			assertNotNull(result.get("leader_email"));
			assertNotNull(result.get("regdate"));
			assertNotNull(result.get("place"));
			assertNotNull(result.get("meeting_date"));
			assertNotNull(result.get("meeting_time"));
			assertNotNull(result.get("max_user_count"));
			assertNotNull(result.get("recruit_due_date"));
			assertNotNull(result.get("is_active"));
			assertNotNull(result.get("long_project"));
			assertNotNull(result.get("leader_name"));
			assertNotNull(result.get("participant_actual_count"));
			assertNotNull(result.get("comments_count"));
			
			assertNotNull(result.get("recruit_due_date_relative")); //service 에서 추가된 field
		} catch(Exception e){
			e.printStackTrace();
			fail();
		} finally{
			finalize();
		} //finally
	} //list column check
	
	@Test
	public void selectLeadingProjects_column_check(){
		try{
			initDaoTest();
			JSONObject userInfo = getUserInfoJsonObject();
			UserInfoDao.insert(userInfo);
			ProjectInfoDao.insert(getProjectInfoJsonObject(userInfo));
			JSONArray results = ProjectService.getInstance().getLeadingProjects(userInfo.getString("email"));
			assertTrue(results.length() != 0);
			JSONObject result = results.getJSONObject(0);
			assertNotNull(result.get("id"));
			assertNotNull(result.get("title"));
			assertNotNull(result.get("description"));
			assertNotNull(result.get("description_title"));
			assertNotNull(result.get("leader_email"));
			assertNotNull(result.get("regdate"));
			assertNotNull(result.get("place"));
			assertNotNull(result.get("meeting_date"));
			assertNotNull(result.get("meeting_time"));
			assertNotNull(result.get("max_user_count"));
			assertNotNull(result.get("recruit_due_date"));
			assertNotNull(result.get("is_active"));
			assertNotNull(result.get("long_project"));
			assertNotNull(result.get("leader_name"));
			assertNotNull(result.get("participant_actual_count"));
			assertNotNull(result.get("comments_count"));
			
			assertNotNull(result.get("recruit_due_date_relative")); //service 에서 추가된 field
		} catch(Exception e){
			e.printStackTrace();
			fail();
		} finally{
			finalize();
		} //finally
	} //selectLeadingProjects_column_check
	
	@Test
	public void getParticipatingProjects_column_check(){
		try{
			initDaoTest();
			
			JSONObject userInfo = getUserInfoJsonObject();
			UserInfoDao.insert(userInfo);
			
			JSONObject projectInfo = getProjectInfoJsonObject(userInfo);
			ProjectInfoDao.insert(projectInfo);
	
			UserParticipatedProjectDao.insert(userInfo, projectInfo);
			
			JSONArray results = ProjectService.getInstance().getParticipatingProjects(userInfo.getString("email"), true);
			assertTrue(results.length() != 0);
			JSONObject result = results.getJSONObject(0);
			assertNotNull(result.get("id"));
			assertNotNull(result.get("title"));
			assertNotNull(result.get("description"));
			assertNotNull(result.get("description_title"));
			assertNotNull(result.get("leader_email"));
			assertNotNull(result.get("regdate"));
			assertNotNull(result.get("place"));
			assertNotNull(result.get("meeting_date"));
			assertNotNull(result.get("meeting_time"));
			assertNotNull(result.get("max_user_count"));
			assertNotNull(result.get("recruit_due_date"));
			assertNotNull(result.get("is_active"));
			assertNotNull(result.get("long_project"));
			assertNotNull(result.get("leader_name"));
			assertNotNull(result.get("participant_actual_count"));
			assertNotNull(result.get("comments_count"));
			
			assertNotNull(result.get("recruit_due_date_relative")); //service 에서 추가된 field
		} catch(Exception e){
			e.printStackTrace();
			fail();
		} finally{
			finalize();
		} //finally
	} //selectLeadingProjects_column_check
} //class