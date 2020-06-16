package com.example.iwm.controller;

import com.example.iwm.mapper.IMedicationRequestMapper;
import com.example.iwm.model.MedicationRequestDTO;
import com.example.iwm.model.PatientDTO;
import com.example.iwm.model.VersionDTO;
import com.example.iwm.server.MedicationRequestClient;
import com.google.gson.Gson;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MedicationRequestController {

    private final MedicationRequestClient medicationRequestClient;
    private final IMedicationRequestMapper medicationRequestMapper;
    private final Gson gson = new Gson();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public MedicationRequestController(MedicationRequestClient client, IMedicationRequestMapper medicationRequestMapper) {
        this.medicationRequestClient = client;
        this.medicationRequestMapper = medicationRequestMapper;
    }

    @GetMapping(value = "/medication/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMedicationById(@PathVariable("id") String id, @RequestParam(value = "version", required = false) String version) {
        MedicationRequest p = (version == null || version.equals("")) ? medicationRequestClient.getResourceById(id) : medicationRequestClient.getResourceByIdAndVersion(id, version);
        MedicationRequestDTO dto = medicationRequestMapper.fromMedicationRequest(p);
        return gson.toJson(dto);
    }

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

    @PostMapping(value = "/medication", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewWeightObservation(@RequestBody MedicationRequestDTO medicationRequestDTO) {
        MedicationRequestDTO result = medicationRequestClient.updateMedicationRequest(medicationRequestDTO);
        System.out.println(result.getId() +  " " + result.getVersion());
        return gson.toJson(result);
    }

    @GetMapping(value = "/medication/versions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getVersions(@PathVariable("id") String patient_id) {
        MedicationRequest resource = medicationRequestClient.getResourceById(patient_id);
        int versions = Integer.valueOf(resource.getIdElement().getVersionIdPart());
        List<VersionDTO> v = new ArrayList<>();
        for (int i = 1; i <= versions; i++) {
            MedicationRequest tmp = medicationRequestClient.getResourceByIdAndVersion(patient_id, String.valueOf(i));
            v.add(new VersionDTO(simpleDateFormat.format(tmp.getAuthoredOn()), tmp.getIdElement().getVersionIdPart(), tmp.getIdElement().getIdPart()));
        }
        return gson.toJson(v);
    }
}
