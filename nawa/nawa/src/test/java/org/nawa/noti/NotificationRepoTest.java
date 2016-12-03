package org.nawa.noti;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class NotificationRepoTest {

	@Test
	public void test() {
		try{
			final String userEmail=UUID.randomUUID().toString() + "@email.com";
			final String contentId=UUID.randomUUID().toString();

			System.out.println(System.getProperty("java.io.tmpdir"));

			Notification data=new Notification();
			data.setTargetUserEmail(userEmail);
			data.setContentId(contentId);

			NotificationRepo.getInstance().put(data);
			List<Notification> list=NotificationRepo.getInstance().getLastN(userEmail, 1);
			assertTrue(list.size()==1);
			assertTrue(list.get(0).getTargetUserEmail().equals(userEmail));
			assertTrue(list.get(0).getContentId().equals(contentId));

			list=NotificationRepo.getInstance().getLastN(userEmail, 10);
			assertTrue(list.size()==1);
		} catch(Exception e){
			fail(e.getMessage());
		} //catch
	} //test
} //class