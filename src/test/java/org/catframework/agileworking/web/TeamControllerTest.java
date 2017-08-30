package org.catframework.agileworking.web;

import java.util.List;

import org.catframework.agileworking.domain.Team;
import org.catframework.agileworking.domain.TeamFactory;
import org.catframework.agileworking.domain.TeamRepository;
import org.catframework.agileworking.domain.User;
import org.catframework.agileworking.domain.UserFactory;
import org.catframework.agileworking.domain.UserRepository;
import org.catframework.agileworking.service.WebTokenService;
import org.catframework.agileworking.web.support.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamControllerTest {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TeamController teamController;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WebTokenService webTokenService;

	@Test
	public void testJoin() {
		Team team = teamRepository.save(TeamFactory.newDefaultTeam());
		User user = UserFactory.newDefaultUser();
		userRepository.save(user);
		team = teamRepository.findOne(team.getId());
		Result<User> result = teamController.join(team.getId(), user, team.getToken());
		String token = (String) result.getHeader("token");
		Assert.assertEquals(webTokenService.generate(user.getOpenId()), token);
		team = teamRepository.findOne(team.getId());
		Assert.assertEquals(1, team.getUsers().size());
		team.getUsers().clear();
		teamRepository.save(team);
		team = teamRepository.findOne(team.getId());
		Assert.assertEquals(0, team.getUsers().size());
		userRepository.delete(user);
		teamRepository.delete(team);
	}

	@Test
	public void testList() {
		Team team = teamRepository.save(TeamFactory.newDefaultTeam());
		Result<List<Team>> result = teamController.findAll();
		Assert.assertEquals(1, result.getPayload().size());
		teamRepository.delete(team);
	}

	@Test
	public void testGetUser() {
		Team team = teamRepository.save(TeamFactory.newDefaultTeam());
		User user = UserFactory.newDefaultUser();
		userRepository.save(user);
		Result<User> result = teamController.getUser(team.getId(), user.getOpenId());
		Assert.assertFalse(result.isSuccess());
		Assert.assertEquals("用户未绑定.", result.getResponseMessage());
		teamController.join(team.getId(), user, team.getToken());
		result = teamController.getUser(team.getId(), user.getOpenId());
		Assert.assertTrue(result.isSuccess());
		String token = (String) result.getHeader("token");
		Assert.assertEquals(webTokenService.generate(user.getOpenId()), token);
		userRepository.delete(user);
		teamRepository.delete(team);
	}
}
