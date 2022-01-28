import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SafeUrl } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';
import { UpdateMenuItemPrice } from 'src/modules/shared/models/menu-models/update-menu-item-price';
import { HelperService } from 'src/modules/shared/services/helper/helper.service';
import { EventEmitter } from '@angular/core';
import { MenuService } from '../../services/menu-service/menu.service';
import { CurrentMenuItemPrice } from 'src/modules/shared/models/menu-models/current-menu-item-prices';

@Component({
  selector: 'app-menu-item-card',
  templateUrl: './menu-item-card.component.html',
  styleUrls: ['./menu-item-card.component.scss'],
})
export class MenuItemCardComponent implements OnInit {
  @Input()
  item: CurrentMenuItemPrice;
  image: SafeUrl;
  priceForm: FormGroup;

  @Output() deleteClicked = new EventEmitter();
  @Output() editrPriceClicked: EventEmitter<UpdateMenuItemPrice> =
    new EventEmitter();
  @Output() toggleIsActiveClicked = new EventEmitter();

  constructor(
    private menuService: MenuService,
    private helperService: HelperService,
    private toastr: ToastrService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.priceForm = this.fb.group({
      purchasePrice: [this.item.purchasePrice, Validators.required],
      price: [this.item.price, Validators.required],
    });
    this.image = this.helperService.bypassUrlSecurity(this.item.menuItem.image);
  }

  onEditPrice() {
    const newPrices: UpdateMenuItemPrice = {
      menuItemId: this.item.menuItem.id,
      newPrice: this.priceForm.value.price,
      newPurchasePrice: this.priceForm.value.purchasePrice,
    };

    this.menuService.updateMenuItemPrice(newPrices).subscribe({
      next: (success) => {
        this.item.price = success.price;
        this.item.purchasePrice = success.purchasePrice;
        this.toastr.success(
          'Successfully updated prices for ' + success.menuItem.name
        );
      },
      error: (error) => {
        this.toastr.error('Unable to update menu item prices');
        console.log(error);
      },
    });
  }

  onDeleteItem() {
    this.deleteClicked.emit(this.item.menuItem.id);
  }

  onTogleIsActive() {
    this.menuService.toggleIsMenuItemActive(this.item.menuItem.id).subscribe({
      next: (success) => {
        this.item.active = success.body.active;
        this.toastr.success(
          'Successfully changed ' + success.body.menuItem.name + ' status'
        );
      },
      error: (error) => {
        this.toastr.error('Unable to change menu item status');
        console.log(error);
      },
    });
  }
}
