import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Item } from 'src/modules/shared/models/item';
import { ItemService } from '../../services/item-service/item.service';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-item-table',
  templateUrl: './item-table.component.html',
  styleUrls: ['./item-table.component.scss'],
})
export class ItemTableComponent implements OnInit {
  dataSource: MatTableDataSource<Item>;
  obs: Observable<any>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(
    private itemService: ItemService,
    private toastr: ToastrService
  ) {}
  ngOnInit(): void {
    this.itemService.getAll().subscribe((response) => {
      this.dataSource = new MatTableDataSource<Item>(response);
      this.dataSource.paginator = this.paginator;
      this.obs = this.dataSource.connect();
      console.log(this.dataSource);
    });
  }

  onApproved(id: string) {
    console.log(id);
    this.itemService.approveMenuItem(id).subscribe(
      (success) => {
        console.log(success);
        this.toastr.success('Successfully approved ');
        this.filterData(id);
      },
      (error) => {
        this.toastr.error('Unable to approve ');
      }
    );
  }

  onDeleted(id: string) {
    console.log(id);
    this.itemService.deleteMenuItem(id).subscribe(
      (success) => {
        this.toastr.success('Successfully deleted');
        this.filterData(id);
      },
      (error) => {
        this.toastr.error('Unable to delete ');
      }
    );
  }

  filterData(id: string) {
    this.dataSource.data = this.dataSource.data.filter(
      (item) => item.id !== id
    );
  }
}
