import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {MatTableModule} from "@angular/material/table"
import { Drink } from 'src/modules/shared/models/drink';
import { DrinkType } from 'src/modules/shared/models/drink-type';
import { UserWithToken } from 'src/modules/shared/models/user-with-token';

const ELEMENT_DATA: Drink[] = [
  {name: "blue lagun", type: "a", price: 1000},
  {name: "blue lagun2",  type: "b", price: 1000}
];

@Component({
  selector: 'app-drink-card',
  templateUrl: './drink-card.component.html',
  styleUrls: ['./drink-card.component.scss']
})
export class DrinkCardComponent implements OnInit {

  displayedColumns: string[] = ['name', "type","price"];
  items: Drink[] =  [
    {name: "blue lagun", type: "a", price: 1000},
    {name: "blue lagun2",  type: "b", price: 1000}
  ];

  drinkTypes: DrinkType[] = [
      {value: 'coffee', viewValue: 'coffee'},
      {value: 'cold drink', viewValue: 'cold drink'},
      {value: 'hot drink', viewValue: 'Hot drink'},
      {value: 'alcoholic', viewValue: 'Alcoholic'},
  ];
  constructor(
    //private drinkService: DrinkCardService,
  ) {}

  ngOnInit(): void {
    this.getDrinks();
  }

  getDrinks():void{ //todo try catch
    console.log("get")
    const user = localStorage.getItem("user");
    //console.log(user);
    if(user!=null){
      const token = user.split('":"')[1].split('",')[0]
      console.log(token)
      //this.drinkService.getDrinks(user).subscribe((result) => {console.log("res", result)});
    }
  }
}
