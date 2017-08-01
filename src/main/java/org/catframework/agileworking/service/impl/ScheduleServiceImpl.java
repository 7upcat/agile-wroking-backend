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
	public List<Schedule> listSchedules(Long meetingRoomId, Date from, Date to) {
		MeetingRoom meetingRoom = meetingRoomRepository.findOne(meetingRoomId);
		List<Schedule> schedules = scheduleRepository.findMeetingRooms(meetingRoom, from, to);
		
		return schedules.stream().map((s) -> {
			// 按周重复的排期，如果非本周，推算出本周的日期设置到排期中
			if(s.getDate().before(from)||s.getDate().after(to)) {
				Calendar cal=Calendar.getInstance();
				cal.setTime(s.getDate());
				// 计算和周一相比较的偏移
				int offset =cal.get(Calendar.DAY_OF_WEEK)-Calendar.MONDAY;
				Calendar destDate = Calendar.getInstance();
				destDate.setTime(from);
				destDate.add(Calendar.DAY_OF_MONTH, offset);
				s.setDate(destDate.getTime());
			}
			return s;
		}).collect(Collectors.toList());
	}
}
