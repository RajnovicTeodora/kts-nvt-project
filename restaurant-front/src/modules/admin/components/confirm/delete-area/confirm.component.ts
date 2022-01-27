import { Component, Inject, OnInit, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss'],
})
export class ConfirmComponent implements OnInit {
  message: number;
  constructor(
    private dialogRef: MatDialogRef<ConfirmComponent>,
    public router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.message = data.message;
  }

  ngOnInit(): void {}

  return() {
    this.dialogRef.close();
    this.router.navigate(['/admin-dashboard']);
  }

  cancel() {
    this.dialogRef.close();
  }
}
