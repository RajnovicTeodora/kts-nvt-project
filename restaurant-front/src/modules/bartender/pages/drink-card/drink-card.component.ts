import { Component, OnInit } from '@angular/core';
import {MatTableModule} from "@angular/material/table"
import { Drink } from 'src/modules/shared/models/drink';
import { DrinkType } from 'src/modules/shared/models/drink-type';


const ELEMENT_DATA: Drink[] = [
  {name: "blue lagun", type: "a", price: 1000},
  {name: "blue lagun2",  type: "b", price: 1000}
];

@Component({
  selector: 'app-drink-card',
  templateUrl: './drink-card.component.html',
  styleUrls: ['./drink-card.component.scss']
})
export class DrinkCardComponent implements OnInit {

  displayedColumns: string[] = ['name', "type","price"];
  items: Drink[] =  [
    {name: "blue lagun", type: "a", price: 1000},
    {name: "blue lagun2",  type: "b", price: 1000}
  ];

  
  drinkTypes: DrinkType[] = [
      {value: 'coffee', viewValue: 'coffee'},
      {value: 'cold drink', viewValue: 'cold drink'},
      {value: 'hot drink', viewValue: 'Hot drink'},
      {value: 'alcoholic', viewValue: 'Alcoholic'},
  ];
  constructor() {}

  ngOnInit(): void {
  }

}
