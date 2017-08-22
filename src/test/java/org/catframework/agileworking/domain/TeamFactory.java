package org.catframework.agileworking.domain;

public final class TeamFactory {

	/** 团队注册时使用的默认的口令. */
	public static final String DEFAULT_TEAM_TOKEN = "9527";

	public static Team newDefaultTeam() {
		Team team = new Team();
		team.setName("深研二部");
		team.setDesc("一个敏捷的团队");
		team.setToken(TeamFactory.DEFAULT_TEAM_TOKEN);
		return team;
	}
}
