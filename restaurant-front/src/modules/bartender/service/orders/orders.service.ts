import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) { }

  finishOrderedItem(id: string):Observable<any>{
    const res = this.http.post<String>("http://localhost:8080/api/orderedItem/finishOrderedItem/"+id,  {
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }

  acceptOrderedItem(id: string):Observable<any>{
    const res = this.http.post<String>("http://localhost:8080/api/orderedItem/acceptOrderedItem/"+id,  {
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }
  //get
}
