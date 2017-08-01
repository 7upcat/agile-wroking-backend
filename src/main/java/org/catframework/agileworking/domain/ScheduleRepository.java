package org.catframework.agileworking.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("select s from Schedule s where meetingRoom=?1 and (date >=?2 and date<=?3 or repeatMode='W') order by date asc ")
	List<Schedule> findMeetingRooms(MeetingRoom meetingRoom, Date from, Date to);
	
}
