import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddDrinkComponent } from './pages/add-drink/add-drink.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AuthRoutes } from '../auth/auth.routes';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { OrderViewComponent } from './pages/order-view/order-view.component';
import { DrinkCardComponent } from './pages/drink-card/drink-card.component';
import { OneDrinkCardComponent } from './pages/one-drink-card/one-drink-card.component';
import { MainPageBartenderComponent } from './pages/main-page-bartender/main-page-bartender.component';

@NgModule({
  declarations: [AddDrinkComponent, OrderViewComponent, DrinkCardComponent, OneDrinkCardComponent, MainPageBartenderComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(AuthRoutes),
    HttpClientModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    ToastrModule.forRoot(),
  ],
 
})
export class BartenderModule { }
