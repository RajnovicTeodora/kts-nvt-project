import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RestaurantTable } from '../../models/restaurant-table';

@Injectable({
  providedIn: 'root',
})
export class RestaurantTableService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getRestaurantTable(tableNum: number): Observable<RestaurantTable> {
    return this.http.get<RestaurantTable>(
      `${environment.baseUrl}/${environment.table}/getTableByTableNumber/${tableNum}`,
      { responseType: 'json' }
    );
  }

  occupyTable(tableNum: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableNum}/occupyTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  unoccupyTable(tableNum: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableNum}/clearTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  claimTable(tableNum: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableNum}/claimTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  unclaimTable(tableNum: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableNum}/leaveTable/${waiter}`,
      { responseType: 'text' }
    );
  }
}
