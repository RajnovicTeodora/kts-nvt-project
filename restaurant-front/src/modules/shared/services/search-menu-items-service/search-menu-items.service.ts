import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SearchResults } from '../../models/searched-items';

@Injectable({
  providedIn: 'root'
})
export class SearchMenuItemsService {
  private headers = new HttpHeaders({ 'Content-Type': 'text' });

  constructor(private http: HttpClient) { }

  searchAndFilterItem(group : string, name : string) : Observable<Array<SearchResults>>{
    return this.http.get<Array<SearchResults>>(
      `${environment.baseUrl}/api/menu/searchMenuItems/${group}/${name}`
    );
  }
}
