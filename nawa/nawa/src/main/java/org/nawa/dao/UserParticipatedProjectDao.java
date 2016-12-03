package org.nawa.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.rdb.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class UserParticipatedProjectDao {
	private static final Logger logger = LoggerFactory.getLogger(UserParticipatedProjectDao.class);
	
	public static void insert(JSONObject userInfo, JSONObject projectInfo){
		Preconditions.checkNotNull(userInfo);
		Preconditions.checkNotNull(projectInfo);
		
		String query="INSERT INTO user_participated_project (project_id, user_email, regdate, leader_accept) VALUES (?, ?, now(), 0)";
		ConnectionSource.getJdbcTemplate().update(query, projectInfo.getString("id"), userInfo.getString("email"));
	} //insert
	
	public static void delete(JSONObject user, JSONObject project){
		Preconditions.checkNotNull(user, "user is null");
		Preconditions.checkNotNull(project, "project is null");
		
		String query = "DELETE FROM user_participated_project "
				+ "WHERE user_email = ? "
				+ "AND project_id = ?"
				+ "AND regdate = SELECT * FROM (SELECT MAX(regdate) FROM user_participated_project WHERE user_email = ? AND project_id = ?) AS temp)";
		ConnectionSource.getJdbcTemplate().update(query, user.getString("email"), project.getString("id"), 
				user.getString("email"), project.getString("id"));
	} //delete
	
	public static void updateAccept(JSONObject project, JSONObject acceptTargetUser, boolean accept){
		Preconditions.checkNotNull(project, "project is null");
		Preconditions.checkNotNull(acceptTargetUser, "acceptTargetUser is null");
		
		String query = "UPDATE user_participated_project SET leader_accept = ? "
				+ "WHERE project_id = ? "
				+ "AND user_email = ?"
				+ "AND regdate = (SELECT * FROM (SELECT MAX(regdate) FROM user_participated_project WHERE project_id = ? AND user_email = ?) AS temp)";
		ConnectionSource.getJdbcTemplate().update(query, accept ? 1 : 2, 
				project.getString("id"), 
				acceptTargetUser.getString("email"),
				project.getString("id"), 
				acceptTargetUser.getString("email"));
	} //updateAccept
	
	public static boolean isParticipated(JSONObject user, JSONObject project){
		Preconditions.checkNotNull(user, "user is null");
		Preconditions.checkNotNull(project, "project is null");
		
		String query = "select count(*) from user_participated_project where user_email = ? and project_id = ?";
		return 1 == ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{user.getString("email"), project.getString("id")}, Integer.class);
	} //isParticipated
	
	public static JSONArray selectWaitingAcceptUsers(String projectId){
		String query="select u.email, "
				+ "u.name, "
				+ "u.gender, "
				//+ "u.pic_path, "
				+ "u.regdate, "
				+ "u.facebook_access_token "
				+ "from user_participated_project p, user_info u "
				+ "where p.user_email = u.email "
				+ "and p.leader_accept = 0 "
				+ "and p.project_id = ?";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, projectId);
	} ///getWatingAcceptList
	
	public static boolean isExistsWaitingAccept(String projectId, String userEmail){
		String query = "select count(*) from user_participated_project where project_id = ? and user_email = ? and leader_accept = 0";
		return 1 == ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{projectId, userEmail}, Integer.class);
	} //isExistsWaitingAccept

	public static JSONArray selectParticipatingUsers(String projectId){
		String query="select u.email, "
				+ "u.name, "
				+ "u.gender, "
				+ "u.regdate, "
				+ "u.facebook_access_token "
				+ "from user_participated_project p, user_info u "
				+ "where p.user_email = u.email "
				+ "and p.leader_accept = 1 "
				+ "and p.project_id = ?";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, projectId);
	} ///selectParticipatingUsers
	
	public static JSONArray selectParticipatingProjects(String email, boolean includeNotAccepted){
		String query = "SELECT p.id, "
				+ "p.title, "
				+ "p.description, "
				+ "p.description_title, "
				+ "p.leader_email, "
				+ "p.regdate, " 
				+ "p.place, "
				+ "p.meeting_date, "
				+ "p.meeting_time, "
				+ "p.max_user_count, "
				+ "p.recruit_due_date, "
				+ "p.recruit_due_date > now() as is_active, "
				+ "p.long_project, "
				+ "u.name as leader_name, "
				
				+ "IFNULL((SELECT COUNT(*) "
				+ "FROM project_info p1, user_participated_project up1 "
				+ "WHERE p1.id = p.id "
				+ "AND p1.id = up1.project_id "
				+ "AND up1.leader_accept = 1 "
				+ "GROUP BY p1.id ), 0) AS participant_actual_count, "
					
				+ " (SELECT count(*) "
				+ "FROM project_reply pr "
				+ "WHERE pr.project_id = p.id) AS comments_count "
				
				+ "FROM project_info p, user_info u, user_participated_project upp "
				+ "WHERE p.leader_email = u.email "
				+ "AND p.id = upp.project_id "
				+ "AND upp.user_email = ?";
		if(includeNotAccepted == false)
			query += "AND upp.leader_accept = 1";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, email);
	} //selectParticipatingProjects
	
	public static int selectParticipatingProjectsCount(String email, boolean includeNotAccepted){
		String query = "SELECT count(*) "
				+ "FROM project_info pi, user_participated_project upp "
				+ "WHERE upp.user_email = ? "
				+ "AND pi.id = upp.project_id ";
		if(includeNotAccepted == false)
				query += "AND upp.leader_accept = 1";
		
		return ConnectionSource.getJdbcTemplate().queryForObject(query,  new Object[]{email}, Integer.class);
	} //selectParticipatingProjectsCount
} //class
