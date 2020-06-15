package com.example.iwm.mapper;

import com.example.iwm.model.DosageInstructionDTO;
import com.example.iwm.model.DoseAndRateDTO;
import com.example.iwm.model.MedicationRequestDTO;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Timing;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class MedicationRequestMapperImpl implements IMedicationRequestMapper {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public MedicationRequestDTO fromMedicationRequest(MedicationRequest medicationRequest) {
        MedicationRequestDTO dto = new MedicationRequestDTO();
        dto.setId(medicationRequest.getIdElement().getIdPart());
        dto.setVersion(medicationRequest.getIdElement().getVersionIdPart());
        dto.setStatus(medicationRequest.hasStatus() ? medicationRequest.getStatus().getDisplay() : null);
        dto.setCodeableConcept(medicationRequest.hasMedicationCodeableConcept() ? medicationRequest.getMedicationCodeableConcept().getText() : null);
        dto.setIntent(medicationRequest.hasIntent() ? medicationRequest.getIntent().getDisplay() : null);
        dto.setRequester(medicationRequest.hasRequester() ? medicationRequest.getRequester().getDisplay() : null);
        dto.setPatientId(medicationRequest.hasReportedReference() ? medicationRequest.getReportedReference().getId() : null);
        dto.setCreationDate(simpleDateFormat.format(medicationRequest.getAuthoredOn()));

        if(medicationRequest.hasDosageInstruction()) {
            List<Dosage> dosages = medicationRequest.getDosageInstruction();
            for (Dosage d:
                 dosages) {
                dto.getDosageInstruction().add(toDosageInstructionDTO(d));
            }
        }

        return dto;
    }

    private DosageInstructionDTO toDosageInstructionDTO(Dosage d) {
        DosageInstructionDTO dto = new DosageInstructionDTO();
        dto.setSequence(d.hasSequence() ? String.valueOf(d.getSequence()) : null);
        if(d.hasTiming() && d.getTiming().hasRepeat()) {
            Timing.TimingRepeatComponent repeat = d.getTiming().getRepeat();
            dto.setFrequency(repeat.hasFrequency() ? String.valueOf(repeat.getFrequency()) : null);
            dto.setPeriod(repeat.hasPeriod() ? String.valueOf(repeat.getPeriod()) : null);
            dto.setPeriodUnit(repeat.hasPeriodUnit() ? repeat.getPeriodUnit().getDisplay() : null);
        }
        if(d.hasDoseAndRate()) {
            List<Dosage.DosageDoseAndRateComponent> doseAndRate = d.getDoseAndRate();
            for (Dosage.DosageDoseAndRateComponent dar:
                 doseAndRate) {
                DoseAndRateDTO darDto = new DoseAndRateDTO();
                darDto.setDoseQuantity(dar.hasDoseQuantity() ? dar.getDoseQuantity().getDisplay() : null);
                if(dar.hasType() && dar.getType().hasCoding()) {
                    List<Coding> types = dar.getType().getCoding();
                    for (Coding code:
                         types) {
                        darDto.getDoseRateType().add(code.getDisplay());
                    }
                }
                dto.getDoseAndRate().add(darDto);
            }
        }
        return dto;
    }
}
