import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserList } from '../../models/user-list';
import { UserLogin } from '../../models/user-login';
import { UserWithToken } from '../../models/user-with-token';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  static getLoggedIn(): UserWithToken {
    const token: {
      token: string;
      expiresIn: number;
      username: string;
      userType: string;
      loggedInFirstTime: boolean;
      password: string;
    } = JSON.parse(localStorage.getItem('currentUser')!);

    const user = new UserWithToken(
      token.token,
      token.expiresIn,
      token.username,
      token.userType,
      token.loggedInFirstTime,
      token.password
    );

    return user;
  }

  getUserByUsernameAndListName(
    username: string,
    listName: string
  ): UserWithToken {
    let users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem(listName)!)
    );
    for (let i of users.value.list) {
      if (i.username === username) {
        return i;
      }
    }

    return new UserWithToken('', 0, '', '', false, '');
  }

  changePassword(data: UserLogin): Observable<Boolean> {
    return this.http.post<Boolean>(
      'http://localhost:8080/auth/firstTimeChangePassword',
      data
    );
  }

  switchToActiveAccount(user: UserLogin): Observable<string> {
    return this.http.post(
      'http://localhost:8080/auth/switchToActiveAccount',
      user,
      { responseType: 'text' }
    );
  }
}
