package org.catframework.agileworking.web;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.web.support.DefaultResult;
import org.catframework.agileworking.web.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantController {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping(path = "/participant/{openId}", method = RequestMethod.GET)
	public Result<List<Object>> participants(@PathVariable String openId , @RequestParam(name="date")  Date date) {
		return DefaultResult.newResult(scheduleRepository.findScheules(openId, date));
	}
	
}
