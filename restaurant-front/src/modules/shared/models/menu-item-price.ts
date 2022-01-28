import { MenuItemDTO } from "./menu-item";

export class MenuItemPriceDTO {

    public menuItem : MenuItemDTO ;
    public purchasePrice : number;
    public price : number;
    public active : boolean;
    public approved : boolean;

    constructor(menuItem : MenuItemDTO, purchasePrice : number, price : number, active : boolean, approved : boolean) {
        this.menuItem = menuItem;
        this.purchasePrice = purchasePrice;
        this.price = price;
        this.active = active;
        this.approved = approved;
    }

}