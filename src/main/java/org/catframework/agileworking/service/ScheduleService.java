package org.catframework.agileworking.service;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.domain.Schedule;

public interface ScheduleService {

	List<Schedule> listSchedules(Long meetingRoomId,Date from ,Date to);
}
