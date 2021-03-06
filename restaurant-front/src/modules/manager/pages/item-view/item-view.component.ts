import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { Item } from 'src/modules/shared/models/item';
import { ItemService } from '../../services/item-service/item.service';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddDrinkManagerComponent } from '../../components/add-drink-manager/add-drink-manager.component';

@Component({
  selector: 'app-item-view',
  templateUrl: './item-view.component.html',
  styleUrls: ['./item-view.component.scss'],
})
export class ItemTableComponent implements OnInit {
  dataSource: MatTableDataSource<Item>;
  observable: Observable<any>;
  searchForm: FormGroup;
  searchString: string;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

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
    this.itemService.getAll('').subscribe((response) => {
      this.setData(response.body);
    });
  }

  setData(data: Item[]) {
    data.sort((a, b) => a.name.localeCompare(b.name));
    this.dataSource = new MatTableDataSource<Item>(data);
    this.dataSource.paginator = this.paginator;
    this.observable = this.dataSource.connect();
  }

  search() {
    this.searchString = this.searchForm.value.search;

    this.itemService.getAll(this.searchString).subscribe((response) => {
      this.dataSource.data = response.body;
    });
  }

  onApproved(id: string) {
    this.itemService.approveMenuItem(id).subscribe({
      next: (success) => {
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
    this.itemService.deleteMenuItem(id).subscribe({
      next: (success) => {
        this.toastr.success('Successfully deleted ' + success.name);
        this.filterData(id);
      },
      error: (error) => {
        this.toastr.error('Unable to delete item');
      },
    });
  }

  filterData(id: string) {
    this.dataSource.data = this.dataSource.data.filter(
      (item) => item.id !== id
    );
  }
}
