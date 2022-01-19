import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WaiterDashboardComponent } from './pages/waiter-dashboard/waiter-dashboard.component';
import { SelectMenuItemsComponent } from './pages/select-menu-items/select-menu-items.component';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { WaiterRoutes } from './waiter.routes';
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';



@NgModule({
  declarations: [
    WaiterDashboardComponent,
    SelectMenuItemsComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    RouterModule.forChild(WaiterRoutes),
    SharedModule,
    AuthModule
  ]
})
export class WaiterModule { }
