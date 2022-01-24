import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MenuItemWithIngredients } from '../../models/menu-item-with-ingredients';

@Injectable({
  providedIn: 'root'
})
export class MenuItemWithIngredientsService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getMenuItem(id: number): Observable<MenuItemWithIngredients> {
    return this.http.get<MenuItemWithIngredients>(
      `${environment.baseUrl}/${environment.menuItem}/getWithIngredientsById/${id}`,
      { responseType: 'json' }
    );
  }
}
