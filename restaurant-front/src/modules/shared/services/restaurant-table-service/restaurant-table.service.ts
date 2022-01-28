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

  getRestaurantTable(tableId: number): Observable<RestaurantTable> {
    return this.http.get<RestaurantTable>(
      `${environment.baseUrl}/${environment.table}/getTableByTableId/${tableId}`,
      { responseType: 'json' }
    );
  }

  occupyTable(tableId: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableId}/occupyTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  unoccupyTable(tableId: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableId}/clearTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  claimTable(tableId: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableId}/claimTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  unclaimTable(tableId: number, waiter: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.table}/${tableId}/leaveTable/${waiter}`,
      { responseType: 'text' }
    );
  }

  getRestaurantTableId(tableNum: number): Observable<number> {
    return this.http.get<number>(
      `${environment.baseUrl}/${environment.table}/getTableIdByTableNumber/${tableNum}`
    );
  }

  getRestaurantTableNumber(tableId: number): Observable<number> {
    return this.http.get<number>(
      `${environment.baseUrl}/${environment.table}/getTableNumberByTableId/${tableId}`
    );
  }
}
