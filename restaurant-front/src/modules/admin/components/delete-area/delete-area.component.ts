import { Component, Inject, OnInit, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from 'src/modules/admin/admin-service/admin.service'

@Component({
  selector: 'app-delete-area',
  templateUrl: './delete-area.component.html',
  styleUrls: ['./delete-area.component.scss'],
})
export class DeleteAreaComponent implements OnInit {
  name: string;
  id: number;
  constructor(
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<DeleteAreaComponent>,
    private adminService: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.name = data.name;
    this.id = data.id;
  }

  ngOnInit(): void {}

  deleteArea() {
      console.log(this.id)
      this.adminService.deleteArea(this.id).subscribe({
        next: (success) => {
          this.toastr.success(
            'Successfully deleted area' + success.name
          );
          this.dialogRef.close();
        },
        error: (error) => {
          this.toastr.error('Unable to delete area');
          console.log(error);
        },
      });
  }

  cancel() {
    this.dialogRef.close();
  }
}
