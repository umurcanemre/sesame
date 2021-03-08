package com.umurcan.sesame.client;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectReader;
import com.umurcan.sesame.domain.Appointment;
import com.umurcan.sesame.mockdata.MockPojoUtil;
import com.umurcan.sesame.mockdata.json.MockJsonUtil;

import lombok.val;

class AppointmentServiceClientTests {
	private AppointmentServiceClient service;
	private final String appointmentServiceURL = "URL";
	private Validator validator;
	private RestTemplate restTemplate;
	private AnnotationConfigApplicationContext context;
	private ObjectReader objectReader;
	
	@BeforeEach
	void init() {
		validator = mock(Validator.class);
		restTemplate = mock(RestTemplate.class);
		context = mock(AnnotationConfigApplicationContext.class);
		objectReader = mock(ObjectReader.class);
		
		when(context.getBean(ObjectReader.class)).thenReturn(objectReader);
		
		service = new AppointmentServiceClient(appointmentServiceURL);
		service.setContext(context);
		service.setRestTemplate(restTemplate);
		service.setValidator(validator);
	}
	
	@Test
	void getAppointmentsTest() throws IOException {
		int appointmentCount = 5;
		
		when(restTemplate.getForEntity(appointmentServiceURL, String.class))
				.thenReturn(new ResponseEntity<String>(MockJsonUtil.getAppointments(appointmentCount, 0),null,HttpStatus.OK));
		
		when(objectReader.readValue(MockJsonUtil.getAppointment(), Appointment.class))
				.thenReturn(MockPojoUtil.getReceivedAppointment());
		
		val result = service.getAppointments();
		
		assertNotNull(result);
		assertNotNull(result.getAppointments());
		assertNotNull(result.getErrors());
		assertTrue(result.getErrors().isEmpty());
		assertEquals(appointmentCount, result.getAppointments().size());
	}
	
	@Test
	void getAppointmentsTest_fail_malformedJsonCollection() throws IOException {
		int appointmentCount = 5;
		var jsonResponse = MockJsonUtil.getAppointments(appointmentCount, 0);
		jsonResponse = jsonResponse.substring(3); // break the form of json file by removig [{" characters in the beginning
		
		
		when(restTemplate.getForEntity(appointmentServiceURL, String.class))
				.thenReturn(new ResponseEntity<String>(jsonResponse,null,HttpStatus.OK));
		

		assertThatExceptionOfType(JsonParseException.class)
			.isThrownBy(() -> { service.getAppointments(); });
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void getAppointmentsTest_successWithErrors() throws IOException {
		int appointmentCount = 5;
		int invalidAppointmentCount = 5;
		val jsonResponse = MockJsonUtil.getAppointments(appointmentCount, invalidAppointmentCount);
		
		when(restTemplate.getForEntity(appointmentServiceURL, String.class))
				.thenReturn(new ResponseEntity<String>(jsonResponse,null,HttpStatus.OK));

		val validAppointmentBean = MockPojoUtil.getReceivedAppointment();
		val invalidAppointmentBean = MockPojoUtil.getReceivedInvalidAppointment();
		val constraintViolation = (ConstraintViolation<Appointment>) Mockito.mock(ConstraintViolation.class);
		
		when(constraintViolation.getPropertyPath()).thenReturn(PathImpl.createPathFromString("object"));
		when(constraintViolation.getMessage()).thenReturn("violationMsg");
		when(constraintViolation.getRootBeanClass()).thenReturn(Appointment.class);

		when(objectReader.readValue(MockJsonUtil.getAppointment(), Appointment.class))
				.thenReturn(validAppointmentBean);
		when(objectReader.readValue(MockJsonUtil.getInvalidAppointment(), Appointment.class))
				.thenReturn(invalidAppointmentBean);
		when(validator.validate(validAppointmentBean)).thenReturn(Collections.emptySet());
		when(validator.validate(invalidAppointmentBean)).thenReturn(Set.of(constraintViolation));
		
		val result = service.getAppointments();
		
		assertNotNull(result);
		assertNotNull(result.getAppointments());
		assertNotNull(result.getErrors());
		assertEquals(invalidAppointmentCount, result.getErrors().size());
		assertEquals(appointmentCount, result.getAppointments().size());
		assertTrue(result.getErrors().stream().allMatch(s -> s.startsWith("object violationMsg of class com.umurcan.sesame.domain.Appointment") ));
	}
}
