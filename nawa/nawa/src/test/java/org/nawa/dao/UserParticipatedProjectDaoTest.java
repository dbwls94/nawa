package org.nawa.dao;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class UserParticipatedProjectDaoTest extends DaoTest {

	@Test
	public void selectParticipatingProjects_column_check(){
		try{
			initDaoTest();
			JSONObject userInfo = getUserInfoJsonObject();
			UserInfoDao.insert(userInfo);
			
			JSONObject projectInfo = getProjectInfoJsonObject(userInfo);
			ProjectInfoDao.insert(projectInfo);
			
			UserParticipatedProjectDao.insert(userInfo, projectInfo);
			
			JSONArray results = UserParticipatedProjectDao.selectParticipatingProjects(userInfo.getString("email"), true);
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
		} catch(Exception e){
			e.printStackTrace();
			fail();
		} finally{
			finalize();
		} //finally
	} //selectLeadingProject_column_check
} //class