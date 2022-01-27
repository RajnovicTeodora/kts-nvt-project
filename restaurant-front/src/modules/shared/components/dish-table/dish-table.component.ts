import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, Output, ViewChild, EventEmitter} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Dish } from '../../models/dish';
import { Select } from '../../models/select';
import { DishService } from '../../services/dishes/dish.service';
import { TableIngredientsDialogComponent } from '../table-ingredients-dialog/table-ingredients-dialog.component';

@Component({
  selector: 'app-dish-table',
  templateUrl: './dish-table.component.html',
  styleUrls: ['./dish-table.component.scss']
})
export class DishTableComponent implements OnInit {

  displayedColumns: string[] = ['name', "type", "view dish"];
  observable: Observable<any>;
  dataSource: MatTableDataSource<Dish>;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @Output() onClickedView = new EventEmitter();

  searchForm: FormGroup;
  searchSting: string;
  filterString: string;

  dishTypes: Select[] = [
      {value: 'DESERT', viewValue: 'desert'},
      {value: 'ENTREE', viewValue: 'entree'},
      {value: 'MAIN_DISH', viewValue: 'main dish'},
      {value: 'SALAD', viewValue: 'salad'},
      {value: 'SOUP', viewValue: 'soup'},
  ];
  constructor(
    public dialog: MatDialog,
    private dishService: DishService,
    private liveAnnouncer: LiveAnnouncer,
    private fb: FormBuilder,
  ) {
    this.searchForm = this.fb.group({
      search: [null],
      filter: [null],
    });
  }

  ngOnInit(): void {
    this.getDishes();
  }

  getDishes():void{
      this.dishService.getDishes().subscribe((result) => {
        this.setData(result);
        console.log(result);
      });
  }
  setData(data: Dish[]) {
    this.dataSource = new MatTableDataSource<Dish>(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.observable = this.dataSource.connect();
  }
  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }
  search() {
    this.searchSting =
      this.searchForm.value.search != null ? this.searchForm.value.search : '';
    this.filterString =
      this.searchForm.get('filter')?.value != null
        ? this.searchForm.value.filter
        : '';

    this.dishService
      .getSearchedOrFiltered(this.searchSting, this.filterString)
      .subscribe((response) => {
        this.dataSource.data = response.body;
      });
  }
  clickedView(dish: Dish){
    this.dishService.getIngredientsByItemid(dish.id).subscribe( (result) =>{
      console.log(result)
    const dialogRef = this.dialog.open(TableIngredientsDialogComponent, {
      width: '300px', data: {items: result.ingredients},
    });
  });
  }

}
