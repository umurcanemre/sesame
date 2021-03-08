package com.umurcan.sesame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umurcan.sesame.http.DoctorResponseBody;
import com.umurcan.sesame.service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
	
	@Autowired
	private DoctorService service;

	@GetMapping
	public DoctorResponseBody getDoctors() {
		return service.getDoctors();
	}
}
