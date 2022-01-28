import { Component, OnInit, Output, ViewChild,  EventEmitter} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Order } from '../../models/order';
import { OrdersService } from '../../services/orders/orders.service';

@Component({
  selector: 'app-list-new-orders',
  templateUrl: './list-new-orders.component.html',
  styleUrls: ['./list-new-orders.component.scss']
})
export class ListNewOrdersComponent implements OnInit {

  @Output() onClickView = new EventEmitter();
  dataSource: MatTableDataSource<Order>;
  observable: Observable<any>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private ordersService: OrdersService,
  ) { }

  ngOnInit(): void {
    this.ordersService.getNewOrders().subscribe((result) => {
      console.log(result)
      this.setData(result);
    })
  }
  setData(data: Order[]) {
    console.log(data)
    this.dataSource = new MatTableDataSource<Order>(data);
    this.dataSource.paginator = this.paginator;
    this.observable = this.dataSource.connect();
  }
  onClick(id:number){
    this.onClickView.emit(id); 
  }

}
