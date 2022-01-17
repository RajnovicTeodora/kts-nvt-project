import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Order } from '../../models/order';
import { OrderedItem } from '../../models/orderedItem';
import { FinishDialogComponent } from '../finish-dialog/finish-dialog.component';

const ELEMENT_DATA: Order[] = [
  {note: "Note1", status: true, orderedItems: [{id:1,name:"item1", quantity: 3, status:"acc"}]},
  {note: "Note2", status: true, orderedItems: [{id:2,name:"item3", quantity: 5, status:"acc"}, {id:3,name:"item3", quantity: 5, status:"acc"}]},
];

@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.scss']
})
export class OrderViewComponent implements OnInit {

  @Input() typeBtn: string="";
  @Output() finishClicked = new EventEmitter();
  @Output() acceptClicked = new EventEmitter();

  displayedColumns: string[] = ['name', "quantity","actions"];
  items: OrderedItem[] = ELEMENT_DATA[1].orderedItems;
  note:string = ELEMENT_DATA[1].note;
  buttonName = "accept"
  isFinished = false;
  constructor(public dialog: MatDialog) 
  {
    this.note= ELEMENT_DATA[1].note
    
  }

  openDialog(id: string): void {
    this.isFinished = false;
    const title = this.typeBtn === "accept" ? "Do you want to accept this order?" : "Do you want to finish this order?"
    const dialogRef = this.dialog.open(FinishDialogComponent, {
      width: '250px', data: {title: title},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.isFinished = result;
      console.log(result)
      if(result){
        if(this.typeBtn === "accept"){
          this.acceptClicked.emit(id);
        }else{
          this.finishClicked.emit(id);
        }
      }
    });
  }

  ngOnInit() {}

}
