export class UserWithToken {

    public token: string;
    public expiresIn: number;
    public username: string;
    public userType: string;
    public loggedInFirstTime : boolean;
    public password : string;

    constructor(token: string, expiresIn: number, username: string, userType: string, loggedInFirstTime : boolean, password : string) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.username = username;
        this.userType = userType;
        this.loggedInFirstTime = loggedInFirstTime;
        this.password = password;
    }

}