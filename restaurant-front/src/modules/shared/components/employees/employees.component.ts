import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { UserService } from '../../services/user-service/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { AdminService } from 'src/modules/admin/admin-service/admin.service';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Select } from 'src/modules/shared/models/select';
import { UserWithPaycheck } from 'src/modules/shared/models/paycheck-models/user-with-paycheck';
import { PaycheckService } from 'src/modules/manager/services/paycheck-service/paycheck.service';  
import { EditPaycheckDialogComponent } from 'src/modules/manager/components/edit-paycheck-dialog/edit-paycheck-dialog.component';  
import { EditPaycheck } from 'src/modules/shared/models/paycheck-models/edit-paycheck';
import { AddEmployeeComponent } from 'src/modules/admin/pages/add-employee/add-employee.component';
import { EditEmployeeComponent } from 'src/modules/admin/pages/edit-employee/edit-employee.component';
import { Employee } from '../../models/employee';
import { DeleteEmployeeComponent } from 'src/modules/admin/components/delete-employee/delete-employee.component';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss']
})
export class EmployeesComponent implements OnInit {
  data: any[];
  showModalLogout: boolean;
  employeesWindowOpen: boolean;
  tablesWindowOpen: boolean;
  user: UserWithToken;
  dataSource: MatTableDataSource<any>;
  searchForm: FormGroup;
  searchSting: string;
  filterString: string;
  enableEdit = false;
  enableEditIndex = null;
 
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  displayedColumns: string[] = [
    'Username',
    'Name',
    'Surname',
    'Role',
    'Telephone'
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
    private fb: FormBuilder,
    private paycheckService: PaycheckService,
    private observer: BreakpointObserver,
    public router: Router,
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private userService: UserService,
    private adminService: AdminService,
    private dialog: MatDialog
  ) {
    this.data = [];
    this.searchForm = this.fb.group({
      search: [null],
      filter: [null],
    });
    const temp = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
    this.user = temp.value;
   }

  ngOnInit(): void {
    if (this.user.userType === "ADMIN"){
      this.adminService.getAllEmployees("", "").subscribe((response) => {
        this.setData(response.body);
      });
      this.displayedColumns.push('Edit employee');
      this.displayedColumns.push('Delete');
    }
    if(this.user.userType === "MANAGER"){
      this.paycheckService.getAll('', '').subscribe((response) => {
        this.setData(response.body);
      });
      this.displayedColumns.push('Paycheck');
      this.displayedColumns.push('Edit paycheck');
    }
  }

  setData(data: any[]) {
    this.dataSource = new MatTableDataSource<any>(data);
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

    if (this.user.userType === "MANAGER"){
      this.paycheckService
      .getAll(this.searchSting, this.filterString)
      .subscribe((response) => {
        this.dataSource.data = response.body;
      });
    }

    if(this.user.userType === "ADMIN"){
      this.adminService.getAllEmployees(this.searchSting, this.filterString).subscribe((response) => {
        this.dataSource.data = response.body;
      });
    }
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

  
  openAddEmployeeDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    dialogConfig.height = '80%';

    const dialogRef = this.dialog.open(AddEmployeeComponent,  dialogConfig);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.adminService.getAllEmployees("", "").subscribe((response) => {
        this.setData(response.body);
      });
    });
  }

  openEditEmployeeDialog(element: Employee) {

    const dialogRef = this.dialog.open(EditEmployeeComponent,  {
      disableClose: true,
      autoFocus: true,
      width: '50%',
      height: '70%',
      data: {
        username: element.username,
        password: element.password,
        name: element.name,
        surname: element.surname,
        telephone: element.telephone,
        image: element.image,
        role: element.role,
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.adminService.getAllEmployees("", "").subscribe((response) => {
        this.setData(response.body);
      });
    });
  }

  openDeleteEmployeeDialog(username: string) {
    console.log(username);
    const dialogRef = this.dialog.open(DeleteEmployeeComponent,  {
      disableClose: true,
      autoFocus: true,
      width: '40%',
      height: '20%',
      data: {
        username: username
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.adminService.getAllEmployees("", "").subscribe((response) => {
        this.setData(response.body);
      });
    });
  }

}

