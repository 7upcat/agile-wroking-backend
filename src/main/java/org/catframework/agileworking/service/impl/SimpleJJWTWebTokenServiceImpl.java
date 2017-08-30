package org.catframework.agileworking.service.impl;

import java.security.Key;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.catframework.agileworking.service.WebTokenService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * 使用 JJWT 做为 WebToken 的实现.
 * 
 * @author devzzm
 */
@Component
public class SimpleJJWTWebTokenServiceImpl implements WebTokenService {

	private static final Log logger = LogFactory.getLog(SimpleJJWTWebTokenServiceImpl.class);

	private Key key = MacProvider.generateKey();

	@Override
	public String generate(String subject) {
		return Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS512, key).compact();
	}

	@Override
	public boolean verify(String subject, String token) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject().equals(subject);
		} catch (Exception e) {
			logger.error("Verify fail:", e);
			return false;
		}
	}
}
