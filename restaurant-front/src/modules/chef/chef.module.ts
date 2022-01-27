import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChefDashboardComponent } from './pages/chef-dashboard/chef-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { RouterModule } from '@angular/router';
import { ChefRoutes } from './chef.routes';
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';



@NgModule({
  declarations: [
    ChefDashboardComponent,
   
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
    RouterModule.forChild(ChefRoutes),
    SharedModule,
    AuthModule
  ]
})
export class ChefModule { }
