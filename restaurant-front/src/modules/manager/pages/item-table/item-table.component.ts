import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ItemService } from '../../services/item-service/item.service';

import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Item } from 'src/modules/shared/models/item';

@Component({
  selector: 'app-item-table',
  templateUrl: './item-table.component.html',
  styleUrls: ['./item-table.component.scss'],
})
export class ItemTableComponent implements OnInit {
  dataSource: MatTableDataSource<Item>;
  displayedColumns: string[] = ['name'];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private itemSevice: ItemService) {}

  ngOnInit(): void {
    this.itemSevice.getAll().subscribe((response) => {
      this.dataSource = new MatTableDataSource<Item>(response);
    });
  }

  ngAfterViewInit() {}
}
