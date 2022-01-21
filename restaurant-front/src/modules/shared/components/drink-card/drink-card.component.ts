import { Component, OnInit } from '@angular/core';
import { DrinkBartender } from 'src/modules/shared/models/drinkBartender';
import { DrinksService } from '../../services/drinks/drinks.service';
import { Select } from '../../models/select';


@Component({
  selector: 'app-drink-card',
  templateUrl: './drink-card.component.html',
  styleUrls: ['./drink-card.component.scss']
})
export class DrinkCardComponent implements OnInit {

  displayedColumns: string[] = ['name', "type","price"];
  items: DrinkBartender[] =  [];

  drinkTypes: Select[] = [
      {value: 'coffee', viewValue: 'coffee'},
      {value: 'cold drink', viewValue: 'cold drink'},
      {value: 'hot drink', viewValue: 'Hot drink'},
      {value: 'alcoholic', viewValue: 'Alcoholic'},
  ];
  constructor(
    private drinkService: DrinksService,
  ) {}

  ngOnInit(): void {
    this.getDrinks();
  }

  getDrinks():void{
      this.drinkService.getDrinks().subscribe((result) => {this.items = result;});
  }
}
