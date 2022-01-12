import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagerDashboardComponent } from './pages/manager-dashboard/manager-dashboard.component';
import { MatTableModule } from '@angular/material/table';
import { MenuTableComponent } from './pages/menu-table/menu-table.component';
import { ItemCardComponent } from './pages/item-card/item-card.component';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AuthModule } from '../auth/auth.module';
import { ManagerRoutes } from './manager.routes';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ItemTableComponent } from './pages/item-table/item-table.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ManagerDashboardComponent,
    MenuTableComponent,
    ItemCardComponent,
    ItemTableComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    MatPaginatorModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
    MatTableModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    RouterModule.forChild(ManagerRoutes),
    SharedModule,
    AuthModule,
  ],
})
export class ManagerModule {}
