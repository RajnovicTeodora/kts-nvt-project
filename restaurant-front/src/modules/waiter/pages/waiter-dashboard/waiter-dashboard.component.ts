import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-waiter-dashboard',
  templateUrl: './waiter-dashboard.component.html',
  styleUrls: ['./waiter-dashboard.component.scss'],
})
export class WaiterDashboardComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  pageSize: number;
  currentPage: number;
  data : any[];
  imagePath : string;

  data2 = [ 
    {id:1, url:
    'assets/images/floor3.png'},
    {id:2, url:
    'assets/images/floor2.png'}
  ];

  constructor(private observer: BreakpointObserver, public router: Router) {
    this.pageSize = 1;
    this.currentPage = 0;
    this.data = [];
    this.imagePath = 'assets/images/floor3.png';
  }

  ngOnInit() {
    this.getData({pageIndex: this.currentPage, pageSize: this.pageSize});
  }

  getData(obj: { pageIndex: any; pageSize: any; }) {
    let index=0,
        startingIndex=obj.pageIndex * obj.pageSize,
        endingIndex=startingIndex + obj.pageSize;
    
    this.data = this.data2.filter(() => {
      index++;
      return (index > startingIndex && index <= endingIndex) ? true : false;
    });
  }

  ngAfterViewInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      } else {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      }
    });
  }

  changeAccount(){}
}
