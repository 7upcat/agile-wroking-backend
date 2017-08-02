package org.catframework.agileworking.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByMeetingRoomAndDateBetween(MeetingRoom meetingRoom, Date from, Date to);
	
	List<Schedule> findByRepeatMode(String repeatMode);
}
