import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  acceptOrder():Observable<any>{
    const res = this.http.post("api/orders/acceptOrderedItem", { //id
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }

  finishOrder():Observable<any>{
    const res = this.http.post("api/orders/finishOrderedItem", { //id
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }
}
