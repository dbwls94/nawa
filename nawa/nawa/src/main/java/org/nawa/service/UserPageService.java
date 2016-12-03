package org.nawa.service;

import org.json.JSONObject;
import org.nawa.common.JwtToken;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserFriendDao;
import org.nawa.dao.UserInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPageService {
	private static final Logger logger = LoggerFactory.getLogger(UserPageService.class);

	private static UserPageService INSTANCE = null;

	private UserPageService() {
	} // INIT

	public static UserPageService getInstance() {
		synchronized (UserPageService.class) {
			if (INSTANCE == null)
				INSTANCE = new UserPageService();
			return INSTANCE;
		} // synch
	} // getInsatnce

	public JSONObject getMyPage(String token){
		String myEmail=(String) JwtToken.getPayloadValue(token, "email");
		if(myEmail==null || myEmail.trim().length()==0){
			logger.error(String.format("failed to parsing token : %s", token));
			return null;
		} //if

		return getUserPage(myEmail);
	} //getMyPage

	public JSONObject getUserPage(String userEmail){
		JSONObject userPageJson = new JSONObject();
		userPageJson.put("leading-projects", ProjectService.getInstance().getLeadingProjects(userEmail));
		userPageJson.put("participating-projects", ProjectService.getInstance().getParticipatingProjects(userEmail, false));
		userPageJson.put("user-info", UserInfoDao.select(userEmail));
		userPageJson.put("followings", UserFriendDao.selectFollowing(userEmail));
		userPageJson.put("followers", UserFriendDao.selectFollowed(userEmail));
		return userPageJson;
	} //getUserPage
} // class