import { OrderedItem } from "./ordered-item";

export interface Order {
    orderedItems: OrderedItem[],
    note: string,
    status: boolean
  }