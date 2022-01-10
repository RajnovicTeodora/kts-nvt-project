import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorInterceptor } from './interceptors/interceptor.interceptor';
import { DrinkCardComponent } from './components/drink-card/drink-card.component';
import { OneDrinkCardComponent } from './components/one-drink-card/one-drink-card.component';
import { OrderViewComponent } from './components/order-view/order-view.component';



@NgModule({
  declarations: [DrinkCardComponent, OneDrinkCardComponent, OrderViewComponent],
  imports: [
    CommonModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorInterceptor, multi: true },
  ],
})
export class SharedModule { }
