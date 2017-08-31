package org.catframework.agileworking.scheduling;

import java.util.Date;

import org.catframework.agileworking.utils.DateUtils;
import org.junit.Test;

public class SendNotifyMessageJobTest {

	@Test
	public void testIsNeedSendMessageNow() {
		Date d =DateUtils.parse("2017-08-31" + " " + "10:20",DateUtils.PATTERN_SIMPLE_DATE + " HH:mm");
		Long time =d.getTime()+15 * 60 * 1000;
		System.out.println(new Date(time));
	}
}
