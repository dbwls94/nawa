package org.nawa.service;

import org.nawa.common.JwtToken;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;
import org.nawa.dao.UserParticipatedProjectDao;
import org.nawa.entity.NavBarEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class NavBarService {
	private static final Logger logger = LoggerFactory.getLogger(NavBarService.class);

	private static NavBarService INSTANCE = null;

	private NavBarService() {
	} // INIT

	public static NavBarService getInstance() {
		synchronized (NavBarService.class) {
			if (INSTANCE == null)
				INSTANCE = new NavBarService();
			return INSTANCE;
		} // sync
	} // getInstance
	
	public NavBarEntity getNavBarInfoForLoginUser(String token){
		try{
			String email = null;
			try {
				Preconditions.checkNotNull(token, "token is null");
				email = (String) JwtToken.getPayloadValue(token, "email");
				Preconditions.checkNotNull(email, "email is null");
			} catch (NullPointerException e) {
				return null;
			} //catch
			
			NavBarEntity navBarEntity=new NavBarEntity();
			navBarEntity.setUsername(UserInfoDao.select(email).getString("name"));
			navBarEntity.setHandlingProjectCount(ProjectInfoDao.selectLeadingProjectsCount(email) + UserParticipatedProjectDao.selectParticipatingProjectsCount(email, false));
			navBarEntity.setNotificationCount(NotificationService.getInstance().getNotiCount(email));
			return navBarEntity;
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			return null;
		} //catch
	} //getNavBarInfoForLoginUser
} // class