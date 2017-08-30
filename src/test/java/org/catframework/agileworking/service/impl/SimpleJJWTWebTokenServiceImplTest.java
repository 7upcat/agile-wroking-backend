package org.catframework.agileworking.service.impl;

import static org.junit.Assert.*;

import org.catframework.agileworking.service.WebTokenService;
import org.junit.Test;

public class SimpleJJWTWebTokenServiceImplTest {

	private  static WebTokenService webTokenService = new SimpleJJWTWebTokenServiceImpl();

	private static String token = null;

	private static String subject = "7upcat";

	@Test
	public void testGenerate() {
		token = webTokenService.generate(subject);
		assertNotNull(token);
	}

	@Test
	public void testVerify() {
		assertTrue(webTokenService.verify(subject, token));
	}

}
