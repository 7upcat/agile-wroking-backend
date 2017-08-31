package org.catframework.agileworking.web;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.domain.ScheduleRepository;
import org.catframework.agileworking.utils.DateUtils;
import org.catframework.agileworking.vo.ScheduleVO;
import org.catframework.agileworking.web.support.DefaultResult;
import org.catframework.agileworking.web.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	public Result<List<ScheduleVO>> participants(@PathVariable String openId,
			@RequestParam(name = "date") @DateTimeFormat(pattern = DateUtils.PATTERN_SIMPLE_DATE) Date date) {
		return DefaultResult.newResult(scheduleRepository.findByOpenIdAndDate(openId, date));
	}
}
