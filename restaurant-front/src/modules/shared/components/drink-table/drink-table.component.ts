import { Component, OnInit, Output, ViewChild,EventEmitter } from '@angular/core';
import { DrinkBartender } from 'src/modules/shared/models/drink-bartender';
import { DrinksService } from '../../services/drinks/drinks.service';
import { Select } from '../../models/select';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { FormBuilder, FormGroup } from '@angular/forms';



@Component({
  selector: 'app-drink-table',
  templateUrl: './drink-table.component.html',
  styleUrls: ['./drink-table.component.scss']
})
export class DrinkTableComponent implements OnInit {

  displayedColumns: string[] = ['name', "type","price", "view drink"];
  observable: Observable<any>;
  dataSource: MatTableDataSource<DrinkBartender>;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @Output() onClickedView = new EventEmitter();

  searchForm: FormGroup;
  searchSting: string;
  filterString: string;

  drinkTypes: Select[] = [
      {value: 'COFFEE', viewValue: 'coffee'},
      {value: 'COLD_DRINK', viewValue: 'cold drink'},
      {value: 'HOT_DRINK', viewValue: 'Hot drink'},
      {value: 'ALCOHOLIC', viewValue: 'Alcoholic'},
  ];
  constructor(
    private drinkService: DrinksService,
    private liveAnnouncer: LiveAnnouncer,
    private fb: FormBuilder,
  ) {
    this.searchForm = this.fb.group({
      search: [null],
      filter: [null],
    });
  }

  ngOnInit(): void {
    this.getDrinks();
  }

  getDrinks():void{
      this.drinkService.getDrinks().subscribe((result) => {
        this.setData(result);
        console.log(result);
      });
  }
  setData(data: DrinkBartender[]) {
    this.dataSource = new MatTableDataSource<DrinkBartender>(data);
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

    this.drinkService
      .getSearchedOrFiltered(this.searchSting, this.filterString)
      .subscribe((response) => {
        this.dataSource.data = response.body;
      });
  }
  clickedView(drink: DrinkBartender){
      this.onClickedView.emit(drink.id!);}
}
