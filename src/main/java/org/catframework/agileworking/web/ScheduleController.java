package org.catframework.agileworking.web;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@RequestMapping("/schedules/{meetingRoomId}")
	public List<Schedule> schedules(@PathVariable Long meetingRoomId,
			@RequestParam(name = "from") @DateTimeFormat(iso = ISO.DATE) Date from,
			@RequestParam(name = "to") @DateTimeFormat(iso = ISO.DATE) Date to) {
		return scheduleService.listSchedules(meetingRoomId, from, to);
	}
}
