import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { MenuItemPrice } from 'src/modules/shared/models/menu-models/menu-item-price';
import { MenuService } from '../../services/menu-service/menu.service';
import { AddDrinkManagerComponent } from '../../components/add-drink-manager/add-drink-manager.component';

@Component({
  selector: 'app-menu-view',
  templateUrl: './menu-view.component.html',
  styleUrls: ['./menu-view.component.scss'],
})
export class MenuViewComponent implements OnInit {
  dataSource: MatTableDataSource<MenuItemPrice>;
  observable: Observable<any>;
  searchForm: FormGroup;
  searchString: string;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    private menuService: MenuService,
    private toastr: ToastrService,
    private dialog: MatDialog,
    private fb: FormBuilder
  ) {
    this.searchForm = this.fb.group({
      search: [null],
    });
  }

  ngOnInit(): void {
    this.setAll();
  }

  setAll() {
    this.menuService.getAll('').subscribe((response) => {
      this.setData(response.body);
    });
  }

  setData(data: MenuItemPrice[]) {
    data.sort((a, b) => a.menuItem.name.localeCompare(b.menuItem.name));
    this.dataSource = new MatTableDataSource<MenuItemPrice>(data);
    this.dataSource.paginator = this.paginator;
    this.observable = this.dataSource.connect();
  }

  onDeleteClicked(id: string) {
    console.log(id);
    this.menuService.deleteMenuItem(id).subscribe({
      next: (success) => {
        this.toastr.success('Successfully deleted ' + success.name);
        this.filterData(id);
      },
      error: (error) => {
        this.toastr.error('Unable to delete item');
        console.log(error);
      },
    });
  }

  filterData(id: string) {
    this.dataSource.data = this.dataSource.data.filter(
      (item) => item.menuItem.id !== id
    );
  }

  search() {
    this.searchString = this.searchForm.value.search;

    this.menuService.getAll(this.searchString).subscribe((response) => {
      (response.body as MenuItemPrice[]).sort((a, b) =>
        a.menuItem.name.localeCompare(b.menuItem.name)
      );
      this.dataSource.data = response.body;
    });
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '50%';
    dialogConfig.height = '70%';

    const dialogRef = this.dialog.open(AddDrinkManagerComponent, dialogConfig);

    dialogRef.afterClosed().subscribe({
      next: () => {
        this.setAll();
      },
      error: (error) => {
        this.toastr.error('Unable to approve item');
        console.log(error);
      },
    });
  }
}
