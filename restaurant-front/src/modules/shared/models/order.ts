import { OrderedItem } from "./ordered-item";

export class Order {

    public id : number;
    public isPaid : boolean;
    public totalPrice: number;    
    public note: string;
    public orderItems: Array<OrderedItem>;
    public waiterUsername: string;
    public tableId: number;
    
    constructor( isPaid : boolean, totalPrice: number, note: string, orderItems: Array<OrderedItem>) {
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderItems = orderItems;
    }

}