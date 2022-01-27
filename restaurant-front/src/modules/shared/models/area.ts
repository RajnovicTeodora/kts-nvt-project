import { Injectable } from "@angular/core";
import { RestaurantTable } from "./restaurant-table";

@Injectable()
export class Area {
    id: number;
    name: string;
    tables: Array<RestaurantTable>;
  }