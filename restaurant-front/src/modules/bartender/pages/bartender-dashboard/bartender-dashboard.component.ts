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
import { UserWithBadgeNum } from 'src/modules/shared/models/user-with-badgenum';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { NotificationService } from 'src/modules/shared/services/notification-service/notification.service';
import { UserService } from 'src/modules/shared/services/user-service/user.service';

@Component({
  selector: 'app-bartender-dashboard',
  templateUrl: './bartender-dashboard.component.html',
  styleUrls: ['./bartender-dashboard.component.scss']
})
export class BartenderDashboardComponent implements OnInit {

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
  currentBadgeContent: number;

  data2 = [
    { id: 1, url: 'assets/images/floor3.png' },
    { id: 2, url: 'assets/images/floor2.png' },
  ];
  
  state:number=0;
  idOrder: any;
  idOfDrink: string ="";

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
   }

  ngOnInit(): void {
    this.getData({ pageIndex: this.currentPage, pageSize: this.pageSize });
    this.setBadgeValues();
  }
  setBadgeValues(){
    this.waiterList = new Array;    
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

  onLogoutCloseClicked(item: boolean) {
    this.showModalLogout = false;
  }

  onPasswordChangeButtonClicked(){
    this.showModalPasswordChange = true;
  }

  
  onClickedView(id:string){
    this.idOfDrink = id;
    this.state=6;
  }
  onClickViewNew(id:number){
    this.idOrder = id;
    this.state = 2;
  }
  onClickViewAccepted(id:number){
    this.idOrder = id;
    this.state = 3;
  }
}
