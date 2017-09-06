package org.catframework.agileworking.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.catframework.agileworking.web.support.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 集成测试获取 openid ，需要指定 jsCode
 * @author devzzm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatControllerIntegrationTest {

	private static final Log logger = LogFactory.getLog(WechatControllerIntegrationTest.class);

	@Autowired
	private WechatController wechatController;

	@Test
	public void test() {
		String jsCode = "someJsCode";
		Result<String> result = wechatController.getOpenId(jsCode);
		logger.info(result.getPayload());
	}
}
