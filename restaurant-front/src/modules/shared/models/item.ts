export interface Item {
  id: string;
  name: string;
  image: string;
  ingredients: Array<Item>;
}
