import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { PatientService } from "src/app/_services/patient.service";
import { IPatient } from "src/app/_interfaces/IPatient";
import { fromEvent } from "rxjs";
import { debounce, debounceTime } from "rxjs/operators";

@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.scss"],
})
export class HomepageComponent implements OnInit {
  patientList: IPatient[] = [];

  @ViewChild("input", { static: true }) input: ElementRef;

  constructor(private patientService: PatientService) {}

  ngOnInit() {
    const event = fromEvent(this.input.nativeElement, "keyup");
    event.pipe(debounceTime(200)).subscribe((x: any) => {
      this.patientService
        .getPatientsByName(x.target.value)
        .subscribe((res: IPatient[]) => {
          this.patientList = res;
        });
    });

    this.patientService.getAllPatients().subscribe((res: IPatient[]) => {
      this.patientList = res;
    });
  }
}
