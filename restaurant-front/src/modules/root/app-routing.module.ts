import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from '../admin/pages/admin-dashboard/admin-dashboard.component';
import { WaiterGuard } from '../auth/guards/waiter/waiter.guard';
import { LoginFormComponent } from '../auth/pages/login/login-form.component';
import { WaiterDashboardComponent } from '../waiter/pages/waiter-dashboard/waiter-dashboard.component';

const routes: Routes = [
  { 
    path: '',
    pathMatch: 'full',
    redirectTo: 'login' 
  },
  { 
    path: 'login',
    component: LoginFormComponent
  },
  {
    path: "admin-dashboard",
    pathMatch: "full",
    component: AdminDashboardComponent,
    canActivate: [WaiterGuard],
    data: { expectedRoles: "ADMIN" },
  },
  {
    path: "waiter-dashboard",
    pathMatch: "full",
    component: WaiterDashboardComponent,
    canActivate: [WaiterGuard],
    data: { expectedRoles: "WAITER" },
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
