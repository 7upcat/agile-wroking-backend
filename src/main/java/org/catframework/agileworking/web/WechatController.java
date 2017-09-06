package org.catframework.agileworking.web;

import java.util.Map;

import org.catframework.agileworking.utils.JsonUtils;
import org.catframework.agileworking.web.support.DefaultResult;
import org.catframework.agileworking.web.support.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WechatController {

	@Value("${wechat.app-id}")
	private String appId;

	@Value("${wechat.app-secret}")
	private String appSecret;

	@Value("${wecaht.jscode2session-url}")
	private String jscode2sessionUrl;

	private RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(path = "/wechat/openid/{jsCode}", method = RequestMethod.GET)
	public Result<String> getOpenId(@PathVariable String jsCode) {
		Map<String, Object> result = JsonUtils
				.decode(restTemplate.getForObject(jscode2sessionUrl, String.class, appId, appSecret, jsCode));
		String openid = (String) result.get("openid");
		Assert.hasLength(openid, "获取openid失败:" + result);
		return DefaultResult.newResult(openid);
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setJscode2sessionUrl(String jscode2sessionUrl) {
		this.jscode2sessionUrl = jscode2sessionUrl;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}
