package com.example.iwm.mapper;

import com.example.iwm.model.DosageInstructionDTO;
import com.example.iwm.model.DoseAndRateDTO;
import com.example.iwm.model.MedicationRequestDTO;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    @Override
    public MedicationRequest fromMedicationRequestDTO(MedicationRequestDTO dto) {
        MedicationRequest medicationRequest = new MedicationRequest();

        for(DosageInstructionDTO dosageDTO : dto.getDosageInstruction()) {
            medicationRequest.getDosageInstruction().add(toDosage(dosageDTO));
        }

        return medicationRequest;
    }

    private Dosage toDosage(DosageInstructionDTO dosageDTO) {
        Dosage dosage = new Dosage();
        List<DoseAndRateDTO> doses = dosageDTO.getDoseAndRate();

        for (DoseAndRateDTO dose : doses) {
            Dosage.DosageDoseAndRateComponent mDosage = new Dosage.DosageDoseAndRateComponent();

            mDosage.setDose(dose.getDoseQuantity() != null ? new Quantity().setValue(new BigDecimal(dose.getDoseQuantity())) : null);

            for (String type : dose.getDoseRateType()) {
                mDosage.getType().getCoding().add(new Coding().setCode(type));
            }
            dosage.getDoseAndRate().add(mDosage);
        }

        dosage.getTiming().getRepeat().setFrequency(Integer.valueOf(dosageDTO.getFrequency()))
                .setPeriodUnit(Timing.UnitsOfTime.fromCode(dosageDTO.getPeriodUnit()))
                .setPeriod(new BigDecimal(dosageDTO.getPeriod()));
        dosage.setSequence(Integer.valueOf(dosageDTO.getSequence()));
        return dosage;
    }

    private DosageInstructionDTO toDosageInstructionDTO(Dosage d) {
        DosageInstructionDTO dto = new DosageInstructionDTO();
        dto.setSequence(d.hasSequence() ? String.valueOf(d.getSequence()) : null);
        if(d.hasTiming() && d.getTiming().hasRepeat()) {
            Timing.TimingRepeatComponent repeat = d.getTiming().getRepeat();
            dto.setFrequency(repeat.hasFrequency() ? String.valueOf(repeat.getFrequency()) : null);
            dto.setPeriod(repeat.hasPeriod() ? String.valueOf(repeat.getPeriod()) : null);
            dto.setPeriodUnit(repeat.hasPeriodUnit() ? repeat.getPeriodUnit().getSystem() : null);
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
