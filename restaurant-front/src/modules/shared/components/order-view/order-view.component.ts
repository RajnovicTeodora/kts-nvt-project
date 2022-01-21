import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Order } from '../../models/order';
import { OrderedItem } from '../../models/orderedItem';
import { FinishDialogComponent } from '../finish-dialog/finish-dialog.component';


@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.scss']
})
export class OrderViewComponent implements OnInit {
  
  @Input() typeBtn: string="";
  @Input() items:  OrderedItem[] =[];
  @Input() note: string="";
  @Output() finishClicked = new EventEmitter();
  @Output() acceptClicked = new EventEmitter();
  

  displayedColumns: string[] = ['name', "quantity","priority","actions"];
  isFinished = false;
  constructor(public dialog: MatDialog) 
  {}

  openDialog(id: string): void {
    this.isFinished = false;
    const title = this.typeBtn === "accept" ? "Do you want to accept this order?" : "Do you want to finish this order?"
    const dialogRef = this.dialog.open(FinishDialogComponent, {
      width: '250px', data: {title: title},
    });

    dialogRef.afterClosed().subscribe(result => {
      this.isFinished = result;
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
