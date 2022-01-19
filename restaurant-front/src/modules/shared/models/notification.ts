export class NotificationDTO {

    public content: string;
    public isActive: boolean;
    public orderedItemId: string;

    constructor(content: string, isActive: boolean, orderedItemId: string) {
        this.content = content;
        this.isActive = isActive;
        this.orderedItemId = orderedItemId;
    }

}