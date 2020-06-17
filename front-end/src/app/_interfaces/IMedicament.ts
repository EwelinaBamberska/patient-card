export interface IMedicament {
  codeableConcept: string;
  creationDate: string;
  dosageInstruction: IDosage[];
  id: string;
  intent: string;
  requester: string;
  status: string;
  version: string;
}

export interface IDosage {
  doseAndRate?: IDoseAndRate[];
  frequency?: string;
  period?: string;
  periodUnit?: string;
  sequence?: string;
}

export interface IDoseAndRate {
  doseRateType?: string[];
  doseQuantity?: string;
}