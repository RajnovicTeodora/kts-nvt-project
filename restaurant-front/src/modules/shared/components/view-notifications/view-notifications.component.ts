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
  element_data_new: Notification[] = [];
  element_data_old: Notification[] = [];
  dataSourceNew = this.element_data_new;
  dataSourceOld = this.element_data_old;
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
              this.element_data_new.push(value);
              
            }else{
              this.element_data_old.push(value);              
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
          this.element_data_new.forEach((value, index)=>{
            if(value.id == notif.id){
              this.element_data_new.splice(index, 1);
            }
          })
          this.element_data_old.push(notif);  
          this.element_data_old.sort(function (a, b) {
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
