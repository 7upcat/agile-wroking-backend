package org.catframework.agileworking.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

	User findOneByOpenId(String openId);
}
