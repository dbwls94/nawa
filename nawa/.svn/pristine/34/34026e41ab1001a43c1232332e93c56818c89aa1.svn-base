package org.nawa.noti;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.nawa.common.Conf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationRepo {
	private static final Logger logger = LoggerFactory.getLogger(NotificationRepo.class);
	private static NotificationRepo INSTANCE = null;
	private MVStore notiStore=null;

	private NotificationRepo() {
		String notiRepoDataPath = Conf.get("noti.repo.data.path");
		if (notiRepoDataPath == null || notiRepoDataPath.trim().length() == 0) {
			logger.warn("noti.repo.data.path not set.");
			notiRepoDataPath = System.getProperty("java.io.tmpdir");
		} // if

		this.notiStore = new MVStore.Builder().fileName(new File(notiRepoDataPath, "noti.dat").getAbsolutePath()).open();
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				if(notiStore!=null){
					notiStore.close();
					logger.info("notification store closed");
				} //if
			} //run
		});
	} // INIT

	public static NotificationRepo getInstance() {
		synchronized (NotificationRepo.class) {
			if (INSTANCE == null)
				INSTANCE = new NotificationRepo();
			return INSTANCE;
		} // sync
	} // getInstance
	
	public void put(Notification notiData){
		MVMap<Long, Notification> map=notiStore.openMap(notiData.getTargetUserEmail());
		synchronized (map) {
			Long key=map.lastKey();
			if(key==null)
				key=0L;
			key++;
			map.put(key, notiData);
			notiStore.commit();
		} //sync
	} //put
	
	public List<Notification> getLastN(String userEmail, int count){
		MVMap<Long, Notification> map=notiStore.openMap(userEmail);
		synchronized (map) {
			Long key=map.lastKey();
			if(key==null)
				return null;
			
			List<Notification> noties=new ArrayList<Notification>();
			Set<String> contentIdSet=new HashSet<String>();
			do{
				try{
					Notification noti=map.get(key);
					if(contentIdSet.contains(noti.getContentId()))
						continue;
					noties.add(noti);
				} finally{
					key--;
				} //finally
			} while(noties.size() < count && key>0);
			
			if(noties.size()==0)
				return null;
			return noties;
		} //sync
	} //getLastN
} // class