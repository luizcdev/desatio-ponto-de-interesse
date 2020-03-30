package com.digitalmaps.desafio.core.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static final String PATTERN_HOUR_MINUTE = "HH:mm";
	public static final String TIME_ZONE_SAO_PAULO = "America/Sao_Paulo";
	
	public static Time stringToTime(String Pattern, String time) throws Exception {
		
		SimpleDateFormat formater = new SimpleDateFormat(Pattern);
		formater.setLenient(false);
		
		Date date = formater.parse(time);
		
		return new Time(date.getTime());
		
	}
	
	public static boolean isBetweenTwoTimes(Time time, Time startTime, Time endTime) {
		 return ( time.compareTo(startTime) >= 0 ) && ( time.compareTo(endTime) <= 0 );
	}
}
