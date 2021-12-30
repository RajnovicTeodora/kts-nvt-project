import { Component, OnInit } from '@angular/core';

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
  constructor() { }

  ngOnInit(): void {
  }

}
