package org.catframework.agileworking.domain;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class ScheduleTest {

	@Test
	public void testCompareTo() {
		Schedule s1 = ScheduleFactory.newSchedule("XXX1", "发哥", "2017-08-02", "13:30", "15:00");
		Schedule s2 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-03", "13:30", "15:00");
		assertTrue(s1.compareTo(s2) < 0);
		Schedule s3 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-02", "16:30", "17:00");
		assertTrue(s1.compareTo(s3) < 0);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s3);
		schedules.add(s2);
		schedules.add(s1);
		schedules = schedules.stream().sorted().collect(Collectors.toList());
		assertSame(s1, schedules.get(0));
		assertSame(s3, schedules.get(1));
		assertSame(s2, schedules.get(2));
	}

	@Test
	public void isConflict() {
		Schedule s1 = ScheduleFactory.newSchedule("XXX1", "发哥", "2017-08-02", "13:30", "15:00");
		s1.setId((long) 1);
		Schedule s2 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-03", "13:30", "15:00");
		s2.setId((long) 2);
		Assert.assertFalse(s1.isConflict(s2));
		Schedule s3 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-02", "13:30", "15:00");
		s3.setId((long) 3);
		Assert.assertTrue(s1.isConflict(s3));
		Schedule s4 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-02", "10:00", "16:00");
		s4.setId((long) 4);
		Assert.assertTrue(s1.isConflict(s4));
		Schedule s5 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-02", "10:00", "13:30");
		s5.setId((long) 5);
		Assert.assertFalse(s1.isConflict(s5));
		Schedule s6 = ScheduleFactory.newSchedule("XXX2", "发哥", "2017-08-02", "15:00", "17:00");
		s6.setId((long) 6);
		Assert.assertFalse(s1.isConflict(s6));
	}
}
