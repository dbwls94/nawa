package org.nawa.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.rdb.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class UserFriendDao {
	private static final Logger logger=LoggerFactory.getLogger(UserFriendDao.class);

	public static void follow(JSONObject user, JSONObject toUser){
		Preconditions.checkNotNull(user, "user is null");
		Preconditions.checkNotNull(toUser, "toUser is null");
		
		String query="insert into user_friend (user, to_user) values (?,?)";
		ConnectionSource.getJdbcTemplate().update(query, user.getString("email"), toUser.getString("email"));
	} //insert
	
	public static void unfollow(JSONObject user, JSONObject toUser){
		Preconditions.checkNotNull(user, "user is null");
		Preconditions.checkNotNull(toUser, "toUser is null");
		
		String query="delete from user_friend where user = ? and to_user = ?";
		ConnectionSource.getJdbcTemplate().update(query, user.getString("email"), toUser.getString("email"));
	} //unfollow
	
	public static JSONArray selectFollowing(String email){
		Preconditions.checkNotNull(email);
		
		String query = "select u.email, u.name, u.gender, u.regdate, u.facebook_access_token "
				+ "from user_info u, user_friend f "
				+ "where u.email = f.to_user "
				+ "and f.user = ?";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, email);
	} //selectFollowing
	
	public static JSONArray selectFollowed(String email){
		Preconditions.checkNotNull(email);
		
		String query = "select u.email, u.passwd, u.name, u.gender, u.regdate, u.facebook_access_token "
				+ "from user_info u, user_friend f "
				+ "where u.email = f.user "
				+ "and f.to_user = ?";
		
		return ConnectionSource.getJdbcTemplate().queryForJsonArray(query, email);
	} //selectFollowing
	
	public static int selectFollowingCount(String email){
		Preconditions.checkNotNull(email);
		
		String query="select count(*) from user_friend where user = ?";
		return ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{email}, Integer.class);
	} //selectFollowingCount
	
	public static int selectFollowedCount(String email){
		Preconditions.checkNotNull(email);
		
		String query="select count(*) from user_friend where to_user = ?";
		return ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{email}, Integer.class);
	} //selectFollowedCount
	
	public static boolean isAFollowingB(String aEmail, String bEmail){
		Preconditions.checkNotNull(aEmail);
		Preconditions.checkNotNull(bEmail);
		
		String query = "select count(*) from user_friend where user = ? and to_user = ?";
		return 1 == ConnectionSource.getJdbcTemplate().queryForObject(query, new Object[]{aEmail, bEmail}, Integer.class);
	} //isFollowing
} //class 