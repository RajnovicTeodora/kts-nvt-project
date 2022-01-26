import { Injectable } from '@angular/core';

@Injectable()
export class UpdateMenuItemPrice {
  menuItemId: string;
  newPrice: number;
  newPurchasePrice: number;
}
