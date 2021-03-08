package com.umurcan.sesame.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.umurcan.sesame.mockdata.MockPojoUtil;

import lombok.val;

class AppointmentTests {
	private Validator validator;
	
	@BeforeEach
	void init() {

		val factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	//should be enriched by types of other violations, like invalid instead of null
	//also for other domain classes. skipping because it's too repetetive
	@Test
	void appointmentValidityTest_failAll() {
		val appointment = MockPojoUtil.getReceivedInvalidAppointment();
		
		val violations = validator.validate(appointment);
		
		assertNotNull(violations);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("time")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("doctor")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("location")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("service")));
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("durationInMinutes")));
	}
	@Test
	void appointmentValidityTest() {
		val appointment =MockPojoUtil.getReceivedAppointment();
		
		val violations = validator.validate(appointment);
		
		assertNotNull(violations);
		assertTrue(violations.isEmpty());
	}
}
