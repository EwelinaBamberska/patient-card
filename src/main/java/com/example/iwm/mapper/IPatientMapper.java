package com.example.iwm.mapper;

import com.example.iwm.model.PatientDTO;
import org.hl7.fhir.r4.model.Patient;

public interface IPatientMapper {

    PatientDTO fromPatient(Patient patient);
}
