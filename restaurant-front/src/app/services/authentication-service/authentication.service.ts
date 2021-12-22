import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap } from 'rxjs';
import { UserLogin } from 'src/app/model/user-login';
import { UserWithToken } from 'src/app/model/user-with-token';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  loggedUser = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('loggedUser')!));

  constructor(private http: HttpClient, private router: Router) { }

  login(data: UserLogin) {
    return this.http.post<UserWithToken>(`${environment.baseUrl}/${environment.auth}/login`, data)
      .pipe(
        tap( resData => {
          this.handleAuthentication(resData);
        })
      );
  }

  private handleAuthentication(
    resData: UserWithToken
  ) {
    const user = new UserWithToken(
      resData.accessToken, 
      resData.expiresIn, 
      resData.userId, 
      resData.userType);
    this.loggedUser.next(user);
    localStorage.setItem('loggedUser', JSON.stringify(user));
  }

  logout() {
    this.loggedUser.next(new UserWithToken("", 0, 0, ""));
    this.router.navigate(['/login']);
    localStorage.removeItem('loggedUser');
  }

  autologin() {
    const user: {
      accessToken: string;
      expiresIn: number;
      userId: number;
      userType: string;
    } = JSON.parse(localStorage.getItem('loggedUser')!);

    if(!user) {
      return;
    }

    const loadedUser = new UserWithToken(
      user.accessToken,
      user.expiresIn,
      user.userId,
      user.userType
    );

    if(loadedUser.getToken) {
      this.loggedUser.next(loadedUser);
      if(loadedUser.userType === "ADMIN") {
        this.router.navigate(['/admin-dashboard']);
      }else if(loadedUser.userType === "MANAGER") {
        this.router.navigate(['/manager-dashboard']);
      }else if(loadedUser.userType === "HEAD_CHEF") {
        this.router.navigate(['/headChef-dashboard']);
      }else if(loadedUser.userType === "CHEF") {
        this.router.navigate(['/chef-dashboard']);
      }else if(loadedUser.userType === "BARTENDER") {
        this.router.navigate(['/bartender-dashboard']);
      }else if(loadedUser.userType === "WAITER") {
        this.router.navigate(['/waiter-dashboard']);
      }
    }
    
    
  }
}
