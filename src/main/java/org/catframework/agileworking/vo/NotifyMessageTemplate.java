package org.catframework.agileworking.vo;

import java.util.HashMap;
import java.util.Map;

import org.catframework.agileworking.domain.Participant;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.User;
import org.catframework.agileworking.utils.DateUtils;

public class NotifyMessageTemplate {

	private String openId;

	private String templateId;

	private String formId;

	private String title;

	private String roomNo;

	private String time;

	private String participantInfo;

	private String creator;

	public NotifyMessageTemplate(User creator, Schedule schedule, Participant participant, String templateId) {
		this.openId = participant.getOpenId();
		this.templateId = templateId;
		this.formId = participant.getFormId();
		this.title = schedule.getTitle();
		this.roomNo = schedule.getMeetingRoom().getRoomNo();
		this.time = (DateUtils.format(schedule.getDate(), "yyyy年MM月dd日") + " " + schedule.getStartTime() + "-"
				+ schedule.getEndTime());
		this.participantInfo = schedule.getParticipants().size() + "人参加";
		this.creator = creator.getNickName() + "/" + creator.getName()+"("+creator.getMobileNo()+")";
	}

	public String getOpenId() {
		return openId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getFormId() {
		return formId;
	}

	public String getTitle() {
		return title;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public String getTime() {
		return time;
	}

	public String getParticipantInfo() {
		return participantInfo;
	}

	public String getCreator() {
		return creator;
	}

	/**
	 * 按照微信小程序模板消息的数据标准进行数据转换.
	 * 
	 * @return 微信小程序格式的模板消息
	 */
	public Map<String, Object> toTemplateMessage() {
		Map<String, Object> message = new HashMap<>();
		message.put("touser", this.openId);
		message.put("template_id", this.templateId);
		message.put("form_id", this.formId);
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> keyword1 = new HashMap<>();
		keyword1.put("value", this.title);
		data.put("keyword1", keyword1);
		Map<String, Object> keyword2 = new HashMap<>();
		keyword2.put("value", this.time);
		data.put("keyword2", keyword2);

		Map<String, Object> keyword3 = new HashMap<>();
		keyword3.put("value", this.roomNo);
		data.put("keyword3", keyword3);

		Map<String, Object> keyword4 = new HashMap<>();
		keyword4.put("value", this.participantInfo);
		data.put("keyword4", keyword4);

		Map<String, Object> keyword5 = new HashMap<>();
		keyword5.put("value", this.creator);
		data.put("keyword5", keyword5);
		message.put("data", data);
		return message;
	}
}
