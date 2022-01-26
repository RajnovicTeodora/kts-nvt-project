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
import { Area } from 'src/modules/shared/models/area';
import { RestaurantTable } from 'src/modules/shared/models/restaurant-table';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteAreaComponent } from '../../components/delete-area/delete-area.component';
import { MatDialog } from '@angular/material/dialog';
import { AddAreaComponent } from '../../components/add-area/add-employee/add-area.component';

@Component({
  selector: 'app-edit-area',
  templateUrl: './edit-area.component.html',
  styleUrls: ['./edit-area.component.scss']
})
export class EditAreaComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  data: any[];
  areas: Area[];
  dataSource: MatTableDataSource<any>;
  activeArea: Area;
  
  constructor(  
    private fb: FormBuilder,
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private adminService: AdminService,
    private dialog: MatDialog
  ) {
    this.data = [];
   }

  ngOnInit(): void {
    this.getAreas();
  }

  getAreas() {
    this.adminService.getAllAreas().subscribe((response) => {
      this.areas = response;
      console.log(this.areas);
      if(this.areas.length !== 0){
        this.activeArea = this.areas[0];
      }
      
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

  openDeleteAreaDialog(name: string, id: number) {
    console.log(name);
    const dialogRef = this.dialog.open(DeleteAreaComponent,  {
      disableClose: true,
      autoFocus: true,
      width: '40%',
      height: '20%',
      data: {
        id: id,
        name: name
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.getAreas();
    });
  }

  openAddAreaDialog() {
    const dialogRef = this.dialog.open(AddAreaComponent,  {
      disableClose: true,
      autoFocus: true,
      width: '40%',
      height: '30%',

    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.getAreas();
    });
  }

  return() {
    this.router.navigate(['/admin-dashboard']);
  }

}
