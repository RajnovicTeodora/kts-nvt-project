import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Select } from 'src/modules/shared/models/select';
import { Employee } from 'src/modules/shared/models/employee';
import { AdminService } from 'src/modules/admin/admin-service/admin.service'

@Component({
  selector: 'app-add-area',
  templateUrl: './add-area.component.html',
  styleUrls: ['./add-area.component.scss'],
})
export class AddAreaComponent implements OnInit {
  addAreaForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<AddAreaComponent>,
    private adminService: AdminService
  ) {
    this.addAreaForm = this.fb.group({
      name: [null, Validators.required],
    });
  }

  ngOnInit(): void {}


  saveArea() {

    if (
      this.addAreaForm.value.name === null
    ) {
      this.toastr.error('All fields must be filled in!');
    } else {
      const name = this.addAreaForm.value.name;
      this.adminService.addArea(name).subscribe({
        next: (success) => {
          this.toastr.success(
            'Successfully added ' + success.name
          );
          this.dialogRef.close();
        },
        error: (error) => {
          this.toastr.error('Area already exists!');
          console.log(error);
          this.dialogRef.close();
        },
      });
    }
  }

  cancel() {
    this.dialogRef.close();
  }
}
