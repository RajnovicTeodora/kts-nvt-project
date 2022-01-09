import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserList } from 'src/modules/shared/models/user-list';
import { UserLogin } from 'src/modules/shared/models/user-login';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { UserService } from 'src/modules/shared/services/user-service/user.service';

@Component({
  selector: 'app-waiter-dashboard',
  templateUrl: './waiter-dashboard.component.html',
  styleUrls: ['./waiter-dashboard.component.scss'],
})
export class WaiterDashboardComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  pageSize: number;
  currentPage: number;
  data: any[];
  imagePath: string;
  user: UserWithToken;
  showModalFirstPasswordChange: boolean;
  showModalLogout: boolean;
  waiterList: UserList;

  data2 = [
    { id: 1, url: 'assets/images/floor3.png' },
    { id: 2, url: 'assets/images/floor2.png' },
  ];

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private userService: UserService
  ) {
    this.pageSize = 1;
    this.currentPage = 0;
    this.data = [];
    this.imagePath = 'assets/images/floor3.png';
    this.user = UserService.getLoggedIn();
    this.showModalFirstPasswordChange = this.user.loggedInFirstTime;
    this.showModalLogout = false;
    let users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem('WAITER_LIST')!)
    );
    this.waiterList = users.value;
  }

  ngOnInit() {
    this.getData({ pageIndex: this.currentPage, pageSize: this.pageSize });
  }

  getData(obj: { pageIndex: any; pageSize: any }) {
    let index = 0,
      startingIndex = obj.pageIndex * obj.pageSize,
      endingIndex = startingIndex + obj.pageSize;

    this.data = this.data2.filter(() => {
      index++;
      return index > startingIndex && index <= endingIndex ? true : false;
    });
  }

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

  onFirstPasswordChangeClose(item: boolean) {
    this.user = new UserWithToken(
      this.user.token,
      this.user.expiresIn,
      this.user.username,
      this.user.userType,
      false,
      this.user.dontlook
    );
    this.showModalFirstPasswordChange = false;
  }

  logoutButtonClicked() {
    this.showModalLogout = true;
  }

  onLogoutCloseClicked(item: boolean) {
    this.showModalLogout = false;
  }

  changeAccount(username: string) {
    if (username === this.user.username) {
      this.toastr.info('Already logged in as ' + username);
    } else {
      const data = this.userService.getUserByUsernameAndListName(
        username,
        'WAITER_LIST'
      );
      this.userService
        .switchToActiveAccount({
          username: data.username,
          password: data.dontlook,
        })
        .subscribe(
          (result) => {
            this.toastr.success(result);

            if (data.token != '') {
              const newUser = new UserWithToken(
                data.token,
                data.expiresIn,
                data.username,
                data.userType,
                data.loggedInFirstTime,
                data.dontlook
              );
              localStorage.setItem('currentUser', JSON.stringify(newUser));
              //this.router.navigate(['/waiter-dashboard']);
              //window.location.reload()
              this.user = newUser;
              this.showModalFirstPasswordChange = this.user.loggedInFirstTime;
              let users = new BehaviorSubject<UserList>(
                JSON.parse(localStorage.getItem('WAITER_LIST')!)
              );
              this.waiterList = users.value;
            } else {
              this.toastr.error(
                'User with username ' + username + ' not found!'
              );
            }
          },
          (error) => {
            this.toastr.error(error.error);
          }
        );
    }
  }
}
