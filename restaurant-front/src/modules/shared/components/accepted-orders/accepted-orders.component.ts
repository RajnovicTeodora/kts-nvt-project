import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { OrdersService } from '../../services/orders/orders.service';


@Component({
  selector: 'app-accepted-orders',
  templateUrl: './accepted-orders.component.html',
  styleUrls: ['./accepted-orders.component.scss']
})
export class AcceptedOrdersComponent implements OnInit {

  acceptedItems: OrderedItem[];
  note = "";
  loaded = false;
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
      this.acceptedItems = result;
      this.loaded = true;
    });

    this.ordersService.getNote("1").subscribe((result) => { this.note = result;})
  }

  onAccept(orderedItemId: string) {}

  onFinish(orderedItemId: string) {
    this.ordersService.finishOrderedItem(orderedItemId).subscribe({
      next: (success) => {
        this.toastr.success('Successfully finished ordered item');
        this.filterData(orderedItemId);
      },
      error: (error) => {
        console.log(error.error.text);
      if(error.error.text.includes("You finished order")){
        this.toastr.success(error.error.text);
        this.filterData(orderedItemId);
        location.reload();
      }else{
        this.toastr.error('Unable to accept item');
      }
      }},);
  }

  filterData(id: string) {
    this.acceptedItems= this.acceptedItems.filter(
      (item) => item.status === "ACCEPTED" || item.id+"" != id
    );
  }
}
