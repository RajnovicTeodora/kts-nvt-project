import { MenuItem } from '../menuItem';

export interface UpdateMenuItemPrice {
  menuItemId: string;
  newPrice: number;
  newPurchasePrice: number;
}
