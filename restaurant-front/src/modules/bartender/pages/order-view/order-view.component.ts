import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorStateMatcher} from '@angular/material/core';
import { CommonModule } from '@angular/common'
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
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
  {//this.items = new MatTableDataSource<OrderedItem>(ELEMENT_DATA[1].orderedItems);
    this.note= ELEMENT_DATA[1].note
    
  }

  ngOnInit() {
    //this.init();
    
  }
   
  // init(){
  //   this.items = new MatTableDataSource<OrderedItem>(ELEMENT_DATA[1].orderedItems);
  //   console.log("aaaaaaaa")
  //   //this.note = ELEMENT_DATA[1].note;
  // }

}
