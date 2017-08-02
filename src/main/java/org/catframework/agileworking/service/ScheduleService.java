package org.catframework.agileworking.service;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.domain.Schedule;

public interface ScheduleService {

	/**
	 * 查找指定会议室指定日期区间的排期，会自动对按周重复的排期进行计算.
	 * 
	 * @param meetingRoomId 会议室 id
	 * @param from 开始日期（包含）
	 * @param to 结束日期（包含）
	 * @return 符合条件的排期列表
	 */
	List<Schedule> find(Long meetingRoomId, Date from, Date to);
}
