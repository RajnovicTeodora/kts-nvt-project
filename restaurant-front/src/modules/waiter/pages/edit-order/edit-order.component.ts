import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { MenuItemWithIngredients } from 'src/modules/shared/models/menu-item-with-ingredients';
import { Order } from 'src/modules/shared/models/order';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
import { OrderService } from 'src/modules/shared/services/order-service/order.service';
import { OrderedItemService } from 'src/modules/shared/services/ordered-item-service/ordered-item.service';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.scss'],
})
export class EditOrderComponent implements OnInit {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  @ViewChild(MatTable) table: MatTable<OrderedItem>;
  totalCost: number;
  showOrderedItemDetails: boolean;
  currentOrderedItemDetails: MenuItemWithIngredients;
  showConfirmAction: boolean;
  showAdditionalNotes: boolean;
  currentAdditionalNotes: string;
  confirmActionTitleDelete: string;
  ELEMENT_DATA: OrderedItem[] = [];
  displayedColumns: string[] = [
    'quantity',
    'name',
    'status',
    'edit',
    'delete',
  ];
  dataSource = [...this.ELEMENT_DATA];
  currentOrder: Order;
  currentOrderedItem: OrderedItem;
  currentMenuItemId: number;

  constructor(
    public router: Router,
    private observer: BreakpointObserver,
    private toastr: ToastrService,
    private orderService: OrderService,
    private orderedItemService: OrderedItemService
  ) {
    this.totalCost = 0;
    this.showOrderedItemDetails = false;
    this.showConfirmAction = false;
    this.confirmActionTitleDelete = '';
    this.currentAdditionalNotes = '';
    this.showAdditionalNotes = false;
  }

  ngOnInit(): void {
    this.getOrder();
  }

  getOrder() {
    const orderNum = new BehaviorSubject<number>(
      JSON.parse(localStorage.getItem('orderNum')!)
    );
    this.orderService.getOrder(orderNum.value).subscribe({
      next: (result) => {
        this.currentOrder = result;
        this.currentAdditionalNotes = result.note;
        this.currentOrder.orderItems.forEach((value, index)=>{
          this.totalCost += value.price * value.quantity;
          this.ELEMENT_DATA.push(value);
          this.dataSource.push(value);
          this.table.renderRows();
        })
      },
      error: (data) => {
        this.toastr.error(data.error);
      },
    });
  }

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  onAddToOrderForwardedClicked(item: MenuItemWithIngredients) {
    this.totalCost += item.price * item.quantity;
    let temp = new OrderedItem(item.priority, item.quantity, item.id, item.ingredients);
    temp.menuItemName = item.name;
    temp.price = item.price;
    temp.id = -1;
    temp.status = "PENDING";

    this.ELEMENT_DATA.push(temp);
    this.dataSource.push(temp);
    this.table.renderRows();
  }

  onCustomizeOrderedItemCloseClicked(item: any) {
    this.showOrderedItemDetails = false;
  }

  viewOrderedItemDetailsAndEdit(element: OrderedItem) {
    this.showOrderedItemDetails = true;
    let oi = new MenuItemWithIngredients(element.id, element.price, element.menuItemName, "", element.activeIngredients);
    this.currentMenuItemId = element.menuItemId;
    oi.priority = element.priority;
    oi.quantity = element.quantity;
    this.currentOrderedItemDetails = oi;
    this.currentOrderedItem = element;
  }

  
  saveChanges() {
    if (this.ELEMENT_DATA.length == 0) {
      this.toastr.error("Can't save order with no ordered items.");
    }
    else {      
      let order = this.currentOrder;
      order.totalPrice = this.totalCost;
      order.note = this.currentAdditionalNotes;
      order.orderItems = this.ELEMENT_DATA;
      
      this.orderService.updateOrder(order).subscribe({
        next: (result) => {
          this.toastr.success(result);    
          this.router.navigate(['/waiter-dashboard']);
        },
        error: (data) => {
          this.toastr.error(data.error);
        },
      });
    }
  }

  cancel() {
    this.router.navigate(['/waiter-dashboard']);
  }

  onEditOrderedItemClicked(item: OrderedItem) {
    this.showOrderedItemDetails = false;
    this.ELEMENT_DATA.forEach((value, index) => {
      if (value == this.currentOrderedItem) {
        this.totalCost -= value.price * value.quantity;
        this.ELEMENT_DATA[index] = item;
        this.totalCost += item.price * item.quantity;
        this.dataSource[index] = item;
      }
    });
    this.table.renderRows();
  }

  onDeleteOrderedItemClicked(item: OrderedItem) {
    this.currentOrderedItem = item;
    this.confirmActionTitleDelete =
      'Are you sure you want to delete ordered ' + item.menuItemName + '?';
    this.showConfirmAction = true;
  }

  onConfirmActionCancelledClicked(item: boolean) {
    this.showConfirmAction = false;
    this.confirmActionTitleDelete = '';
  }

  onConfirmActionConfirmedClicked(item: boolean) {
    this.showConfirmAction = false;
    this.confirmActionTitleDelete = '';
    this.ELEMENT_DATA.forEach((value, index) => {
      if (value == this.currentOrderedItem) {
        this.totalCost -= value.price * value.quantity;
        this.ELEMENT_DATA.splice(index, 1);
        this.dataSource.splice(index, 1);

        if(this.currentOrderedItem.id != -1){
          this.orderedItemService.deleteOrderedItem(this.currentOrderedItem.id).subscribe({
            next: (result) => {
              this.toastr.success(result);            
            },
            error: (data) => {
              this.toastr.error(data.error);
            },
          });
        }
      }
    });
    this.table.renderRows();
  }

  onAdditionalNotesClicked() {
    this.showAdditionalNotes = true;
  }

  onAdditionalNotesCloseClicked(item: boolean) {
    this.showAdditionalNotes = false;
  }

  onAdditionalNotesConfirmClicked(item: string) {
    this.currentAdditionalNotes = item;
    this.showAdditionalNotes = false;
  }
}
