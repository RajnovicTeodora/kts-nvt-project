import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EditPaycheck } from 'src/modules/shared/models/paycheck-models/edit-paycheck';

@Injectable({
  providedIn: 'root',
})
export class PaycheckService {
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  username = JSON.parse(localStorage.getItem('currentUser')!).username;

  constructor(private http: HttpClient) {}

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
      'http://localhost:8080/api/paychecks/getCurrent',
      queryParams
    );
  }

  updatePaycheck(data: EditPaycheck): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/paychecks/changePaycheck',
      data,
      {
        headers: this.headers,
        responseType: 'json',
      }
    );
  }
}
