import { Injectable } from "@angular/core";

@Injectable()
export class Employee {
    public username: string;
    public password: string;
    public name: string;
    public surname: string;
    public image: string;
    public telephone: string;
    public role: string;

    constructor(username: string, password: string, name: string, surname: string, image: string, telephone: string, role: string) {
       this.username = username;
       this.password = password;
       this.name = name;
       this.surname = name;
       this.image = image;
       this.telephone = telephone;
       this.role = role;
    }
}
