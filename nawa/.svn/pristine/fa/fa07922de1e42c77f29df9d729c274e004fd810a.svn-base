package org.nawa.service;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.dao.NotificationDao;
import org.nawa.dao.NotificationType1Dao;
import org.nawa.dao.NotificationType2Dao;
import org.nawa.dao.NotificationType4Dao;
import org.nawa.dao.NotificationType5Dao;
import org.nawa.dao.NotificationType6Dao;
import org.nawa.dao.NotificationType7Dao;
import org.nawa.dao.NotificationType8Dao;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.ProjectReplyDao;
import org.nawa.dao.UserFriendDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.dao.UserParticipatedProjectDao;
import org.nawa.exception.NotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	private static NotificationService INSTANCE = null;

	private Map<String, Integer> userNotiCounts = new HashMap<String, Integer>();
	private Map<String, PrintWriter> realtimeNotiWriters = new HashMap<String, PrintWriter>();
		
	private NotificationService() {
		this.userNotiCounts = NotificationDao.selectUnreadCount();
		if(this.userNotiCounts == null)
			this.userNotiCounts = new HashMap<String, Integer>();
	} // INIT

	public static NotificationService getInstance() {
		synchronized (NotificationService.class) {
			if (INSTANCE == null)
				INSTANCE = new NotificationService();
			return INSTANCE;
		} // sync
	} // getInstance
	
	public void registerWriter(String userEmail, PrintWriter writer) {
		synchronized (realtimeNotiWriters) {
			realtimeNotiWriters.put(userEmail, writer);
		} //sync
	} //registerWriter
	
	private void increaseNotiCount(String userEmail){
		Integer count = userNotiCounts.get(userEmail);
		if(count == null)
			count = 0;
		count++;
		userNotiCounts.put(userEmail, count);
		
		synchronized (realtimeNotiWriters) {
			PrintWriter writer = realtimeNotiWriters.get(userEmail);
			if(writer == null)
				return;
			writer.println(new JSONObject().put("notiCount", count).toString());
		} //sync
	} //increaseNotiCount
	
	private void sendNotiRealtime(NotificationDao noti){
		synchronized (realtimeNotiWriters) {
			PrintWriter writer = realtimeNotiWriters.get(noti.getOwner());
			if(writer == null)
				return;
			writer.println(new JSONObject().put("noti", noti.toString()));
		}//sync
	} //sendNotiRealtim
	
	/**
	 * @throws NotExistsException 
	 * @description 프로젝트 생성시 자신의 follower들에게 noti 데이터를 생성해준다.
	 */
	public void putNotiWhenCreateProject(String userEmail, String projectId) throws NotExistsException{
		logger.info("userEmail:{}, projectId:{}", userEmail, projectId);
		
		try{
			Preconditions.checkNotNull(userEmail, "userEmail is null");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(UserInfoDao.select(userEmail), "failed to select user, userEmail : " + userEmail);
			Preconditions.checkNotNull(ProjectInfoDao.select(projectId), "failed to select project, projectId : " + projectId);
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			return;
		} //catch
		
		JSONArray followers = UserFriendDao.selectFollowed(userEmail);
		if(followers == null)
			return;
		
		for (int i = 0; i < followers.length(); i++) {
			JSONObject follower = followers.getJSONObject(i);
			
			NotificationType2Dao notiDao = new NotificationType2Dao();
			notiDao.setFollowingUserEmail(userEmail);
			notiDao.setIsRead(0);
			notiDao.setNotiId(UUID.randomUUID().toString());
			notiDao.setOwner(follower.getString("email"));
			notiDao.setParticipatingProjectId(projectId);
			notiDao.setRegdate(new Date());
			boolean result = NotificationDao.insert(notiDao);
			if(result == true){
				increaseNotiCount(userEmail);
				sendNotiRealtime(notiDao);
			} //if
			else
				logger.error("failed to insert noti, noti : {}", notiDao.toString());
		} //for i
	} //putNotiWhenCreateProject	
	
	/**
	 * @throws NotExistsException 
	 * @description 프로젝트 참여시 자신의 follower들에게 noti 데이터를 생성해준다.
	 */
	public void putNotiWhenParticipateProject(String userEmail, String projectId) throws NotExistsException{
		logger.info("userEmail:{}, projectId:{}", userEmail, projectId);
		
		try{
			Preconditions.checkNotNull(userEmail, "userEmail is null");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(UserInfoDao.select(userEmail), "failed to select user, userEmail : " + userEmail);
			Preconditions.checkNotNull(ProjectInfoDao.select(projectId), "failed to select project, projectId : " + projectId);
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			return;
		} //catch
		
		JSONArray followers = UserFriendDao.selectFollowed(userEmail);
		if(followers == null)
			return;
		
		for (int i=0; i<followers.length(); i++){
			JSONObject follower = followers.getJSONObject(i);
			NotificationType1Dao notiDao = new NotificationType1Dao();
			notiDao.setFollowingUserEmail(userEmail);
			notiDao.setIsRead(0);
			notiDao.setNotiId(UUID.randomUUID().toString());
			notiDao.setOwner(follower.getString("email"));
			notiDao.setParticipatingProjectId(projectId);
			notiDao.setRegdate(new Date());
			boolean result = NotificationDao.insert(notiDao);
			if(result == false){
				increaseNotiCount(userEmail);
				sendNotiRealtime(notiDao);
			} //if 
			else
				logger.error("failed to insert noti, noti : {}", notiDao.toString());
		} //for follower
	} //putNotiWhenParticipateProject

	/**
	 * @throws NotExistsException 
	 * @description 댓글 작성시 해당 프로젝트에 참가한 사람 + 댓글을 남긴 사람에게 noti 데이터를 생성해준다.
	 */
	public void putNotiWhenCreateComment(String userEmail, String projectId, String commentSeq) throws NotExistsException{
		logger.info("userEmail:{}, projectId:{}, commentSeq:{}", userEmail, projectId, commentSeq);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		try{
			Preconditions.checkNotNull(userEmail, "userEmail is null");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(commentSeq, "commentSeq is null");
			Preconditions.checkNotNull(UserInfoDao.select(userEmail), "failed to select user, userEmail : " + userEmail);
			Preconditions.checkNotNull(project, "failed to select project, projectId : " + projectId);
			Preconditions.checkNotNull(ProjectReplyDao.select(projectId, Long.parseLong(commentSeq)), 
					"failed to select comment, projectId : " + projectId + ", commentSeq : " + commentSeq);
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			return;
		} catch(NumberFormatException e){
			logger.warn("invalid commentSeq : {}", commentSeq);
			return;
		} //catch

		//"프로젝트에 참가"중이고 "댓글"도 남긴 경우에는 "댓글"을 남겼을 경우 전달되는 noti 데이터로 생성한다.
		Set<String> commentedUserEmails = new HashSet<String>();
//		List<ProjectReplyDao> replies = ProjectReplyDao.selectByProjectId(projectId);
		JSONArray replies = ProjectReplyDao.selectByProjectId(projectId);
		
		for (int i = 0; i < replies.length(); i++) {
			JSONObject reply = replies.getJSONObject(i);
			String commentedUserEmail = reply.getString("user_email");
			if(commentedUserEmails.contains(commentedUserEmail) == true)
				continue;
			commentedUserEmails.add(commentedUserEmail);

			NotificationType7Dao notiDao = new NotificationType7Dao();
			notiDao.setIsRead(0);
			notiDao.setNotiId(UUID.randomUUID().toString());
			notiDao.setOwner(commentedUserEmail);
			notiDao.setRegdate(new Date());
			notiDao.setCommentProjectId(projectId);
			notiDao.setCommentSeq(commentSeq);
			notiDao.setCommentUserEmail(userEmail);
			boolean result = NotificationDao.insert(notiDao);
			if(result == true){
				increaseNotiCount(commentedUserEmail);
				sendNotiRealtime(notiDao);
			} //if
			else
				logger.error("failed to insert noti, noti : {}", notiDao.toString());
		} //for i
		
		JSONArray participatedUsers = UserParticipatedProjectDao.selectParticipatingUsers(projectId);
		for (int i = 0; i < participatedUsers.length(); i++) {
			JSONObject participatedUser = participatedUsers.getJSONObject(i);
			String participatedUserEmail = participatedUser.getString("email");
			if(commentedUserEmails.contains(participatedUserEmail) == true)
				continue;
			commentedUserEmails.add(participatedUserEmail);
			
			NotificationType7Dao notiDao = new NotificationType7Dao();
			notiDao.setIsRead(0);
			notiDao.setNotiId(UUID.randomUUID().toString());
			notiDao.setOwner(participatedUserEmail);
			notiDao.setRegdate(new Date());
			notiDao.setCommentProjectId(projectId);
			notiDao.setCommentSeq(commentSeq);
			notiDao.setCommentUserEmail(userEmail);
			boolean result = NotificationDao.insert(notiDao);
			if(result == true){
				increaseNotiCount(participatedUserEmail);
				sendNotiRealtime(notiDao);
			} //if
			else
				logger.error("failed to insert noti, noti : {}", notiDao.toString());
		} //for i
	} //putNotiWhenCreateComment

	/**
	 * project 수정시에 해당 project 참가하는 사용자들에게 noti 데이터들을 생성해준다.
	 * @throws NotExistsException 
	 */
	public void putNotiWhenModifyProject(String projectId) throws NotExistsException{
		logger.info("projectId:{}", projectId);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		try{
			Preconditions.checkNotNull(project, "failed to select project, projectId : ", projectId);
		} catch(NullPointerException e){
			logger.warn(e.getMessage());
			return;
		} //catch
		
		JSONArray participatedUsers = UserParticipatedProjectDao.selectParticipatingUsers(projectId);
		for (int i = 0; i < participatedUsers.length(); i++) {
			JSONObject participatedUser = participatedUsers.getJSONObject(i);
			NotificationType4Dao notiDao = new NotificationType4Dao();
			notiDao.setIsRead(0);
			notiDao.setNotiId(UUID.randomUUID().toString());
			notiDao.setOwner(participatedUser.getString("email"));
			notiDao.setRegdate(new Date());
			notiDao.setRelatedProjectId(projectId);
			boolean result = NotificationDao.insert(notiDao);
			if(result == true){
				increaseNotiCount(participatedUser.getString("email"));
				sendNotiRealtime(notiDao);
			} //if
			else
				logger.error("failed to insert noti, noti : {}", notiDao.toString());
		} //for user
	} //putNotiWhenModifyProject

	/**
	 * 프로젝트에서 참가중인 사용자 퇴출시에 해당 사용자에게 noti 데이터를 생성해준다.
	 * @throws NotExistsException 
	 */
	public void putNotiWhenKickSomeoneFromProject(String projectId, String kickedUserEmail) throws NotExistsException{
		logger.info("projectId:{}, kickedUserEmail:{}", projectId, kickedUserEmail);
		
		try {
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(kickedUserEmail, "kickedUserEmail is null");
			Preconditions.checkNotNull(ProjectInfoDao.select(projectId), "failed to select project, projectId : " + projectId);
			Preconditions.checkNotNull(UserInfoDao.select(kickedUserEmail), "failed to select user, kickedUserEmail : " + kickedUserEmail);
		} catch (NullPointerException e) {
			logger.warn(e.getMessage());
			return;
		} //catch
		
		NotificationType5Dao notiDao = new NotificationType5Dao();
		notiDao.setIsRead(0);
		notiDao.setNotiId(UUID.randomUUID().toString());
		notiDao.setOwner(kickedUserEmail);
		notiDao.setRegdate(new Date());
		notiDao.setRelatedProjectId(projectId);
		boolean result = NotificationDao.insert(notiDao);
		if(result == true){
			increaseNotiCount(kickedUserEmail);
			sendNotiRealtime(notiDao);
		} //if
		else
			logger.error("failed to insert noti, noti : {}", notiDao.toString());
	} //putNotiWhenKickSomeoneFromProject

	/**
	 * @throws NotExistsException 
	 * @description 프로젝트에 초대했을 때 초대받은 사용자에게 noti 데이터를 생성해준다.
	 */
	public void putNotiWhenInviteSomeoneToProject(String hostEmail, String guestEmail, String projectId) throws NotExistsException{
		logger.info("hostEmail:{}, guestEmail:{}, projectId:{}", hostEmail, guestEmail, projectId);
		
		try {
			Preconditions.checkNotNull(hostEmail, "hostEmail is null");
			Preconditions.checkNotNull(guestEmail, "guestEmail is null");
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(UserInfoDao.select(hostEmail), "failed to select user, hostEmail : " + hostEmail);
			Preconditions.checkNotNull(UserInfoDao.select(guestEmail), "failed to select user, guestEmail : " + guestEmail);
			Preconditions.checkNotNull(ProjectInfoDao.select(projectId), "failed to select project, projectId : " + projectId);
		} catch (NullPointerException e) {
			logger.warn(e.getMessage());
		} //catch
		
		NotificationType6Dao notiDao = new NotificationType6Dao();
		notiDao.setIsRead(0);
		notiDao.setNotiId(UUID.randomUUID().toString());
		notiDao.setOwner(guestEmail);
		notiDao.setRegdate(new Date());
		notiDao.setInvitedProjectId(projectId);
		notiDao.setInvitorUserEmail(hostEmail);
		boolean result = NotificationDao.insert(notiDao);
		if(result == true){
			increaseNotiCount(guestEmail);
			sendNotiRealtime(notiDao);
		} //if
		else
			logger.error("failed to insert noti, noti : {}", notiDao.toString());
	} //putNotiWhenInviteSomeoneToProject

	/**
	 * 프로젝트 참가 요청시 수락/거절에 대한 noti 데이터를 생성해준다.
	 * @throws NotExistsException 
	 */
	public void putNotiWhenAcceptOrRejectToProject(String projectId, String userEmail, boolean whetherAcceptOrReject) throws NotExistsException{
		logger.info("projectId:{}, userEmail:{}, wehtherAcceptOrReject:{}", projectId, userEmail, whetherAcceptOrReject);
		
		try {
			Preconditions.checkNotNull(projectId, "projectId is null");
			Preconditions.checkNotNull(userEmail, "userEmail is null");
			Preconditions.checkNotNull(ProjectInfoDao.select(projectId), "failed to select project, projectId : " + projectId);
			Preconditions.checkNotNull(UserInfoDao.select(userEmail), "failed to select user, userEmail : " + userEmail);
		} catch (NullPointerException e) {
			logger.warn(e.getMessage());
			return;
		} //catch
		
		NotificationType8Dao notiDao = new NotificationType8Dao();
		notiDao.setIsRead(0);
		notiDao.setNotiId(UUID.randomUUID().toString());
		notiDao.setOwner(userEmail);
		notiDao.setRegdate(new Date());
		notiDao.setProjectId(projectId);
		notiDao.setWhetherAcceptOrRejected(whetherAcceptOrReject);
		boolean result = NotificationDao.insert(notiDao);
		if(result == true){
			increaseNotiCount(userEmail);
			sendNotiRealtime(notiDao);
		} //if
		else
			logger.error("failed to insert noti, noti : {}", notiDao.toString());
	} //putNotiWhenAcceptOrRejectToProject

	public int getNotiCount(String email){
		Integer count = userNotiCounts.get(email);
		if(count == null)
			return 0;
		return count;
	} //getNotiCount

	public String getNotiAsJson(String email){
		//TODO
		return null;
	} //getNotiAsJson
} // class