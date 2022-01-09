import { UserWithToken } from "./user-with-token";

export class UserList{
    public list: Array<UserWithToken>;
    public numberOfUsers: number;

    constructor(list: Array<UserWithToken>) {
        this.list = list;
        this.numberOfUsers = 0;
        for (let rr of list) {
            this.numberOfUsers += 1;
        }
    }

    public addUserToList(user: UserWithToken) {
        this.list.push(user);
        this.numberOfUsers += 1;
    }

    public removeUserFromList(username: string) {
        this.list.forEach((value,index)=>{
            if(value.username==username) this.list.splice(index,1);
        });
    } 
}