package org.catframework.agileworking.web;

import java.util.List;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.Participant;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleFactory;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.web.support.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleControllerTest {

	@Autowired
	private ScheduleController scheduleController;
	
	@Autowired
	private MeetingRoomController meetingRoomController;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Test
	public void testJoin() {
		Result<List<MeetingRoom>> result = meetingRoomController.list();
		Schedule s = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.newSchedule(result.getPayload().get(0).getId(), s);
		Participant p = new Participant();
	    p.setAvatarUrl("some url");
	    p.setNickName("卯争");
		scheduleController.join(s.getId(), p);
		s=scheduleRepository.findOne(s.getId());
		Assert.assertEquals(1, s.getParticipants().size());
		Assert.assertEquals("卯争", s.getParticipants().get(0).getNickName());
		scheduleRepository.delete(s);
	}
}
