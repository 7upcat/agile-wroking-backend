package org.catframework.agileworking.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

	List<MeetingRoom> findByTeamId(Long id);
}
