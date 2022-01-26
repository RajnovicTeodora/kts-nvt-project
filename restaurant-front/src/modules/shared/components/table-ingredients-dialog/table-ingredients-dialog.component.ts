import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Ingredient } from '../../models/ingredient';

@Component({
  selector: 'app-table-ingredients-dialog',
  templateUrl: './table-ingredients-dialog.component.html',
  styleUrls: ['./table-ingredients-dialog.component.scss']
})
export class TableIngredientsDialogComponent implements OnInit {

  displayedColumns: string[] = ['name', "isAlergen"];
  dataSource: MatTableDataSource<Ingredient> = new MatTableDataSource();
  observable: Observable<any>;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    public dialogRef: MatDialogRef<TableIngredientsDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: {items:Ingredient[]}) 
    { }

  ngOnInit(): void {
    console.log(this.data.items)
    this.dataSource = new MatTableDataSource<Ingredient>(this.data.items);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.observable = this.dataSource.connect();
  }

  

}
