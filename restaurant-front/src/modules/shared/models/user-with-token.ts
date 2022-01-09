export class UserWithToken {

    public token: string;
    public expiresIn: number;
    public username: string;
    public userType: string;
    public loggedInFirstTime : boolean;
    public dontlook : string;

    constructor(token: string, expiresIn: number, username: string, userType: string, loggedInFirstTime : boolean, dontlook : string) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.username = username;
        this.userType = userType;
        this.loggedInFirstTime = loggedInFirstTime;
        this.dontlook = dontlook;
    }

}