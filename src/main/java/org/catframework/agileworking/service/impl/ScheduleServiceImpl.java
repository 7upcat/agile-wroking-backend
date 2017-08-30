package org.catframework.agileworking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.service.ScheduleService;
import org.catframework.agileworking.utils.DateUtils;
import org.catframework.agileworking.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	private RestTemplate restTemplate = new RestTemplate();

	private String appId;

	private String appSecret;

	private Long expireTime = 0L;

	private String accessToken;

	@Override
	public List<Schedule> find(Long meetingRoomId, Date date) {
		MeetingRoom meetingRoom = meetingRoomRepository.findOne(meetingRoomId);
		List<Schedule> schedules = scheduleRepository.findByMeetingRoomAndDate(meetingRoom, date);
		List<Schedule> weeklySchedules = scheduleRepository.findByRepeatMode(Schedule.REPEAT_MODE_WEEKLY);
		weeklySchedules.stream().forEach((s1) -> {
			if (schedules.stream().noneMatch((s2) -> s1.getId().equals(s2.getId()))) {
				if (s1.getDate().compareTo(date) < 0) {
					if (DateUtils.isSameWeekOfday(s1.getDate(), date)) {
						s1.setDate(date);
						schedules.add(s1);
					}
				}
			}
		});
		// 按照开始时间进行排序
		return schedules.stream().sorted((s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime()))
				.collect(Collectors.toList());
	}

	@Override
	public synchronized void notify(Schedule schedule) {

		if (null == accessToken || System.currentTimeMillis() > expireTime) {
			Map<String, Object> result = JsonUtils.decode(restTemplate.getForObject(
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={appSecret}",
					String.class, appId, appSecret));
			this.accessToken = (String) result.get("access_token");
			Assert.notNull(this.accessToken, "accessToken 获取失败!");
			expireTime = System.currentTimeMillis() + 2 * 60 * 60 * 1000L;
		}else {
			
		}

	}

	public void setScheduleRepository(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	public void setMeetingRoomRepository(MeetingRoomRepository meetingRoomRepository) {
		this.meetingRoomRepository = meetingRoomRepository;
	}
}
