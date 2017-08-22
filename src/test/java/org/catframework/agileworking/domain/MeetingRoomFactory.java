package org.catframework.agileworking.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于单元测试的 {@link MeetingRoom} 工厂,毕竟在案例中繁琐的重复的构建初始化数据.
 * 
 * @author devzzm
 */
public final class MeetingRoomFactory {

	public static final Long DEFAULT_TEAM_ID = 1L;

	public static final List<MeetingRoom> defaultMeetingRooms() {
		List<MeetingRoom> meetingRooms = new ArrayList<>();
		meetingRooms.add(MeetingRoomFactory.newVideoMeetingRoom("3201", "大", "思科", "182.207.96.163", "21169"));
		meetingRooms.add(MeetingRoomFactory.newVideoMeetingRoom("3202", "中", "华为", "182.207.96.166", "120706"));
		meetingRooms.add(MeetingRoomFactory.newMeetingRoom("3203", "小"));
		meetingRooms.add(MeetingRoomFactory.newMeetingRoom("3206", "小"));
		return meetingRooms;
	}

	public static final MeetingRoom newVideoMeetingRoom(String roomNo, String size, String vendor, String ip,
			String terminalIp) {
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomNo(roomNo);
		meetingRoom.setIp(ip);
		meetingRoom.setSize(size);
		meetingRoom.setTerminalId(terminalIp);
		meetingRoom.setType("视频");
		meetingRoom.setVnedor(vendor);
		meetingRoom.setTeamId(MeetingRoomFactory.DEFAULT_TEAM_ID);
		return meetingRoom;
	}

	public static final MeetingRoom newMeetingRoom(String roomNo, String size) {
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomNo(roomNo);
		meetingRoom.setSize(size);
		meetingRoom.setType("普通");
		meetingRoom.setTeamId(MeetingRoomFactory.DEFAULT_TEAM_ID);
		return meetingRoom;
	}
}
