import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderedItem } from 'src/modules/shared/models/orderedItem';
import { OrdersService } from '../../service/orders/orders.service';

@Component({
  selector: 'app-new-orders',
  templateUrl: './new-orders.component.html',
  styleUrls: ['./new-orders.component.scss']
})
export class NewOrdersComponent implements OnInit {

  newItems: OrderedItem[] = [];

  constructor(
    private ordersService: OrdersService,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void {
  }

  onAccept(id: string) {
    this.ordersService.acceptOrderedItem(id).subscribe({
    next: (success) => {
      this.toastr.success('Successfully accepted ordered item ' + id);
      this.filterData();
    },
    error: (error) => {
      this.toastr.error('Unable to accept item');
      console.log(error);
    }},);
}
  onFinish(id: string) {}

  filterData() {//uradi get ili promeni item,, razmisli
    this.newItems= this.newItems.filter(
      (item) => item.status === "ORDERED"
    );
  }
}
