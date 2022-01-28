import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WaiterDashboardComponent } from './pages/waiter-dashboard/waiter-dashboard.component';
import { SelectMenuItemsComponent } from './components/select-menu-items/select-menu-items.component';
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
import { TableOptionsComponent } from './components/table-options/table-options.component';
import { CreateOrderComponent } from './pages/create-order/create-order.component';
import { CustomizeOrderedItemComponent } from './components/customize-ordered-item/customize-ordered-item.component';
import { AdditionalNotesComponent } from './components/additional-notes/additional-notes.component';
import { PayOrderComponent } from './components/pay-order/pay-order.component';
import { EditOrderComponent } from './pages/edit-order/edit-order.component';



@NgModule({
  declarations: [
    WaiterDashboardComponent,
    SelectMenuItemsComponent,
    TableOptionsComponent,
    CreateOrderComponent,
    CustomizeOrderedItemComponent,
    AdditionalNotesComponent,
    PayOrderComponent,
    EditOrderComponent
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
