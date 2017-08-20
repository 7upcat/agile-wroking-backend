package org.catframework.agileworking.web;

import java.util.List;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomFactory;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Participant;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleFactory;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.web.support.Result;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	private List<MeetingRoom> meetingRooms = MeetingRoomFactory.defaultMeetingRooms();

	@Before
	public void before() {
		meetingRoomRepository.save(meetingRooms);
	}

	@After
	public void after() {
		meetingRoomRepository.delete(meetingRooms);
	}

	@Test
	public void testJoin() {
		Result<List<MeetingRoom>> result = meetingRoomController.list();
		Schedule s = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), s);
		Participant p = new Participant();
		p.setAvatarUrl("some url");
		p.setNickName("卯争");
		p.setOpenId("卯争");
		scheduleController.join(s.getId(), p);
		s = scheduleRepository.findOne(s.getId());
		Assert.assertEquals(2, s.getParticipants().size());
		Assert.assertEquals("七猫", s.getParticipants().get(0).getNickName());
		Assert.assertEquals("卯争", s.getParticipants().get(1).getNickName());
		try {
			scheduleController.join(s.getId(), p);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("您已加入过此会议啦.", e.getMessage());
		}
		scheduleRepository.delete(s);
	}
}
