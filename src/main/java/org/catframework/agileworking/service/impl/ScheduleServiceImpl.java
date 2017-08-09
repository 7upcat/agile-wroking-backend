package org.catframework.agileworking.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceImpl implements ScheduleService {

	private static final Log logger = LogFactory.getLog(ScheduleServiceImpl.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	@Override
	public List<Schedule> find(Long meetingRoomId, Date date,Date to) {
		MeetingRoom meetingRoom = meetingRoomRepository.findOne(meetingRoomId);
		List<Schedule> schedules = scheduleRepository.findByMeetingRoomAndDate(meetingRoom, date);
		List<Schedule> weeklySchedules = scheduleRepository.findByRepeatMode(Schedule.REPEAT_MODE_WEEKLY);
		weeklySchedules.stream().forEach((s1) -> {
			if (schedules.stream().noneMatch((s2) -> {
				return s1.getId().equals(s2.getId());
			})) {
				if (s1.getDate().compareTo(date) < 0) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(s1.getDate());
					Calendar c2 = Calendar.getInstance();
					c2.setTime(date);
					logger.info(c1.getTime());
					logger.info(c2.getTime());
					if (c1.get(Calendar.DAY_OF_WEEK) == c2.get(Calendar.DAY_OF_WEEK)) {
						s1.setDate(date);
						schedules.add(s1);
					}
				}
			}
		});
		return schedules;
	}

	public void setScheduleRepository(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	public void setMeetingRoomRepository(MeetingRoomRepository meetingRoomRepository) {
		this.meetingRoomRepository = meetingRoomRepository;
	}
}
