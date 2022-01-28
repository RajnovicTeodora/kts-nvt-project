import { UserWithToken } from "./user-with-token";

export class UserWithBadgeNum{
    public badgeNum : number;
    public user: UserWithToken;
    
    constructor(badgeNum : number, user : UserWithToken){
        this.badgeNum = badgeNum;
        this.user = user;
    }

}