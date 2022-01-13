import { MenuItemPriceDTO } from "./menu-item-price";


export class SearchResults {

    public list: Array<MenuItemPriceDTO>;

    constructor(list: Array<MenuItemPriceDTO>) {
        this.list = list;
    }

}