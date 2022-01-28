import { Injectable } from '@angular/core';
import { MenuItemPrice } from './menu-item-price';

@Injectable()
export class CurrentMenuItemPrice extends MenuItemPrice {
  currentPurchasePrice: number;
  currentPrice: number;
}
