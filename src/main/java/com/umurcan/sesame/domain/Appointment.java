package com.umurcan.sesame.domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(Include.NON_NULL)
@Data
public class Appointment{
	@EqualsAndHashCode.Include
	@NotBlank
	private String id;
	@Valid
	@NotNull
	private Doctor doctor;
	@NotNull
	@Positive
	private int durationInMinutes;
	// @Future fails many appointments as appointment can be valid but "in past" for servers timezone
	// a custom validator can be implemented for the business requirements
	@NotNull                   						      //2021-03-06 03:48:00
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;
	@Valid
	@NotNull
	private Service service;
	@Valid
	@NotNull
	private Location location;
}