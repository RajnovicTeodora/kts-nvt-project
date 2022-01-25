import { Ingredient } from "./ingredient";

export class OrderedItem {

    public id : number;
    public status : string;
    public priority: number;    
    public quantity: number;
    public menuItemId: number;
    public activeIngredients: Array<Ingredient>;
    public menuItemName: string;
    public price: number;
    
    constructor( priority: number, quantity: number, menuItemId: number, activeIngredients: Array<Ingredient>) {
        this.priority = priority;
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.activeIngredients = activeIngredients;
    }

}