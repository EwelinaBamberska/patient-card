import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable } from "rxjs";
import { IPatient } from "../_interfaces/IPatient";
import { IObservation } from "../_interfaces/IObservation";
import { IMedicament } from '../_interfaces/IMedicament';

@Injectable({
  providedIn: "root",
})
export class PatientService {
  constructor(private http: HttpClient) {}

  getAllPatients(): Observable<IPatient[]> {
    return this.http.get<IPatient[]>(`${environment.host}patient`);
  }

  getPatientsByName(name: string): Observable<IPatient[]> {
    return this.http.get<IPatient[]>(`${environment.host}patient?name=${name}`);
  }

  getPatientById(id: string): Observable<IPatient> {
    return this.http.get<IPatient>(`${environment.host}patient/${id}`);
  }

  getWeightObservation( id: string, from?: string, to?: string ): Observable<IObservation[]> {
    const fromString = from && from.length > 0 ? `?from=${from}` : '';
    const toString = to && to.length > 0 ? from && from.length > 0 ? `&to=${to}` : `?to=${to}` : '';

    return this.http.get<IObservation[]>(
      `${environment.host}observation/${id}/weight${fromString}${toString}`
    );
  }

  getMedicamentHistory(id: string, from?: string, to?: string): Observable<IMedicament[]> {
    return this.http.get<IMedicament[]>(
      `${environment.host}medicament/${id}`
    );
  }

  addWeight(data) {
    return this.http.post(
      `${environment.host}observation`, data
    );
  }
}
