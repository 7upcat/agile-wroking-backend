package org.catframework.agileworking.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

	@SuppressWarnings("unchecked")
	public static final Map<String, Object> decode(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, Map.class);
		} catch (IOException e) {
			throw new RuntimeException("JSON解析失败", e);
		}
	}
}
