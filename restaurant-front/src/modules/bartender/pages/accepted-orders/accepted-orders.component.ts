import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderedItem } from 'src/modules/shared/models/orderedItem';
import { OrdersService } from '../../service/orders/orders.service';

@Component({
  selector: 'app-accepted-orders',
  templateUrl: './accepted-orders.component.html',
  styleUrls: ['./accepted-orders.component.scss']
})
export class AcceptedOrdersComponent implements OnInit {

  acceptedItems: OrderedItem[] = [];

  constructor(
    private ordersService: OrdersService,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void {
  }
  onAccept(id: string) {}

  onFinish(id: string) {
    this.ordersService.finishOrderedItem(id).subscribe({
      next: (success) => {
        this.toastr.success('Successfully finished ordered item ' + id);
        this.filterData();
      },
      error: (error) => {
        this.toastr.error('Unable to finish item');
        console.log(error);
      }},);
  
  }//mozda da rename komponentu u items

  filterData() {
    this.acceptedItems= this.acceptedItems.filter(
      (item) => item.status === "ACCEPTED"
    );
  }
}
