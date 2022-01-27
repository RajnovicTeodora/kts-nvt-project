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

  acceptOrderedItem(username: string, id: string):Observable<string>{
    const parameters = id + "/" + username;
    return this.http.post<string>("http://localhost:8080/api/orderedItem/acceptOrderedItem/"+parameters,  {
        headers: this.headers,
        responseType: "text",
      });
      
  }
  getNote(id: string):Observable<string>{
    return this.http.get("http://localhost:8080/api/order/getNote/"+id,  {
        headers: this.headers,
        responseType: "text",
      });
  }

  getNewOrderedItems(orderId: string):Observable<any>{ 
    const res = this.http.get("http://localhost:8080/api/orderedItem/itemsOfOrder/"+orderId, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }

  getAccepteOdrderedItems(username: string, orderId: string):Observable<any>{ 
    const parameters = orderId + "/" +username;
    const res = this.http.get("http://localhost:8080/api/orderedItem/getAccepted/"+parameters, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
  getNewOrders():Observable<any>{
    const res = this.http.get("http://localhost:8080/api/order/getNewOrders",{
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
  getAcceptedOrders(username: string):Observable<any>{
    const res = this.http.get("http://localhost:8080/api/order/getAcceptedOrders/"+username,{
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
}
