import { Injectable } from '@angular/core';
import {
  HttpClientModule,
  HttpParams,
  HttpClient,
  HttpHeaders,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}
  getAll(): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get('http://localhost:8080/api/menuItem/getAll');
  }

  getById(id: string): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get('http://localhost:8080/api/menuItem/getById/' + id);
  }

  deleteMenuItem(id: string): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get(
      'http://localhost:8080/api/menuItem/deleteMenuItem/' + id
    );
  }

  approveMenuItem(id: string): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
    };

    return this.http.get(
      'http://localhost:8080/api/menuItem/approveMenuItem/' + id
    );
  }
}
