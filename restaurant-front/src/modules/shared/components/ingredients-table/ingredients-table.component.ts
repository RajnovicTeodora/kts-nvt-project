import { Component, OnInit, Output, EventEmitter, Input, ViewChild} from '@angular/core';
import { Ingredient } from '../../models/ingredient';
import { InputIngredientDialogComponent } from '../input-ingredient-dialog/input-ingredient-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { DeleteIngredientDialogComponent } from '../delete-ingredient-dialog/delete-ingredient-dialog.component';
import { elementEventFullName } from '@angular/compiler/src/view_compiler/view_compiler';
import { MatTableDataSource } from '@angular/material/table';
import { OrderedItem } from '../../models/orderedItem';
import { Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { LiveAnnouncer } from '@angular/cdk/a11y';


@Component({
  selector: 'app-ingredients-table',
  templateUrl: './ingredients-table.component.html',
  styleUrls: ['./ingredients-table.component.scss']
})
export class IngredientsTableComponent implements OnInit {

  @Output() onAddIngredient = new EventEmitter();
  @Output() onDeleteIngredient = new EventEmitter();
  @Input() items:  Ingredient[];
  displayedColumns: string[] = ['name', "isAlergen","delete"];
  dataSource: MatTableDataSource<Ingredient> = new MatTableDataSource();
  observable: Observable<any>;

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    public dialog: MatDialog,
    private liveAnnouncer: LiveAnnouncer,) 
    { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<Ingredient>(this.items);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.observable = this.dataSource.connect();
  }

  openDialog(): void {

    const dialogRef = this.dialog.open(InputIngredientDialogComponent, {
      width: '250px',
    });
  

    dialogRef.afterClosed().subscribe(result => {
      if(result){
      const newIngredient = result;
      this.onAddIngredient.emit(newIngredient); 
      
      let newList = [...this.items];
      this.dataSource = new MatTableDataSource<Ingredient>(newList);
      this.observable = this.dataSource.connect();
      }
    });
  }
  openDialogDelete(element: any): void {

    const dialogRef = this.dialog.open(DeleteIngredientDialogComponent, {
      width: '250px', data: {name: element.name},
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.onDeleteIngredient.emit(element as Ingredient);
        const newList = this.items.filter(ing => ing.name !== element.name);
        this.dataSource = new MatTableDataSource<Ingredient>(newList);
        this.observable = this.dataSource.connect();
      }
    });
  }

}
