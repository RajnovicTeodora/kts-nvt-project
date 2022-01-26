import { Component, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Order } from '../../models/order';
import { OrdersService } from '../../services/orders/orders.service';

@Component({
  selector: 'app-list-accepted-orders',
  templateUrl: './list-accepted-orders.component.html',
  styleUrls: ['./list-accepted-orders.component.scss']
})
export class ListAcceptedOrdersComponent implements OnInit {
  @Output() onClickView = new EventEmitter();
  dataSource: MatTableDataSource<Order>;
  observable: Observable<any>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private ordersService: OrdersService,
  ) { }

  ngOnInit(): void {
    const loggedUser = localStorage.getItem('currentUser');
    const username = loggedUser?.split('"username":"')[1].split('","')[0]
    const stringUsername = username != null? username : "";
    this.ordersService.getAcceptedOrders(stringUsername).subscribe((result) => {
      
      this.setData(result);
    })
  }
  setData(data: Order[]) {
    this.dataSource = new MatTableDataSource<Order>(data);
    this.dataSource.paginator = this.paginator;
    this.observable = this.dataSource.connect();
  }
  onClick(id:number){
    this.onClickView.emit(id); 
  }
}
