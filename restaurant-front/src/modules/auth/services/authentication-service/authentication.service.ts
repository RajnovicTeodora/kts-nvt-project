import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UserLogin } from 'src/modules/shared/models/user-login';
import {  UserWithToken } from 'src/modules/shared/models/user-with-token';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  login(auth: UserLogin): Observable<UserWithToken> {
    return this.http.post<UserWithToken>('http://localhost:8080/login', auth, {
      headers: this.headers,
      responseType: "json",
    });
  }

  logout(): Observable<string> {
    return this.http.get("api/logout", {
      headers: this.headers,
      responseType: "text",
    });
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem("user")) {
      return false;
    }
    return true;
  }

}
