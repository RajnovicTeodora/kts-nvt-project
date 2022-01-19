import { Component, OnInit } from '@angular/core';
import { Drink } from 'src/modules/shared/models/drink';
import { DrinksService } from '../../services/drinks/drinks.service';

@Component({
  selector: 'app-one-drink-card',
  templateUrl: './one-drink-card.component.html',
  styleUrls: ['./one-drink-card.component.scss']
})
export class OneDrinkCardComponent implements OnInit { //todo proslediti id da bi moglo da se pronadje

  drink: Drink ={name: "", drinkType: "", price: 0, containerType: ""}
  
  constructor(
    private drinkService: DrinksService
  ) { }

  ngOnInit(): void {
    this.getDrink();
  }

  getDrink(){
    const drink = this.drinkService.getDrink(8).subscribe((res) => {
      this.drink = res;
      console.log(res)
    } )
  }
}
