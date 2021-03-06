import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Select } from 'src/modules/shared/models/select';
import { Employee } from 'src/modules/shared/models/employee';
import { AdminService } from 'src/modules/admin/admin-service/admin.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss'],
})
export class AddEmployeeComponent implements OnInit {
  addEmployeeForm: FormGroup;
  roles: Select[] = [
    { value: 'MANAGER', viewValue: 'Manager' },
    { value: 'HEAD_CHEF', viewValue: 'Head chef' },
    { value: 'CHEF', viewValue: 'Chef' },
    { value: 'BARTENDER', viewValue: 'Bartender' },
    { value: 'WAITER', viewValue: 'Waiter' },
  ];

  isImageChanged = false;
  url: any;

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<AddEmployeeComponent>,
    private adminService: AdminService
  ) {
    this.addEmployeeForm = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      name: [null, Validators.required],
      surname: [null, Validators.required],
      telephone: [null, Validators.required],
      role: [null, Validators.required],
    });
  }

  ngOnInit(): void {}

  fileChangeEvent(event: any) {
    let reader = new FileReader();
    if (event.target.files && event.target.files.length > 0) {
      let file = event.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.url = reader.result;
        this.isImageChanged = true;
      };
    }
  }

  saveEmployee() {
    let image = "";
    if(this.isImageChanged){
      image = this.url.split(',')[1];
    }
    const employee: Employee = {
      username: this.addEmployeeForm.value.username,
      password: this.addEmployeeForm.value.password,
      name: this.addEmployeeForm.value.name,
      surname: this.addEmployeeForm.value.surname,
      telephone: this.addEmployeeForm.value.telephone,
      image: image,
      role: this.addEmployeeForm.value.role,
    };
    console.log(employee);
    this.adminService.addEmployee(employee).subscribe({
      next: (success) => {
        this.toastr.success('Successfully added ' + success.name);
        this.dialogRef.close();
      },
      error: (error) => {
        this.toastr.error('Unable to add new employee');
        console.log(error);
        this.dialogRef.close();
      },
    });
  }

  cancel() {
    this.dialogRef.close();
  }
}
