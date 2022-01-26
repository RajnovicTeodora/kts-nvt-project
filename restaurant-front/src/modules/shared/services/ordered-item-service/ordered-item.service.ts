import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { OrderedItem } from '../../models/ordered-item';

@Injectable({
  providedIn: 'root'
})
export class OrderedItemService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getOrderedItemsForOrderId(orderId: number): Observable<Array<OrderedItem>> {
    return this.http.get<Array<OrderedItem>>(
      `${environment.baseUrl}/${environment.orderedItem}/getOrderedItemsForOrderId/${orderId}`,
      { responseType: 'json' }
    );
  }

  setOrderedItemDelivered(orderedItemId: number): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.orderedItem}/confirmPickup/${orderedItemId}`,
      { responseType: 'text' }
    );
  }

  deleteOrderedItem(orderedItemId: number): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.orderedItem}/setDeleted/${orderedItemId}`,
      { responseType: 'text' }
    );
  }

}
