import { Component, OnInit } from "@angular/core";
import { PatientService } from "src/app/_services/patient.service";
import { ActivatedRoute } from "@angular/router";
import { IObservation } from "src/app/_interfaces/IObservation";
import { ChartDataSets, ChartOptions } from "chart.js";
import { Color, Label } from "ng2-charts";
import { MatDatepickerInputEvent } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { numberValidator } from 'src/app/_helpers/validators';
import { IVersion } from "../../_interfaces/IVersion";
import { IMedicament } from "../../_interfaces/IMedicament";

@Component({
  selector: "app-user-details",
  templateUrl: "./user-details.component.html",
  styleUrls: ["./user-details.component.scss"],
})
export class UserDetailsComponent implements OnInit {
  constructor( private service: PatientService, private route: ActivatedRoute, private formBuilder: FormBuilder ) {}

  lineChartData: ChartDataSets[] = [{ data: [], label: "Waga" }];
  lineChartLabels: Label[] = [];
  lineChartOptions = {
    responsive: true,
  };
  lineChartColors: Color[] = [
    {
      borderColor: "black",
      backgroundColor: "rgba(255,255,0,0.28)",
    },
  ];
  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = "line";

  dateFrom: string;
  dateTo: string;

  dateFromMeds: string;
  dateToMeds: string;

  private id: string;

  medicaments: IMedicament[];
  addWeightForm: FormGroup;
  editing: IDetailsEditing[] = [];

  versions: IVersion[][] = [];
  actualVersionIds: string[] = [];
  
  ngOnInit() {
    this.addWeightForm = this.formBuilder.group({
      weight: ['', [Validators.required, numberValidator]],
      date: ['', Validators.required]
    });

    this.id = this.route.snapshot.paramMap.get("id");
    this.service.getWeightObservation(this.id).subscribe((res: IObservation[]) => {
      this.parseToChartData(res);
    });

    this.service.getMedicamentHistory(this.id).subscribe((res: IMedicament[]) => {
      this.medicaments = res;

      res.forEach(r => {
        this.editing.push({
          frequence: false,
          period: false,
          periodUnit: false,
          sequence: false,
          doseRateType: false,
          doseQuantity: false
        });

        this.service.getVersions(r.id).subscribe((version: IVersion[]) => {
          this.versions.push(version)
          this.actualVersionIds.push(version[version.length - 1].version);
          // console.log("VER", version)
        });
      })

      console.log(res);
    })
  }

  parseToChartData(data) {
    this.lineChartData[0].data = [];
    this.lineChartLabels = [];

    data.forEach((observation) => {
      this.lineChartData[0].data.push(
        this.round(parseFloat(observation.value))
      );

      this.lineChartLabels.push(
        observation.date.split("-").reverse().join(".") + "r."
      );
    });
  }

  changeDateFrom($event: MatDatepickerInputEvent<Date>) {
    const datestring = this.convertDateToString($event.value);
      
    this.dateFrom = datestring;
    this.service.getWeightObservation(this.id, this.dateFrom, this.dateTo).subscribe((res: IObservation[]) => {
      this.parseToChartData(res);
    });
  }

  changeDateTo($event: MatDatepickerInputEvent<Date>) {
    const datestring = this.convertDateToString($event.value);

    this.dateTo = datestring;
    this.service.getWeightObservation(this.id, this.dateFrom, this.dateTo).subscribe((res: IObservation[]) => {
      this.parseToChartData(res);
    });
  }

  changeDateFromMed($event: MatDatepickerInputEvent<Date>) {
    const datestring = this.convertDateToString($event.value);
      
    this.dateFromMeds = datestring;
    this.service.getMedicamentHistory(this.id, this.dateFromMeds, this.dateToMeds).subscribe((res: IMedicament[]) => {
      // this.parseToChartData(res);
      this.medicaments = res;
      this.editing = [];
      this.versions = [];
      this.actualVersionIds = [];
      res.forEach(r => {
        this.editing.push({
          frequence: false,
          period: false,
          periodUnit: false,
          sequence: false,
          doseRateType: false,
          doseQuantity: false
        })

        this.service.getVersions(r.id).subscribe((version: IVersion[]) => {
          this.versions.push(version)
          this.actualVersionIds.push(version[version.length - 1].version);
          // console.log("VER", version)
        });
      })
    });
  }

