import { Injectable } from "@angular/core";

@Injectable()
export class RestaurantTable {

    public id: number;
    public tableNum: number;
    public x: number;
    public y: number;
    public areaId: number;
    public waiterUsername: string;
    public occupied: boolean;
    public claimed: boolean;

    constructor(tableNum: number, x: number, y: number, areaId: number, waiter: string, occupied: boolean) {
        this.id = 0;
        this.tableNum = tableNum;
        this.x = x;
        this.y = y;
        this.areaId = areaId;
        this.waiterUsername = waiter;
        this.occupied = occupied;
        if(waiter == ""){
            this.claimed = false;
        }else
            this.claimed = true;
    }

}