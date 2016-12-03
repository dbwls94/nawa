package org.nawa.dao;

public class NotificationType3Dao extends NotificationDao{
	public NotificationType3Dao() {
		setType(TYPE_NEW_COMMENT_IN_PROJECT_WHICH_RELATE_ME);
	} //INIT
	
	public String getRelatedProjectId(){
		return getAdditionalData1();
	} //getRelatedProjectIds
	
	public void setRelatedProjectId(String projectId){
		setAdditionalData1(projectId);
	} //setRelatedProjectId
	
	public String getCommentSeq(){
		return getAdditionalData2();
	} //getCommentSeq
	
	public void setCommentSeq(String commentSeq){
		setAdditionalData2(commentSeq);
	} //setCommentSeq
} //class