package com.umurcan.sesame.service;

import java.util.List;
import java.util.Set;

import com.umurcan.sesame.domain.Appointment;
import com.umurcan.sesame.domain.Doctor;

public interface AppointmentToDoctorRestructureService {
	Set<Doctor> restructureAppointments(List<Appointment> appointments);
}
