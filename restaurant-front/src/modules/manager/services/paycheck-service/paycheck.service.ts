import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { EditPaycheck } from 'src/modules/shared/models/paycheck-models/edit-paycheck';

@Injectable({
  providedIn: 'root',
})
export class PaycheckService {
  constructor(private http: HttpClient) {}

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  username = JSON.parse(localStorage.getItem('currentUser')!).username;

  getAll(name: string, filter: string): Observable<any> {
    let param = {
      username: this.username,
      searchName: name,
      filterName: filter,
    };
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: param,
    };

    return this.http.get(
      `${environment.baseUrl}/api/paychecks/getCurrent`,
      queryParams
    );
  }

  updatePaycheck(data: EditPaycheck): Observable<any> {
    return this.http.post(
      `${environment.baseUrl}/api/paychecks/changePaycheck`,
      data,
      {
        headers: this.headers,
        responseType: 'json',
      }
    );
  }
}
