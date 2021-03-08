package com.umurcan.sesame.domain;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@Data
public class Location {
	@NotBlank
	private String name;
	@NotBlank
	private String timeZoneCode;
	@EqualsAndHashCode.Exclude
	private Set<Appointment> appointments;
}
