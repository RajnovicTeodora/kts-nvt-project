import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { IngredientsTableComponent } from '../ingredients-table/ingredients-table.component';

@Component({
  selector: 'app-delete-ingredient-dialog',
  templateUrl: './delete-ingredient-dialog.component.html',
  styleUrls: ['./delete-ingredient-dialog.component.scss']
})
export class DeleteIngredientDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<IngredientsTableComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {name:String},)
    { }

  ngOnInit(): void {
  }
  onDeleteClick(): void {
    this.dialogRef.close(true); 
  }

  close(): void {
    this.dialogRef.close(); 
  }

}
