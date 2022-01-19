import { Ingredient } from "./ingredient";

export interface Drink2{
    name: string,
    drinkType: string,
    price: number,
    containerType: string,
    image?: string,
    ingredients?: Ingredient[]
}