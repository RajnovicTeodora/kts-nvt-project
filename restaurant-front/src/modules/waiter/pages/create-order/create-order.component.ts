import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { MenuItemWithIngredients } from 'src/modules/shared/models/menu-item-with-ingredients';
import { Order } from 'src/modules/shared/models/order';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { OrderService } from 'src/modules/shared/services/order-service/order.service';
import { RestaurantTableService } from 'src/modules/shared/services/restaurant-table-service/restaurant-table.service';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.scss'],
})
export class CreateOrderComponent implements OnInit {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  @ViewChild(MatTable) table: MatTable<MenuItemWithIngredients>;
  totalCost: number;
  showOrderedItemDetails: boolean;
  currentOrderedItemDetails: MenuItemWithIngredients;
  showConfirmAction: boolean;
  showAdditionalNotes: boolean;
  showPaymentModal: boolean;
  currentAdditionalNotes: string;
  confirmActionTitleDelete: string;
  element_data: MenuItemWithIngredients[] = [];
  displayedColumns: string[] = ['quantity', 'name', 'details', 'delete'];
  dataSource = [...this.element_data];
  createdOrderId: number;

  constructor(
    public router: Router,
    private observer: BreakpointObserver,
    private toastr: ToastrService,
    private orderService: OrderService,
    private activatedRoute: ActivatedRoute,
    private restaurantTableService: RestaurantTableService
  ) {
    this.totalCost = 0;
    this.showOrderedItemDetails = false;
    this.showConfirmAction = false;
    this.confirmActionTitleDelete = '';
    this.currentAdditionalNotes = '';
    this.showAdditionalNotes = false;
  }

  ngOnInit(): void {}

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
    this.element_data.push(item);
    this.dataSource.push(item);
    this.table.renderRows();
  }

  onCustomizeOrderedItemCloseClicked(item: any) {
    this.showOrderedItemDetails = false;
  }

  viewOrderedItemDetails(element: MenuItemWithIngredients) {
    this.showOrderedItemDetails = true;
    this.currentOrderedItemDetails = element;
  }

  onPaylaterClicked(item: boolean) {
    this.showPaymentModal = false;
    this.router.navigate(['/waiter-dashboard']);
  }

  confirm() {
    if (this.element_data.length == 0) {
      this.toastr.error("Can't create order with no ordered items.");
    } else {
      let orderItems = Array<OrderedItem>();
      this.element_data.forEach((value, index) => {
        let orderitem = new OrderedItem(
          value.priority,
          value.quantity,
          value.id,
          value.ingredients
        );
        orderItems.push(orderitem);
      });

      let order = new Order(
        false,
        this.totalCost,
        this.currentAdditionalNotes,
        orderItems
      );
      const temp = new BehaviorSubject<UserWithToken>(
        JSON.parse(localStorage.getItem('currentUser')!)
      );
      let tableNum = this.activatedRoute.snapshot.paramMap.get('parameter');
      order.waiterUsername = temp.value.username;
      this.restaurantTableService
        .getRestaurantTableId(Number(tableNum))
        .subscribe({
          next: (result) => {
            order.tableId = result;
            this.orderService.createOrder(order).subscribe({
              next: (result) => {
                this.toastr.success('Succesfully added new order.');
                this.createdOrderId = result;
                this.showPaymentModal = true;
              },
              error: (data) => {
                this.toastr.error(data.error);
              },
            });
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

  onEditOrderedItemClicked(item: MenuItemWithIngredients) {
    this.showOrderedItemDetails = false;
    this.element_data.forEach((value, index) => {
      if (value == this.currentOrderedItemDetails) {
        this.totalCost -= value.price * value.quantity;
        this.element_data[index] = item;
        this.totalCost += item.price * item.quantity;
        this.dataSource[index] = item;
      }
    });
    this.table.renderRows();
  }

  onDeleteOrderedItemClicked(item: MenuItemWithIngredients) {
    this.currentOrderedItemDetails = item;
    this.confirmActionTitleDelete =
      'Are you sure you want to delete ordered ' + item.name + '?';
    this.showConfirmAction = true;
  }

  onConfirmActionCancelledClicked(item: boolean) {
    this.showConfirmAction = false;
    this.confirmActionTitleDelete = '';
  }

  onConfirmActionConfirmedClicked(item: boolean) {
    this.showConfirmAction = false;
    this.confirmActionTitleDelete = '';
    this.element_data.forEach((value, index) => {
      if (value == this.currentOrderedItemDetails) {
        this.totalCost -= value.price * value.quantity;
        this.element_data.splice(index, 1);
        this.dataSource.splice(index, 1);
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

  onPayOrderCloseClicked(item: boolean) {
    this.showPaymentModal = false;
    this.router.navigate(['/waiter-dashboard']);
  }
}
