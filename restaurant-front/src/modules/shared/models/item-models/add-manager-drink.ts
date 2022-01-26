import { Ingredient } from '../ingredient';
import { Injectable } from '@angular/core';

@Injectable()
export class AddManagerDrink {
  name: string;
  image: string;
  drinkType: string;
  containerType: string;
  ingredients: Array<Ingredient>;
}
