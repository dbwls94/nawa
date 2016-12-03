package org.nawa.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.nawa.exception.NotExistsException;

public class ProjectInfoDaoTest extends DaoTest {

	@Test(expected = NotExistsException.class)
	public void update_fail_존재하지_않는_id() throws NotExistsException {
		try {
			initDaoTest();
			ProjectInfoDao.update(getProjectInfoJsonObject());
		} catch (NotExistsException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			finalize();
		} // finally
	} // update fail
	
	@Test(expected = NotExistsException.class)
	public void delete_fail_존재하지_않는_id() throws NotExistsException{
		try {
			initDaoTest();
			ProjectInfoDao.delete(UUID.randomUUID().toString());
		} catch (NotExistsException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			finalize();
		} // finally	
	} //delete fail
	
	@Test
	public void list_column_check(){
		try{
			initDaoTest();
			
			JSONObject userInfo = getUserInfoJsonObject();
			UserInfoDao.insert(userInfo);
			
			JSONObject projectInfo = getProjectInfoJsonObject(userInfo);
			ProjectInfoDao.insert(projectInfo);
			
			JSONArray results = ProjectInfoDao.list(1);
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
	} //list column check
	
	@Test
	public void selectLeadingProject_column_check(){
		try{
			initDaoTest();
			
			JSONObject userInfo = getUserInfoJsonObject();
			UserInfoDao.insert(userInfo);
			
			JSONObject projectInfo = getProjectInfoJsonObject(userInfo);
			ProjectInfoDao.insert(projectInfo);
			
			JSONArray results = ProjectInfoDao.list(1);
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
} // class