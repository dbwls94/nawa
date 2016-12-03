package org.nawa.dao;

public class NotificationType7Dao extends NotificationDao{
	public NotificationType7Dao() {
		setType(TYPE_NEW_COMMENT_IN_PROJECT_WHICH_I_COMMENTED);
	} //INIT
	
	public String getCommentUserEmail(){
		return getAdditionalData1();
	} //getCommentUserEmail
	
	public void setCommentUserEmail(String email){
		setAdditionalData1(email);
	} //setCommentUserEmail
	
	public String getCommentProjectId(){
		return getAdditionalData2();
	} //getComentProjectId
	
	public void setCommentProjectId(String projectId){
		setAdditionalData2(projectId);
	} //setCommentProjectId
	
	public String getCommentSeq(){
		return getAdditionalData3();
	} //getCommentSeq
	
	public void setCommentSeq(String seq){
		setAdditionalData3(seq);
	} //setCommentSeq
} //class