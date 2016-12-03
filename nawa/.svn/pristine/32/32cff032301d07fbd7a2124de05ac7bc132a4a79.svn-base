package org.nawa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nawa.async.FunctionExecutorQueue;
import org.nawa.common.Conf;
import org.nawa.common.Functions;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.dao.UserParticipatedProjectDao;
import org.nawa.exception.DuplicateException;
import org.nawa.exception.InsufficientAuthorityException;
import org.nawa.exception.InvalidOperationException;
import org.nawa.exception.NotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class ProjectService 
{
	private static final Logger logger=LoggerFactory.getLogger(ProjectService.class);
	private static ProjectService INSTANCE=null;
	
	private ProjectService(){} //INIT
	
	public static ProjectService getInstance() {
		synchronized (ProjectService.class) {
			if(INSTANCE==null)
				INSTANCE=new ProjectService();
			return INSTANCE;
		} //sync
	} //getInstance

	public void insert(final JSONObject projectInfo) throws DuplicateException, JSONException, NotExistsException {
		JSONObject leader = UserInfoDao.select(projectInfo.getString("leader_email"));
		if(leader == null){
			logger.warn("failed to select leader_email : {}", projectInfo.getString("leader_email"));
			throw new NotExistsException(projectInfo.getString("leader_email"));
		} //if
		
		ProjectInfoDao.insert(projectInfo);
		UserParticipatedProjectDao.insert(leader, projectInfo);
		UserParticipatedProjectDao.updateAccept(projectInfo, leader, true);
		
		FunctionExecutorQueue.getInstance().executeAsync(new Functions() {
			@Override
			public void execute(Object... params) {
				try {
					NotificationService.getInstance().putNotiWhenCreateProject(projectInfo.getString("leader_email"), projectInfo.getString("id"));
				} catch (JSONException | NotExistsException e) {
					logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
				} //catch
			} //execute
		});
	}//insert
	
	public void update(final JSONObject updatedProjectInfo) throws InsufficientAuthorityException, JSONException, NotExistsException {
		JSONObject existringProjectInfo = ProjectInfoDao.select(updatedProjectInfo.getString("id"));
		
		if(existringProjectInfo.getString("leader_email").equals(updatedProjectInfo.getString("leader_email")) == false)
			throw new InsufficientAuthorityException();
		
		ProjectInfoDao.update(updatedProjectInfo);
	
		FunctionExecutorQueue.getInstance().executeAsync(new Functions() {
			@Override
			public void execute(Object... params) {
				try {
					NotificationService.getInstance().putNotiWhenModifyProject(updatedProjectInfo.getString("id"));
				} catch (JSONException | NotExistsException e) {
					logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
				} //catch
			} //execute
		});
	}//update
	
	public JSONObject get(String projectId) throws NotExistsException {
		JSONObject project = ProjectInfoDao.select(projectId);
		
		Calendar currentDate = Calendar.getInstance();
		Calendar recruitDueDate = Calendar.getInstance();
		recruitDueDate.setTime((Date) project.get("recruit_due_date"));

		long dMinusDays = (currentDate.get(Calendar.YEAR) * 365);
		dMinusDays += currentDate.get(Calendar.DAY_OF_YEAR);
		dMinusDays -= recruitDueDate.get(Calendar.YEAR) * 365;
		dMinusDays -= recruitDueDate.get(Calendar.DAY_OF_YEAR);
		project.put("recruit_due_date_relative", dMinusDays < 0 ? dMinusDays+"" : "+" + dMinusDays);

		return project;
	} //get
	
	public JSONArray list(int page){
		JSONArray list = ProjectInfoDao.list(page);
		
		for (int i = 0; i < list.length(); i++) {
			JSONObject project = list.getJSONObject(i);
			
			Calendar currentDate = Calendar.getInstance();
			Calendar recruitDueDate = Calendar.getInstance();
			recruitDueDate.setTime((Date) project.get("recruit_due_date"));
			
			long dMinusDays = (currentDate.get(Calendar.YEAR) * 365);
			dMinusDays += currentDate.get(Calendar.DAY_OF_YEAR);
			dMinusDays -= recruitDueDate.get(Calendar.YEAR) * 365;
			dMinusDays -= recruitDueDate.get(Calendar.DAY_OF_YEAR);
			project.put("recruit_due_date_relative", dMinusDays < 0 ? dMinusDays+"" : "+" + dMinusDays);
		} //for i
		
		return list;
	} //list
	
	public JSONArray loadAll(){
		return ProjectInfoDao.select();
	} //loadAll
	
	public JSONArray searchByTitle(String keyword){
		return ProjectInfoDao.searchByTitle(keyword);
	} //searchByTitle
	
	public void remove(String id) throws NotExistsException{
		ProjectInfoDao.delete(id);
	} //remove
	
	public void participate(final String projectId, final String userEmail) throws NotExistsException, DuplicateException{
		JSONObject user = UserInfoDao.select(userEmail);
		JSONObject project = ProjectInfoDao.select(projectId);
	
		Preconditions.checkNotNull(user, "failed to select user : " + userEmail);
		Preconditions.checkNotNull(project, "failed to select project : " + projectId);
		try{
			Preconditions.checkArgument(UserParticipatedProjectDao.isExistsWaitingAccept(projectId, userEmail) == false);
		} catch(IllegalArgumentException e){
			throw new DuplicateException(String.format("projectId: %s, email: %s", projectId, userEmail));
		} //catch
		
		UserParticipatedProjectDao.insert(user, project);
		
		FunctionExecutorQueue.getInstance().executeAsync(new Functions() {
			@Override
			public void execute(Object... params) {
				try {
					NotificationService.getInstance().putNotiWhenParticipateProject(userEmail, projectId);
				} catch (NotExistsException e) {
					logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
				} //catch
			} //execute
		});
	} //participate
	
	public void unparticipate(String projectId, String userEmail) throws InvalidOperationException, NotExistsException{
		JSONObject user = UserInfoDao.select(userEmail);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		Preconditions.checkNotNull(user, "failed to select user : " + userEmail);
		Preconditions.checkNotNull(project, "failed to select project : " + projectId);
		
		if(project.getString("leader_email").equals(userEmail)){
			String msg = String.format("leader cannot unparticpate project");
			throw new InvalidOperationException(msg);
		} //if
		
		UserParticipatedProjectDao.delete(user, project);
	} //unparticipate
	
	public boolean isParticipated(String projectId, String userEmail) throws NotExistsException{
		JSONObject user = UserInfoDao.select(userEmail);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		Preconditions.checkNotNull(user, "failed to select user : " + userEmail);
		Preconditions.checkNotNull(project, "failed to select project : " + projectId);
		
		return UserParticipatedProjectDao.isParticipated(user, project);
	} //isParticipated
	
	public void acceptParticipation(String leadingUserEmail, String participationUserEmail, String projectId, boolean accept) throws InsufficientAuthorityException, NotExistsException{
		JSONObject leadingUser = UserInfoDao.select(leadingUserEmail);
		JSONObject participationUser = UserInfoDao.select(participationUserEmail);
		JSONObject project = ProjectInfoDao.select(projectId);
		
		try{
			Preconditions.checkNotNull(leadingUser, "failed to select user : " + leadingUserEmail);
			Preconditions.checkNotNull(participationUser, "failed to select user : " + participationUserEmail);
			Preconditions.checkNotNull(project, "failed to select project : " + projectId);
			Preconditions.checkArgument(leadingUserEmail.equals(project.getString("leader_email")), "authority insufficient");
		} catch(IllegalArgumentException e) {
			throw new InsufficientAuthorityException(leadingUserEmail);
		} //catch
		
		UserParticipatedProjectDao.updateAccept(project, participationUser, accept);
	} //acceptParticipation
	
	public JSONArray getWaitingAcceptUsers(String email, String projectId) throws NotExistsException {
		Preconditions.checkNotNull(UserInfoDao.select(email), "failed to select user : " + email);
		Preconditions.checkNotNull(ProjectInfoDao.select(projectId), "failed to select project : " + projectId);
		
		return UserParticipatedProjectDao.selectWaitingAcceptUsers(projectId);
	} //getWatingAcceptList
	
	public JSONArray getParticipatingUsers(String projectId) throws NotExistsException {
		JSONObject project = ProjectInfoDao.select(projectId);
		
		Preconditions.checkNotNull(project, "failed to select project : " + projectId);
		
		return UserParticipatedProjectDao.selectParticipatingUsers(projectId);
	} //getWatingAcceptList
	
	public JSONArray getParticipatingProjects(String email, boolean includeNotAccepted){
		Preconditions.checkNotNull(UserInfoDao.select(email), "failed to select user : " + email);
		
		JSONArray participatingProjects = UserParticipatedProjectDao.selectParticipatingProjects(email, includeNotAccepted);
		for (int i = 0; i < participatingProjects.length(); i++) {
			JSONObject project = participatingProjects.getJSONObject(i);
			
			Calendar currentDate = Calendar.getInstance();
			Calendar recruitDueDate = Calendar.getInstance();
			recruitDueDate.setTime((Date) project.get("recruit_due_date"));
			
			long dMinusDays = (currentDate.get(Calendar.YEAR) * 365);
			dMinusDays += currentDate.get(Calendar.DAY_OF_YEAR);
			dMinusDays -= recruitDueDate.get(Calendar.YEAR) * 365;
			dMinusDays -= recruitDueDate.get(Calendar.DAY_OF_YEAR);
			project.put("recruit_due_date_relative", dMinusDays < 0 ? dMinusDays+"" : "+" + dMinusDays);
		} //for i
		
		return participatingProjects;
	} //getParticipatingProjects
	
	public JSONArray getLeadingProjects(String email){
		Preconditions.checkNotNull(UserInfoDao.select(email), "failed to select user : " + email);
		
		JSONArray leadingProjects = ProjectInfoDao.selectLeadingProjects(email);
		for (int i = 0; i < leadingProjects.length(); i++) {
			JSONObject project = leadingProjects.getJSONObject(i);
			
			Calendar currentDate = Calendar.getInstance();
			Calendar recruitDueDate = Calendar.getInstance();
			recruitDueDate.setTime((Date) project.get("recruit_due_date"));
			
			long dMinusDays = (currentDate.get(Calendar.YEAR) * 365);
			dMinusDays += currentDate.get(Calendar.DAY_OF_YEAR);
			dMinusDays -= recruitDueDate.get(Calendar.YEAR) * 365;
			dMinusDays -= recruitDueDate.get(Calendar.DAY_OF_YEAR);
			project.put("recruit_due_date_relative", dMinusDays < 0 ? dMinusDays+"" : "+" + dMinusDays);
		} //for i
		
		return leadingProjects;
	} //getLeadingProjects
	
	public byte[] getProjectPic(String id, final int width, final int height) throws NotExistsException, FileNotFoundException, IOException{
		Preconditions.checkNotNull(ProjectInfoDao.select(id));
		
		File path = new File(Conf.getProjectPicPath(), id);
		File[] files = path.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.startsWith(width + "_" + height))
					return true;
				return false;
			} //accept
		});
		
		if(files == null || files.length == 0){
			File file = new File(Conf.getProjectPicPath(), "default.jpg");
			return IOUtils.toByteArray(new FileInputStream(file));
		} //if
		
		return IOUtils.toByteArray(new FileInputStream(files[0]));
	} //getProjectPic
} //class