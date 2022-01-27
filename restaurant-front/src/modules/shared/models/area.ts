import { Injectable } from "@angular/core";
import { RestaurantTable } from "./restaurant-table";

@Injectable()
export class Area {
    id: number;
    name: string;
    tables: Array<RestaurantTable>;

    constructor(id: number, name: string, tables: Array<RestaurantTable>) {
        this.id = id;
        this.name = name;
        this.tables = tables;
    }
  }