import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MenuItem } from 'src/modules/shared/models/menuItem';
import { MenuService } from '../../services/menu-service/menu.service';

@Component({
  selector: 'app-menu-table',
  templateUrl: './menu-table.component.html',
  styleUrls: ['./menu-table.component.scss'],
})
export class MenuTableComponent implements OnInit {
  dataSource: MatTableDataSource<MenuItem>;
  displayedColumns: string[] = ['name'];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private menuService: MenuService) {}

  ngOnInit(): void {
    this.menuService.getAll().subscribe((response) => {
      this.dataSource = new MatTableDataSource<MenuItem>(response);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  ngAfterViewInit() {}
}
