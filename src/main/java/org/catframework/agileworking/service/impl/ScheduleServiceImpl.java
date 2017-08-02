package org.catframework.agileworking.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	@Override
	public List<Schedule> find(Long meetingRoomId, Date from, Date to) {
		MeetingRoom meetingRoom = meetingRoomRepository.findOne(meetingRoomId);
		List<Schedule> schedules = scheduleRepository.findByMeetingRoomAndDateBetween(meetingRoom, from, to);
		List<Schedule> weeklySchedules = scheduleRepository.findByRepeatMode(Schedule.REPEAT_MODE_WEEKLY);

		weeklySchedules.stream().forEach((s1) -> {
			if (schedules.stream().noneMatch((s2) -> {
				return s1.getId().equals(s2.getId());
			})) {
				schedules.add(s1);
			}
		});

		return schedules.stream().map((s) -> {
			if (s.isRepeatModeWeekly()) {
				rescheduling(from, to, s);
			}
			return s;
		}).filter((s) -> {
			return s.getDate().compareTo(from) >= 0 || s.getDate().compareTo(to) <= 0;
		}).sorted().collect(Collectors.toList());
	}

	// 如果是按周重复的那么需要重新设置调度
	private Schedule rescheduling(Date from, Date to, Schedule s) {

		if (s.getDate().before(from) || s.getDate().after(to)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(s.getDate());
			Calendar destDate = Calendar.getInstance();
			destDate.setTime(from);
			destDate.add(Calendar.DAY_OF_MONTH,  cal.get(Calendar.DAY_OF_WEEK)- destDate.get(Calendar.DAY_OF_WEEK) );
			s.setDate(destDate.getTime());
		}
		return s;
	}
}
