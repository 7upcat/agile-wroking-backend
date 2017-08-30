package org.catframework.agileworking.service.impl;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomFactory;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleFactory;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.utils.DateUtils;
import org.catframework.agileworking.web.MeetingRoomController;
import org.catframework.agileworking.web.support.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceImplTest {

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
	public void testCalendar() throws ParseException {
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(dateFormat.parse("2017-08-01"));
		assertEquals(Calendar.TUESDAY, cal.get(Calendar.DAY_OF_WEEK));
	}

	@Test
	public void testFind() {
		Schedule s1 = ScheduleFactory.newSchedule("分行业务平台项目组临时会议", "七猫", "2017-08-09", "09:00", "12:00");
		Schedule s2 = ScheduleFactory.newWeeklySchedule("分行业务平台项目组临时会议", "七猫", "2017-08-02", "12:00", "14:00");
		Schedule s3 = ScheduleFactory.newSchedule("分行业务平台项目组临时会议", "七猫", "2017-08-16", "14:00", "16:00");
		try {
			meetingRoomController.createOrUpdateSchedule(meetingRooms.get(0).getId(),"fakeFormId" ,s1);
			meetingRoomController.createOrUpdateSchedule(meetingRooms.get(0).getId(),"fakeFormId",s2);
			meetingRoomController.createOrUpdateSchedule(meetingRooms.get(0).getId(),"fakeFormId",s3);
			Result<List<Schedule>> result = meetingRoomController.schedules(meetingRooms.get(0).getId(),
					DateUtils.parse("2017-08-09", DateUtils.PATTERN_SIMPLE_DATE));
			assertEquals(2, result.getPayload().size());
		} finally {
			scheduleRepository.delete(s1);
			scheduleRepository.delete(s2);
			scheduleRepository.delete(s3);
		}
	}
}
