import { Ingredient } from '../ingredient';

export interface AddManagerDrink {
  name: string;
  image: string;
  type: string;
  containerType: string;
  ingredients: Array<Ingredient>;
}
