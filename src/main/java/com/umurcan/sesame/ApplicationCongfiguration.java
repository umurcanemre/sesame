package com.umurcan.sesame;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.umurcan.sesame.domain.Appointment;

import lombok.val;

@Configuration
public class ApplicationCongfiguration {

	@Bean(name = "AppointmentReader")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ObjectReader getAppointmenObjectReader() {
		val objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);

		return objectMapper.readerFor(Appointment.class);
	}

	@Bean
	public Validator getValidator() {
		val factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		var factory = new SimpleClientHttpRequestFactory();
	    factory.setConnectTimeout(3000);
	    factory.setReadTimeout(3000);
	    return new RestTemplate(factory);
	}
}
