package org.catframework.agileworking.web;

import org.catframework.agileworking.domain.MeetingRoom;
import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingRoomController {

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	@RequestMapping("/meetingRooms")
	public Iterable<MeetingRoom> meetingRooms() {
		return meetingRoomRepository.findAll();
	}
}
