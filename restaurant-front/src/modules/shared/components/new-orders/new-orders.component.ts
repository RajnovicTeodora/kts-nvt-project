import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
import { OrdersService } from '../../services/orders/orders.service';

@Component({
  selector: 'app-new-orders',
  templateUrl: './new-orders.component.html',
  styleUrls: ['./new-orders.component.scss']
})
export class NewOrdersComponent implements OnInit {

  newItems: OrderedItem[];
  note = "";
  loaded: boolean =false;

  constructor(
    private ordersService: OrdersService,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void { 
    this.ordersService.getNewOrderedItems("1").subscribe((result) => {
      this.setItems(result);
    });
    this.ordersService.getNote("1").subscribe((result) => { this.note = result;})
    console.log(this.note)
  }

  setItems(items: OrderedItem[]){
    this.newItems = items;
    this.loaded = true;
  }

  onAccept(id: string) {
    const loggedUser = localStorage.getItem('currentUser');
    const username = loggedUser?.split('"username":"')[1].split('","')[0]
    const stringUsername = username != null? username : "";
    this.ordersService.acceptOrderedItem(stringUsername, id).subscribe({
    next: (success) => {
      this.toastr.success('Successfully accepted ordered item');
      this.filterData(id);
    },
    error: (error) => {
      console.log(error.error.text);
      if(error.error.text.includes("You accepted order")){
        this.toastr.success(error.error.text);
        this.filterData(id);
        location.reload();
      }else{
        this.toastr.error('Unable to accept item');
      }
    }},);
}
  onFinish(id: string) {}

  filterData(id: string) {
    this.newItems = this.newItems.filter(
      (item) => item.status === "ORDERED" || item.id+"" != id
    );
  }
}
