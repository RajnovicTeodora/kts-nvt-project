import { Component, Input, OnInit } from '@angular/core';
import { DrinkBartender } from 'src/modules/shared/models/drink-bartender';
import { DrinksService } from '../../services/drinks/drinks.service';

@Component({
  selector: 'app-one-drink-card',
  templateUrl: './one-drink-card.component.html',
  styleUrls: ['./one-drink-card.component.scss']
})
export class OneDrinkCardComponent implements OnInit {

  drink: DrinkBartender ={name: "", drinkType: "", price: 0, containerType: ""}
  
  @Input() id: string;

  constructor(
    private drinkService: DrinksService
  ) { }

  ngOnInit(): void {
    this.getDrink();
  }

  getDrink(){
    const drink = this.drinkService.getDrink(parseInt(this.id)).subscribe((res) => {
      this.drink = res;
      console.log(res)
      console.log(this.drink)
    } )
  }
}
