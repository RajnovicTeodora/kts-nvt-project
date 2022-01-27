import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  ElementRef,
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
import { CdkDragDrop, CdkDragEnd, moveItemInArray } from '@angular/cdk/drag-drop';
import { ConfirmComponent } from '../../components/confirm/delete-area/confirm.component';

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
  tablePositions: any[];
  savedChanges: boolean;
  
  constructor(  
    private fb: FormBuilder,
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private adminService: AdminService,
    private dialog: MatDialog,
     private elem: ElementRef
  ) {
    this.data = [];
    this.savedChanges = true;
   }

  ngOnInit(): void {
    this.getAreas();
  }

  getAreas() {
    this.tablePositions = [];
    this.adminService.getAllAreas().subscribe((response) => {
      this.areas = response;
      if(this.areas.length !== 0){
        console.log(this.areas[0]);
        this.activeArea = this.areas[0];
        this.setTableCoordinates();
      }
    });
  }

  setTableCoordinates() {
    this.tablePositions = [];
    this.activeArea.tables.sort((n1,n2) => n1.tableNum - n2.tableNum);
    this.activeArea.tables.forEach(table => {
      this.tablePositions.push({x: table.x, y: table.y});
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

  openEditTables(area: Area) {
    if(!this.savedChanges){
      this.saveChanges();
    }
    this.activeArea = area;
    this.setTableCoordinates();
  }

  onDragEnded($event: CdkDragEnd, index: number) {
    this.savedChanges = false;
    const p = $event.source.getFreeDragPosition()//.getBoundingClientRect();
    let newX = p.x;
    let newY = p.y;
    this.tablePositions[index] = {x: newX, y: newY};
  }

  return() {
    if(!this.savedChanges) {
      const dialogRef = this.dialog.open(ConfirmComponent,  {
        disableClose: true,
        autoFocus: true,
        width: '40%',
        height: '20%',
        data: {
          message: "You have unsaved changes! Are you sure you want to return?",
        }
      });
  
      dialogRef.afterClosed().subscribe((result) => {
        console.log(`Dialog result: ${result}`);
        this.getAreas();
      });
    }
    else{
      this.router.navigate(['/admin-dashboard']);
    }
    
  }

  addTable(){
    let tableNumber = 0;
    if(this.activeArea.tables.length == 0){
      tableNumber = 1;
    }
    else{
      tableNumber = this.activeArea.tables[this.activeArea.tables.length - 1].tableNum + 1
    }
    const table = {
      id: -1,
      tableNum: tableNumber,
      x: 0,
      y: 0,
      areaId: this.activeArea.id,
      waiterUsername: "",
      occupied: false,
      claimed: false
    };
    this.adminService.addTable(table).subscribe({
      next: (success) => {
        this.toastr.success(
          'Successfully added table'
        );
        this.activeArea.tables.push(success);
        this.setTableCoordinates();
      },
      error: (error) => {
        this.toastr.error('Unable to add new table');
        console.log(error);
      },
    });
  }

  saveChanges() {
    this.activeArea.tables.forEach(table => {
      table.x = this.tablePositions[this.activeArea.tables.indexOf(table)].x;
      table.y = this.tablePositions[this.activeArea.tables.indexOf(table)].y;
    });

    this.adminService.editArea(this.activeArea).subscribe({
      next: (success) => {
        this.toastr.success(
          'Successfully edited area'
        );
        this.savedChanges = true;
        this.setTableCoordinates();
      },
      error: (error) => {
        this.toastr.error('Unable to edit area');
        console.log(error);
      },
    });
  }

}
