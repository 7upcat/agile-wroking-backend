package org.catframework.agileworking.web;

import java.util.Optional;

import org.catframework.agileworking.domain.MeetingRoomRepository;
import org.catframework.agileworking.domain.Participant;
import org.catframework.agileworking.domain.Schedule;
import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.domain.TeamRepository;
import org.catframework.agileworking.domain.User;
import org.catframework.agileworking.web.support.DefaultResult;
import org.catframework.agileworking.web.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;

	@Autowired
	private TeamRepository teamRepository;

	@RequestMapping(path = "/schedules/{id}/join", method = RequestMethod.POST)
	public Result<Schedule> join(@PathVariable Long id, @RequestBody Participant participant) {
		Schedule schedule = scheduleRepository.findOne(id);
		Optional<User> user = teamRepository
				.findOne(meetingRoomRepository.findOne(schedule.getMeetingRoom().getId()).getTeamId()).getUsers()
				.stream().filter(s -> s.getOpenId().equals(participant.getOpenId())).findAny();
		Assert.isTrue(user.isPresent(), "用户未绑定.");
		if (!schedule.getParticipants().stream().anyMatch((p) -> {
			return p.getOpenId().equals(participant.getOpenId());
		})) {
			schedule.addParticipant(participant);
			scheduleRepository.save(schedule);
			return DefaultResult.newResult(schedule);
		} else {
			throw new RuntimeException("您已加入过此会议啦.");
		}
	}

	@RequestMapping(path = "/schedules/{id}", method = RequestMethod.GET)
	public Result<Schedule> get(@PathVariable Long id) {
		Schedule schedule = scheduleRepository.findOne(id);
		return DefaultResult.newResult(schedule);
	}
}
