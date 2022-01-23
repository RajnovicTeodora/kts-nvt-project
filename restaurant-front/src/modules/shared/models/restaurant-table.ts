export class RestaurantTableDTO {

    public tableNum: number;
    public x: number;
    public y: number;
    public areaId: number;
    public waiterUsername: string;
    public occupied: boolean;
    public claimed: boolean;

    constructor(tableNum: number, x: number, y: number, areaId: number, waiter: string, occupied: boolean) {
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