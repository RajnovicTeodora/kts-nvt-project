import { Ingredient } from "./ingredient";

export interface DrinkBartender{
    name: string,
    drinkType: string,
    price: number,
    containerType: string,
    image?: string,
    ingredients?: Ingredient[],
    id?:string,
}