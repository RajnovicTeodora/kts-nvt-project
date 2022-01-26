import { Injectable } from '@angular/core';
import { Report } from './report';

@Injectable()
export class PaychecksReport extends Report {
  totalPaychecks: number;
}
