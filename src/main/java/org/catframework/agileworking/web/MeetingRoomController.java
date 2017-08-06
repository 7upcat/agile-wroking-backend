package org.catframework.agileworking.web;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.service.ScheduleService;
import org.catframework.agileworking.web.support.DefaultResult;
import org.catframework.agileworking.web.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingRoomController {

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ScheduleService scheduleService;

	@RequestMapping(path = "/meetingRooms", method = RequestMethod.GET)
	public Result<List<MeetingRoom>> list() {
		return DefaultResult.newResult(meetingRoomRepository.findAll());
	}

	/**
	 * 创新排期，单 JVM 并发量可期的情况下，简单粗暴的使用 synchronized 来解决并发创建排期的问题.
	 * 
	 * @param id 会议室 id
	 * @param schedule 新建的排期
	 */
	@RequestMapping(path = "/meetingRooms/{id}/schedule", method = RequestMethod.POST)
	public synchronized void newSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
		MeetingRoom meetingRoom = meetingRoomRepository.findOne(id);
		validate(id, schedule);
		schedule.setMeetingRoom(meetingRoom);
		scheduleRepository.save(schedule);
	}

	/**
	 * 取消已设置的排期.
	 * 
	 * @param id 排期 id
	 */
	@RequestMapping(path = "/meetingRooms/schedule/{id}", method = RequestMethod.DELETE)
	public void cancelSchedule(@PathVariable Long id) {
		Schedule schedule = scheduleRepository.findOne(id);
		scheduleRepository.delete(schedule);
	}

	/**
	 * 查询指定会议室下指定日期区间的排期.
	 * 
	 * @param id 会议室 id
	 * @param from 开始时间
	 * @param to 结束时间
	 * @return 指定的会议室指定日期的排期列表
	 */
	@RequestMapping(path = "/meetingRooms/{id}/schedule", method = RequestMethod.GET)
	public Result<List<Schedule>> schedules(@PathVariable Long id,
			@RequestParam(name = "from") @DateTimeFormat(iso = ISO.DATE) Date from,
			@RequestParam(name = "to") @DateTimeFormat(iso = ISO.DATE) Date to) {
		return DefaultResult.newResult(scheduleService.find(id, from, to));
	}

	private void validate(Long id, Schedule schedule) {
		Assert.isTrue(!scheduleService.find(id, schedule.getDate(), schedule.getDate()).stream().anyMatch((s) -> {
			return s.isConflict(schedule);
		}), "同已有排期冲突");
	}

	public void setMeetingRoomRepository(MeetingRoomRepository meetingRoomRepository) {
		this.meetingRoomRepository = meetingRoomRepository;
	}

	public void setScheduleRepository(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
}