  changeDateToMed($event: MatDatepickerInputEvent<Date>) {
    const datestring = this.convertDateToString($event.value);

    this.dateToMeds = datestring;
    this.service.getMedicamentHistory(this.id, this.dateFromMeds, this.dateToMeds).subscribe((res: IMedicament[]) => {
      // this.parseToChartData(res);
      this.medicaments = res;
      this.editing = [];
      this.versions = [];
      this.actualVersionIds = [];
      res.forEach(r => {
        this.editing.push({
          frequence: false,
          period: false,
          periodUnit: false,
          sequence: false,
          doseRateType: false,
          doseQuantity: false
        })

        this.service.getVersions(r.id).subscribe((version: IVersion[]) => {
          this.versions.push(version)
          this.actualVersionIds.push(version[version.length - 1].version);
          // console.log("VER", version)
        });
      })
    });
  }

  addWeight($event) {
    $event.preventDefault();
    if(this.addWeightForm.status === 'VALID') {
      const data = {
        patientId: this.id,
        date: this.convertDateToString(this.addWeightForm.get('date').value),
        value: this.addWeightForm.get('weight').value
      }
      console.log("dodawana waga", data)

      this.service.addWeight(data).subscribe(res => {
        console.log("otrzymana waga", res);

        this.lineChartData[0].data.push(
          this.round(parseFloat(data.value))
        );
  
        this.lineChartLabels.push(
          data.date.split("-").reverse().join(".") + "r."
        );
      })
      // console.log(this.convertDateToString(this.addWeightForm.get('date').value), this.addWeightForm.get('weight').value);
    }
  }

  startEditing(i, value) {
    this.editing[i][value] = true;
  }

  stopEditing(i, value) {
    this.editing[i][value] = false;
  }

  acceptEdit(obj: IMedicament, i, value, newProperty) {

    // if( !obj.dosageInstruction || obj.dosageInstruction.length === 0 ) {
    //   let newDosage = {};
    //   if( value === "doseRateType" ) {
    //     newDosage['doseAndRate'] = [{ 'doseRateType': [newProperty] }];
    //   } else if( value === "doseQuantity" ) {
    //     newDosage['doseAndRate'] = [{ 'doseQuantity': newProperty }];
    //   } else {
    //     newDosage[value] = newProperty;
    //     newDosage['doseAndRate'] = [];
    //   }

    //   obj.dosageInstruction = [];
    //   obj.dosageInstruction.push(newDosage);
    // } else if( value !== "doseRateType" && value !== "doseQuantity" ) {
    //   obj.dosageInstruction[0][value] = newProperty;
    // } else if ( value === "doseRateType" ) {
    //   obj.dosageInstruction[0]['doseAndRate'] = [{ 'doseRateType': [newProperty] }];
    // } else if( value === "doseQuantity" ) {
    //   obj.dosageInstruction[0]['doseAndRate'] = [{ 'doseQuantity': newProperty }];
    // }

    if( !obj.dosageInstruction || obj.dosageInstruction.length === 0 ) {
      let newDosage = {};
      newDosage[value] = newProperty;

      obj.dosageInstruction = [];
      obj.dosageInstruction.push(newDosage);
    } else {
      obj.dosageInstruction[0][value] = newProperty;
    }
    
    this.editing[i][value] = false;

    const dataToSend = {
      id: obj.id,
      dosageInstruction: obj.dosageInstruction
    }

    console.log(dataToSend)
    this.service.editMedication(dataToSend).subscribe(res => {
      console.log("updated",res);

      this.service.getVersions(obj.id).subscribe((versions: IVersion[]) => {
        this.versions[i] = versions;
        this.actualVersionIds[i] = versions[versions.length - 1].version;
      });
    })
  }

  changeVersion(medicament: IMedicament, version: IVersion, i: number) {
    this.service.getMedicamentByVersion(medicament.id, version.version).subscribe((res: any) => {
      this.medicaments[i] = res;
      this.actualVersionIds[i] = version.version;

      // this.service.getVersions(version.id).subscribe((versions: IVersion[]) => {
      //   this.versions[i] = versions;
      //   this.actualVersionIds[i] = versions[versions.length - 1].version;
      //   // console.log("VER", version)
      // });
    })
  }

  round(num) {
    return Math.round(num * 100) / 100;
  }

  convertDateToString(date) {
    const datestring = 
      date.getFullYear()
      + "-" 
      + ("0"+(date.getMonth()+1)).slice(-2) 
      + "-" 
      + ("0" + date.getDate()).slice(-2) 
    
    return datestring;
  }
}

interface IDetailsEditing {
  frequence: boolean;
  period: boolean;
  periodUnit: boolean;
  sequence: boolean;
  doseRateType: boolean;
  doseQuantity: boolean;
}