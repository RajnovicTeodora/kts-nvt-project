import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.scss'],
})
export class ManagerDashboardComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  user: UserWithToken;
  showModalPasswordChange: boolean;
  showModalLogout: boolean;
  activeView: string = '1';

  constructor(private observer: BreakpointObserver, public router: Router) {
    const temp = new BehaviorSubject<UserWithToken>(
      JSON.parse(localStorage.getItem('currentUser')!)
    );
    this.user = temp.value;
    this.showModalPasswordChange = this.user.loggedInFirstTime;
    this.showModalLogout = false;
  }

  ngOnInit() {}

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  onPasswordChangeClose() {
    const temp = new BehaviorSubject<UserWithToken>(
      JSON.parse(localStorage.getItem('currentUser')!)
    );
    this.user = temp.value;
    this.showModalPasswordChange = false;
  }

  onLogoutButtonClicked() {
    this.showModalLogout = true;
  }

  onLogoutCloseClicked() {
    this.showModalLogout = false;
  }

  onPasswordChangeButtonClicked() {
    this.showModalPasswordChange = true;
  }

}
