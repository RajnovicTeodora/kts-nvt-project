import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagerDashboardComponent } from './pages/manager-dashboard/manager-dashboard.component';
import { ItemTableComponent } from './pages/item-table/item-table.component';
import { MaterialModule } from '../root/material-module';
import { AppRoutingModule } from '../root/app-routing.module';
import { ToastrModule } from 'ngx-toastr';
import { MatTableModule } from '@angular/material/table';

@NgModule({
  declarations: [ManagerDashboardComponent, ItemTableComponent],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
    MatTableModule,
  ],
})
export class ManagerModule {}
