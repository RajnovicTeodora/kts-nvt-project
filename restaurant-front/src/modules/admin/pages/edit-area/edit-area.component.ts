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
import { MatDialog } from '@angular/material/dialog';
import { AddAreaComponent } from '../../components/add-area/add-employee/add-area.component';
import { CdkDragDrop, CdkDragEnd, moveItemInArray } from '@angular/cdk/drag-drop';

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
  activeArea: Area;
  tablePositions: any[];
  savedChanges: boolean;
  selectedTable: number;
  selectedDeleteTable: boolean;
  selectedDeleteArea: boolean;
  areaToDelete: number;
  messageDeleteArea: string;
  selectedReturn: boolean;
  
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
    this.selectedTable = -1;
    this.selectedDeleteTable = false;
    this.selectedDeleteArea = false;
    this.areaToDelete -1;
    this.selectedReturn = false;
   }

  ngOnInit(): void {
    this.getAreas();
  }

  getAreas() {
    this.tablePositions = [];
    this.adminService.getAllAreas().subscribe((response) => {
      this.areas = response;
      if(this.areas.length !== 0){
        this.activeArea = this.areas[0];
        this.setTableCoordinates();
      }
    });
  }

  refreshArea() {
    this.adminService.getAreaById(this.activeArea.id).subscribe((response) => {
      this.activeArea = response;
      console.log(response);
      this.setTableCoordinates();
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
    this.areaToDelete = id;
    this.messageDeleteArea = "Are you sure you want to delete area " + name;
    this.selectedDeleteArea = true;
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
    this.selectedTable = -1;
    this.activeArea = area;
    this.refreshArea();
  }

  onDragEnded($event: CdkDragEnd, index: number) {
    this.savedChanges = false;
    const p = $event.source.getFreeDragPosition()//.getBoundingClientRect();
    let newX = p.x;
    let newY = p.y;
    this.tablePositions[index] = {x: newX, y: newY};
  }

  return() {
    if(!this.savedChanges){
      this.selectedReturn = true;
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
        this.refreshArea();
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

  selectTable(id: number) {
    if(this.selectedTable === id){
      this.selectedTable = -1;
    }
    else{
      this.selectedTable = id;
    }
  }

  deleteTable() {
    this.selectedDeleteTable = true;
  }

  onConfirmDeleteTableCancelClicked(item: boolean) {
    this.selectedDeleteTable = false;
    this.selectedTable = -1;
  }

  onConfirmDeleteTableConfirmedClicked(item: boolean) {
    console.log(this.selectedTable);
    this.adminService.deleteTable(this.selectedTable).subscribe({
      next: (success) => {
        this.toastr.success(
          'Successfully deleted table'
        );
        this.selectedTable = -1;
        this.selectedDeleteTable = false;
        this.refreshArea();
      },
      error: (error) => {
        this.toastr.error('Table occupied!');
        console.log(error);
        this.selectedDeleteTable = false;
      },
    });
  }

  onConfirmAreaTableCancelClicked(item: boolean) {
    this.selectedDeleteArea = false;
    this.areaToDelete = -1;
    this.messageDeleteArea = "";
  }

  onConfirmDeleteAreaConfirmedClicked(item: boolean) {
    this.adminService.deleteArea(this.areaToDelete).subscribe({
      next: (success) => {
        this.toastr.success(
          'Successfully deleted area'
        );
        this.getAreas();
        this.selectedDeleteArea = false;
      },
      error: (error) => {
        this.toastr.error('Area has occupied tables, or does not exits');
        console.log(error);
        this.selectedDeleteArea = false;
      },
    });
  }

  onConfirmReturnCancelClicked(item: boolean) {
    this.selectedReturn = false;
  }

  onConfirmReturnConfirmedClicked(item: boolean) {
    this.router.navigate(['/admin-dashboard']);
  }


}
