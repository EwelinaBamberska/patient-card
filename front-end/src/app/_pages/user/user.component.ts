import { Component, OnInit } from "@angular/core";
import { PatientService } from "src/app/_services/patient.service";
import { ActivatedRoute } from "@angular/router";
import { IPatient } from "src/app/_interfaces/IPatient";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"],
})
export class UserComponent implements OnInit {
  patient: IPatient;

  constructor(private service: PatientService, private route: ActivatedRoute) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get("id");
    this.service.getPatientById(id).subscribe((res: IPatient) => {
      this.patient = res;
    });
  }
}
