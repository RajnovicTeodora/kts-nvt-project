import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserLogin } from '../../models/user-login';
import { UserWithToken } from '../../models/user-with-token';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  static getLoggedIn(): UserWithToken{
    const token: {
      token: string;
      expiresIn: number;
      username: string;
      userType: string;
      loggedInFirstTime:boolean;
    } = JSON.parse(localStorage.getItem('currentUser')!);

    const user = new UserWithToken(
      token.token, 
      token.expiresIn, 
      token.username, 
      token.userType,
      token.loggedInFirstTime);
      
    return user;    
  }

  changePassword(data: UserLogin): Observable<Boolean> {
    return this.http.post<Boolean>('http://localhost:8080/auth/firstTimeChangePassword', data);
  }

}
