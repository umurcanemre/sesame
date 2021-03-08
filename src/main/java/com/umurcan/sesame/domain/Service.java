package com.umurcan.sesame.domain;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * - The constraints described on POJO's under com.umurcan.sesame.domain package, apply **ONLY** for appointment first structure and are used to validate the structure of the response of appointment system. Since the creation of the doctor first data is the responsibility of this service, that data is validated by unit tests.
 * 
 * @author umurcan
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class Service {
	@NotBlank
	private String name;
	@DecimalMin(value = "0.0", inclusive = true)
	@NotNull
	private BigDecimal price;
}
