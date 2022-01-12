import { Component, OnInit, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Order } from '../../models/order';
import { OrderedItem } from '../../models/orderedItem';
import { FinishDialogComponent } from '../finish-dialog/finish-dialog.component';

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

  @Input() typeBtn: string="";

  displayedColumns: string[] = ['name', "quantity","actions"];
  items: OrderedItem[] = ELEMENT_DATA[1].orderedItems;
  note:string = ELEMENT_DATA[1].note;
  buttonName = "accept"
  isFinished = false;
  constructor(public dialog: MatDialog) 
  {
    this.note= ELEMENT_DATA[1].note
    
  }

  openDialog(): void {

    this.isFinished = false;
    const dialogRef = this.dialog.open(FinishDialogComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.isFinished = result;
      console.log(result)
  
    });
  }

  ngOnInit() {}

}
