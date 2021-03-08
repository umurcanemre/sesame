package com.umurcan.sesame.mockdata.json;

import java.util.StringJoiner;
import java.util.stream.IntStream;

import lombok.val;

public class MockJsonUtil {
	
	public static String getAppointments(int valid, int invalid) {
		val sj = new StringJoiner(",", "[", "]");
		
		IntStream.range(0, valid).forEach(i-> sj.add(getAppointment()));
		IntStream.range(0, invalid).forEach(i-> sj.add(getInvalidAppointment()));
		
		return sj.toString();
	}
	
	public static String getAppointment() {
		return "{\"doctor\":{\"firstName\":\"Madisyn\",\"lastName\":\"Strosin\"},"
				+ "\"durationInMinutes\":8,"
				+ "\"time\":\"2021-03-08 11:43:00\","
				+ "\"service\":{\"name\":\"Counselling\",\"price\":6465},"
				+ "\"location\":{\"name\":\"New York City Clinic\",\"timeZoneCode\":\"America/New_York\"},"
				+ "\"id\":\"bb1bd522-4f8f-4155-ad07-734b97d1d628\"}";
	}
	
	public static String getInvalidAppointment() {
		return "{"+getInvalidDoctor()+","
				+ "\"durationInMinutes\":-3,"
				+ "\"time\":\"2021-03-08 11:43:00\","
				+ getInvalidService() +","
				+ getInvalidLocation() +","
				+ "\"id\":\"\"}";
	}

	public static String getInvalidDoctor() {
		return "\"doctor\":{\"firstName\":\"\"}";
	}
	public static String getInvalidService() {
		return "\"service\":{\"name\":\"\",\"price\":-1}";
	}
	public static String getInvalidLocation() {
		return "\"location\":{\"name\":\"\",\"timeZoneCode\":\"\"}";
	}
}
