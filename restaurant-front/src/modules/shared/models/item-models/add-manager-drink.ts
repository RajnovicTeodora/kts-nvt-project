import { Ingredient } from '../ingredient';

export interface AddManagerDrink {
  name: string;
  image: string;
  drinkType: string;
  containerType: string;
  ingredients: Array<Ingredient>;
}
