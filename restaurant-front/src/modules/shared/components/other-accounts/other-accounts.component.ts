import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserList } from '../../models/user-list';
import { UserWithBadgeNum } from '../../models/user-with-badgenum';
import { UserWithToken } from '../../models/user-with-token';
import { NotificationService } from '../../services/notification-service/notification.service';
import { UserService } from '../../services/user-service/user.service';

@Component({
  selector: 'app-other-accounts',
  templateUrl: './other-accounts.component.html',
  styleUrls: ['./other-accounts.component.scss'],
})
export class OtherAccountsComponent implements OnInit {
  @Output() onOtherAccountsClose = new EventEmitter();
  @Output() onLoginOpen = new EventEmitter();
  closeButtonShow: boolean;
  waiterList: UserList;//Array<UserWithBadgeNum>;
  bartenderList: UserList;//Array<UserWithBadgeNum>;
  chefList: UserList;//Array<UserWithBadgeNum>;
  loggedUser = new BehaviorSubject<UserWithToken>(
    JSON.parse(localStorage.getItem('currentUser')!)
  );

  constructor(
    private toastr: ToastrService,
    private userService: UserService,
    public router: Router
  ) {
    this.closeButtonShow = this.loggedUser.value === null ? false : true;
    this.waiterList = this.getUserLists('WAITER_LIST');
    this.bartenderList = this.getUserLists('BARTENDER_LIST');
    this.chefList = this.getUserLists('CHEF_LIST');
  }

  ngOnInit(): void {
    //this.setBadgeValues();
  }

  /*setBadgeValues(){
    this.waiterList = new Array; 
    this.bartenderList = new Array; 
    this.chefList = new Array; 
    const listnames = ['WAITER_LIST', 'BARTENDER_LIST', 'CHEF_LIST'];
    for(let i in listnames){
      const users = new BehaviorSubject<UserList>(
        JSON.parse(localStorage.getItem(listnames[i])!)
      );
      if(users.value === null){
        if(listnames[i] === 'WAITER_LIST') this.waiterList = new Array; 
        else if(listnames[i] === 'BARTENDER_LIST' ) this.bartenderList = new Array;
        else  this.chefList = new Array; 
      }
      else
        users.value.list.forEach((value, index) => {
          this.notifService.getNumberOfActiveNotificationsForWaiter(value.username).subscribe(
            {
              next: (result) => {
                let badgeNum = result.length;
                if(listnames[i] === 'WAITER_LIST') this.waiterList.push(new UserWithBadgeNum(badgeNum, value));
                else if(listnames[i] === 'BARTENDER_LIST' ) this.bartenderList.push(new UserWithBadgeNum(badgeNum, value));
                else  this.chefList.push(new UserWithBadgeNum(badgeNum, value));          
              },
              error: data => {
                  this.toastr.error(data.error);            
              }
            }
          );      
        });
    }
    
  }*/


  login() {
    this.onLoginOpen.emit(true);
  }

  close() {
    this.onOtherAccountsClose.emit(true);
  }

  getUserLists(listName: string): UserList {
    let users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem(listName)!)
    );
    return users.value;
  }

  changeAccount(username: string, listName: string) {
    if (username === this.loggedUser.value.username) {
      this.toastr.info('Already logged in as ' + username);
    } else {
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

              if ( newUser.userType === 'CHEF' ||newUser.userType === 'HEAD_CHEF') {
                this.onOtherAccountsClose.emit(true);
                if(this.router.url ==='/chef-dashboard'){
                  window.location.reload()
                }
                this.router.navigate(['/chef-dashboard']);
              } else if (newUser.userType === 'BARTENDER') {
                this.onOtherAccountsClose.emit(true);
                if(this.router.url ==='/bartender-dashboard'){
                  window.location.reload()
                }
                this.router.navigate(['/bartender-dashboard']);
              } else if (newUser.userType === 'WAITER') {
                this.onOtherAccountsClose.emit(true);
                if(this.router.url ==='/waiter-dashboard'){
                  window.location.reload()
                }
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
}
