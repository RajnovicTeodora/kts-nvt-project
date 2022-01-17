import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-finish-dialog',
  templateUrl: './finish-dialog.component.html',
  styleUrls: ['./finish-dialog.component.scss']
})
export class FinishDialogComponent implements OnInit {
 
  constructor(
    public dialogRef: MatDialogRef<FinishDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: {title:String},

  ) {}

  onYessClick(): void {
    console.log(this.data.title)
    this.dialogRef.close(true); 
  }
  onNoClick(): void {
    this.dialogRef.close(false); 
  }

  ngOnInit(): void {
  }

}
