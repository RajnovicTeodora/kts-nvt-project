import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Area } from 'src/modules/shared/models/area';
import { Employee } from 'src/modules/shared/models/employee';
import { RestaurantTable } from 'src/modules/shared/models/restaurant-table';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private headers = new HttpHeaders({ 'Content-Type': 'text' });
  private headersJson = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  constructor(private http: HttpClient) { }


  getAllEmployees(search: string, filter: string) : Observable<any> {
    let param = {
      search: search,
      filter: filter,
    };
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: param,
    };
    return this.http.get(
      `${environment.baseUrl}/api/employees/getAllEmployees`, queryParams
    );
  }

  addEmployee(employee: Employee): Observable<any> {
    return this.http.post<Employee>(
      `${environment.baseUrl}/api/employees/addUser`,
      employee,
      {
        headers: this.headersJson,
        responseType: 'json',
      }
    );
  }

  editEmployee(employee: Employee): Observable<any> {
    return this.http.post<Employee>(
      `${environment.baseUrl}/api/employees/editUser`,
      employee,
      {
        headers: this.headersJson,
        responseType: 'json',
      }
    );
  }

  deleteEmployee(username: string): Observable<any> {
    return this.http.delete<any>(
      `${environment.baseUrl}/api/employees/deleteUser/${username}`
    );
  }

  getAllAreas() : Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/api/area/getAllAreas`
    );
  }

  getAreaById(id: number) : Observable<any> {
    return this.http.get(
      `${environment.baseUrl}/api/area/getById/${id}`
    );
  }

  deleteArea(id: number): Observable<any> {
    return this.http.delete<any>(
      `${environment.baseUrl}/api/area/deleteArea/${id}`
    );
  }

  addArea(name: string) : Observable<any> {
    return this.http.post(
      `${environment.baseUrl}/api/area/addArea`,
      name,
      {
        headers: this.headersJson,
        responseType: 'json',
      }
    );
  }

  addTable(table: RestaurantTable) : Observable<any> {
    return this.http.post(
      `${environment.baseUrl}/api/table/addTable`,
      table,
      {
        headers: this.headersJson,
        responseType: 'json',
      }
    );
  }

  editArea(area: Area) : Observable<any> {
    return this.http.put(
      `${environment.baseUrl}/api/area/editTables`,
      area,
      {
        headers: this.headersJson,
        responseType: 'json',
      }
    );
  }

  deleteTable(id: number): Observable<any> {
    return this.http.delete<any>(
      `${environment.baseUrl}/api/table/deleteTable/${id}`
    );
  }
}
