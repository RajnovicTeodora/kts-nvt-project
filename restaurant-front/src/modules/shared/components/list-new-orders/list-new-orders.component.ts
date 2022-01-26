import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Order } from '../../models/order';

@Component({
  selector: 'app-list-new-orders',
  templateUrl: './list-new-orders.component.html',
  styleUrls: ['./list-new-orders.component.scss']
})
export class ListNewOrdersComponent implements OnInit {

  dataSource: MatTableDataSource<Order>;
  observable: Observable<any>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor() { }

  ngOnInit(): void {
  }
  setData(data: Order[]) {
    this.dataSource = new MatTableDataSource<Order>(data);
    this.dataSource.paginator = this.paginator;
    this.observable = this.dataSource.connect();
  }
  onClick(id:string){}

}
