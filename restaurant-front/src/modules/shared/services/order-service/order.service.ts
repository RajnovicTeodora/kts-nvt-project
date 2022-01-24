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
}
