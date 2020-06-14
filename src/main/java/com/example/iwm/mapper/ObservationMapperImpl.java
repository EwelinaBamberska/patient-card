package com.example.iwm.mapper;

import com.example.iwm.model.ObservationDTO;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ObservationMapperImpl implements IObservationMapper {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ObservationDTO fromObservation(Observation observation) {
        ObservationDTO observationDTO = new ObservationDTO();
        observationDTO.setId(observation.getIdElement().getIdPart());
        observationDTO.setVersion(observation.getIdElement().getVersionIdPart());
        observationDTO.setCode(observation.getCode().getCodingFirstRep().getCode());
        observationDTO.setDate(simpleDateFormat.format(observation.getIssued()));
        observationDTO.setPatientId(observation.getSubject().getReference());
        observationDTO.setValue(observation.getValueQuantity().getValue().toString());
        return observationDTO;
    }
}
