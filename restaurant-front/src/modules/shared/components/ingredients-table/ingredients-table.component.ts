import { Component, OnInit } from '@angular/core';
import { Ingredient } from '../../models/ingredient';
import { InputIngredientDialogComponent } from '../input-ingredient-dialog/input-ingredient-dialog.component';
import { MatDialog } from '@angular/material/dialog';

// const ELEMENT_DATA: Ingredient[] = [
//   {name: "Ing2", isAlergen: true},
//   {name: "Ing1", isAlergen: false},
// ];
@Component({
  selector: 'app-ingredients-table',
  templateUrl: './ingredients-table.component.html',
  styleUrls: ['./ingredients-table.component.scss']
})
export class IngredientsTableComponent implements OnInit {

  displayedColumns: string[] = ['name', "isAlergen","delete"];
  items: Ingredient[] = [];
  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openDialog(): void {

    const dialogRef = this.dialog.open(InputIngredientDialogComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => { //todo refresh, i da nekako vratim gore
      if(result){
      const newIngredient = result;
      this.items.push(newIngredient);
      console.log(result);
      console.log(this.items);}
  
    });
  }
}
