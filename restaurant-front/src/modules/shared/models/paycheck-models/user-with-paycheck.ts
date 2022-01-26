import { Injectable } from '@angular/core';

@Injectable()
export class UserWithPaycheck {
  username: string;
  name: string;
  surname: string;
  role: string;
  paycheck: number;
}
