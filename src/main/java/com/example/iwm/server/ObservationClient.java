package com.example.iwm.server;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.gclient.IQuery;
import com.example.iwm.model.ObservationDTO;
import com.example.iwm.model.ObservationType;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ObservationClient {

    private final IClient client;
    private final CustomServer server = new CustomServer();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

        if(!date_from.equals("") && !date_to.equals("")) {
            query.where(Observation.DATE.afterOrEquals().day(date_from));
            query.and(Observation.DATE.beforeOrEquals().day(date_to));
        } else if(!date_from.equals("")) {
            query.where(Observation.DATE.afterOrEquals().day(date_from));
        } else if(!date_to.equals("")) {
            query.where(Observation.DATE.beforeOrEquals().day(date_to));
        }

        Bundle results = query
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();

        return client.readObjectsFromBundle(results);
    }

    public ObservationDTO createNewObservation(ObservationDTO observationDTO) {
        Observation observation = new Observation();
        observation.addIdentifier().setSystem("urn:system").setValue("12345");
        observation.getCode()
                .addCoding()
                    .setCode(ObservationType.WEIGHT.getCode())
                    .setDisplay("Body weight")
                    .setSystem("http://localhost:8081");
        observation.setValue(new Quantity()
                .setValue(new BigDecimal(observationDTO.getValue()))
                .setUnit("kg")
                .setSystem("http://unitsofmeasure.org")
                .setCode("kg"));
        observation.setSubject(new Reference("Patient/" + observationDTO.getPatientId()));

        try {
            observation.setIssued(simpleDateFormat.parse(observationDTO.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MethodOutcome outcome = server.getClient().create()
                .resource(observation)
                .prettyPrint()
                .encodedJson()
                .execute();

        IIdType id = outcome.getId();
        ObservationDTO dto = new ObservationDTO();
        dto.setId(id.getIdPart());
        dto.setVersion(id.getVersionIdPart());
        return dto;
    }
}
