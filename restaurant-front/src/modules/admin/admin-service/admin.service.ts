import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Employee } from 'src/modules/shared/models/employee';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private headers = new HttpHeaders({ 'Content-Type': 'text' });
  private headers2 = new HttpHeaders({
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
        headers: this.headers2,
        responseType: 'json',
      }
    );
  }

  editEmployee(employee: Employee): Observable<any> {
    return this.http.post<Employee>(
      `${environment.baseUrl}/api/employees/editUser`,
      employee,
      {
        headers: this.headers2,
        responseType: 'json',
      }
    );
  }

  deleteEmployee(username: string): Observable<any> {
    return this.http.delete<any>(
      `${environment.baseUrl}/api/employees/deleteUser/${username}`
    );
  }
}