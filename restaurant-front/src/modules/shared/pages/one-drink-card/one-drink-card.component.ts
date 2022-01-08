import { Component, OnInit } from '@angular/core';
import { Drink } from 'src/modules/shared/models/drink';
import { DrinkCardService } from '../../services/drinks/drink-card.service';

@Component({
  selector: 'app-one-drink-card',
  templateUrl: './one-drink-card.component.html',
  styleUrls: ['./one-drink-card.component.scss']
})
export class OneDrinkCardComponent implements OnInit {

  
  name:string="aaa";
  container:string="ggg";
  price:number =145;
  avilable:boolean=true;
  constructor(
    private drinkService: DrinkCardService
  ) { }

  ngOnInit(): void {
    this.getDrink();
  }

  getDrink(){
    const drink = this.drinkService.getDrink(1).subscribe((res) => {
      this.name = res.name;
      this.container = res.container; 
      this.price = res.price;
      this.avilable = res.avilable;
    } )
  }
}
