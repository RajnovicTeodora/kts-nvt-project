import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) { }

  finishOrderedItem(id: string):Observable<string>{
    return this.http.post<string>("http://localhost:8080/api/orderedItem/finishOrderedItem/"+id,  {
        headers: this.headers,
        responseType: "text",
      });
  }

  acceptOrderedItem(id: string):Observable<string>{
    return this.http.post<string>("http://localhost:8080/api/orderedItem/acceptOrderedItem/"+id,  {
        headers: this.headers,
        responseType: "text",
      });
      
  }

  getNewOrderedItems(token: string, orderId: string):Observable<any>{ 
    const res = this.http.get("http://localhost:8080/api/orderedItem/itemsOfOrder/"+orderId, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }

  getAccepteOdrderedItems(token: string, orderId: string):Observable<any>{ 
    const res = this.http.get("http://localhost:8080/api/orderedItem/getAdcepted/"+orderId, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
}
