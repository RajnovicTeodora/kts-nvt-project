import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Notification } from '../../models/notification';
import { NotificationService } from '../../services/notification-service/notification.service';

@Component({
  selector: 'app-view-notifications',
  templateUrl: './view-notifications.component.html',
  styleUrls: ['./view-notifications.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ViewNotificationsComponent implements OnInit {
  @Output() onNotificationsClose = new EventEmitter();
  @Input() currentUsername: string;
  @ViewChild('newnotif') tableNew: MatTable<Notification>;
  @ViewChild('oldnotif') tableOld: MatTable<Notification>;
  ELEMENT_DATA_NEW: Notification[] = [];
  ELEMENT_DATA_OLD: Notification[] = [];
  dataSourceNew = this.ELEMENT_DATA_NEW;
  dataSourceOld = this.ELEMENT_DATA_OLD;
  columnsToDisplay = ['content', 'active'];
  expandedElement: Notification | null;

  constructor(
    public router: Router,
    private notifService: NotificationService,
    private toastr: ToastrService,) { }

  ngOnInit(): void {
    this.getNotificationsForUser();
  }

  getNotificationsForUser(){
    this.notifService.getAllNotificationsForEmployee(this.currentUsername).subscribe(
      {
        next: (result) => {
          let temp1 = result.sort(function (a, b) {
            return b.id - a.id;
          });
          temp1.forEach((value) =>{
            if(value.active){
              this.ELEMENT_DATA_NEW.push(value);
              
            }else{
              this.ELEMENT_DATA_OLD.push(value);              
            }
          });
          this.tableNew.renderRows();
          this.tableOld.renderRows();
        },
        error: data => {
            this.toastr.error(data.error);          
        }
      }
    );
  }

  close(){
    this.onNotificationsClose.emit(true);
  }

  onViewedNotification(notif: Notification){
    this.notifService.setNotificationInactive(notif.id).subscribe(
      {
        next: (result) => {           
          this.ELEMENT_DATA_NEW.forEach((value, index)=>{
            if(value.id == notif.id){
              this.ELEMENT_DATA_NEW.splice(index, 1);
            }
          })
          this.ELEMENT_DATA_OLD.push(notif);  
          this.ELEMENT_DATA_OLD.sort(function (a, b) {
            return b.id - a.id;
          });
          this.tableNew.renderRows();
          this.tableOld.renderRows();
          this.toastr.success(result);
        },
        error: data => {
            this.toastr.error(data.error);          
        }
      }
    );
  }
}
