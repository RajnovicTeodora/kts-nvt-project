import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Drink2 } from 'src/modules/shared/models/Drink2';

@Injectable({
  providedIn: 'root'
})
export class AddDrinkService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  addDrink(drink: Drink2):Observable<any>{
    const res = this.http.post<Drink2>("http://localhost:8080/api/drink/addDrink", drink, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
}
