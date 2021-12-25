import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthenticationService } from '../../services/authentication-service/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole: string = route.data['expectedRoles'];
    const token = localStorage.getItem('user');

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    const user: {
      accessToken: string;
      expiresIn: number;
      userId: number;
      userType: string;
    } = JSON.parse(token);
    const userType = user.userType;

    if (userType === expectedRole) {
      return true;
    } else {
      if (userType === 'ADMIN') {
        this.router.navigate(['/admin-dashboard']);
      } else if (userType === 'MANAGER') {
        this.router.navigate(['/manager-dashboard']);
      } else if (userType === 'HEAD_CHEF') {
        this.router.navigate(['/headChef-dashboard']);
      } else if (userType === 'CHEF') {
        this.router.navigate(['/chef-dashboard']);
      } else if (userType === 'BARTENDER') {
        this.router.navigate(['/bartender-dashboard']);
      } else if (userType === 'WAITER') {
        this.router.navigate(['/waiter-dashboard']);
      }
      return false;
    }

    return true;
  }
}
