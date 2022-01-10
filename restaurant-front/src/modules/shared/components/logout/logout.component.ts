import { Component, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { AuthenticationService } from 'src/modules/auth/services/authentication-service/authentication.service';
import { UserList } from '../../models/user-list';
import { UserWithToken } from '../../models/user-with-token';
import { UserService } from '../../services/user-service/user.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss'],
})
export class LogoutComponent implements OnInit {
  @Output() onLogoutClose = new EventEmitter();

  constructor(
    private authService: AuthenticationService,
    private toastr: ToastrService,
    public router: Router,
    private userService : UserService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onLogoutClose.emit(true);
  }

  confirm() {
    this.authService.logout().subscribe(
      (result) => {
        const currentUser = this.userService.getLoggedIn();
        if (currentUser.userType === 'WAITER') {
          //TODO check which other userTypes to add to 'if'
          this.removeUserFromUserList(
            currentUser,
            currentUser.userType + '_LIST'
          );
        }
        localStorage.removeItem('currentUser');
        this.toastr.success('Successfully logged out!');
        //this.router.navigate(['/login']);
        //TODO show active user list window
        this.router.navigate(['/after-logout']);
      },
      (error) => {
        this.toastr.error(error.error);
      }
    );
  }

  removeUserFromUserList(user: UserWithToken, listName: string) {
    let users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem(listName)!)
    );
    if (users.value.numberOfUsers === 1) {
      localStorage.removeItem(listName);
    } else {
      users.value.numberOfUsers--;
      users.value.list.forEach((value, index) => {
        if (value.username == user.username) users.value.list.splice(index, 1);
      });
      localStorage.setItem(listName, JSON.stringify(users.value));
    }
  }
}
