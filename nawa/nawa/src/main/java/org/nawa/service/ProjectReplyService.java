package org.nawa.service;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.async.FunctionExecutorQueue;
import org.nawa.common.Functions;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.ProjectReplyDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.exception.InvalidOperationException;
import org.nawa.exception.NotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class ProjectReplyService {
	private static final Logger logger=LoggerFactory.getLogger(ProjectReplyService.class);
	private static ProjectReplyService INSTANCE=null;
	
	private ProjectReplyService(){
	} //INIT
	
	public static ProjectReplyService getInstance(){
		synchronized (ProjectReplyService.class) {
			if(INSTANCE==null)
				INSTANCE=new ProjectReplyService();
			return INSTANCE;
		} //sync
	} //getInstance

	public void insert(final String email, final String projectId, String content, Long parentSeq, Date regDate) throws NotExistsException {
		JSONObject user = UserInfoDao.select(email);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		Preconditions.checkNotNull(user, "failed to select user, email : " + email);
		Preconditions.checkNotNull(project, "failed to select project, projectId : " + projectId);
	
		final Long seq = ProjectReplyDao.selectNextSeq(projectId);
		String depth = null;
		if(parentSeq == null){
			depth = "/" + String.format("%04d", seq);
		} else{
			String parentDepth = ProjectReplyDao.selectDepth(projectId, parentSeq);
			depth = parentDepth + "/" + String.format("%04d", seq);
		} //if
		
		JSONObject reply = new JSONObject();
		reply.put("seq", seq);
		reply.put("project_id", projectId);
		reply.put("user_email", email);
		reply.put("depth", depth);
		reply.put("regdate", new Date());
		reply.put("content", content);

		ProjectReplyDao.insert(reply);

		FunctionExecutorQueue.getInstance().executeAsync(new Functions() {
			@Override
			public void execute(Object... params) {
				try {
					NotificationService.getInstance().putNotiWhenCreateComment(email, projectId, seq+"");
				} catch (NotExistsException e) {
					logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
				} //catch
			} //execute
		});
	} //insert

	public void update(Long seq, String projectId, String email, String content) throws NotExistsException, InvalidOperationException {
		JSONObject project = ProjectInfoDao.select(projectId);
		JSONObject user = UserInfoDao.select(email);
		
		Preconditions.checkNotNull(project, "failed to select project, projectId : " + project);
		Preconditions.checkNotNull(user, "failed to select user, userId : " + email);
		
		JSONObject reply = ProjectReplyDao.select(projectId, seq);
		
		Preconditions.checkNotNull(reply, String.format("failed to select comment, projectId: %s, seq: %s", projectId, seq));
		
		if(email.equals(reply.getString("user_email")) == false)
			throw new InvalidOperationException(String.format("%s cannot update %s's comment", email, reply.getString("user_email")));
	
		reply.put("content", content);
		
		ProjectReplyDao.update(reply);
	}//update
	
	public void remove(String email, String projectId, Long seq) throws InvalidOperationException, NotExistsException {
		JSONObject user = UserInfoDao.select(email);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		Preconditions.checkNotNull(user, "failed to select user, email : " + email);
		Preconditions.checkNotNull(project, "failed to select project, projectId : " + projectId);
		
		JSONObject reply = ProjectReplyDao.select(projectId, seq);
	
		Preconditions.checkNotNull(reply, String.format("failed to select comment, projectId : %s, seq : %s", projectId, seq));
		
		if(email.equals(reply.getString("user_email")) == false)
			throw new InvalidOperationException(String.format("%s cannot remove %s's comment", email, reply.getString("user_email")));
	
		ProjectReplyDao.delete(projectId, seq);
	} //delete
	
	public JSONArray getReplyByProjectId(String projectId){
		return ProjectReplyDao.selectByProjectId(projectId);
	} //getReply
	
	public JSONArray getAllReply(){
		return ProjectReplyDao.select();
	} //getAllReply
} //class