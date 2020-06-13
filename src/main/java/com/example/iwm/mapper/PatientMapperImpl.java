package com.example.iwm.mapper;

import com.example.iwm.model.PatientDTO;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class PatientMapperImpl implements IPatientMapper{

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public PatientDTO fromPatient(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getIdElement().getIdPart());
        dto.setVersion(patient.getIdElement().getVersionIdPart());
        dto.setName(patient.getNameFirstRep().getNameAsSingleString());
        dto.setBirthDate(simpleDateFormat.format(patient.getBirthDate()));
        dto.setGender(patient.getGender().getDefinition());
        return dto;
    }
}
