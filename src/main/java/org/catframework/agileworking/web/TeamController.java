package org.catframework.agileworking.web;

import java.util.List;
import java.util.Optional;

import org.catframework.agileworking.domain.Team;
import org.catframework.agileworking.domain.TeamRepository;
import org.catframework.agileworking.domain.User;
import org.catframework.agileworking.domain.UserRepository;
import org.catframework.agileworking.web.support.DefaultResult;
import org.catframework.agileworking.web.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserRepository userRepository;

	// 加入团队
	@RequestMapping(path = "/team/{id}/join", method = RequestMethod.POST)
	public Result<User> join(@PathVariable Long id, @RequestBody User user,
			@RequestParam(name = "token") String token) {
		Team team = teamRepository.findOne(id);
		Assert.notNull(team, "团队不存在.");
		Assert.isTrue(team.getToken().equals(token), "口令校验失败.");
		if (user.getId() == null) {
			userRepository.save(user);
		}

		if (team.getUsers().stream().noneMatch(u -> user.getId().equals(u.getId()))) {
			team.addUser(user);
			teamRepository.save(team);
			return DefaultResult.newResult(user);
		} else {
			throw new RuntimeException("不可重复加入团队.");
		}

	}

	// 查询所有的团队
	@RequestMapping(path = "/teams", method = RequestMethod.GET)
	public Result<List<Team>> findAll() {
		return DefaultResult.newResult(teamRepository.findAll());
	}

	// 根据团队 id 及 微信 openId 查询绑定的用户
	@RequestMapping(path = "/team/{teamId}/user/{openId}", method = RequestMethod.GET)
	public Result<User> getUser(@PathVariable(name = "teamId") Long id, @PathVariable(name = "openId") String openId) {
		Team team = teamRepository.findOne(id);
		Optional<User> optional = team.getUsers().stream().filter(s -> s.getOpenId().equals(openId)).findAny();
		return optional.isPresent()? DefaultResult.newResult(optional.get()): DefaultResult.newFailResult("用户未绑定.");  
	}

	public void setTeamRepository(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
