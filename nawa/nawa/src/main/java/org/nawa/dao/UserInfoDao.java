package org.nawa.dao;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.MysqlUtil;
import org.nawa.common.json.JsonSerializable;
import org.nawa.rdb.ConnectionSource;
import org.nawa.rdb.DbHandler;

public class UserInfoDao extends JsonSerializable{
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;

	private UserInfoDao() {
	} // INIT

	public static JSONArray select(boolean includePasswd){
		String query="select email, ";
		if(includePasswd == true)
			query += "passwd, ";
		query += "name, "
			+ "gender, "
			+ "regdate, "
			+ "facebook_access_token " //5
			+ "from user_info";
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query);
	} //select
	
	public static JSONObject select(String email){
		String query="select email, " //0
									+ "name, "
									+ "gender, "
									+ "regdate, "
									+ "facebook_access_token " //5
									+ "from user_info "
									+ "where email = ?";
		JSONArray result = ConnectionSource.getJdbcTemplate().queryForJsonArray(query, email);
		if(result == null || result.length() == 0)
			return null;
		return result.getJSONObject(0);
	} //select
	
	public static JSONObject selectWithRelation(String userEmail, String myEmail){
		String query="select email, "
									+ "name, "
									+ "gender, "
									+ "regdate, "
									+ "facebook_access_token, "
										+ "( select count(*) "
										+ " from user_friend "
										+ " where user = ? and to_user = ? ) as am_i_follow_him ,"
										
										+ "( select count(*) "
										+ " from user_friend "
										+ " where user = ? and to_user = ? ) as is_him_follow_me "
									+ "from user_info u, user_friend uf "
									+ "where email = ?";
		JSONArray result = ConnectionSource.getJdbcTemplate().queryForJsonArray(query, myEmail ,userEmail, userEmail, myEmail, userEmail);
		if(result == null || result.length() == 0)
			return null;
		return result.getJSONObject(0);
	} //selectWithRelation
	
	public static JSONArray searchByName(String keyword){
		List<String> keywords = MysqlUtil.makeConditionString4Search(keyword);
		
		String query="select email, " //0
									+ "name, "
									+ "gender, "
									+ "regdate, "
									+ "facebook_access_token " //4
									+ "from user_info ";
		
		for (int i = 0; i < keywords.size(); i++) {
			if(i==0)
				query += "where name like ? ";
			else
				query += "or name like ? ";
		} //for i
	
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, keywords.toArray(new String[keywords.size()]));
	} //select
	
	public static boolean isEmailExists(String email) {
		String query="select count(*) as count from user_info where email = ?";
		String[][] result = DbHandler.preparedSelect(query, email);
		if(result==null || result.length==0 || result[0].length==0)
			return false;
		return "1".equalsIgnoreCase(result[0][0]);
	} //isEmailExists
	
	public static void insert(JSONObject userInfo){
		String query="insert into user_info (email, passwd, name, gender, regdate, facebook_access_token) values (?,?,?,?,?,?)";
		ConnectionSource.getJdbcTemplate().update(query, 
				userInfo.isNull("email") ? null : userInfo.get("email"),
				userInfo.isNull("passwd") ? null : userInfo.get("passwd"),
				userInfo.isNull("name") ? null : userInfo.get("name"),
				userInfo.isNull("gender") ? null : userInfo.get("gender"),
				userInfo.isNull("regdate") ? null : userInfo.get("regdate"),
				userInfo.isNull("facebook_access_token") ? null : userInfo.get("facebook_access_token"));
	} //insert
	
	public static void update(JSONObject userInfo){
		String query="update user_info set passwd=?, name=?, gender=?, facebook_access_token=? where email=?";
		ConnectionSource.getJdbcTemplate().update(query, 
				userInfo.isNull("passwd") ? null : userInfo.get("passwd"),
				userInfo.isNull("name") ? null : userInfo.get("name"),
				userInfo.isNull("gender") ? null : userInfo.get("gender"),
				userInfo.isNull("facebook_access_token") ? null : userInfo.get("facebook_access_token"),
				userInfo.isNull("email") ? null : userInfo.get("email"));
	} //update
	
	public static void delete(String email){
		String query="delete from user_info where email = ?";
		ConnectionSource.getJdbcTemplate().update(query, email);
	} //delete
} // class