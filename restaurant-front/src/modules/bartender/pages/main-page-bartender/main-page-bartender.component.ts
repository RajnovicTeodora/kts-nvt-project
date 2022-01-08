import { Component, OnInit } from '@angular/core';

export interface Table{
  number:number;
}
@Component({
  selector: 'app-main-page-bartender',
  templateUrl: './main-page-bartender.component.html',
  styleUrls: ['./main-page-bartender.component.scss']
})
export class MainPageBartenderComponent implements OnInit {

  displayedColumns: string[] = ['number'];
  tableAcepted: Table[] = [{number:1}, {number:2}];
  tableNotAcepted: Table[] = [{number:4}, {number:6}];

  constructor() { }

  ngOnInit(): void {
  }

}
