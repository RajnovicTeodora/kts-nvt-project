import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
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
  @Input() refreshRequired :boolean;
  @Output() onRestaurantTableClose = new EventEmitter();
  @Output() onViewOrderAndBill = new EventEmitter();
  @Output() refreshFinished = new EventEmitter();
  title: string;
  currentUser: UserWithToken;
  table: RestaurantTable;
  element_data: number[] = [];
  displayedColumns: string[] = [
    'orderNumber',
    'edit',
    'view',
  ];
  dataSource = [...this.element_data];
  @ViewChild('tableorders') matTable: MatTable<number>;

  constructor(
    private observer: BreakpointObserver,
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

  ngOnChanges(changes: SimpleChanges) {
    if(this.refreshRequired){
      this.element_data = [];
      this.dataSource = [];
      this.matTable.renderRows();
      this.getActiveTableOrderNumbers();
      this.refreshFinished.emit(true);
      this.refreshRequired = false;
    }
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
          this.element_data.push(value);
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
    this.router.navigate(['/create-order/'+this.tableNumber]);
  }

  editOrder(orderNumber:number){
    this.orderService
      .checkIfOrderIsPaid(orderNumber)
      .subscribe({
        next: (result) => {
          if(!result){
            this.router.navigate(['/edit-order/'+orderNumber]);
          }else{
            this.toastr.error("Can't edit order that is paid.");
          }
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  viewOrderAndBill(orderNumber:number){
    this.onViewOrderAndBill.emit(orderNumber);
  }
}
