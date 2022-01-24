import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { PeriodicElement } from '../../models/PeriodicElement';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.scss'],
})
export class CreateOrderComponent implements OnInit {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  totalCost: number;

  ELEMENT_DATA: PeriodicElement[] = [
    {
      quantity: 1,
      name: 'Shwarma',
    },
    { quantity: 2, name: 'Pizza' },
    { quantity: 3, name: 'Soup' },
    { quantity: 4, name: 'Soufle' },
    { quantity: 5, name: 'Cake' },
    { quantity: 6, name: 'Chicken' },
    { quantity: 7, name: 'Nitrogen' },
    { quantity: 8, name: 'Oxygen' },
    { quantity: 9, name: 'Fluorine' },
    { quantity: 10, name: 'Neon' },
    { quantity: 10, name: 'Neon' },
    { quantity: 10, name: 'Neon' },
    { quantity: 10, name: 'Neon' },
    { quantity: 10, name: 'Neon' },
    { quantity: 10, name: 'Neon' },
    { quantity: 10, name: 'Neon' },
  ];

  displayedColumns: string[] = ['quantity', 'name', 'details'];
  dataSource = [...this.ELEMENT_DATA];

  @ViewChild(MatTable) table: MatTable<PeriodicElement>;

  constructor(public router: Router, private observer: BreakpointObserver) {
    this.totalCost = 0;
  }

  ngOnInit(): void {}

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

  viewOrderedItemDetails(element: PeriodicElement) {}

  confirm() {}

  cancel() {}
}
