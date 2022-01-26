import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { RestaurantTable } from 'src/modules/shared/models/restaurant-table';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { OrderService } from 'src/modules/shared/services/order-service/order.service';
import { RestaurantTableService } from 'src/modules/shared/services/restaurant-table-service/restaurant-table.service';

@Component({
  selector: 'app-table-options',
  templateUrl: './table-options.component.html',
  styleUrls: ['./table-options.component.scss'],
})
export class TableOptionsComponent implements OnInit {
  @Input() tableNumber = 0;
  @Output() onRestaurantTableClose = new EventEmitter();
  @Output() onViewOrderAndBill = new EventEmitter();
  @Output() onEditOrder = new EventEmitter();
  title: string;
  currentUser: UserWithToken;
  table: RestaurantTable;
  ELEMENT_DATA: number[] = [];
  displayedColumns: string[] = [
    'orderNumber',
    'edit',
    'view',
  ];
  dataSource = [...this.ELEMENT_DATA];
  @ViewChild(MatTable) matTable: MatTable<number>;

  constructor(
    private tableService: RestaurantTableService,
    private orderService: OrderService,
    public router: Router,
    private toastr: ToastrService
  ) {
    const temp = new BehaviorSubject<UserWithToken>(
      JSON.parse(localStorage.getItem('currentUser')!)
    );
    this.currentUser = temp.value;
    this.table = new RestaurantTable(0, 0, 0, 0, '', false);
    this.title = '';
  }

  ngOnInit(): void {
    this.setTable();
    this.getActiveTableOrderNumbers();
    
  }

  setTable() {
    this.tableService.getRestaurantTable(this.tableNumber).subscribe({
      next: (result) => {
        this.title = 'Table ' + result.tableNum;
        this.table = new RestaurantTable(
          result.tableNum,
          result.x,
          result.y,
          result.areaId,
          result.waiterUsername,
          result.occupied
        );
      },
      error: (data) => {
        this.toastr.error(data.error);
      },
    });
  }

  getActiveTableOrderNumbers(){
    this.orderService.getActiveOrdersForTable(this.tableNumber, this.currentUser.username).subscribe({
      next: (result) => {
        result.forEach(value =>{
          this.ELEMENT_DATA.push(value);
          this.dataSource.push(value);
          this.matTable.renderRows();
        });

      },
      error: (data) => {
        this.toastr.error(data.error);
      },
    });
  }

  claim() {
    this.tableService
      .claimTable(this.tableNumber, this.currentUser.username)
      .subscribe({
        next: (result) => {
          this.toastr.success(result);
          this.table.waiterUsername = this.currentUser.username;
          this.table.claimed = true;
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  unclaim() {
    this.tableService
      .unclaimTable(this.tableNumber, this.currentUser.username)
      .subscribe({
        next: (result) => {
          this.toastr.success(result);
          this.table.waiterUsername = "";
          this.table.claimed = false;
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  setOccupied() {
    this.tableService
      .occupyTable(this.tableNumber, this.currentUser.username)
      .subscribe({
        next: (result) => {
          this.toastr.success(result);
          this.table.occupied = true;
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  setUnoccupied() {
    this.tableService
      .unoccupyTable(this.tableNumber, this.currentUser.username)
      .subscribe({
        next: (result) => {
          this.toastr.success(result);
          this.table.occupied = false;
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  close() {
    this.onRestaurantTableClose.emit(true);
  }

  newOrder() {
    localStorage.setItem('tableId', JSON.stringify(this.tableNumber));
    this.router.navigate(['/create-order']);
  }

  editOrder(orderNumber:number){
    this.onEditOrder.emit(orderNumber);
  }

  viewOrderAndBill(orderNumber:number){
    this.onViewOrderAndBill.emit(orderNumber);
  }
}
