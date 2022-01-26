import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagerDashboardComponent } from './pages/manager-dashboard/manager-dashboard.component';
import { MatTableModule } from '@angular/material/table';
import { ItemCardComponent } from './components/item-card/item-card.component';
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
import { ItemTableComponent } from './pages/item-view/item-view.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EmployeesComponent } from '../shared/components/employees/employees.component'; 
import { AddDrinkManagerComponent } from './components/add-drink-manager/add-drink-manager.component';
import { EditPaycheckDialogComponent } from './components/edit-paycheck-dialog/edit-paycheck-dialog.component';
import { ReportComponent } from './pages/report/report.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { MenuViewComponent } from './pages/menu-view/menu-view.component';
import { MenuItemCardComponent } from './pages/menu-item-card/menu-item-card.component';

@NgModule({
  declarations: [
    ManagerDashboardComponent,
    ItemCardComponent,
    ItemTableComponent,
    AddDrinkManagerComponent,
    EditPaycheckDialogComponent,
    ReportComponent,
    MenuViewComponent,
    MenuItemCardComponent
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
    NgApexchartsModule,
  ],
})
export class ManagerModule {}
