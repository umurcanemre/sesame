package com.umurcan.sesame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import com.umurcan.sesame.mockdata.MockPojoUtil;
import com.umurcan.sesame.serviceimpl.AppointmentToDoctorRestructureServiceImpl;

import lombok.val;

class AppointmentToDoctorRestructureServiceTests {
	private AppointmentToDoctorRestructureServiceImpl service;
	
	@BeforeEach
	void init() {
		service = new AppointmentToDoctorRestructureServiceImpl();
	}
	
	@Test
	void restructureAppointmentsTest_Single() {
		val appointment = MockPojoUtil.getReceivedAppointment();
		val appointmentDoctor = appointment.getDoctor();
		val appointmentLocation = appointment.getLocation();
		
		val result = service.restructureAppointments(List.of(appointment));

		assertNotNull(result);
		assertEquals(1, result.size());
		assertNull(appointment.getDoctor());
		assertNull(appointment.getLocation());

		result.stream().forEach(doctor -> {
			assertEquals(appointmentDoctor, doctor);
			assertFalse(CollectionUtils.isEmpty(doctor.getAppointmentsByLocation()));
			assertEquals(1, doctor.getAppointmentsByLocation().size());
			
			doctor.getAppointmentsByLocation().stream().forEach(location -> {
				assertEquals(appointmentLocation, location);
				assertFalse(CollectionUtils.isEmpty(location.getAppointments()));
				assertEquals(1, location.getAppointments().size());
				assertTrue(location.getAppointments().stream().allMatch(a -> appointment.equals(a)));
			});
		});
	}
}
