import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddManagerDrink } from 'src/modules/shared/models/item-models/add-manager-drink';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  constructor(private http: HttpClient) {}

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  getAll(name: string): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: { searchName: name },
    };

    return this.http.get(
      `${environment.baseUrl}/api/menuItem/getAll`,
      queryParams
    );
  }

  getById(id: string): Observable<any> {
    return this.http.get(`${environment.baseUrl}/api/menuItem/getById/` + id, {
      headers: this.headers,
      responseType: 'json',
    });
  }

  deleteMenuItem(id: string): Observable<any> {
    return this.http.delete(
      `${environment.baseUrl}/api/menuItem/deleteMenuItem/ ` + id,
      { headers: this.headers, responseType: 'json' }
    );
  }

  approveMenuItem(id: string): Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/api/menuItem/approveMenuItem/` + id,
      { headers: this.headers, responseType: 'json' }
    );
  }

  saveMenuItem(data: AddManagerDrink): Observable<any> {
    return this.http.post<AddManagerDrink>(
      `${environment.baseUrl}/api/menuItem/addDrink`,
      data,
      {
        headers: this.headers,
        responseType: 'json',
      }
    );
  }
}
