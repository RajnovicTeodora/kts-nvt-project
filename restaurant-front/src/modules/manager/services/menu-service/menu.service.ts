import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UpdateMenuItemPrice } from 'src/modules/shared/models/menu-models/update-menu-item-price';

@Injectable({
  providedIn: 'root',
})
export class MenuService {
  constructor(private http: HttpClient) {}

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  getAll(name: string): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: { searchName: name },
    };

    return this.http.get(`${environment.baseUrl}/api/menu/getAll`, queryParams);
  }

  deleteMenuItem(id: string): Observable<any> {
    return this.http.delete(
      `${environment.baseUrl}/api/menu/deleteMenuItem/` + id,
      { headers: this.headers, responseType: 'json' }
    );
  }

  toggleIsMenuItemActive(id: string): Observable<any> {
    return this.http.put(
      `${environment.baseUrl}/api/menu/toggleIsActive`,
      {},
      {
        headers: this.headers,
        observe: 'response',
        params: { id: id },
      }
    );
  }

  updateMenuItemPrice(data: UpdateMenuItemPrice): Observable<any> {
    return this.http.post<UpdateMenuItemPrice>(
      `${environment.baseUrl}/api/menuItemPrice/updatePrice`,
      data,
      {
        headers: this.headers,
        responseType: 'json',
      }
    );
  }
}
