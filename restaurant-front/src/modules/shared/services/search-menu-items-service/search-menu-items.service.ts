import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MenuItemTypes } from '../../models/menu-item-types';
import { MenuItemDTO } from '../../models/menu-item';

@Injectable({
  providedIn: 'root'
})
export class SearchMenuItemsService {
  private headers = new HttpHeaders({ 'Content-Type': 'text' });

  constructor(private http: HttpClient) { }

  searchAndFilterItem(group : string, name : string) : Observable<Array<MenuItemDTO>>{
    return this.http.get<Array<MenuItemDTO>>(
      `${environment.baseUrl}/api/menu/searchMenuItems/${group}/${name}`
    );
  }

  getDrinkTypes() : Observable<Array<MenuItemTypes>> {
    return this.http.get<Array<MenuItemTypes>>(
      `${environment.baseUrl}/api/drink/getDrinkTypes`
    );
  }

  getDishTypes() : Observable<Array<MenuItemTypes>> {
    return this.http.get<Array<MenuItemTypes>>(
      `${environment.baseUrl}/api/dish/getDishTypes`
    );
  }
}
