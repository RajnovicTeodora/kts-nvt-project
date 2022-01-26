export class Notification {
    
    public id: number;
    public content: string;
    public active: boolean;

    constructor(id:number, content: string, active: boolean) {
        this.id = id;
        this.content = content;
        this.active = active;
    }
}