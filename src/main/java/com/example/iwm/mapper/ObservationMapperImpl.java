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
        observationDTO.setId(observation.hasIdElement() ? observation.getIdElement().getIdPart() : null);
        observationDTO.setVersion(observation.hasIdElement() ? observation.getIdElement().getVersionIdPart() : null);
        observationDTO.setCode(observation.hasCode() ? observation.getCode().getCodingFirstRep().getCode() : null);
        observationDTO.setDate(observation.hasIssued() ? simpleDateFormat.format(observation.getIssued()) : null);
        observationDTO.setPatientId(observation.hasSubject() ? observation.getSubject().getReference() : null);
        observationDTO.setValue(observation.hasValue() ? observation.getValueQuantity().getValue().toString() : null);
        return observationDTO;
    }
}
