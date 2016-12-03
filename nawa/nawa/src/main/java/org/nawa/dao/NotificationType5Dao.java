package org.nawa.dao;

public class NotificationType5Dao extends NotificationDao{
	public NotificationType5Dao() {
		setType(TYPE_KICKED_FROM_PARTICIPATING_PROJECT);
	} //INIT
	
	public String getRelatedProjectId(){
		return getAdditionalData1();
	} //getRelatedProjectIds
	
	public void setRelatedProjectId(String projectId){
		setAdditionalData1(projectId);
	} //setRelatedProjectId
} //class