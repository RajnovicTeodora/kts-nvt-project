import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  constructor(private http: HttpClient) {}

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  // period = monthly, quarterly, annual
  // type = incomeAndSold, preparationCosts, paychecks
  getAll(period: string, type: String): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get(
      `${environment.baseUrl}/api/reports/` + period + '/' + type,
      queryParams
    );
  }
}
