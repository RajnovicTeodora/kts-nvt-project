import { Injectable } from '@angular/core';
import { Report } from './report';

@Injectable()
export class IncomeReport extends Report {
  earnings: number;
  totalSoldItems: number;
}
