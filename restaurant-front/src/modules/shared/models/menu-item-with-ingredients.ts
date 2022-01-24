import { Ingredient } from "./ingredient";

export class MenuItemWithIngredients {

    public id : number;
    public price: number;
    public name : string;
    public type : string;
    public ingredients: Array<Ingredient>;
    public priority: number;
    public container: string;
    
    constructor(id : number, price: number, name : string, type : string, ingredients: Array<Ingredient>) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
    }

}