package org.catframework.agileworking.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 会议室排期.
 * 
 * @author devzzm
 */
@Entity
public class Schedule implements Serializable, Comparable<Schedule> {

	private static final long serialVersionUID = 1L;

	/** 排期的重复模式：不重复. */
	public static final String REPEAT_MODE_NO = "N";

	/** 排期的重复模式：按周. */
	public static final String REPEAT_MODE_WEEKLY = "W";

	@Id
	@GeneratedValue
	private Long id;

	/** 会议的标题. */
	@Column(nullable = false, length = 1024)
	private String title;

	/** 会议室房间编号. */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private MeetingRoom meetingRoom;

	/** 预订日期. */
	@Column(nullable = false)
	private Date date;

	/** 开始时间,格式为 'HH:mm:ss'. */
	@Column(nullable = false)
	private String startTime;

	/** 结束时间.格式为 'HH:mm:ss'. */
	@Column(nullable = false)
	private String endTime;

	/** 创建者的微信 openId*/
	@Column(nullable = false)
	private String creatorOpenId;
	
	/** 创建者的微信昵称. */
	@Column(nullable = false)
	private String creatorNickName;

	/** 创建者的微信头像的链接. */
	@Column(nullable = false, length = 1024)
	private String creatorAvatarUrl;

	@Column(nullable = false)
	/** 会议重复的模式: N-不重复/W-每周. */
	private String repeatMode;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "schedule")
	private List<Participant> participants = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getCreatorNickName() {
		return creatorNickName;
	}

	public void setCreatorNickName(String creatorNickName) {
		this.creatorNickName = creatorNickName;
	}

	public String getCreatorAvatarUrl() {
		return creatorAvatarUrl;
	}

	public void setCreatorAvatarUrl(String creatorAvatarUrl) {
		this.creatorAvatarUrl = creatorAvatarUrl;
	}

	public String getRepeatMode() {
		return repeatMode;
	}

	public void setRepeatMode(String repeatMode) {
		this.repeatMode = repeatMode;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}
	
	public String getCreatorOpenId() {
		return creatorOpenId;
	}

	public void setCreatorOpenId(String creatorOpenId) {
		this.creatorOpenId = creatorOpenId;
	}

	public void addParticipant(Participant participant) {
		participant.setSchedule(this);
		this.getParticipants().add(participant);
	}

	/**
	 * @return 当排期的重复模式为按周重复返回 <code>true</code>
	 */
	public boolean isRepeatModeWeekly() {
		return Schedule.REPEAT_MODE_WEEKLY.equals(getRepeatMode());
	}

	/**
	 * 判断当前排期和指定的排期是否有冲突.
	 * 
	 * @param schedule 指定用来判断是否冲突的排期
	 * @return 有冲突返回 <code>true</code>
	 */
	public boolean isConflict(Schedule schedule) {
		
		// 同一个排期肯定不算冲突 
		if(getId().equals(schedule.getId())) {
			return false;
		}
		
		if (date.compareTo(schedule.getDate()) != 0) {
			return false;
		}
		if (startTime.compareTo(schedule.getStartTime()) >= 0 && startTime.compareTo(schedule.getEndTime()) < 0) {
			return true;
		}
		if (endTime.compareTo(schedule.getStartTime()) > 0 && endTime.compareTo(schedule.getEndTime()) <= 0) {
			return true;
		}
		if (startTime.compareTo(schedule.getStartTime()) < 0 && endTime.compareTo(schedule.getEndTime()) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Schedule o) {
		if (this.date.compareTo(o.getDate()) == 0) {
			return this.startTime.compareTo(o.getStartTime());
		} else {
			return this.date.compareTo(o.getDate());
		}
	}
}
