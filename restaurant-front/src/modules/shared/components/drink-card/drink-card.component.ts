import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {MatTableModule} from "@angular/material/table"
import { Drink } from 'src/modules/shared/models/drink';
import { DrinkType } from 'src/modules/shared/models/drink-type';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';
import { DrinksService } from '../../services/drinks/drinks.service';


@Component({
  selector: 'app-drink-card',
  templateUrl: './drink-card.component.html',
  styleUrls: ['./drink-card.component.scss']
})
export class DrinkCardComponent implements OnInit {

  displayedColumns: string[] = ['name', "type","price"];
  items: Drink[] =  [];

  drinkTypes: DrinkType[] = [
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
    const user = localStorage.getItem("user");
    if(user!=null){
      const token = user.split('":"')[1].split('",')[0]
      this.drinkService.getDrinks(user).subscribe((result) => {this.items = result;});
    }
  }
}
