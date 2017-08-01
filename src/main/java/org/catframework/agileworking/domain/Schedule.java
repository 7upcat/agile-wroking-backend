package org.catframework.agileworking.domain;

import java.io.Serializable;
import java.util.Calendar;
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
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	/** 会议的标题. */
	@Column(nullable = false)
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

	/** 创建者的微信昵称. */
	@Column(nullable = false)
	private String creatorNickName;

	/** 创建者的微信头像的链接. */
	@Column(nullable = false)
	private String creatorAvatarUrl;

	@Column(nullable = false)
	/** 会议重复的模式: N-不重复/W-每周. */
	private String repeatMode;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "schedule")
	private List<Participant> participants;

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

	@JsonFormat(pattern="yyyy-MM-dd")
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

	/**
	 * @return 返回当前排期属于周几,严格按照 周一为 1 星期天为 7
	 */
	public int getWeekDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return 7;
		}
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}
}
