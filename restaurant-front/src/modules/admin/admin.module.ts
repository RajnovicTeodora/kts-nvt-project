import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from '../root/material-module';
import { AppRoutingModule } from '../root/app-routing.module';



@NgModule({
  declarations: [
    AdminDashboardComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    MaterialModule,
    ToastrModule.forRoot(),
  ]
})
export class AdminModule { }
