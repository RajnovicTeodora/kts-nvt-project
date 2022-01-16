import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { IngredientsTableComponent } from '../ingredients-table/ingredients-table.component';
import { Ingredient } from '../../models/ingredient';
@Component({
  selector: 'app-input-ingredient-dialog',
  templateUrl: './input-ingredient-dialog.component.html',
  styleUrls: ['./input-ingredient-dialog.component.scss']
})
export class InputIngredientDialogComponent implements OnInit {

  name: string = "";
  isAlergen: string = "";
  constructor(
    public dialogRef: MatDialogRef<IngredientsTableComponent>,
    private toastr: ToastrService,) {}

  ngOnInit(): void {
  }
  onSaveClick(): void {
    if(this.name === "" || this.isAlergen ===""){
      this.toastr.error("All fields must be filled in!");
    }else{
    const newIngredient: Ingredient = {name: this.name, isAlergen: this.isAlergen === "1" ? true :false}
    this.dialogRef.close(newIngredient); }
  }

  close(): void {
    this.dialogRef.close(); 
  }

}
