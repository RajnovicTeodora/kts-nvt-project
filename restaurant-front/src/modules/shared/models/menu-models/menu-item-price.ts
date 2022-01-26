import { MenuItem } from '../menuItem';

export interface MenuItemPrice {
  menuItem: MenuItem;
  purchasePrice: number;
  price: number;
  active: boolean;
  approved: boolean;
}
