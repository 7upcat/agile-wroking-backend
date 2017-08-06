package org.catframework.agileworking.web;

import org.catframework.agileworking.domain.Participant;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping(path = "/schedules/{id}/join", method = RequestMethod.POST)
	public void join(@PathVariable Long id, @RequestBody Participant participant) {
		Schedule schedule = scheduleRepository.findOne(id);
		if (!schedule.getParticipants().stream().anyMatch((p) -> {
			return p.getNickName().equals(participant.getNickName());
		})) {
			schedule.addParticipant(participant);
			scheduleRepository.save(schedule);
		}
	}
}
