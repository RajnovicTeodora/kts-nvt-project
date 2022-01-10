import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserList } from '../../models/user-list';
import { UserWithToken } from '../../models/user-with-token';
import { UserService } from '../../services/user-service/user.service';

@Component({
  selector: 'app-other-accounts',
  templateUrl: './other-accounts.component.html',
  styleUrls: ['./other-accounts.component.scss']
})
export class OtherAccountsComponent implements OnInit {
  @Output() onOtherAccountsClose = new EventEmitter();
  @Output() onLoginOpen = new EventEmitter();
  closeButtonShow:boolean;
  waiterList: UserList;
  bartenderList: UserList;
  chefList: UserList;

  constructor(
    private toastr: ToastrService,
    private userService: UserService,
     public router: Router) {
    const loggedUser = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.closeButtonShow = (loggedUser.value === null ? false : true);
    this.waiterList = this.getUserLists('WAITER_LIST');
    this.bartenderList = this.getUserLists('BARTENDER_LIST');
    this.chefList = this.getUserLists('CHEF_LIST');
   }

  ngOnInit(): void {
  }

  login(){
    this.onLoginOpen.emit(true);
  }

  close(){
    this.onOtherAccountsClose.emit(true);
  }

  getUserLists(listName: string): UserList{
    let users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem(listName)!)
    );
    return users.value;
  }

  changeAccount(username: string, listName: string) {
      const data = this.userService.getUserByUsernameAndListName(
        username,
        listName
      );
      this.userService
        .switchToActiveAccount({
          username: data.username,
          password: data.password,
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
                data.password
              );
              localStorage.setItem('currentUser', JSON.stringify(newUser));

              if (newUser.userType === 'CHEF' || newUser.userType === 'HEAD_CHEF') {
                this.router.navigate(['/chef-dashboard']);
              } else if (newUser.userType === 'BARTENDER') {
                this.router.navigate(['/bartender-dashboard']);
              } else if (newUser.userType === 'WAITER') {
                this.router.navigate(['/waiter-dashboard']);
              }
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
