import { Component, Inject, OnInit, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from 'src/modules/admin/admin-service/admin.service'

@Component({
  selector: 'app-delete-employee',
  templateUrl: './delete-employee.component.html',
  styleUrls: ['./delete-employee.component.scss'],
})
export class DeleteEmployeeComponent implements OnInit {
  username: string;
  constructor(
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<DeleteEmployeeComponent>,
    private adminService: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.username = data.username;
  }

  ngOnInit(): void {}

  deleteEmployee() {

      console.log(this.username)
      this.adminService.deleteEmployee(this.username).subscribe({
        next: (success) => {
          this.toastr.success(
            'Successfully deleted ' + success.username
          );
          this.dialogRef.close();
        },
        error: (error) => {
          this.toastr.error('Unable to delete employee');
          console.log(error);
        },
      });
  }

  cancel() {
    this.dialogRef.close();
  }
}
