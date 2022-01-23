import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { RestaurantTableDTO } from 'src/modules/shared/models/restaurant-table';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { RestaurantTableService } from 'src/modules/shared/services/restaurant-table-service/restaurant-table.service';

@Component({
  selector: 'app-table-options',
  templateUrl: './table-options.component.html',
  styleUrls: ['./table-options.component.scss'],
})
export class TableOptionsComponent implements OnInit {
  @Input() tableNumber = 0;
  @Output() onRestaurantTableClose = new EventEmitter();
  title: string;
  currentUser: UserWithToken;
  table: RestaurantTableDTO;

  constructor(
    private tableService: RestaurantTableService,
    public router: Router,
    private toastr: ToastrService
  ) {
    const temp = new BehaviorSubject<UserWithToken>(
      JSON.parse(localStorage.getItem('currentUser')!)
    );
    this.currentUser = temp.value;
    this.table = new RestaurantTableDTO(0, 0, 0, 0, '', false);
    this.title = '';
  }

  ngOnInit(): void {
    this.setTable();
  }

  setTable() {
    this.tableService.getRestaurantTable(this.tableNumber).subscribe({
      next: (result) => {
        this.title = 'Table ' + result.tableNum;
        this.table = new RestaurantTableDTO(
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

  newOrder() {}
}
