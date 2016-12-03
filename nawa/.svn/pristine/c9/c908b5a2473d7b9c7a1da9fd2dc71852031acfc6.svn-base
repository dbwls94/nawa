package org.nawa.dao;

import java.util.Date;
import java.util.List;

import org.nawa.common.json.JsonColumn;
import org.nawa.common.json.JsonSerializable;
import org.nawa.daomapper.DaoMapper;
import org.nawa.daomapper.Mapped;
import org.nawa.rdb.DbHandler;

public class ProjectInvitationDao extends JsonSerializable {
	@JsonColumn(key = "host_email")
	@Mapped(column = "host_email")
	private String hostEmail;

	@JsonColumn(key = "guest_email")
	@Mapped(column = "guest_email")
	private String guestEmail;

	@JsonColumn(key = "project_id")
	@Mapped(column = "project_id")
	private String projectId;

	@JsonColumn(key = "regdate")
	@Mapped(column = "regdate")
	private Date regdate;

	public static boolean insert(ProjectInvitationDao invitation){
		String query = "insert into project_invitation (host_email, guest_email, project_id, regdate) "
				+ "values (?, ?, ?, ?)";
		return DbHandler.preparedUpdate(query, invitation.getHostEmail(), invitation.getGuestEmail(), invitation.getProjectId(), invitation.getRegdate());
	} //insert
	
	public static boolean delete(ProjectInvitationDao invitation){
		String query = "delete from project_invitation where host_email = ? and guest_email = ? and project_id = ?";
		return DbHandler.preparedUpdate(query, invitation.getHostEmail(), invitation.getGuestEmail(), invitation.getProjectId());
	} //delete
	
	public static List<ProjectInvitationDao> selectReceivedInvitations(String email){
		String query = "select host_email, guest_email, project_id, regdate from project_invitation where guest_email = ?";
		return new DaoMapper<ProjectInvitationDao>().selectList(ProjectInvitationDao.class, query, email);
	} //selectReceivedInvitations
	
	public static List<ProjectInvitationDao> selectSentInvitations(String email){
		String query = "select host_email, guest_email, project_id, regdate from project_invitation where host_email = ?";
		return new DaoMapper<ProjectInvitationDao>().selectList(ProjectInvitationDao.class, query, email);
	} //selectSentInvitations
	
	public static boolean isExists(String hostEmail, String guestEmail, String projectId){
		String query = "select count(*) from project_invitation where host_email = ? and guest_email = ? and project_id = ?";
		String[][] result = DbHandler.preparedSelect(query, hostEmail, guestEmail, projectId);
		
		if(result == null || result.length == 0 || result[0].length == 0)
			return false;
		return "1".equals(result[0][0]);
	} //isExists
	
	public String getHostEmail() {
		return hostEmail;
	}

	public void setHostEmail(String hostEmail) {
		this.hostEmail = hostEmail;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return toJson();
	}
} // class