import { MenuItem } from '../menuItem';
import { Injectable } from '@angular/core';

@Injectable()
export class MenuItemPrice {
  menuItem: MenuItem;
  purchasePrice: number;
  price: number;
  active: boolean;
  approved: boolean;
}
