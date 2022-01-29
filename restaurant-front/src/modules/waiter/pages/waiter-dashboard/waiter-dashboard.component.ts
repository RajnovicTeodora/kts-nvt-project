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
import { AdminService } from 'src/modules/admin/admin-service/admin.service';
import { Area } from 'src/modules/shared/models/area';
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
  showNotificationsModal: boolean;
  showModalRestaurantTableOptions: number;
  areas: Area[];
  activeArea: Area;
  tablePositions: any[];


  constructor(
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private userService: UserService,
    private adminService: AdminService,
    private notifService: NotificationService
  ) {
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
    this.showNotificationsModal = false;
    this.getAreas(0);
  }

  ngOnInit() {
    this.setBadgeValues();
    this.getAreas(0);
  }

  getAreas(areaId: number) {
    this.tablePositions = [];
    this.adminService.getAllAreas().subscribe((response) => {
      this.areas = response;
      if(this.areas.length !== 0){
        this.activeArea = this.areas[areaId];
        this.setTableCoordinates();
      }
    });
  }

  openArea(area: Area) {
    this.activeArea = area;
    this.setTableCoordinates();
  }

  setTableCoordinates() {
    this.tablePositions = [];
    this.activeArea.tables.sort((n1,n2) => n1.tableNum - n2.tableNum);
    this.activeArea.tables.forEach(table => {
      this.tablePositions.push({x: table.x, y: table.y});
    });
  }


  setBadgeValues(){
    this.waiterList = new Array;    
    this.setBagdeValueForCurrentUser();
    const users = new BehaviorSubject<UserList>(
      JSON.parse(localStorage.getItem('WAITER_LIST')!)
    );
    users.value.list.forEach((value, index) => {
      this.notifService.getActiveNotificationsForEmployee(value.username).subscribe(
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

  setBagdeValueForCurrentUser(){
    this.notifService.getActiveNotificationsForEmployee(this.user.username).subscribe(
      {
        next: (result) => {
          this.currentBadgeContent = result.length;
        },
        error: data => {
            this.toastr.error(data.error);          
        }
      }
    );
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

  onRestaurantTableShowClicked(tableId: number){
    this.showModalRestaurantTableOptions = tableId;
  }

  onRestaurantTableCloseClicked(item: boolean){
    this.showModalRestaurantTableOptions = -1;
    this.getAreas(this.areas.indexOf(this.activeArea));
  }

  onPayOrderCloseClicked(item:boolean){
    this.showPaymentModal = false;
    this.refreshRestaurantTableRequired = true;
  }

  onRefreshFinished(item:boolean){
    this.refreshRestaurantTableRequired = false;
  }

  onViewOrderAndBillClicked(order:number){
    this.currentOrderViewed = order;
    this.showPaymentModal = true;    
  }

  onNotificationsClicked(){
    this.showNotificationsModal = true;
  }

  onNotificationsCloseClicked(item:boolean){
    this.showNotificationsModal = false;
    this.setBadgeValues();
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
