package org.catframework.agileworking.web;

import java.util.List;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleFactory;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.utils.DateUtils;
import org.catframework.agileworking.web.support.Result;
import org.junit.Assert;
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
	private ScheduleRepository scheduleRepository;

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
			meetingRoomController.newSchedule(result.getPayload().get(0).getId(), schedule1);

			// 当天时间有冲突的案例
			Schedule schedule2 = ScheduleFactory.newSchedule("POS清算代码评审", "发哥", "2017-08-02", "10:00", "11:00");
			try {
				meetingRoomController.newSchedule(result.getPayload().get(0).getId(), schedule2);
				Assert.fail();
			} catch (Exception e) {
				Assert.assertEquals("同已有排期冲突", e.getMessage());
			}

			// 下周时间有冲突的案例
			Schedule schedule3 = ScheduleFactory.newSchedule("POS清算代码评审", "发哥", "2017-08-09", "09:30", "15:00");
			try {
				meetingRoomController.newSchedule(result.getPayload().get(0).getId(), schedule3);
				Assert.fail();
			} catch (Exception e) {
				Assert.assertEquals("同已有排期冲突", e.getMessage());
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
		meetingRoomController.newSchedule(result.getPayload().get(0).getId(), s);
		Assert.assertNotNull(scheduleRepository.findOne(s.getId()));
		meetingRoomController.cancelSchedule(s.getId());
		Assert.assertNull(scheduleRepository.findOne(s.getId()));
	}

	@Test
	public void schedules() {
		Result<List<MeetingRoom>> result = meetingRoomController.list();
		// 创建一个每周重复的排期
		Schedule schedule1 = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "13:00", "14:00");
		meetingRoomController.newSchedule(result.getPayload().get(0).getId(), schedule1);
		Schedule schedule2 = ScheduleFactory.newWeeklySchedule("CPOS临时例会", "发哥", "2017-08-08", "14:00", "16:00");
		meetingRoomController.newSchedule(result.getPayload().get(0).getId(), schedule2);
		try {
			Result<List<Schedule>> schedulesResult = meetingRoomController.schedules(result.getPayload().get(0).getId(),
					DateUtils.parse("2017-08-07", DateUtils.PATTERN_SIMPLE_DATE),
					DateUtils.parse("2017-08-11", DateUtils.PATTERN_SIMPLE_DATE));
			Assert.assertEquals(2, schedulesResult.getPayload().size());
		} finally {
			scheduleRepository.delete(schedule1);
			scheduleRepository.delete(schedule2);
		}
	}
}
