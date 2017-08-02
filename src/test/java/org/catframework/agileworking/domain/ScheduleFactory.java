package org.catframework.agileworking.domain;

import org.catframework.agileworking.utils.DateUtils;

public class ScheduleFactory {

	public static final String DEFAULT_CREATOR_AVATAR_URL = "http://baidu.com";

	public static Schedule newSchedule(String title, String creator, String date, String startTime, String endTime) {
		Schedule s = new Schedule();
		s.setTitle(title);
		s.setCreatorNickName(creator);
		s.setCreatorAvatarUrl(ScheduleFactory.DEFAULT_CREATOR_AVATAR_URL);
		s.setDate(DateUtils.parse(date, DateUtils.PATTERN_SIMPLE_DATE));
		s.setStartTime(startTime);
		s.setEndTime(endTime);
		s.setRepeatMode(Schedule.REPEAT_MODE_NO);
		return s;
	}

	public static Schedule newWeeklySchedule(String title, String creator, String date, String startTime,
			String endTime) {
		Schedule s = newSchedule(title, creator, date, startTime, endTime);
		s.setRepeatMode(Schedule.REPEAT_MODE_WEEKLY);
		return s;
	}

}
