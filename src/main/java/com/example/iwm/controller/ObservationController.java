package com.example.iwm.controller;

import com.example.iwm.mapper.IObservationMapper;
import com.example.iwm.model.ObservationDTO;
import com.example.iwm.server.ObservationClient;
import com.google.gson.Gson;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ObservationController {

    private final ObservationClient observationClient;
    private final IObservationMapper observationMapper;
    private final Gson gson = new Gson();

    @Autowired
    public ObservationController(ObservationClient observation, IObservationMapper observationMapper) {
        this.observationClient = observation;
        this.observationMapper = observationMapper;
    }

    @GetMapping(value = "/observation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getObservationById(@PathVariable("id") String id, @RequestParam(value = "version", required = false) String version) {
        Observation observation = (version == null || version == "") ? observationClient.getResourceById(id) : observationClient.getResourceByIdAndVersion(id, version);
        ObservationDTO observationDTO = observationMapper.fromObservation(observation);
        return gson.toJson(observationDTO);
    }

    @GetMapping(value = "/observation/{id}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getObservationsByTimeRange(@RequestParam(value = "from", required = false) String date_from,
                                             @RequestParam(value = "to", required = false) String date_to,
                                             @PathVariable("type") String type,
                                             @PathVariable("id") String patient_id) {
        date_from = date_from == null ? "" : date_from;
        date_to = date_to == null ? "" : date_to;
        type = type.equals("weight") ? "29463-7" : "";
        List<IBaseResource> resources = resources = observationClient.getObservationsByCodeAndTimeRange(type, patient_id, date_from, date_to);
        return gson.toJson(mapToObservationDTO(resources));
    }

    private List<ObservationDTO> mapToObservationDTO(List<IBaseResource> resources) {
        List<ObservationDTO> observationDTOS = new ArrayList<>();
        for (IBaseResource resource :
                resources) {
            observationDTOS.add(observationMapper.fromObservation((Observation) resource));
        }
        return observationDTOS;
    }

}
