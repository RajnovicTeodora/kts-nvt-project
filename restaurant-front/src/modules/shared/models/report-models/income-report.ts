import { Report } from './report';

export interface IncomeReport extends Report {
  earnings: number;
  totalSoldItems: number;
}
