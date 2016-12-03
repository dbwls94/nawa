package org.nawa.noti;

import java.io.Serializable;

public class Notification implements Serializable{
	private String targetUserEmail;
	private String contentId;

	public String getTargetUserEmail() {
		return targetUserEmail;
	}

	public void setTargetUserEmail(String targetUserEmail) {
		this.targetUserEmail = targetUserEmail;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
} // class