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

	@RequestMapping(path = "/meetingRooms/{id}", method = RequestMethod.GET)
	public Result<List<MeetingRoom>> list(@PathVariable Long teamId) {
		return DefaultResult.newResult(meetingRoomRepository.findAll());
	}

	/**
	 * 创新排期，单 JVM 并发量可期的情况下，简单粗暴的使用 synchronized 来解决并发创建、更新排期的问题.
	 * 
	 * @param id 会议室 id
	 * @param schedule 新建的排期
	 */
	@RequestMapping(path = "/meetingRooms/{id}/schedule", method = RequestMethod.POST)
	public synchronized Result<Schedule> createOrUpdateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
		MeetingRoom meetingRoom = meetingRoomRepository.findOne(id);
		validate(id, schedule);
		if (null == schedule.getId()) {
			schedule.setMeetingRoom(meetingRoom);
			scheduleRepository.save(schedule);
		} else {
			Schedule s = scheduleRepository.findOne(schedule.getId());
			Assert.notNull(s, "修改的排期不存在.");
			s.setTitle(schedule.getTitle());
			s.setStartTime(schedule.getStartTime());
			s.setEndTime(schedule.getEndTime());
			s.setDate(schedule.getDate());
			s.setRepeatMode(schedule.getRepeatMode());
			scheduleRepository.save(s);
		}
		return DefaultResult.newResult(schedule);
	}

	/**
	 * 取消已设置的排期.
	 * 
	 * @param id 排期 id
	 */
	@RequestMapping(path = "/meetingRooms/schedule/{id}", method = RequestMethod.DELETE)
	public Result<?> cancelSchedule(@PathVariable Long id) {
		Schedule schedule = scheduleRepository.findOne(id);
		scheduleRepository.delete(schedule);
		return DefaultResult.newResult();
	}

	/**
	 * 查询指定会议室下指定日期区间的排期.
	 * 
	 * @param id 会议室 id
	 * @param date 指定日期
	 * @return 指定的会议室指定日期的排期列表
	 */
	@RequestMapping(path = "/meetingRooms/{id}/schedule", method = RequestMethod.GET)
	public Result<List<Schedule>> schedules(@PathVariable Long id,
			@RequestParam(name = "date") @DateTimeFormat(iso = ISO.DATE) Date date) {
		return DefaultResult.newResult(scheduleService.find(id, date));
	}

	private void validate(Long id, Schedule schedule) {
		Assert.isTrue(schedule.getStartTime().compareTo(schedule.getEndTime()) < 0, "会议开始时间需小于结束时间.");
		Assert.isTrue(!scheduleService.find(id, schedule.getDate()).stream().anyMatch((s) -> {
			return s.isConflict(schedule);
		}), "同已有排期冲突.");
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
