import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { Order } from 'src/modules/shared/models/order';
import { OrderedItem } from 'src/modules/shared/models/ordered-item';
  

const ELEMENT_DATA: Order[] = [
  {note: "Note1", status: true, orderedItems: [{name:"item1", quantity: 3}]},
  {note: "Note2", status: true, orderedItems: [{name:"item3", quantity: 5}, {name:"item3", quantity: 5}]},
];


@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.scss']
})
export class OrderViewComponent implements OnInit {
   displayedColumns: string[] = ['name', "quantity"];
   items: OrderedItem[] = ELEMENT_DATA[1].orderedItems;
   note:string = ELEMENT_DATA[1].note;
  constructor() 
  {
    this.note= ELEMENT_DATA[1].note
    
  }

  ngOnInit() {}

}
