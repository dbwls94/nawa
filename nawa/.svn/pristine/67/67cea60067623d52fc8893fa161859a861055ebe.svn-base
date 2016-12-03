package org.nawa.service;

import org.json.JSONArray;
import org.nawa.dao.ProjectInfoDao;
import org.nawa.dao.UserInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchService {
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	private static SearchService INSTANCE = null;

	private SearchService() {
	} // INIT

	public static SearchService getInstance() {
		synchronized (SearchService.class) {
			if (INSTANCE == null)
				INSTANCE = new SearchService();
			return INSTANCE;
		} // sync
	} // getInstance

	public JSONArray searchProject(String keyword){
		return ProjectInfoDao.searchByTitle(keyword);
	} //searchProject
	
	public JSONArray searchMyProject(String email, String keyword){
		return ProjectInfoDao.searchByTitleInMyProject(email, keyword);
	} //searchMyProject
	
	public JSONArray searchUser(String keyword){
		return UserInfoDao.searchByName(keyword);
	} //searchUser
} // class