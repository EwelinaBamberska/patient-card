package com.example.iwm.mapper;

import com.example.iwm.model.ObservationDTO;
import org.hl7.fhir.r4.model.Observation;

public interface IObservationMapper {
    ObservationDTO fromObservation(Observation observation);
}
