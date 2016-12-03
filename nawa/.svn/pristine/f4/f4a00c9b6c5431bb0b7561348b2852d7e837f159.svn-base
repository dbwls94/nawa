package org.nawa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.common.Conf;
import org.nawa.dao.UserFriendDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.exception.DuplicateException;
import org.nawa.exception.InsufficientAuthorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private static UserService INSTANCE = null;

	private UserService() {
	} // INIT

	public static UserService getInstance() {
		synchronized (UserService.class) {
			if (INSTANCE == null)
				INSTANCE = new UserService();
			return INSTANCE;
		} // sync
	} // getInstance

	public JSONArray loadAllUsers(boolean includePasswd) {
		return UserInfoDao.select(includePasswd);
	} // loadAllUsers
	
	public JSONObject loadUser(String email){
		return UserInfoDao.select(email); 
	} //loadUser
	
	public JSONObject loadUserWithRelation(String userEmail, String myEmail){
		return UserInfoDao.selectWithRelation(userEmail, myEmail);
	} //loadUserWithRelation
	
	public void remove(String email){
		UserInfoDao.delete(email);
	} //remove
	
	public JSONArray searchByName(String name){
		return UserInfoDao.searchByName(name);
	} //searchByName
	
	public void insert(String email, String password, String name, int gender_i, Date regdate_d, String fb_token) throws DuplicateException {
		if(UserInfoDao.isEmailExists(email))
			throw new DuplicateException("email duplicated");
		
		JSONObject user = new JSONObject();
		user.put("email", email);
		user.put("passwd", password);
		user.put("name", name);
		user.put("gender", gender_i);
		user.put("regdate", regdate_d);
		user.put("facebook_access_token", fb_token);
		UserInfoDao.insert(user);
	} //insert
	
	public void update(String requestEmail, String email, String password, String name, int gender, String fb_token) throws InsufficientAuthorityException {
		if(requestEmail.equals(email) == false)
			throw new InsufficientAuthorityException();
		
		JSONObject user = UserInfoDao.select(email);
		user.put("passwd", password);
		user.put("name", name);
		user.put("gender", gender);
		user.put("facebook_access_token", fb_token);
		UserInfoDao.update(user);
	} //update
	
	public void pwdupdate(String eurl, String npwd) throws InsufficientAuthorityException {		
		JSONObject user = UserInfoDao.select(eurl);
		user.put("passwd", npwd);
		UserInfoDao.update(user);
	} //pwdupdate
	
	public boolean isEmailExists(String email){
		return UserInfoDao.isEmailExists(email);
	} //isEmailExist
	
	public boolean isAFollowingB(String aEmail, String bEmail) {
		return UserFriendDao.isAFollowingB(aEmail, bEmail);
	} // isFollowingRelation

	public void addFollowing(String email, String toEmail) throws IllegalAccessException {
		JSONObject user = UserInfoDao.select(email);
		JSONObject toUser = UserInfoDao.select(toEmail);
	
		try{
			Preconditions.checkNotNull(user, "unknown email : " + email);
			Preconditions.checkNotNull(toUser, "unknown toEmail : " + toEmail);
			Preconditions.checkArgument(email.equals(toEmail) == false);
		} catch(IllegalArgumentException e){
			throw new IllegalAccessException("cannot follow yourself");
		} //catch
		
		UserFriendDao.follow(user, toUser);
	} // addFollowing
	
	public void unfollow(String email, String toEmail){
		JSONObject user = UserInfoDao.select(email);
		JSONObject toUser = UserInfoDao.select(toEmail);
		
		Preconditions.checkNotNull(user, "unknown email : " + email);
		Preconditions.checkNotNull(toUser, "unknown toEmail : " + toEmail);
		
		UserFriendDao.unfollow(user, toUser);
	} //unfollow
	
	public JSONArray getFollowing(String email){
		Preconditions.checkNotNull(email, "email is null");
		Preconditions.checkNotNull(UserInfoDao.select(email), "failed to select user, email : " + email);
		
		return UserFriendDao.selectFollowing(email);
	} //getFollowing
	
	public JSONArray getFollowed(String email){
		Preconditions.checkNotNull(email, "email is null");
		Preconditions.checkNotNull(UserInfoDao.select(email), "failed to select user, email : " + email);
		
		return UserFriendDao.selectFollowed(email);
	} //getFollowed
	
	public byte[] getUserPic(String email, final int width, final int height) throws FileNotFoundException, IOException{
		Preconditions.checkNotNull(UserInfoDao.select(email), "failed to select user : " + email);
		
		File path = new File(Conf.getUserPicPath(), email);
		File[] files = path.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.startsWith(width + "_" + height))
					return true;
				return false;
			} //accept
		});
		
		if(files == null || files.length == 0){
			File file = new File(Conf.getUserPicPath(), "default.jpg");
			return IOUtils.toByteArray(new FileInputStream(file));
		} //if
		
		return IOUtils.toByteArray(new FileInputStream(files[0]));
	} //getUserPic
} // class