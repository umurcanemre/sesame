package com.umurcan.sesame.domain;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * - The constraints described on POJO's under com.umurcan.sesame.domain package, apply **ONLY** for appointment first structure and are used to validate the structure of the response of appointment system. Since the creation of the doctor first data is the responsibility of this service, that data is validated by unit tests.
 * 
 * @author umurcan
 *
 */
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
