import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { JwtHelperService } from "@auth0/angular-jwt";
import { AuthenticationService } from '../../services/authentication-service/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem("user");
    const jwt: JwtHelperService = new JwtHelperService();

    if (this.auth.isLoggedIn()) {
      const userType = (jwt.decodeToken(token!)).role[0].authority;

      if(userType === "ADMIN") {
        this.router.navigate(['/admin-dashboard']);
      }else if(userType === "MANAGER") {
        this.router.navigate(['/manager-dashboard']);
      }else if(userType === "HEAD_CHEF") {
        this.router.navigate(['/headChef-dashboard']);
      }else if(userType === "CHEF") {
        this.router.navigate(['/chef-dashboard']);
      }else if(userType === "BARTENDER") {
        this.router.navigate(['/bartender-dashboard']);
      }else if(userType === "WAITER") {
        this.router.navigate(['/waiter-dashboard']);
      }

      return false;
    }
    return true;
  }
  
}
