import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';
import { BartenderRoutes } from './bartender.routes';
import { ViewOneDrinkComponent } from './pages/view-one-drink/view-one-drink.component';
import { AddDrinkComponent } from './pages/add-drink/add-drink.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AcceptedOrdersComponent } from '../shared/components/accepted-orders/accepted-orders.component';
import { BartenderDashboardComponent } from './pages/bartender-dashboard/bartender-dashboard.component';
//import { AuthRoutes } from './auth.routes';

@NgModule({
  declarations: [
   ViewOneDrinkComponent, AddDrinkComponent,   BartenderDashboardComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ///RouterModule.forChild(AuthRoutes),
    HttpClientModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    ToastrModule.forRoot(),
    RouterModule.forChild(BartenderRoutes),
    SharedModule,
    AuthModule
  ],
})
export class BartenderModule { }
