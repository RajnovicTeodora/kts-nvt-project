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
import { UserWithBadgeNum } from 'src/modules/shared/models/user-with-badgenum';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { NotificationService } from 'src/modules/shared/services/notification-service/notification.service';
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
  showModalPasswordChange: boolean;
  showModalLogout: boolean;
  waiterList: Array<UserWithBadgeNum>;
  showModalOtherAccounts: boolean;
  showModalLogin: boolean;
  showPaymentModal: boolean;
  currentBadgeContent: number;
  currentOrderViewed: number;
  refreshRestaurantTableRequired = false;
  ////////////////TODO isidora
  showModalRestaurantTableOptions: number;

  data2 = [
    { id: 1, url: 'assets/images/floor3.png' },
    { id: 2, url: 'assets/images/floor2.png' },
  ];

  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private userService: UserService,
    private notifService: NotificationService
  ) {
    this.pageSize = 1;
    this.currentPage = 0;
    this.data = [];
    this.imagePath = 'assets/images/floor3.png';
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
    this.showModalPasswordChange = this.user.loggedInFirstTime;
    this.showModalLogout = false;    
    this.waiterList = new Array;    
    this.showModalOtherAccounts = false;
    this.showModalLogin = false;
    this.currentBadgeContent = 0;
    this.showModalRestaurantTableOptions = -1;
    this.showPaymentModal = false;
  }

  ngOnInit() {
    this.getData({ pageIndex: this.currentPage, pageSize: this.pageSize });
    this.setBadgeValues();
  }

  setBadgeValues(){
    this.waiterList = new Array;    
    this.notifService.getNumberOfActiveNotificationsForWaiter(this.user.username).subscribe(
      {
        next: (result) => {
          this.currentBadgeContent = result.length;
        },
        error: data => {
            this.toastr.error(data.error);          
        }
      }
    );
    const users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem('WAITER_LIST')!)
    );
    users.value.list.forEach((value, index) => {
      this.notifService.getNumberOfActiveNotificationsForWaiter(value.username).subscribe(
        {
          next: (result) => {
            let badgeNum = result.length;
            this.waiterList.push(new UserWithBadgeNum(badgeNum, value));
          },
          error: data => {
              this.toastr.error(data.error);            
          }
        }
      );      
    });
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

  onPasswordChangeClose(item: boolean) {
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
    this.showModalPasswordChange = false;
  }

  onLogoutButtonClicked() {
    this.showModalLogout = true;
  }

  onSearchItemsButtonClicked(){
    this.router.navigate(['/select-menu-items']);
  }

  onLogoutCloseClicked(item: boolean) {
    this.showModalLogout = false;
  }

  onOtherAccountsButtonClicked(){
    this.showModalOtherAccounts = true;
  }

  onOtherAccountsCloseClicked(item: boolean){
    this.showModalOtherAccounts = false;
  }

  onLoginOpenClicked(item: boolean){
    this.showModalLogin = true;
    this.showModalOtherAccounts = false;
  }

  onLoginCloseClicked(item: boolean){
    this.showModalLogin = false;
  }

  onPasswordChangeButtonClicked(){
    this.showModalPasswordChange = true;
  }

  onRestaurantTableShowClicked(tableNum: number){
    this.showModalRestaurantTableOptions = tableNum;
  }

  onRestaurantTableCloseClicked(item: boolean){
    this.showModalRestaurantTableOptions = -1;
  }

  onPayOrderCloseClicked(item:boolean){
    this.showPaymentModal = false;
    this.refreshRestaurantTableRequired = true;
    //TODO olja, after order finished remove from
  }

  onRefreshFinished(item:boolean){
    this.refreshRestaurantTableRequired = false;
  }

  onViewOrderAndBillClicked(order:number){
    this.currentOrderViewed = order;
    //this.showModalRestaurantTableOptions = -1;
    this.showPaymentModal = true;    
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
              //this.router.navigate(['/waiter-dashboard']);
              //window.location.reload()
              this.user = newUser;
              this.showModalPasswordChange = this.user.loggedInFirstTime;
              this.setBadgeValues();
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
