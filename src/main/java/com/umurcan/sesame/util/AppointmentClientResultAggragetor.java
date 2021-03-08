package com.umurcan.sesame.util;

import java.util.ArrayList;
import java.util.List;

import com.umurcan.sesame.domain.Appointment;

import lombok.Data;

@Data
public class AppointmentClientResultAggragetor {
	private List<Appointment> appointments = new ArrayList<>();
	private List<String> errors = new ArrayList<>();
}
