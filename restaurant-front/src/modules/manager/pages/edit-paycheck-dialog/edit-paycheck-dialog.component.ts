import { Component, Inject, Input, OnInit, Optional } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-paycheck-dialog',
  templateUrl: './edit-paycheck-dialog.component.html',
  styleUrls: ['./edit-paycheck-dialog.component.scss'],
})
export class EditPaycheckDialogComponent implements OnInit {
  editPaycheckForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditPaycheckDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: number
  ) {
    this.editPaycheckForm = this.fb.group({
      paycheck: [this.data, Validators.required],
    });
  }

  ngOnInit(): void {}

  save() {
    this.dialogRef.close({ data: this.editPaycheckForm.value.paycheck });
  }

  cancel() {
    this.dialogRef.close();
  }
}
