import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Drink } from 'src/modules/shared/models/drink';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';

@Injectable({
  providedIn: 'root'
})
export class DrinkCardService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  getDrinks(token: string):Observable<any>{ //todo proveri za null
    this.headers.append("Authorization",`Bearer ${token}`);
    const res = this.http.get("http://localhost:8080/api/drink/getDrinks", {
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }

  getDrink(id:number):Observable<any>{
    const res = this.http.get("http://localhost:8080/api/drinks/getDrink/{id}", { //promeni na 1
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }

  addDrink(drink: Drink):Observable<any>{
    const res = this.http.post<Drink>("http://localhost:8080/api/drinks/addDrinks", drink, { //promeni na 1
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }
}
