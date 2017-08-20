package org.catframework.agileworking.domain;

import java.util.List;

import org.catframework.agileworking.utils.DateUtils;
import org.catframework.agileworking.vo.ScheduleVO;
import org.catframework.agileworking.web.MeetingRoomController;
import org.catframework.agileworking.web.ScheduleController;
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
public class ScheduleRepositoryTest {

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private ScheduleController scheduleController;

	@Autowired
	private MeetingRoomController meetingRoomController;

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
	public void testFindScheules() {

		Result<List<MeetingRoom>> result = meetingRoomController.list();
		Schedule s = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), s);
		Participant p = new Participant();
		p.setDate(DateUtils.parse("2017-08-02", DateUtils.PATTERN_SIMPLE_DATE));
		p.setAvatarUrl("some url");
		p.setNickName("卯争");
		p.setOpenId("卯争");
		scheduleController.join(s.getId(), p);
		List<ScheduleVO> scheduleVOs=scheduleRepository.findScheules("卯争",DateUtils.parse("2017-08-09", DateUtils.PATTERN_SIMPLE_DATE));
		Assert.assertEquals(1, scheduleVOs.size());
		scheduleRepository.delete(s);
	}

}
