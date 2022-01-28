import { MenuItemPrice } from './menu-item-price';
import { Injectable } from '@angular/core';

@Injectable()
export class CurrentMenuItemPrice extends MenuItemPrice {
  currentPurchasePrice: number;
  currentPrice: number;
}
