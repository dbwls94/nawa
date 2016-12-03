package org.nawa.dao;

public class NotificationType1Dao extends NotificationDao{
	public NotificationType1Dao() {
		setType(TYPE_FOLLOWING_USER_PARTICIPATE_PROJECT);
	} //INIT
	
	public String getFollowingUserEmail(){
		return getAdditionalData1();
	} //getFollowingUserEmail
	
	public void setFollowingUserEmail(String email){
		setAdditionalData1(email);
	} //setFollowingUserEmail
	
	public String getParticipatingProjectId(){
		return getAdditionalData2();
	} //getParticipatingProjectId
	
	public void setParticipatingProjectId(String projectId){
		setAdditionalData2(projectId);
	} //setParticipatingProjectId
} //class