package com.umurcan.sesame.serviceimpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.umurcan.sesame.domain.Appointment;
import com.umurcan.sesame.domain.Doctor;
import com.umurcan.sesame.domain.Location;
import com.umurcan.sesame.service.AppointmentToDoctorRestructureService;

import lombok.val;

@Service
public class AppointmentToDoctorRestructureServiceImpl implements AppointmentToDoctorRestructureService {

	@Override
	public Set<Doctor> restructureAppointments(List<Appointment> appointments) {
		
		// Doctor to Location to Appointment map. In the structure that is requested
		val result = new HashMap<Doctor, Map<Location, Set<Appointment>>>();
		
		appointments.forEach(a -> {
			val doctor = a.getDoctor();
			val location = a.getLocation();
			
			
			result.putIfAbsent(doctor, new HashMap<>());
			result.get(doctor).putIfAbsent(location, new HashSet<>());
			result.get(doctor).get(location).add(a);
			
			// clear relations from appointment to location and doctor 
			// because information flow is reversed (as doctor -> location -> appointment)
			a.setDoctor(null);
			a.setLocation(null);
		});

		assignLocationsToDoctors(result);
		assignAppointmentsToLocations(result);
		
		return result.keySet();
	}
	
	private void assignLocationsToDoctors(HashMap<Doctor, Map<Location, Set<Appointment>>> doctorToLocationMap) {
		doctorToLocationMap.entrySet()
			.forEach(e -> {
				val doctor = e.getKey();
				val locationSet = e.getValue().keySet();
				
				doctor.setAppointmentsByLocation(locationSet);
			});
	}
	
	private void assignAppointmentsToLocations(HashMap<Doctor, Map<Location, Set<Appointment>>> doctorToLocationMap) {
		doctorToLocationMap.entrySet().stream().flatMap(e -> e.getValue().entrySet().stream()) // stream of <Location, Set<Appointment>> entries
		.forEach(e -> {
			val location = e.getKey();
			val appointmentSet = e.getValue();
			
			location.setAppointments(appointmentSet);
		});
	}
}
