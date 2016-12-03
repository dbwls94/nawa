package org.nawa.dao;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.MysqlUtil;
import org.nawa.common.json.JsonSerializable;
import org.nawa.exception.NotExistsException;
import org.nawa.rdb.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description project_info ���̺�� ��Ī�Ǵ� DAO Ŭ����
 * @author leejy
 * @date 2015. 02. 09
 */
public class ProjectInfoDao extends JsonSerializable{
	private static final Logger logger = LoggerFactory.getLogger(ProjectInfoDao.class);
	
	public static final int PROJECT_STATUS_ING = 1;
	public static final int PROJECT_STATUS_FULL = 2;
	public static final int PROJECT_STATUS_FINISHED = 3;
	
	public static final int LONG_PROJECT_LONG=1;
	public static final int LONG_PROJECT_SHORT=2;

	private ProjectInfoDao() {
	} // INIT
	
	public static void insert(JSONObject projectInfo){
		String query = "insert into project_info (id, title, description, description_title, leader_email, regdate, place, "
				+ "meeting_date, meeting_time, max_user_count, recruit_due_date, long_project) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
		
		ConnectionSource.getJdbcTemplate().update(query,
				projectInfo.isNull("id") ? null : projectInfo.get("id"),
				projectInfo.isNull("title") ? null : projectInfo.get("title"),
				projectInfo.isNull("description") ? null : projectInfo.get("description"),
				projectInfo.isNull("description_title") ? null : projectInfo.get("description_title"),
				projectInfo.isNull("leader_email") ? null : projectInfo.get("leader_email"),
				projectInfo.isNull("regdate") ? null : projectInfo.get("regdate"),
				projectInfo.isNull("place") ? null : projectInfo.get("place"),
				projectInfo.isNull("meeting_date") ? null : projectInfo.get("meeting_date"),
				projectInfo.isNull("meeting_time") ? null : projectInfo.get("meeting_time"),
				projectInfo.isNull("max_user_count") ? null : projectInfo.get("max_user_count"),
				projectInfo.isNull("recruit_due_date") ? null : projectInfo.get("recruit_due_date"),
				projectInfo.isNull("long_project") ? null : projectInfo.get("long_project"));
	} //insert
	
	public static void update(JSONObject projectInfo) throws NotExistsException {
		String query = "update project_info set title=?, description=?, description_title=?, leader_email=?, place=?, "
				+ "meeting_date=?, meeting_time=?, max_user_count=?, recruit_due_date=?, long_project=? where id = ?";
		int result = ConnectionSource.getJdbcTemplate().update(query, 
				projectInfo.isNull("title") ? null : projectInfo.get("title"),
				projectInfo.isNull("description") ? null : projectInfo.get("description"),
				projectInfo.isNull("description_title") ? null : projectInfo.get("description_title"),
				projectInfo.isNull("leader_email") ? null : projectInfo.get("leader_email"),
				projectInfo.isNull("place") ? null : projectInfo.get("place"),
				projectInfo.isNull("meeting_date") ? null : projectInfo.get("meeting_date"),
				projectInfo.isNull("meeting_time") ? null : projectInfo.get("meeting_time"),
				projectInfo.isNull("max_user_count") ? null : projectInfo.get("max_user_count"),
				projectInfo.isNull("recruit_due_date") ? null : projectInfo.get("recruit_due_date"),
				projectInfo.isNull("long_project") ? null : projectInfo.get("long_project"),
				projectInfo.isNull("id") ? null : projectInfo.get("id"));
		if(result == 0)
			throw new NotExistsException("failed to update");
	} //update
	
	public static void delete(String id) throws NotExistsException{
		String query="delete from project_info where id = ?";
		int result = ConnectionSource.getJdbcTemplate().update(query, id);
		if(result == 0)
			throw new NotExistsException("failed to delete");
	} //delete
	
	public static String generateId(){
		return UUID.randomUUID().toString();
	} //generateId
	
	public static JSONArray select(){
		String query="select id, title, description, description_title, leader_email, regdate, place, "
				+ "meeting_date, meeting_time, max_user_count, recruit_due_date, long_project from project_info";
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query);
	} //select
	
	public static JSONArray list(int page){
		String query="SELECT p.id, "
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
					
				+ " (SELECT COUNT(*) "
				+ "FROM project_reply pr "
				+ "WHERE pr.project_id = p.id) AS comments_count "
				
				+ "FROM project_info p, user_info u "
				+ "WHERE p.leader_email = u.email "
				+ "ORDER BY regdate desc " 
				+ "LIMIT ?, ?";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, (page*10)-10, (page*10));
	} //select
	
	public static JSONObject select(String id) throws NotExistsException{
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
				
				+ "FROM project_info p, user_info u "
				+ "WHERE p.leader_email = u.email "
				+ "AND p.id = ?";
		
		JSONArray result = ConnectionSource.getJdbcTemplate().queryForJsonArray(query, id);
		if(result == null || result.length() == 0)
			throw new NotExistsException(id);
		return result.getJSONObject(0);
	} //select
	
	public static JSONArray selectLeadingProjects(String email){
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
				
				+ "FROM project_info p, user_info u "
				+ "WHERE p.leader_email = u.email "
				+ "AND p.leader_email = ?";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, email);
	} //selectLeadingProjects
	
	public static int selectLeadingProjectsCount(String email){
		String query="select count(*) from project_info where leader_email = ?";
		return ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{email}, Integer.class);
	} //selectLeadingProjectsCount

	public static JSONArray searchByTitle(String keyword){
		keyword = MysqlUtil.makeRegexpKeyword(keyword);
		String query="select id, title, description, description_title, leader_email, regdate, place, "
				+ "meeting_date, meeting_time, max_user_count, recruit_due_date, long_project from project_info where title regexp(?)";
	
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, keyword);
	} //searchByTitle
	
	public static JSONArray searchByTitleInMyProject(String email, String keyword){
		keyword = MysqlUtil.makeRegexpKeyword(keyword);
		String query="select id, title, description, description_title, leader_email, regdate, place, "
				+ "meeting_date, meeting_time, max_user_count, recruit_due_date, long_project from project_info "
				+ "where leader_email = ? and title regexp(?)";
	
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, email, keyword);
	} //searchByTitleInMyProject
} // class