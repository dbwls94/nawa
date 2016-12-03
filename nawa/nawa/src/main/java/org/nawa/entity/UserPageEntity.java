package org.nawa.entity;

import java.util.List;

import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;

public class UserPageEntity {
	private List<ProjectInfoDao> leadingProjects;
	private int leadingProjectsCount;
	private List<ProjectInfoDao> participatingProjects;
	private int participatingProjectsCount;
	private UserInfoDao userInfo;
	private List<UserInfoDao> followings;
	private List<UserInfoDao> followers;

	public UserPageEntity() {
	} // INIT

	public List<ProjectInfoDao> getLeadingProjects() {
		return leadingProjects;
	}

	public void setLeadingProjects(List<ProjectInfoDao> leadingProjects) {
		this.leadingProjects = leadingProjects;
	}

	public int getLeadingProjectsCount() {
		return leadingProjectsCount;
	}

	public void setLeadingProjectsCount(int leadingProjectsCount) {
		this.leadingProjectsCount = leadingProjectsCount;
	}

	public List<ProjectInfoDao> getParticipatingProjects() {
		return participatingProjects;
	}

	public void setParticipatingProjects(List<ProjectInfoDao> participatingProjects) {
		this.participatingProjects = participatingProjects;
	}

	public int getParticipatingProjectsCount() {
		return participatingProjectsCount;
	}

	public void setParticipatingProjectsCount(int participatingProjectsCount) {
		this.participatingProjectsCount = participatingProjectsCount;
	}

	public UserInfoDao getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDao userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserInfoDao> getFollowings() {
		return followings;
	}

	public void setFollowings(List<UserInfoDao> followings) {
		this.followings = followings;
	}

	public List<UserInfoDao> getFollowers() {
		return followers;
	}

	public void setFollowers(List<UserInfoDao> followers) {
		this.followers = followers;
	}
} // class