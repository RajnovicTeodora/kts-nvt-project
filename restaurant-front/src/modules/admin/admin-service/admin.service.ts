import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MenuItemTypes } from '../../shared/models/menu-item-types';
import { MenuItemPriceDTO } from '../../shared/models/menu-item-price';
import { Employee } from 'src/modules/shared/models/employee';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private headers = new HttpHeaders({ 'Content-Type': 'text' });

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
    return this.http.get<Array<Employee>>(
      `${environment.baseUrl}/api/employees/getAllEmployees`, queryParams
    );
  }

}
