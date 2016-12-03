package org.nawa.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nawa.common.json.JsonColumn;
import org.nawa.common.json.JsonSerializable;
import org.nawa.daomapper.DaoMapper;
import org.nawa.daomapper.Mapped;
import org.nawa.rdb.DbHandler;

/**
 * @description 사용자에게 알림 전달을 위한 notification 데이터 객체. notification table과 1:1 매핑된다.
 * @author leejy
 */
public class NotificationDao extends JsonSerializable {
	//follow하는 친구가 모임 참가시
	public static final int TYPE_FOLLOWING_USER_PARTICIPATE_PROJECT = 1;
	
	//follow하는 친구가 모임 추진시
	public static final int TYPE_FOLLOWING_USER_CREATE_PROJECT = 2;
	
	//내가 추진/참가하는 모임에 새로운 댓글 달릴때
	public static final int TYPE_NEW_COMMENT_IN_PROJECT_WHICH_RELATE_ME = 3;
	
	//내가 참가하는 모임이 수정되었을 때
	public static final int TYPE_PROJECT_MODIFIED_WHICH_I_PARTICIPATE = 4;
	
	//모임에서 쫓겨났을 때
	public static final int TYPE_KICKED_FROM_PARTICIPATING_PROJECT = 5;
	
	//모임에 초대되었을 때
	public static final int TYPE_SOMEONE_INVITE_ME_TO_PROJECT = 6;
	
	//내가 댓글을 남긴 모임에 다른 댓글이 달렸을 때
	public static final int TYPE_NEW_COMMENT_IN_PROJECT_WHICH_I_COMMENTED = 7;
	
	//참가 신청한 모임에서 수락/거절되었을 때
	public static final int TYPE_ACCEPTED_OR_DENIED_FROM_PROJECT = 8;

	@JsonColumn(key = "noti_id")
	@Mapped(column = "noti_id")
	private String notiId;

	@JsonColumn(key = "type")
	@Mapped(column = "type")
	private int type;

	@JsonColumn(key = "owner")
	@Mapped(column = "owner")
	private String owner;

	@JsonColumn(key = "is_read")
	@Mapped(column = "is_read")
	private int isRead;

	@JsonColumn(key = "regdate")
	@Mapped(column = "regdate")
	private Date regdate;

	@JsonColumn(key = "additional_data1")
	@Mapped(column = "additional_data1")
	private String additionalData1;

	@JsonColumn(key = "additional_data2")
	@Mapped(column = "additional_data2")
	private String additionalData2;

	@JsonColumn(key = "additional_data3")
	@Mapped(column = "additional_data3")
	private String additionalData3;

	@JsonColumn(key = "additional_data4")
	@Mapped(column = "additional_data4")
	private String additionalData4;

	@JsonColumn(key = "additional_data5")
	@Mapped(column = "additional_data5")
	private String additionalData5;

	public static boolean insert(NotificationDao notiDao){
		String query="insert into notification (noti_id, type, owner, is_read, regdate, "
				+ "additional_data1, additional_data2, additional_data3, additional_data4, additional_data5) "
				+ "values (?,?,?,?,?,?,?,?,?,?)";
		return DbHandler.preparedUpdate(query, notiDao.getNotiId(), notiDao.getType(), notiDao.getOwner(), 
				notiDao.getIsRead(), notiDao.getRegdate(), notiDao.getAdditionalData1(), 
				notiDao.getAdditionalData2(), notiDao.getAdditionalData3(), notiDao.getAdditionalData4(), notiDao.getAdditionalData5());
	} //insert
	
	public static boolean delete(String id){
		String query="delete from notification where noti_id = ?";
		return DbHandler.preparedUpdate(query, id);
	} //delete
	
	public static Map<Integer, List<NotificationDao>> selectUnreadList(String email){
		Map<Integer, List<NotificationDao>> daoMap = new HashMap<Integer, List<NotificationDao>>();
		String query = "select noti_id, type, owner, is_read, regdate, additional_data1, additional_data2, "
				+ "additional_data3, additional_data4, additional_data5 from notification "
				+ "where owner = ? "
				+ "and is_read = 0";
		List<NotificationDao> notis = new DaoMapper<NotificationDao>().selectList(NotificationDao.class, query, email);
		for(NotificationDao noti : notis){
			int type = noti.getType();
			if(daoMap.get(type) == null)
				daoMap.put(type, new ArrayList<NotificationDao>());
			daoMap.get(type).add(noti);
		} //for noti
		return daoMap;
	} //selectUnreadList
	
	/**
	 * @description 모든 사용자들의 unread count를 db로부터 잃어온다.
	 * @return Map<String:user_email, Integer:unreadCount>
	 */
	public static Map<String, Integer> selectUnreadCount(){
		String query = "select owner, count(*) from notification where is_read = 0 group by owner";
		String[][] result = DbHandler.select(query);
		if(result == null || result.length == 0 || result[0].length == 0)
			return null;
		
		Map<String, Integer> countsMap = new HashMap<String, Integer>();
		for (int i = 0; i < result.length; i++)
			countsMap.put(result[i][0], Integer.parseInt(result[i][1]));
		
		return countsMap;
	} //selectUnreadCount	
	
	public String getNotiId() {
		return notiId;
	}

	public void setNotiId(String notiId) {
		this.notiId = notiId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	protected String getAdditionalData1() {
		return additionalData1;
	}

	protected void setAdditionalData1(String additionalData1) {
		this.additionalData1 = additionalData1;
	}

	protected String getAdditionalData2() {
		return additionalData2;
	}

	protected void setAdditionalData2(String additionalData2) {
		this.additionalData2 = additionalData2;
	}

	protected String getAdditionalData3() {
		return additionalData3;
	}

	protected void setAdditionalData3(String additionalData3) {
		this.additionalData3 = additionalData3;
	}

	protected String getAdditionalData4() {
		return additionalData4;
	}

	protected void setAdditionalData4(String additionalData4) {
		this.additionalData4 = additionalData4;
	}

	protected String getAdditionalData5() {
		return additionalData5;
	}

	protected void setAdditionalData5(String additionalData5) {
		this.additionalData5 = additionalData5;
	}

	@Override
	public String toString() {
		return toJson();
	} // tostring
} // class