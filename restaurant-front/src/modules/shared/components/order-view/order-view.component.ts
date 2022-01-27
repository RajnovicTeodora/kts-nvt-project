import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { OrderedItem } from '../../models/ordered-item';
import { OrderedItemService } from '../../services/ordered-item-service/ordered-item.service';
import { FinishDialogComponent } from '../finish-dialog/finish-dialog.component';
import { TableIngredientsDialogComponent } from '../table-ingredients-dialog/table-ingredients-dialog.component';


@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.scss']
})
export class OrderViewComponent implements OnInit {
  
  @Input() typeBtn: string="";
  @Input() items:  OrderedItem[];//  //
  @Input() note: string="";
  @Output() finishClicked = new EventEmitter();
  @Output() acceptClicked = new EventEmitter();
  dataSource: MatTableDataSource<OrderedItem> = new MatTableDataSource();
  observable: Observable<any>;
  
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  
  displayedColumns: string[] = ['name', "quantity","priority", "activeIng","actions"];
  isFinished = false;

  constructor(
    private orderedItemService: OrderedItemService,
    public dialog: MatDialog,
    private liveAnnouncer: LiveAnnouncer,
    ) {}

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
  openIng(element: OrderedItem): void {
     this.orderedItemService.getActiveIngredients(element.id).subscribe(result => {
      console.log(result, "ing")
    const dialogRef = this.dialog.open(TableIngredientsDialogComponent, {
      width: '400px', data: {items: result},
    });
    });
    
  }

  ngOnInit() {
    this.dataSource = new MatTableDataSource<OrderedItem>(this.items);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.observable = this.dataSource.connect();
  }
  
  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }
}
