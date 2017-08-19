package org.catframework.agileworking.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByMeetingRoomAndDate(MeetingRoom meetingRoom, Date date);
	
	List<Schedule> findByRepeatMode(String repeatMode);
	
	@Query(nativeQuery = true,value="select  t.schedule_id,t.date,t.meeting_room_id,t.title,t.open_id,m.room_no,t.start_time,t.end_time    from (select  p.schedule_id,p.date,s.meeting_room_id,s.title,p.open_id,s.start_time,s.end_time from participant p left join  schedule  s on    p.schedule_id = s.id  ) as t left join meeting_room m on t.meeting_room_id  = m.id  where t.open_id=?1 and  t.date=?2")
	List<Object> findScheules(String openId,Date date);
}
