package org.catframework.agileworking.domain;

public final class UserFactory {

	public static User newDefaultUser() {
		User user = new User();
		user.setOpenId("7upcat_open_id");
		user.setAvatarUrl("http://catframework.cn");
		user.setMobileNo("18603010499");
		user.setName("郑明明");
		user.setNickName("7upcat");
		return user;
	}
}
