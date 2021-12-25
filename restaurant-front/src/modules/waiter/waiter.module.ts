import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WaiterDashboardComponent } from './pages/waiter-dashboard/waiter-dashboard.component';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';



@NgModule({
  declarations: [
    WaiterDashboardComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    MaterialModule,
    ToastrModule.forRoot(),
  ]
})
export class WaiterModule { }
