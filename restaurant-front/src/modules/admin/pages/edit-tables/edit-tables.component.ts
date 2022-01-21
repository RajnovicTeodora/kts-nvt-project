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
  selector: 'app-edit-tables',
  templateUrl: './edit-tables.component.html',
  styleUrls: ['./edit-tables.component.scss']
})
export class EditTablesComponent implements OnInit {
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
    this.employeesWindowOpen = false;
    this.tablesWindowOpen = true;
   }

  ngOnInit(): void {
  }

}
