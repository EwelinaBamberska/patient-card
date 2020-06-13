package com.example.iwm.server;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientClient {
    private final IClient client;
    private final CustomServer server = new CustomServer();

    public PatientClient(){
        client = new Client<Patient>(Patient.class);
    }

    public Patient getResourceById(String id) {
        return (Patient) client.getResourceById(id);
    }

    public List<IBaseResource> searchByName(String name) {
        Bundle results = server.getClient()
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value(name))
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();
        return client.readObjectsFromBundle(results);
    }

    public Patient getResourceByIdAndVersion(String id, String version) {
        return (Patient) client.getResourceByIdAndVersion(id, version);
    }
}
