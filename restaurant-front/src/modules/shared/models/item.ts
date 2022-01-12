export interface Item {
  id: number;
  name: string;
  image: string;
  ingredients: Array<Item>;
}
