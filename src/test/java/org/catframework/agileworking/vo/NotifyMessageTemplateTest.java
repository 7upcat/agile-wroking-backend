package org.catframework.agileworking.vo;

import java.util.Map;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.Participant;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.User;
import org.catframework.agileworking.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class NotifyMessageTemplateTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testToTemplateMessage() {
		User creator = new User();
		creator.setOpenId("xxOpenId");
		creator.setName("郑明明");
		creator.setNickName("七猫");
		creator.setMobileNo("18603010498");
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomNo("3203");
		Schedule schedule = new Schedule();
		schedule.setTitle("讨论一下明天吃点咩");
		schedule.setMeetingRoom(meetingRoom);
		schedule.setDate(DateUtils.parse("2017-08-31", DateUtils.PATTERN_SIMPLE_DATE));
		schedule.setStartTime("10:00");
		schedule.setEndTime("12:00");
		Participant participant = new Participant();
		participant.setNickName("七猫");
		participant.setOpenId(creator.getOpenId());
		participant.setFormId("mockFormId");
		schedule.addParticipant(participant);
		NotifyMessageTemplate teamplate = new NotifyMessageTemplate(creator, schedule, participant, "mockTemplateId");
		Map<String,Object> message = teamplate.toTemplateMessage();
		
		Assert.assertEquals("xxOpenId", message.get("touser"));
		Assert.assertEquals("mockTemplateId", message.get("template_id"));
		Assert.assertEquals("mockFormId", message.get("form_id"));
		Map<String,Object> data = (Map<String, Object>) message.get("data");
		Map<String,Object> keyword1 = (Map<String, Object>) data.get("keyword1");
		Assert.assertEquals(schedule.getTitle(), keyword1.get("value"));
		Map<String,Object> keyword2 = (Map<String, Object>) data.get("keyword2");
		Assert.assertEquals("2017年08月31日 10:00-12:00", keyword2.get("value"));
		Map<String,Object> keyword3 = (Map<String, Object>) data.get("keyword3");
		Assert.assertEquals(meetingRoom.getRoomNo(), keyword3.get("value"));
		Map<String,Object> keyword4 = (Map<String, Object>) data.get("keyword4");
		Assert.assertEquals("1人参加", keyword4.get("value"));
		Map<String,Object> keyword5 = (Map<String, Object>) data.get("keyword5");
		Assert.assertEquals(creator.getNickName()+"/"+creator.getName()+"("+creator.getMobileNo()+")", keyword5.get("value"));
	}
}
