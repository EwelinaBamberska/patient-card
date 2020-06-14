package com.example.iwm.mapper;

import com.example.iwm.model.MedicationRequestDTO;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class MedicationRequestMapperImpl implements IMedicationRequestMapper {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public MedicationRequestDTO fromMedicationRequest(MedicationRequest medicationRequest) {
        MedicationRequestDTO dto = new MedicationRequestDTO();
        dto.setId(medicationRequest.getIdElement().getIdPart());
        dto.setVersion(medicationRequest.getIdElement().getVersionIdPart());
        dto.setStatus(medicationRequest.getStatus() == null ? null : medicationRequest.getStatus().getDisplay());
        dto.setReason(medicationRequest.getStatus() == null ? null : medicationRequest.getStatus().getDisplay());
        dto.setIntent(medicationRequest.getIntent() == null ? null : medicationRequest.getIntent().getDisplay());
        dto.setCategory(medicationRequest.getCategoryFirstRep() == null ? null : medicationRequest.getCategoryFirstRep().getText());
        dto.setPriority(medicationRequest.getPriority() == null ? null : medicationRequest.getPriority().getDisplay());
        dto.setPatientId(medicationRequest.getReportedReference() == null ? null : medicationRequest.getReportedReference().getId());
        dto.setCreationDate(simpleDateFormat.format(medicationRequest.getAuthoredOn()));
        dto.setNote(medicationRequest.getNoteFirstRep() == null ? null : medicationRequest.getNoteFirstRep().getText());
        dto.setDosageInstruction(medicationRequest.getDosageInstructionFirstRep() == null ? null : medicationRequest.getDosageInstructionFirstRep().getText());

        MedicationRequest.MedicationRequestDispenseRequestComponent dispenseRequestComponent = medicationRequest.getDispenseRequest();
        if(dispenseRequestComponent != null) {
            MedicationRequest.MedicationRequestDispenseRequestInitialFillComponent initialFill = dispenseRequestComponent.getInitialFill();
            if(initialFill != null) {
                dto.setFirstFillQuantity(initialFill.getQuantity() == null ? null :
                        (initialFill.getQuantity().getValue() == null ? null : initialFill.getQuantity().getValue().toString()));
                dto.setFirstFillDuration(initialFill.getDuration() == null ? null :
                        (initialFill.getDuration().getValue() == null ? null : initialFill.getDuration().getValue().toString()));
            }
            dto.setDispenseInterval(dispenseRequestComponent.getDispenseInterval() == null ? null :
                    (dispenseRequestComponent.getDispenseInterval().getValue() == null ? null : dispenseRequestComponent.getDispenseInterval().getValue().toString()));
            dto.setValidityPeriodStartDate(dispenseRequestComponent.getValidityPeriod() == null ? null :
                    (dispenseRequestComponent.getValidityPeriod().getStart() == null ? null :
                    simpleDateFormat.format(dispenseRequestComponent.getValidityPeriod().getStart())));
            dto.setValidityPeriodEndDate(dispenseRequestComponent.getValidityPeriod() == null ? null :
                    (dispenseRequestComponent.getValidityPeriod().getEnd() == null ? null :
                            simpleDateFormat.format(dispenseRequestComponent.getValidityPeriod().getEnd())));
            dto.setNumberOfRepeatsAllowed(String.valueOf(dispenseRequestComponent.getNumberOfRepeatsAllowed()));
            dto.setQuantity(dispenseRequestComponent.getQuantity() == null ? null :
                    (dispenseRequestComponent.getQuantity().getValue() == null ? null : dispenseRequestComponent.getQuantity().getValue().toString()));
            dto.setExpectedSupplyDuration(dispenseRequestComponent.getExpectedSupplyDuration() == null ? null :
                    dispenseRequestComponent.getExpectedSupplyDuration().getValue() == null ? null : dispenseRequestComponent.getExpectedSupplyDuration().getValue().toString());
        }
        dto.setSubstitutionAllowed(medicationRequest.getSubstitution() == null ? null :
                (medicationRequest.getSubstitution().getAllowed() == null ? null : medicationRequest.getSubstitution().getAllowed().toString()));
        dto.setSubstitutionReason(medicationRequest.getSubstitution() == null ? null :
                (medicationRequest.getSubstitution().getReason() == null ? null : medicationRequest.getSubstitution().getReason().getText()));
        return dto;
    }
}
