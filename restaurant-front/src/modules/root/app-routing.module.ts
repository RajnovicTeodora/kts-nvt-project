import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from '../admin/pages/admin-dashboard/admin-dashboard.component';
import { RoleGuard } from '../auth/guards/role/role.guard';
import { LoginFormComponent } from '../auth/pages/login/login-form.component';
import { SelectMenuItemsComponent } from '../waiter/components/select-menu-items/select-menu-items.component';
import { ItemTableComponent } from '../manager/pages/item-table/item-table.component';
import { ManagerDashboardComponent } from '../manager/pages/manager-dashboard/manager-dashboard.component';
import { ItemCardComponent } from '../manager/pages/item-card/item-card.component';
import { WaiterDashboardComponent } from '../waiter/pages/waiter-dashboard/waiter-dashboard.component';
import { AfterLogoutComponent } from '../shared/components/after-logout/after-logout.component';
import { PaycheckTableComponent } from '../manager/pages/paycheck-table/paycheck-table.component';
import { CreateOrderComponent } from '../waiter/pages/create-order/create-order.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
  {
    path: 'login',
    component: LoginFormComponent,
  },
  {
    path: 'admin-dashboard',
    pathMatch: 'full',
    component: AdminDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ADMIN' },
  },
  {
    path: 'waiter-dashboard',
    pathMatch: 'full',
    component: WaiterDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'WAITER' },
  },
  {
    path: 'after-logout',
    pathMatch: 'full',
    component: AfterLogoutComponent,
  },
  {
    path: 'manager-dashboard',
    pathMatch: 'full',
    component: ManagerDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'MANAGER' },
    children: [{ path: 'items', component: ItemTableComponent }],
  },
  {
    path: 'item-table',
    pathMatch: 'full',
    component: ItemTableComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'MANAGER' },
  },
  {
    path: 'paychecks',
    pathMatch: 'full',
    component: PaycheckTableComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'MANAGER' },
  },
  {
    path: 'select-menu-items',
    pathMatch: 'full',
    component: SelectMenuItemsComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'WAITER' },
  },
  {
    path: 'create-order',
    pathMatch: 'full',
    component: CreateOrderComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'WAITER' },
  },
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
