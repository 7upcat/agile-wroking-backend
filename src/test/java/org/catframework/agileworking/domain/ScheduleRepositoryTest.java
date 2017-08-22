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
	
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private UserRepository userRepository;

	private Team team = TeamFactory.newDefaultTeam();
	
	private User user = UserFactory.newDefaultUser();

	private List<MeetingRoom> meetingRooms = MeetingRoomFactory.defaultMeetingRooms();

	@Before
	public void before() {
		userRepository.save(user);
		teamRepository.save(team);
		team.addUser(user);
		teamRepository.save(team);
		meetingRooms.stream().forEach(m-> m.setTeamId(team.getId()));
		meetingRoomRepository.save(meetingRooms);
	}

	@After
	public void after() {
		teamRepository.delete(team.getId());
		userRepository.delete(user);
		meetingRoomRepository.delete(meetingRooms);
	}

	@Test
	public void testFindScheules() {

		Result<List<MeetingRoom>> result = meetingRoomController.list(MeetingRoomFactory.DEFAULT_TEAM_ID);
		Schedule s = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), s);
		Participant p = new Participant();
		p.setDate(DateUtils.parse("2017-08-02", DateUtils.PATTERN_SIMPLE_DATE));
		p.setAvatarUrl("some url");
		p.setNickName("7upcat");
		p.setOpenId("7upcat_open_id");
		scheduleController.join(s.getId(), p);
		s = scheduleRepository.findOne(s.getId());
		Assert.assertEquals(2, s.getParticipants().size());
		Assert.assertEquals("七猫", s.getParticipants().get(0).getNickName());
		try {
			scheduleController.join(s.getId(), p);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("您已加入过此会议啦.", e.getMessage());
		}
		List<ScheduleVO> scheduleVOs=scheduleRepository.findScheules("7upcat_open_id",DateUtils.parse("2017-08-09", DateUtils.PATTERN_SIMPLE_DATE));
		Assert.assertEquals(1, scheduleVOs.size());
		scheduleRepository.delete(s);
	}

}
