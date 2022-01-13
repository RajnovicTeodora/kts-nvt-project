import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddManagerDrink } from 'src/modules/shared/models/item-models/add-manager-drink';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(private http: HttpClient) {}
  getAll(): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get('http://localhost:8080/api/menuItem/getAll');
  }

  getAllBySearchCriteria(name: string): Observable<any> {
    let param = { searchName: name };

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: param,
    };

    return this.http.get(
      'http://localhost:8080/api/menuItem/getAll',
      queryParams
    );
  }

  getById(id: string): Observable<any> {
    return this.http.get('http://localhost:8080/api/menuItem/getById/' + id, {
      headers: this.headers,
      responseType: 'json',
    });
  }

  deleteMenuItem(id: string): Observable<any> {
    return this.http.delete(
      'http://localhost:8080/api/menuItem/deleteMenuItem/' + id,
      { headers: this.headers, responseType: 'json' }
    );
  }

  approveMenuItem(id: string): Observable<any> {
    return this.http.get(
      'http://localhost:8080/api/menuItem/approveMenuItem/' + id,
      { headers: this.headers, responseType: 'json' }
    );
  }

  saveMenuItem(data: AddManagerDrink): Observable<any> {
    return this.http.post<AddManagerDrink>(
      'http://localhost:8080/api/menuItem/addDrink',
      data,
      {
        headers: this.headers,
        responseType: 'json',
      }
    );
  }
}
