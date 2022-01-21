import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { OrderedItem } from 'src/modules/shared/models/orderedItem';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { OrdersService } from '../../service/orders/orders.service';

@Component({
  selector: 'app-accepted-orders',
  templateUrl: './accepted-orders.component.html',
  styleUrls: ['./accepted-orders.component.scss']
})
export class AcceptedOrdersComponent implements OnInit {

  acceptedItems: OrderedItem[] = [];
  note = "";

  constructor(
    private ordersService: OrdersService,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void {
    const loggedUser = localStorage.getItem('currentUser');
    const username = loggedUser?.split('"username":"')[1].split('","')[0]
    const stringUsername = username != null? username : "";
    this.ordersService.getAccepteOdrderedItems(stringUsername,"1" ).subscribe(
      (result) => {
      this.acceptedItems = result;});
    this.ordersService.getNote("1").subscribe((result) => { this.note = result;})
  }

  onAccept(orderedItemId: string) {}

  onFinish(orderedItemId: string) {
    this.ordersService.finishOrderedItem(orderedItemId).subscribe({
      next: (success) => {
        this.toastr.success('Successfully finished ordered item');
        this.filterData();
      },
      error: (error) => {
        this.toastr.error('Unable to finish item');
      }},);
  }

  filterData() {
    this.acceptedItems= this.acceptedItems.filter(
      (item) => item.status === "ACCEPTED"
    );
  }
}
