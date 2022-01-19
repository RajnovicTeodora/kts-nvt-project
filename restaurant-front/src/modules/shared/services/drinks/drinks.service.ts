import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DrinksService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  getDrinks(token: string):Observable<any>{ 
    const res = this.http.get("http://localhost:8080/api/drink/getDrinks", {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }

  getDrink(id:number):Observable<any>{
    const res = this.http.get("http://localhost:8080/api/drink/getDrink/"+id, { 
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }

}
