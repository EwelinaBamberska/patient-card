package com.example.iwm.server;

import ca.uhn.fhir.rest.gclient.IQuery;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicationRequestClient {

    private final IClient client;
    private final CustomServer server = new CustomServer();

    public MedicationRequestClient(){
        client = new Client<MedicationRequest>(MedicationRequest.class);
    }

    public MedicationRequest getResourceById(String id) {
        return (MedicationRequest) client.getResourceById(id);
    }

    public MedicationRequest getResourceByIdAndVersion(String id, String version) {
        return (MedicationRequest) client.getResourceByIdAndVersion(id, version);
    }

    public List<IBaseResource> getMedicamentsByTimeRange(String patient_id, String date_from, String date_to) {
        IQuery<IBaseBundle> query = server.getClient()
                .search()
                .forResource(MedicationRequest.class)
                .where(MedicationRequest.SUBJECT.hasId(patient_id));
        query = client.addTimeRangeToQuery(date_from, date_to, query);
        Bundle results = query
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();

        return client.readObjectsFromBundle(results);
    }

}
