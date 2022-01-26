import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddChefDish } from 'src/modules/shared/models/item-models/add-chef-dish';
import { AddDishComponent } from '../../pages/add-dish/add-dish.component';

@Injectable({
  providedIn: 'root'
})
export class DishesService {

  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  addDish(drink: AddChefDish):Observable<any>{
    const res = this.http.post<AddChefDish>("http://localhost:8080/api/dish/addDish", drink, {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }
}
