import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeadChefDashboardComponent } from './pages/head-chef-dashboard/head-chef-dashboard.component';
import { AddDishComponent } from './pages/add-dish/add-dish.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { AuthModule } from '../auth/auth.module';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { HeadChefRoutes } from './head-chef.routes';



@NgModule({
  declarations: [
    HeadChefDashboardComponent, AddDishComponent
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
    RouterModule.forChild(HeadChefRoutes),
    SharedModule,
    AuthModule
  ]
})
export class HeadChefModule { }
