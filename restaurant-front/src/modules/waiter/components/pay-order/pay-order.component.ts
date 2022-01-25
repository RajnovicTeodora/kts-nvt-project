import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
  ElementRef,
} from '@angular/core';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Order } from 'src/modules/shared/models/order';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
import { OrderService } from 'src/modules/shared/services/order-service/order.service';
import { OrderedItemService } from 'src/modules/shared/services/ordered-item-service/ordered-item.service';

@Component({
  selector: 'app-pay-order',
  templateUrl: './pay-order.component.html',
  styleUrls: ['./pay-order.component.scss'],
})
export class PayOrderComponent implements OnInit {
  @Input() currentOrderId: number;
  @Input() showPaymentQuestion: boolean;
  @Output() onPaylater = new EventEmitter();
  @Output() onPayOrderClose = new EventEmitter();
  @ViewChild(MatTable) table: MatTable<OrderedItem>;
  @ViewChild('received') received: ElementRef;
  ELEMENT_DATA: OrderedItem[] = [];
  displayedColumns: string[] = [
    'name',
    'quantity',
    'price',
    'status',
    'deliver',
  ];
  dataSource = [...this.ELEMENT_DATA];
  totalCost: number;
  changeLeft: number;
  orderIsPaid: boolean;

  constructor(
    public router: Router,
    private orderedItemsService: OrderedItemService,
    private orderService: OrderService,
    private toastr: ToastrService
  ) {
    this.totalCost = 0;
  }

  ngOnInit(): void {
    this.getOrderedItems();
    this.checkIfOrderIsPaid();
  }

  getOrderedItems() {
    this.orderedItemsService
      .getOrderedItemsForOrderId(this.currentOrderId)
      .subscribe({
        next: (result) => {
          result.forEach((value )=>{
            this.ELEMENT_DATA.push(value);
            this.dataSource.push(value);
            this.totalCost += value.quantity * value.price;
          });
          this.table.renderRows();
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  checkIfOrderIsPaid(){
    this.orderService
      .checkIfOrderIsPaid(this.currentOrderId)
      .subscribe({
        next: (result) => {
          this.orderIsPaid = result;
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
  }

  receivedAmountChange(){
    if(this.received.nativeElement.value < this.totalCost){
      this.toastr.error("Received amount insufficient, can't pay order.");
    }
    else{
      this.changeLeft = this.received.nativeElement.value - this.totalCost;
    }
    
  }

  payNow() {
    this.showPaymentQuestion = false;
  }

  payLater() {
    this.onPaylater.emit(true);
  }

  finishOrder() {
    if(this.received.nativeElement.value < this.totalCost){
      this.toastr.error("Received amount insufficient, can't pay order.");
    }
    else{
      this.orderService
      .payOrder(this.currentOrderId)
      .subscribe({
        next: (result) => {
          this.toastr.success(result);
          this.onPayOrderClose.emit(true);
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
    }
  }

  close() {
    this.onPayOrderClose.emit(true);
  }

  deliverOrderedItem(element: OrderedItem){}
}
