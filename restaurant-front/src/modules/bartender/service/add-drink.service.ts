import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Drink } from 'src/modules/shared/models/drink';

@Injectable({
  providedIn: 'root'
})
export class AddDrinkService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  addDrink(drink: Drink):Observable<any>{
    const res = this.http.post<Drink>("http://localhost:8080/api/drink/addDrink", drink, { //promeni na 1
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }
}
