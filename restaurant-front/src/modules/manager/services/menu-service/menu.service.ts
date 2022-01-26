import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UpdateMenuItemPrice } from 'src/modules/shared/models/menu-models/update-menu-item-price';

@Injectable({
  providedIn: 'root',
})
export class MenuService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}
  getAll(name: string): Observable<any> {
    let param = { searchName: name };

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: param,
    };

    return this.http.get('http://localhost:8080/api/menu/getAll', queryParams);
  }

  deleteMenuItem(id: string): Observable<any> {
    return this.http.delete(
      'http://localhost:8080/api/menu/deleteMenuItem/' + id,
      { headers: this.headers, responseType: 'json' }
    );
  }

  toggleIsMenuItemActive(id: string): Observable<any> {
    let param = { id: id };
    return this.http.put(
      'http://localhost:8080/api/menu/toggleIsActive',
      {},
      {
        headers: this.headers,
        observe: 'response',
        params: param,
      }
    );
  }

  updateMenuItemPrice(data: UpdateMenuItemPrice): Observable<any> {
    return this.http.post<UpdateMenuItemPrice>(
      'http://localhost:8080/api/menuItemPrice/updatePrice',
      data,
      {
        headers: this.headers,
        responseType: 'json',
      }
    );
  }
}
