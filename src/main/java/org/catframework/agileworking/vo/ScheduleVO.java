package org.catframework.agileworking.vo;

import java.math.BigInteger;
import java.util.Date;

import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.utils.DateUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 排期 {@link Schedule} 的值对象聚合了排期及会议室相关的信息.
 * 
 * @author devzzm
 * @see ScheduleRepository#findByOpenIdAndDate(String, Date)
 */
public class ScheduleVO implements Comparable<ScheduleVO> {

	private BigInteger scheduleId;

	private Date date;

	private BigInteger meetingRoomId;

	private String title;

	private String openId;

	private String roomNo;

	private String startTime;

	private String endTime;

	private String repeatMode;

	public BigInteger getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(BigInteger scheduleId) {
		this.scheduleId = scheduleId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigInteger getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(BigInteger meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRepeatMode() {
		return repeatMode;
	}

	public void setRepeatMode(String repeatMode) {
		this.repeatMode = repeatMode;
	}

	/**
	 * 判断当前的排期值对象是否同指定的日期具有相同的星期属性且排期日期小于指定的日期. <br>
	 * 此方法用于查询本人某一日的排期清单使用.
	 * 
	 * @param date 指定日期
	 * @return 当排期的日期同指定的日期的星期属性相同且日期小于指定的日期时返回 <code>true</code>
	 */
	public boolean isNeedInclude(Date date) {
		if (getDate().equals(date)) {
			return true;
		}
		// 排除未来的排期
		if (getDate().compareTo(date) > 0) {
			return false;
		}
		return DateUtils.isSameWeekOfday(date, getDate());
	}

	@Override
	public int compareTo(ScheduleVO o) {
		int result = getStartTime().compareTo(o.getStartTime());
		if (result == 0) {
			return getRoomNo().compareTo(o.getRoomNo());
		}
		return result;
	}
}
