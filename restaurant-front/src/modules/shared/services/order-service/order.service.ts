import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Order } from '../../models/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  createOrder(order: Order): Observable<number> {
    return this.http.post<number>(
      `${environment.baseUrl}/${environment.order}/createOrder`, order, {
        headers: this.headers
      }
    );
  }

  payOrder(orderId: number): Observable<string>{
    return this.http.get(
      `${environment.baseUrl}/${environment.order}/payOrder/${orderId}`,
      { responseType: 'text' }
    );
  }

  checkIfOrderIsPaid(orderId: number): Observable<boolean>{
    return this.http.get<boolean>(
      `${environment.baseUrl}/${environment.order}/checkIfOrderIsPaid/${orderId}`
    );
  }

  getActiveOrdersForTable(tableId: number, waiterUsername: string): Observable<Array<number>>{
    return this.http.get<Array<number>>(
      `${environment.baseUrl}/${environment.order}/getActiveOrdersForTable/${tableId}/${waiterUsername}`
    );
  }

  getOrder(orderNum: number): Observable<Order>{
    return this.http.get<Order>(
      `${environment.baseUrl}/${environment.order}/getOrder/${orderNum}`,
      { responseType: 'json' }
    );
  }

  updateOrder(order: Order): Observable<string> {
    return this.http.post(
      `${environment.baseUrl}/${environment.order}/updateOrder`, order, {
        headers: this.headers,
        responseType: 'text'
      }
    );
  }
}
