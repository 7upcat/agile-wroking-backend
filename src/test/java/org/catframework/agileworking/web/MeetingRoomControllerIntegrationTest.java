package org.catframework.agileworking.web;

import org.catframework.agileworking.domain.ScheduleFactory;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class MeetingRoomControllerIntegrationTest {

	private String url = "http://localhost:8080";
	
	@Test
	public void schedules() {
		RestTemplate restTemplate= new RestTemplate();
		restTemplate.postForObject(url+"/meetingRooms/{id}/schedule", ScheduleFactory.newSchedule("测试排期", "7upcat", "2017-02-02", "12:00", "16:00"), String.class,"1");
	}
}
