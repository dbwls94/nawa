package org.nawa.dao;

public class NotificationType4Dao extends NotificationDao{
	public NotificationType4Dao() {
		setType(TYPE_PROJECT_MODIFIED_WHICH_I_PARTICIPATE);
	} //INIT
	
	public String getRelatedProjectId(){
		return getAdditionalData1();
	} //getRelatedProjectIds
	
	public void setRelatedProjectId(String projectId){
		setAdditionalData1(projectId);
	} //setRelatedProjectId
} //class