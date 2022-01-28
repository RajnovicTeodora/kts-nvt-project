import { Ingredient } from '../ingredient';

export interface AddChefDish {
  name: string;
  image: string;
  type: string;
  ingredients: Array<Ingredient>;
}
