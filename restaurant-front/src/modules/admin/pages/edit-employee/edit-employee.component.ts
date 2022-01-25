import { Component, Inject, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Select } from 'src/modules/shared/models/select';
import { Employee } from 'src/modules/shared/models/employee';
import { AdminService } from 'src/modules/admin/admin-service/admin.service'

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss'],
})
export class EditEmployeeComponent implements OnInit {
  editEmployeeForm: FormGroup;
  selectedEmployee: Employee;
  isImageSaved: boolean = true;
  url: any;
  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<EditEmployeeComponent>,
    private adminService: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: Employee
  ) {
    this.selectedEmployee = data;
    this.url = this.selectedEmployee.image;
    console.log(this.url)
    this.editEmployeeForm = this.fb.group({
      username: [this.selectedEmployee.username, Validators.required],
      password: [this.selectedEmployee.password, Validators.required],
      name: [this.selectedEmployee.name, Validators.required],
      surname: [this.selectedEmployee.surname, Validators.required],
      image: [this.selectedEmployee.image, Validators.nullValidator],
      telephone: [this.selectedEmployee.telephone, Validators.required]
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
        this.isImageSaved = true;
      };
    }
  }

  saveEmployee() {

    if (
      this.editEmployeeForm.value.username === null ||
      this.editEmployeeForm.value.password === null ||
      this.editEmployeeForm.value.name === null ||
      this.editEmployeeForm.value.surname === null ||
      this.editEmployeeForm.value.telephone === null
    ) {
      this.toastr.error('All fields must be filled in!');
    } else {
        if(this.url != this.selectedEmployee.image){
            console.log(this.url.split(',')[1])
            this.url = this.url.split(',')[1]
        }
      const employee: Employee = {
        username: this.editEmployeeForm.value.username,
        password: this.editEmployeeForm.value.password,
        name: this.editEmployeeForm.value.name,
        surname: this.editEmployeeForm.value.surname,
        telephone: this.editEmployeeForm.value.telephone,
        image: this.url,
        role: ""
      };
      console.log(employee)
      this.adminService.editEmployee(employee).subscribe({
        next: (success) => {
          this.toastr.success(
            'Successfully edited ' + success.username
          );
          this.dialogRef.close();
        },
        error: (error) => {
          this.toastr.error('Unable to edit employee');
          console.log(error);
        },
      });
    }
  }

  cancel() {
    this.dialogRef.close();
  }
}
