package com.umurcan.sesame.client;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.umurcan.sesame.ApplicationCongfiguration;
import com.umurcan.sesame.domain.Appointment;
import com.umurcan.sesame.util.AppointmentClientResultAggragetor;

import lombok.Setter;
import lombok.val;

@Setter
@Service
public class AppointmentServiceClient {
	private final String appointmentServiceURL;
	@Autowired
	private Validator validator;
	@Autowired
	private RestTemplate restTemplate;

	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
			ApplicationCongfiguration.class);

	public AppointmentServiceClient(@Value("${application.config.api.appointment.url}") String appointmentServiceURL) {
		super();
		this.appointmentServiceURL = appointmentServiceURL;
	}

	public AppointmentClientResultAggragetor getAppointments() {
		val responseStr = restTemplate.getForEntity(appointmentServiceURL, String.class).getBody();

		val strs = split(responseStr);

		return mapToAppointment(strs);
	}

	private List<String> split(final String jsonArray) {
		JsonNode jsonNode;
		try {
			jsonNode = new ObjectMapper().readTree(jsonArray);
		} catch (JsonProcessingException e) {
			throw new JsonParseException(e);
		}
		return StreamSupport.stream(jsonNode.spliterator(), false).map(JsonNode::toString).collect(Collectors.toList());
	}

	private AppointmentClientResultAggragetor mapToAppointment(List<String> appointmentJsons) {
		val appointmentReader = context.getBean(ObjectReader.class);
		val result = new AppointmentClientResultAggragetor();

		appointmentJsons.forEach(json -> {
			try {
				val appointment = appointmentReader.readValue(json, Appointment.class);
				val violations = validator.validate(appointment);

				if (violations.isEmpty()) {
					result.getAppointments().add(appointment);
				} else {
					violations.forEach(v -> result.getErrors().add(getValidationErrorText(v) + System.lineSeparator() + json));
				}
			} catch (Exception e) {
				result.getErrors().add(e.getMessage() + System.lineSeparator() + json);
			}
		});

		return result;
	}

	private String getValidationErrorText(ConstraintViolation<Appointment> violation) {
		return violation.getPropertyPath() + " " + violation.getMessage() + " of " + violation.getRootBeanClass();
	}
}
