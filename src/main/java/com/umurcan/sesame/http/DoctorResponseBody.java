package com.umurcan.sesame.http;

import java.util.List;
import java.util.Set;

import com.umurcan.sesame.domain.Doctor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorResponseBody {
	private Set<Doctor> doctors;
	private List<String> errors;
}
