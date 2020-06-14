package com.example.iwm.controller;

import com.example.iwm.mapper.IMedicationRequestMapper;
import com.example.iwm.model.MedicationRequestDTO;
import com.example.iwm.server.MedicationRequestClient;
import com.google.gson.Gson;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MedicationRequestController {

    private final MedicationRequestClient medicationRequestClient;
    private final IMedicationRequestMapper medicationRequestMapper;
    private final Gson gson = new Gson();

    @Autowired
    public MedicationRequestController(MedicationRequestClient client, IMedicationRequestMapper medicationRequestMapper) {
        this.medicationRequestClient = client;
        this.medicationRequestMapper = medicationRequestMapper;
    }

//    @GetMapping(value = "/medicamentrequest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getObservationById(@PathVariable("id") String id, @RequestParam(value = "version", required = false) String version) {
//        MedicationRequest medicationRequest = (version == null || version == "") ? medicationRequestClient.getResourceById(id) : medicationRequestClient.getResourceByIdAndVersion(id, version);
//        MedicationRequestDTO medicationRequestDTO = medicationRequestMapper.fromMedicationRequest(medicationRequest);
//        return gson.toJson(medicationRequestDTO);
//    }

    @GetMapping(value = "/medicament/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getObservationsByTimeRange(@PathVariable("id") String patient_id,
                                             @RequestParam(value = "from", required = false) String date_from,
                                             @RequestParam(value = "to", required = false) String date_to) {
        date_from = date_from == null ? "" : date_from;
        date_to = date_to == null ? "" : date_to;
        List<IBaseResource> resources = medicationRequestClient.getMedicamentsByTimeRange(patient_id, date_from, date_to);
        return gson.toJson(mapToObservationDTO(resources));
    }

    private List<MedicationRequestDTO> mapToObservationDTO(List<IBaseResource> resources) {
        List<MedicationRequestDTO> observationDTOS = new ArrayList<>();
        for (IBaseResource resource :
                resources) {
            observationDTOS.add(medicationRequestMapper.fromMedicationRequest((MedicationRequest) resource));
        }
        return observationDTOS;
    }
}
