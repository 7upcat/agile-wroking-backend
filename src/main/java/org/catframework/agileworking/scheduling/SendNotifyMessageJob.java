package org.catframework.agileworking.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 发送会议通知，每60秒轮训一次.
 * 
 * @author devzzm
 */
@Component
public class SendNotifyMessageJob {

	@Scheduled(fixedDelay = 60 * 1000)
	public void invoke() {

	}
}
