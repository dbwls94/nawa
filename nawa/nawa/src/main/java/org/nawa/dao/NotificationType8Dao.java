package org.nawa.dao;

public class NotificationType8Dao extends NotificationDao{
	public NotificationType8Dao() {
		setType(TYPE_ACCEPTED_OR_DENIED_FROM_PROJECT);
	} //INIT
	
	public String getProjectId(){
		return getAdditionalData1();
	} //getProjectId
	
	public void setProjectId(String projectId){
		setAdditionalData1(projectId);
	} //setProjectId
	
	public String whetherAcceptOrDenied(){
		return getAdditionalData2();
	} //whetherAcceptOrDenied
	
	public void setWhetherAcceptOrRejected(boolean acceptOrRejected){
		setAdditionalData2(String.valueOf(acceptOrRejected));
	} //setWhetherAcceptOrRejected
} //class