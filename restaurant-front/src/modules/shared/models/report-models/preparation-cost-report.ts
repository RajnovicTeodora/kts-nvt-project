import { Injectable } from '@angular/core';
import { Report } from './report';

@Injectable()
export class PreparationCostReport extends Report {
  totalPreparationCosts: number;
}
