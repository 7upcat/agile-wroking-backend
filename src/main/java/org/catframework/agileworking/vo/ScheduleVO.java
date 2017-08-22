package org.catframework.agileworking.vo;

import java.math.BigInteger;
import java.util.Date;

import org.catframework.agileworking.utils.DateUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ScheduleVO {

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
}
