package com.umurcan.sesame.mockdata;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.umurcan.sesame.domain.Appointment;
import com.umurcan.sesame.domain.Doctor;
import com.umurcan.sesame.domain.Location;
import com.umurcan.sesame.domain.Service;

import lombok.val;

public class MockPojoUtil {

	public static Appointment getReceivedAppointment() {
		val appointment = new Appointment();

		appointment.setDoctor(getReceivedDoctor());
		appointment.setLocation(getReceivedLocation());
		appointment.setService(getReceivedService());
		appointment.setTime(Date.from(LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant()));
		appointment.setDurationInMinutes(10);
		appointment.setId("anuuididstringwhichisveryverylong");

		return appointment;
	}

	public static Appointment getReceivedInvalidAppointment() {
		val appointment = new Appointment();

		appointment.setDurationInMinutes(-10);
		appointment.setId("");

		return appointment;
	}

	public static Doctor getReceivedDoctor() {
		val doctor = new Doctor();

		doctor.setFirstName("mockDoctorFirstName");
		doctor.setLastName("mockDoctorLastName");

		return doctor;
	}

	public static Location getReceivedLocation() {
		val location = new Location();

		location.setName("mockLocationName");
		location.setTimeZoneCode("mockLocationTimeZone");

		return location;
	}

	public static Service getReceivedService() {
		val service = new Service();

		service.setName("mockServiceName");
		service.setPrice(BigDecimal.ONE);

		return service;
	}
}
