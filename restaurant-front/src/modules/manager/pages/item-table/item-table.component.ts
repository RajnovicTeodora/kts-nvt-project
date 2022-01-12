import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Item } from 'src/modules/shared/models/item';
import { ItemService } from '../../services/item-service/item.service';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddDrinkManagerComponent } from '../add-drink-manager/add-drink-manager.component';

@Component({
  selector: 'app-item-table',
  templateUrl: './item-table.component.html',
  styleUrls: ['./item-table.component.scss'],
})
export class ItemTableComponent implements OnInit {
  dataSource: MatTableDataSource<Item>;
  observable: Observable<any>;
  searchForm: FormGroup;
  searchSting: string;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    private itemService: ItemService,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {
    this.searchForm = this.fb.group({
      search: [null],
    });
  }

  ngOnInit(): void {
    this.itemService.getAll().subscribe((response) => {
      this.setData(response);
    });
  }

  setData(data: Item[]) {
    this.dataSource = new MatTableDataSource<Item>(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.observable = this.dataSource.connect();
  }

  search() {
    this.searchSting = this.searchForm.value.search;

    this.itemService
      .getAllBySearchCriteria(this.searchSting)
      .subscribe((response) => {
        console.log(response.body);
        this.dataSource.data = response.body;
      });
  }

  onApproved(id: string) {
    console.log(id);
    this.itemService.approveMenuItem(id).subscribe({
      next: (success) => {
        console.log(success.name);
        this.toastr.success('Successfully approved ' + success.name);
        this.filterData(id);
      },
      error: (error) => {
        this.toastr.error('Unable to approve item');
        console.log(error);
      },
    });
  }

  onDeleted(id: string) {
    console.log(id);
    this.itemService.deleteMenuItem(id).subscribe({
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
      (item) => item.id !== id
    );
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '50%';
    dialogConfig.height = '50%';

    const dialogRef = this.dialog.open(AddDrinkManagerComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
