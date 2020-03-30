package com.digitalmaps.desafio.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Time;

import org.junit.Test;

import com.digitalmaps.desafio.core.util.TimeUtil;


public class TimeUtilTest {
	
	
	@Test
	public void TestStringToTime() throws Exception {
				
		Time expected = Time.valueOf("08:00:00");
		assertEquals(expected, TimeUtil.stringToTime(TimeUtil.PATTERN_HOUR_MINUTE, "08:00"));
	}
		
	@Test(expected = Exception.class)
	public void TestStringToTimeException() throws Exception{
		
		TimeUtil.stringToTime(TimeUtil.PATTERN_HOUR_MINUTE, "25:00");
		
	}
	
	@Test
	public void TestisBetweenTwoTimesTrue() throws Exception{
		
		Time time = Time.valueOf("10:00:00");
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("18:00:00");
		
		assertTrue(TimeUtil.isBetweenTwoTimes(time, startTime, endTime));
		
	}
	
	@Test
	public void TestisBetweenTwoTimesFalse() throws Exception{
		
		Time time = Time.valueOf("20:00:00");
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("18:00:00");
		
		assertFalse(TimeUtil.isBetweenTwoTimes(time, startTime, endTime));
		
	}
	
}

