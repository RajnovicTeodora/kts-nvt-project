import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Select } from 'src/modules/shared/models/select';
import { UserWithPaycheck } from 'src/modules/shared/models/paycheck-models/user-with-paycheck';
import { PaycheckService } from '../../services/paycheck-service/paycheck.service';
import { EditPaycheckDialogComponent } from '../edit-paycheck-dialog/edit-paycheck-dialog.component';
import { EditPaycheck } from 'src/modules/shared/models/paycheck-models/edit-paycheck';

@Component({
  selector: 'app-paycheck-table',
  templateUrl: './paycheck-table.component.html',
  styleUrls: ['./paycheck-table.component.scss'],
})
export class PaycheckTableComponent implements OnInit {
  dataSource: MatTableDataSource<UserWithPaycheck>;
  searchForm: FormGroup;
  searchSting: string;
  filterString: string;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  displayedColumns: string[] = [
    'Username',
    'Name',
    'Surname',
    'Role',
    'Paycheck',
    'Edit paycheck',
  ];

  roles: Select[] = [
    { value: '', viewValue: 'All' },
    { value: 'MANAGER', viewValue: 'Manager' },
    { value: 'HEAD_CHEF', viewValue: 'Head chef' },
    { value: 'CHEF', viewValue: 'Chef' },
    { value: 'BARTENDER', viewValue: 'Bartender' },
    { value: 'WAITER', viewValue: 'Waiter' },
  ];

  constructor(
    private paycheckService: PaycheckService,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private liveAnnouncer: LiveAnnouncer,
    private dialog: MatDialog
  ) {
    this.searchForm = this.fb.group({
      search: [null],
      filter: [null],
    });
  }

  ngOnInit(): void {
    this.paycheckService.getAll('', '').subscribe((response) => {
      this.setData(response.body);
    });
  }

  setData(data: UserWithPaycheck[]) {
    this.dataSource = new MatTableDataSource<UserWithPaycheck>(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  search() {
    this.searchSting =
      this.searchForm.value.search != null ? this.searchForm.value.search : '';
    this.filterString =
      this.searchForm.get('filter')?.value != null
        ? this.searchForm.value.filter
        : '';

    this.paycheckService
      .getAll(this.searchSting, this.filterString)
      .subscribe((response) => {
        this.dataSource.data = response.body;
      });
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  openDialog(element: any) {
    const dialogRef = this.dialog.open(EditPaycheckDialogComponent, {
      width: '300px',
      data: element.paycheck,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === undefined) {
        return;
      }

      this.dataSource.data = this.dataSource.data.filter((value) => {
        if (value.username == element.username) {
          value.paycheck = result.data;
        }
        return true;
      });

      const editPaycheck: EditPaycheck = {
        username: element.username,
        newSalary: result.data,
      };

      this.paycheckService.updatePaycheck(editPaycheck).subscribe({
        next: (success) => {
          this.toastr.success(
            'Successfully edited ' + element.username + "'s salary"
          );
        },
        error: (error) => {
          this.toastr.error('Unable to edit user paycheck');
        },
      });
    });
  }
}
