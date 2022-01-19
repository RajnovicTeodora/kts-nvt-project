import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Ingredient } from '../../models/ingredient';
import { InputIngredientDialogComponent } from '../input-ingredient-dialog/input-ingredient-dialog.component';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-ingredients-table',
  templateUrl: './ingredients-table.component.html',
  styleUrls: ['./ingredients-table.component.scss']
})
export class IngredientsTableComponent implements OnInit {

  @Output() addedIngredient = new EventEmitter();
  @Input() items:  Ingredient[] = [];
  displayedColumns: string[] = ['name', "isAlergen","delete"];
  //items: Ingredient[] = [];
  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openDialog(): void {

    const dialogRef = this.dialog.open(InputIngredientDialogComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
      const newIngredient = result;
      //this.items.push(newIngredient);
      this.addedIngredient.emit(newIngredient);
      }
    });
  }
}
