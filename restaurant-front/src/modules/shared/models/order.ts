import { OrderedItem } from "./orderedItem";

export interface Order {
    orderedItems: OrderedItem[],
    note: string,
    status: boolean
  }