package com.umurcan.sesame.domain;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@Data
public class Doctor {
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@EqualsAndHashCode.Exclude
	private Set<Location> appointmentsByLocation;
}
