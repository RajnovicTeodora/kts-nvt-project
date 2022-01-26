import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from '../admin/pages/admin-dashboard/admin-dashboard.component';
import { RoleGuard } from '../auth/guards/role/role.guard';
import { LoginFormComponent } from '../auth/pages/login/login-form.component';
import { AcceptedOrdersComponent } from '../bartender/pages/accepted-orders/accepted-orders.component';
import { AddDrinkComponent } from '../bartender/pages/add-drink/add-drink.component';
import { NewOrdersComponent } from '../bartender/pages/new-orders/new-orders.component';
import { ViewDrinksComponent } from '../bartender/pages/view-drinks/view-drinks.component';
import { ViewOneDrinkComponent } from '../bartender/pages/view-one-drink/view-one-drink.component';

import { ItemTableComponent } from '../manager/pages/item-table/item-table.component';
import { ManagerDashboardComponent } from '../manager/pages/manager-dashboard/manager-dashboard.component';
import { WaiterDashboardComponent } from '../waiter/pages/waiter-dashboard/waiter-dashboard.component';

import { AfterLogoutComponent } from '../shared/components/after-logout/after-logout.component';

import { BartenderDashboardComponent } from '../bartender/pages/bartender-dashboard/bartender-dashboard.component';

import { CreateOrderComponent } from '../waiter/pages/create-order/create-order.component';
import { EditAreaComponent } from '../admin/pages/edit-area/edit-area.component';

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
    path: 'bartender-dashboard',
    pathMatch: 'full',
    component: BartenderDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
  },
  {
    path: 'view-drinks',
    pathMatch: 'full',
    component: ViewDrinksComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
  },
  {
    path: 'view-one-drink',
    pathMatch: 'full',
    component: ViewOneDrinkComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
  },
  {
    path: 'add-drink',
    pathMatch: 'full',
    component: AddDrinkComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
  },
  {
    path: 'new-orders',
    pathMatch: 'full',
    component: NewOrdersComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
  },
  {
    path: 'accepted-orders',
    pathMatch: 'full',
    component: AcceptedOrdersComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
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
    path: 'edit-area',
    pathMatch: 'full',
    component: EditAreaComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ADMIN' },
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
