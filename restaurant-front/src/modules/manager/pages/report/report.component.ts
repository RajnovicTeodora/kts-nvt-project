import { Component, OnInit, ViewChild } from '@angular/core';

import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexTooltip,
  ApexStroke,
} from 'ng-apexcharts';
import { IncomeReport } from 'src/modules/shared/models/report-models/income-report';
import { PaychecksReport } from 'src/modules/shared/models/report-models/paychecks-report';
import { PreparationCostReport } from 'src/modules/shared/models/report-models/preparation-cost-report';
import { Report } from 'src/modules/shared/models/report-models/report';
import { ReportService } from '../../services/report-service/report.service';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  tooltip: ApexTooltip;
  dataLabels: ApexDataLabels;
};

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss'],
})
export class ReportComponent implements OnInit {
  @ViewChild('chart') chart: ChartComponent;
  public chartOptions: Partial<ChartOptions> | any;

  type: string;
  period: string;

  constructor(private reportService: ReportService) {
    this.type = 'incomeAndSold';
    this.period = 'monthly';

    this.chartOptions = {
      colors: ['#f55997', '#424242'],
      chart: {
        height: 350,
        type: 'bar',
        stacked: true,
        toolbar: {
          show: true,
        },
        zoom: {
          enabled: true,
        },
      },
      dataLabels: {
        enabled: true,
      },
      stroke: {
        curve: 'smooth',
      },
    };
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.reportService.getAll(this.period, this.type).subscribe((response) => {
      this.setData(response.body);
    });
  }

  setData(data: Report[]) {
    this.chartOptions.xaxis = {
      tickPlacement: 'on',
      categories: data.map((a) => a.period),
    };

    if (this.type == 'incomeAndSold')
      this.chartOptions.series = [
        {
          name: 'Income',
          data: (data as IncomeReport[]).map((a) => a.earnings),
        },
        {
          name: 'Sold items',
          data: (data as IncomeReport[]).map((a) => a.totalSoldItems),
        },
      ];
    else if (this.type == 'paychecks') {
      this.chartOptions.series = [
        {
          name: 'Paychecks',
          data: (data as PaychecksReport[]).map((a) => a.totalPaychecks),
        },
      ];
    } else {
      this.chartOptions.series = [
        {
          name: 'Preparation cost',
          data: (data as PreparationCostReport[]).map(
            (a) => a.totalPreparationCosts
          ),
        },
      ];
    }
  }

  onChangeTypeClick(type: string) {
    this.type = type;
    this.getAll();
  }

  onChangePeriodClick(period: string) {
    this.period = period;
    this.getAll();
  }
}
