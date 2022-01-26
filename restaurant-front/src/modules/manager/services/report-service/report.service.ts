import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(private http: HttpClient) {}

  // period = monthly, quarterly, annual
  // type = incomeAndSold, preparationCosts, paychecks
  getAll(period: string, type: String): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get(
      'http://localhost:8080/api/reports/' + period + '/' + type,
      queryParams
    );
  }
}
