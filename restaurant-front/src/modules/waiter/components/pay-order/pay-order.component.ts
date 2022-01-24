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

  constructor(
    public router: Router,
    private orderedItemaService: OrderedItemService,
    private toastr: ToastrService
  ) {
    this.totalCost = 0;
    this.changeLeft = 0;
  }

  ngOnInit(): void {
    this.getOrderedItems();
  }

  getOrderedItems() {
    this.orderedItemaService
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

  receivedAmountChange(){
    this.changeLeft = this.totalCost - this.received.nativeElement.value;
  }

  payNow() {
    this.showPaymentQuestion = false;
  }

  payLater() {
    this.onPaylater.emit(true);
  }

  finishOrder() {}

  close() {}

  deliverOrderedItem(element: OrderedItem){}
}
