package com.example.iwm.server;

import ca.uhn.fhir.rest.gclient.IQuery;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObservationClient {

    private final IClient client;
    private final CustomServer server = new CustomServer();

    public ObservationClient(){
        client = new Client<Observation>(Observation.class);
    }

    public Observation getResourceById(String id) {
        return (Observation) client.getResourceById(id);
    }

    public Observation getResourceByIdAndVersion(String id, String version) {
        return (Observation) client.getResourceByIdAndVersion(id, version);
    }

    public List<IBaseResource> getObservationsByCodeAndTimeRange(String type, String patientId, String date_from, String date_to) {
        IQuery<IBaseBundle> query = server.getClient()
                .search()
                .forResource(Observation.class)
                .where(Observation.PATIENT.hasId(patientId))
                .where(Observation.CODE.exactly().code(type));
        query = client.addTimeRangeToQuery(date_from, date_to, query);

        Bundle results = query
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();

        return client.readObjectsFromBundle(results);
    }
}
