package com.example.iwm.server;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.gclient.IQuery;
import com.example.iwm.mapper.IMedicationRequestMapper;
import com.example.iwm.model.DosageInstructionDTO;
import com.example.iwm.model.MedicationRequestDTO;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicationRequestClient {

    private final IClient client;
    private final CustomServer server = new CustomServer();
    private final IMedicationRequestMapper mapper;

    @Autowired
    public MedicationRequestClient(IMedicationRequestMapper mapper){
        this.mapper = mapper;
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

        if(!date_from.equals("") && !date_to.equals("")) {
            query.where(MedicationRequest.AUTHOREDON.afterOrEquals().day(date_from));
            query.and(MedicationRequest.AUTHOREDON.beforeOrEquals().day(date_to));
        } else if(!date_from.equals("")) {
            query.where(MedicationRequest.AUTHOREDON.afterOrEquals().day(date_from));
        } else if(!date_to.equals("")) {
            query.where(MedicationRequest.AUTHOREDON.beforeOrEquals().day(date_to));
        }
        Bundle results = query
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();

        return client.readObjectsFromBundle(results);
    }

    public MedicationRequestDTO updateMedicationRequest(MedicationRequestDTO dto) {
        MedicationRequest medicationRequest = getResourceById(dto.getId());

        if (dto.getDosageInstruction() != null) {
            DosageInstructionDTO dosageDto = dto.getDosageInstruction().get(0);
            if(dosageDto.getSequence() != null) {
                medicationRequest.getDosageInstruction().get(0).setSequence(Integer.valueOf(dosageDto.getSequence()));
            }
            if(dosageDto.getFrequency() != null) {
                medicationRequest.getDosageInstruction().get(0).getTiming().getRepeat().setFrequency(Integer.valueOf(dosageDto.getFrequency()));
            }
            if(dosageDto.getPeriod() != null) {
                medicationRequest.getDosageInstruction().get(0).getTiming().getRepeat().setPeriod(Integer.valueOf(dosageDto.getPeriod()));
            }
        }

        MethodOutcome outcome = server.getClient().update()
                .resource(medicationRequest)
                .execute();

        MedicationRequestDTO result = new MedicationRequestDTO();
        result.setId(outcome.getId().getIdPart());
        result.setVersion(outcome.getId().getVersionIdPart());
        return result;
    }

}
