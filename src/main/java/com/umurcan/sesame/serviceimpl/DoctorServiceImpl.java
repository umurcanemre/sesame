package com.umurcan.sesame.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umurcan.sesame.client.AppointmentServiceClient;
import com.umurcan.sesame.http.DoctorResponseBody;
import com.umurcan.sesame.service.DoctorService;

import lombok.val;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	private AppointmentServiceClient appointmentServiceClient;
	@Autowired 
	private AppointmentToDoctorRestructureServiceImpl restructureService;
	
	
	@Override
	public DoctorResponseBody getDoctors() {
		val appointmentResponse = appointmentServiceClient.getAppointments();
		
		return DoctorResponseBody.builder()
				.doctors(restructureService.restructureAppointments(appointmentResponse.getAppointments()))
				.errors(appointmentResponse.getErrors())
				.build();
	}

}
