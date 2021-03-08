package com.umurcan.sesame.domain;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class Service {
	@NotBlank
	private String name;
	@DecimalMin(value = "0.0", inclusive = true)
	@NotNull
	private BigDecimal price;
}
