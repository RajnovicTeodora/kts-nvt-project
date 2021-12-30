import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Drink } from 'src/modules/shared/models/drink';


@Injectable({
  providedIn: 'root'
})
export class DrinkCardService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  getDrinks():Observable<any>{
    const res = this.http.get("api/drinks/getDrinks", {
        headers: this.headers,
        responseType: "json",
      });
      console.log(res);
      return res;
  }

}