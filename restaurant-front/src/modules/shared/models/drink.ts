import { Item } from './item';

export interface Drink extends Item {
  drinkType: string;
  containerType: string;
}
