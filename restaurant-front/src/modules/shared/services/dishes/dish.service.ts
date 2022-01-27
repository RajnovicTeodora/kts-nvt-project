import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DishService {
  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) {}

  getDishes():Observable<any>{ 
    const res = this.http.get("http://localhost:8080/api/dish/getDishes", {
        headers: this.headers,
        responseType: "json",
      });
      return res;
  }

  getDish(id:number):Observable<any>{
    return this.http.get('http://localhost:8080/api/menuItem/getById/' + id, {
      headers: this.headers,
      responseType: 'json',
    });
  }
  username = JSON.parse(localStorage.getItem('currentUser')!).username;
  
  getSearchedOrFiltered(name: string, filter: string): Observable<any> {
    let param = {
      username: this.username,
      searchName: name,
      filterName: filter,
    };
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: param,
    };

    return this.http.get(
      'http://localhost:8080/api/dish/getSearchedOrFiltered',
      queryParams
    );
  }
  
  getIngredientsByItemid(id:string):Observable<any>{
    return this.http.get('http://localhost:8080/api/menuItem/getById/' + id, {
      headers: this.headers,
      responseType: 'json',
    });
  }
}
