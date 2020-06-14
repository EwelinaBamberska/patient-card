import { Component, OnInit } from "@angular/core";
import { PatientService } from "src/app/_services/patient.service";
import { ActivatedRoute } from "@angular/router";
import { IObservation } from "src/app/_interfaces/IObservation";
import { ChartDataSets, ChartOptions } from "chart.js";
import { Color, Label } from "ng2-charts";
import { MatDatepickerInputEvent } from '@angular/material';

@Component({
  selector: "app-user-details",
  templateUrl: "./user-details.component.html",
  styleUrls: ["./user-details.component.scss"],
})
export class UserDetailsComponent implements OnInit {
  constructor(private service: PatientService, private route: ActivatedRoute) {}

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

  private id: string;

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get("id");
    this.service.getWeightObservation(this.id).subscribe((res: IObservation[]) => {
      this.parseToChartData(res);
    });

    this.service.getMedicamentHistory(this.id).subscribe(res => {
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

  round(num) {
    return Math.round(num * 100) / 100;
  }

  changeDateFrom($event: MatDatepickerInputEvent<Date>) {
    const datestring = 
      $event.value.getFullYear()
      + "-" 
      + ("0"+($event.value.getMonth()+1)).slice(-2) 
      + "-" 
      + ("0" + $event.value.getDate()).slice(-2)
      
    this.dateFrom = datestring;
    this.service.getWeightObservation(this.id, this.dateFrom, this.dateTo).subscribe((res: IObservation[]) => {
      this.parseToChartData(res);
    });
  }

  changeDateTo($event: MatDatepickerInputEvent<Date>) {
    const datestring = 
      $event.value.getFullYear()
      + "-" 
      + ("0"+($event.value.getMonth()+1)).slice(-2) 
      + "-" 
      + ("0" + $event.value.getDate()).slice(-2) 

    this.dateTo = datestring;
    this.service.getWeightObservation(this.id, this.dateFrom, this.dateTo).subscribe((res: IObservation[]) => {
      this.parseToChartData(res);
    });
  }
}
