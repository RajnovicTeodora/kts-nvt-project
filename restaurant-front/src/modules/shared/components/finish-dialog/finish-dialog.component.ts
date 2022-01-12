import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-finish-dialog',
  templateUrl: './finish-dialog.component.html',
  styleUrls: ['./finish-dialog.component.scss']
})
export class FinishDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<FinishDialogComponent>,

  ) {}

  onNoClick(): void {
    this.dialogRef.close(true); //ovde vacam da li je tru ili false
  }

  ngOnInit(): void {
  }

}
