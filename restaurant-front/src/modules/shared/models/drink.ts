import { Ingredient } from "./ingredient";

export interface Drink {
    name: string,
    drinkType: string,
    price: number,
    containerType: string,
    image?: string,
    ingredients?: Ingredient[]
}