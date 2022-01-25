import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { EditTablesComponent } from './pages/edit-tables/edit-tables.component';
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
import { AdminRoutes } from './admin.routes';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { AddEmployeeComponent } from './pages/add-employee/add-employee.component';


@NgModule({
  declarations: [
    AdminDashboardComponent,
    EditTablesComponent,
    AddEmployeeComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    MaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule.forChild(AdminRoutes),
    SharedModule,
    AuthModule,
    MatPaginatorModule,
    MatTableModule,
    ReactiveFormsModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    SharedModule
  ]
})
export class AdminModule { }
