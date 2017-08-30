package org.catframework.agileworking.utils;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class JsonUtilsTest {

	@Test
	public void testDecode() {
		Map<String,Object> result =JsonUtils.decode("{\"access_token\":\"ExV9F-XgAZR3uLnUv269VoEuCkeSoSISWigFuwYHw3zlQMKqNn88hW4tsQbp-Ie46oI3MIKYBeKm1nsCO4qBr_PiyC4dqkpqBMgI0n8buVhHBZC4qrsYDqf8Zb2GxQdUMPTgAJAILP\",\"expires_in\":7200}");
		assertEquals(7200,result.get("expires_in"));
	}
}
