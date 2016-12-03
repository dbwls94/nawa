package org.nawa.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.json.JsonSerializable;
import org.nawa.exception.NotExistsException;
import org.nawa.rdb.ConnectionSource;

public class ProjectReplyDao extends JsonSerializable{

	public static void insert(JSONObject reply){
		String query = "insert into project_reply (seq, project_id, depth, regdate, user_email, content) values(?,?,?,?,?,?)";
		ConnectionSource.getJdbcTemplate().update(query, 
				reply.isNull("seq") ? null : reply.get("seq"),
				reply.isNull("project_id") ? null : reply.get("project_id"),
				reply.isNull("depth") ? null : reply.get("depth"),
				reply.isNull("regdate") ? null : reply.get("regdate"),
				reply.isNull("user_email") ? null : reply.get("user_email"),
				reply.isNull("content") ? null : reply.get("content"));
	} //insert
	
	public static void update(JSONObject reply) {
		String query = "update project_reply set content = ? where seq = ? and project_id = ?";
		ConnectionSource.getJdbcTemplate().update(query, 
				reply.isNull("content") ? null : reply.get("content"),
				reply.isNull("seq") ? null : reply.get("seq"),
				reply.isNull("project_id") ? null : reply.get("project_id"));
	} //update

	public static JSONObject select(String projectId, Long seq) throws NotExistsException {
		String query ="select seq, project_id, depth, user_email, regdate, content from project_reply where project_id=? and seq=?";
		JSONArray result = ConnectionSource.getJdbcTemplate().queryForJsonArray(query, projectId, seq);
		if(result == null || result.length() == 0)
			throw new NotExistsException(String.format("project_id: %s, seq: %s", projectId, seq));
		return result.getJSONObject(0);
	} //select
	
	public static JSONArray select(){
		String query="select seq, project_id, depth, user_email, regdate, content from project_reply";
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query);
	} //select
	
	public static JSONArray selectByProjectId(String projectId){
		String query="select "
						+ "pr.seq, pr.project_id, pr.depth, pr.user_email, pr.regdate, pr.content, ui.name "
					+ "from "
						+ "project_reply pr, user_info ui "
					+ "where "
						+ "pr.project_id = ? "
						+ "and pr.user_email = ui.email "
					+ "order by depth";
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, projectId);
	} //selectByProjectId
	
	public static String selectDepth(String projectId, Long seq){
		String query="select depth from project_reply where project_id = ? and seq = ?";
		return ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{projectId, seq}, String.class);
	} //selectDepth
	
	public static Long selectNextSeq(String projectId) {
		String query="select max(seq) as seq from project_reply where project_id = ?";
		Long currentMaxValue = ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{projectId}, Long.class);
		if(currentMaxValue == null)
			return 1L;
		return currentMaxValue + 1L;
	} //getNextSeq
	
	public static void delete(String projectId, Long seq) {
		String query="delete from project_reply where project_id = ? and seq = ?";
		ConnectionSource.getJdbcTemplate().update(query, projectId, seq);
	} //delete
} // class