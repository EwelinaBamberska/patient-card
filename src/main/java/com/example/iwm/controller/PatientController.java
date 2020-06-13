package com.example.iwm.controller;

import com.example.iwm.mapper.IPatientMapper;
import com.example.iwm.model.PatientDTO;
import com.example.iwm.server.PatientClient;
import com.google.gson.Gson;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PatientController {
    private final PatientClient patientClient;
    private final IPatientMapper patientMapper;
    private final Gson gson = new Gson();

    @Autowired
    public PatientController(PatientClient patientClient, IPatientMapper mapper) {
        this.patientClient = patientClient;
        this.patientMapper = mapper;
    }

    @GetMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPatientById(@PathVariable("id") String id, @RequestParam(value = "version", required = false) String version) {
        Patient p = (version == null || version == "") ? patientClient.getResourceById(id) : patientClient.getResourceByIdAndVersion(id, version);
        PatientDTO patientDTO = patientMapper.fromPatient(p);
        return gson.toJson(patientDTO);
    }

    @GetMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPatients(@RequestParam(value = "name", required = false) String name) {
        name = name == null ? "" : name;
        List<IBaseResource> resources = patientClient.searchByName(name);
        return gson.toJson(mapToPatientsDTO(resources));
    }

    private List<PatientDTO> mapToPatientsDTO(List<IBaseResource> resources) {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (IBaseResource resource:
             resources) {
            patientDTOS.add(patientMapper.fromPatient((Patient) resource));
        }
        return patientDTOS;
    }

}
