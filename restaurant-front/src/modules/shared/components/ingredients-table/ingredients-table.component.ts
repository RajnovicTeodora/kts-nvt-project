import { Component, OnInit } from '@angular/core';
import { Ingredient } from '../../models/ingredient';


const ELEMENT_DATA: Ingredient[] = [
  {name: "Ing2", isAlergen: true},
  {name: "Ing1", isAlergen: false},
];
@Component({
  selector: 'app-ingredients-table',
  templateUrl: './ingredients-table.component.html',
  styleUrls: ['./ingredients-table.component.scss']
})
export class IngredientsTableComponent implements OnInit {

  displayedColumns: string[] = ['name', "isAlergen","delete"];
  items: Ingredient[] = ELEMENT_DATA;
  constructor() { }

  ngOnInit(): void {
  }

  openDialog(): void {}
}
