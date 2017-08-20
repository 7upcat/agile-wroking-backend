package org.catframework.agileworking.web;

import java.util.List;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomFactory;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleFactory;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.utils.DateUtils;
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
public class MeetingRoomControllerTest {

	@Autowired
	private MeetingRoomController meetingRoomController;

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

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
	public void list() throws Exception {
		Result<List<MeetingRoom>> result = meetingRoomController.list();
		Assert.assertTrue(result.isSuccess());
		org.junit.Assert.assertEquals(4, result.getPayload().size());
	}

	@Test
	public void newSchedule() {
		Result<List<MeetingRoom>> result = meetingRoomController.list();

		// 创建一个每周重复的排期
		Schedule schedule1 = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "09:00", "12:00");
		try {
			meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), schedule1);
			Assert.assertNotNull(schedule1.getId());
			// 案例验证更新排期
			schedule1.setTitle("分行业务平台项目组临时会议-修订后");
			meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), schedule1);
			Schedule dbSchedule = scheduleRepository.findOne(schedule1.getId());
			Assert.assertEquals(schedule1.getTitle(), dbSchedule.getTitle());
			// 创建人默人参加会议
			Assert.assertEquals(1, dbSchedule.getParticipants().size());
			Assert.assertEquals(schedule1.getCreatorAvatarUrl(), dbSchedule.getParticipants().get(0).getAvatarUrl());
			
			// 当天时间有冲突的案例
			Schedule schedule2 = ScheduleFactory.newSchedule("POS清算代码评审", "发哥", "2017-08-02", "10:00", "11:00");
			try {
				meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), schedule2);
				Assert.fail();
			} catch (Exception e) {
				Assert.assertEquals("同已有排期冲突.", e.getMessage());
			}

			// 下周时间有冲突的案例
			Schedule schedule3 = ScheduleFactory.newSchedule("POS清算代码评审", "发哥", "2017-08-09", "09:30", "15:00");
			try {
				meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), schedule3);
				Assert.fail();
			} catch (Exception e) {
				Assert.assertEquals("同已有排期冲突.", e.getMessage());
			}
		} finally {
			scheduleRepository.delete(schedule1);
		}

	}

	@Test
	public void cancelSchedule() {
		Result<List<MeetingRoom>> result = meetingRoomController.list();
		// 创建一个每周重复的排期
		Schedule s = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), s);
		Assert.assertNotNull(scheduleRepository.findOne(s.getId()));
		meetingRoomController.cancelSchedule(s.getId());
		Assert.assertNull(scheduleRepository.findOne(s.getId()));
	}

	@Test
	public void schedules() {
		Result<List<MeetingRoom>> result = meetingRoomController.list();
		// 创建一个每周重复的排期
		Schedule schedule1 = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), schedule1);
		Schedule schedule2 = ScheduleFactory.newWeeklySchedule("CPOS临时例会", "发哥", "2017-08-09", "14:00", "16:00");
		meetingRoomController.createOrUpdateSchedule(result.getPayload().get(0).getId(), schedule2);
		try {
			Result<List<Schedule>> schedulesResult = meetingRoomController.schedules(result.getPayload().get(0).getId(),
					DateUtils.parse("2017-08-09", DateUtils.PATTERN_SIMPLE_DATE));
			Assert.assertEquals(2, schedulesResult.getPayload().size());
		} finally {
			scheduleRepository.delete(schedule1);
			scheduleRepository.delete(schedule2);
		}
	}
}
