import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrinkBartender } from 'src/modules/shared/models/drinkBartender';

@Injectable({
  providedIn: 'root'
})
export class AddDrinkService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  addDrink(drink: DrinkBartender):Observable<any>{
    const res = this.http.post<DrinkBartender>("http://localhost:8080/api/drink/addDrink", drink, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
}
