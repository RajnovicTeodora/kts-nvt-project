import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { AdminService } from 'src/modules/admin/admin-service/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  data: any[];
  showModalLogout: boolean;
  employeesWindowOpen: boolean;
  tablesWindowOpen: boolean;

  constructor(  
    private fb: FormBuilder,
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private adminService: AdminService
  ) {
    this.data = [];
    this.showModalLogout = false;    
    this.employeesWindowOpen = true;
    this.tablesWindowOpen = false;
   }

  ngOnInit(): void {
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

  onLogoutCloseClicked(item: boolean) {
    this.showModalLogout = false;
  }

  onLogoutButtonClicked() {
    this.showModalLogout = true;
  }

  onRestaurantTablesClicked(){
    this.employeesWindowOpen = false;
    this.router.navigate(['/edit-area']);
  }

  onEmployeesClicked(){
    this.tablesWindowOpen = false;
    this.employeesWindowOpen = true;
  }
}
