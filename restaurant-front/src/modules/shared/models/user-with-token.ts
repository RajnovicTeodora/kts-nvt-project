export class UserWithToken {

    public token: string;
    public expiresIn: number;
    public username: string;
    public userType: string;
    public expirationDate: Date;
    public loggedInFirstTime : boolean;

    constructor(token: string, expiresIn: number, username: string, userType: string, loggedInFirstTime : boolean) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.username = username;
        this.userType = userType;
        this.expirationDate = new Date(new Date().getTime() + expiresIn * 1000);
        this.loggedInFirstTime = loggedInFirstTime;
    }

    setLoggedFirstTimeFalse(){
        this.loggedInFirstTime = false;
    }

}