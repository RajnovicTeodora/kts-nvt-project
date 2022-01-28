import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from '../admin/pages/admin-dashboard/admin-dashboard.component';
import { RoleGuard } from '../auth/guards/role/role.guard';
import { LoginFormComponent } from '../auth/pages/login/login-form.component';


import { ViewOneDrinkComponent } from '../bartender/pages/view-one-drink/view-one-drink.component';
import { ItemTableComponent } from '../manager/pages/item-view/item-view.component';
import { ManagerDashboardComponent } from '../manager/pages/manager-dashboard/manager-dashboard.component';
import { WaiterDashboardComponent } from '../waiter/pages/waiter-dashboard/waiter-dashboard.component';
import { EditOrderComponent } from '../waiter/pages/edit-order/edit-order.component';
import { AfterLogoutComponent } from '../shared/components/after-logout/after-logout.component';
import { BartenderDashboardComponent } from '../bartender/pages/bartender-dashboard/bartender-dashboard.component';
import { EditAreaComponent } from '../admin/pages/edit-area/edit-area.component';
import { AddDrinkComponent } from '../bartender/pages/add-drink/add-drink.component';
import { ChefDashboardComponent } from '../chef/pages/chef-dashboard/chef-dashboard.component';
import { AddDishComponent } from '../head-chef/pages/add-dish/add-dish.component';
import { HeadChefDashboardComponent } from '../head-chef/pages/head-chef-dashboard/head-chef-dashboard.component';
import { SelectMenuItemsComponent } from '../waiter/components/select-menu-items/select-menu-items.component';
import { MenuViewComponent } from '../manager/pages/menu-view/menu-view.component';
import { ReportComponent } from '../manager/pages/report/report.component';
import { NewOrdersComponent } from '../shared/components/new-orders/new-orders.component';
import { AcceptedOrdersComponent } from '../shared/components/accepted-orders/accepted-orders.component';
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
    path: 'bartender-dashboard',
    pathMatch: 'full',
    component: BartenderDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'BARTENDER' },
  },
  {
    path: 'chef-dashboard',
    pathMatch: 'full',
    component: ChefDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'CHEF'}, 
  },
  {
    path: 'head-chef-dashboard',
    pathMatch: 'full',
    component: HeadChefDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'HEAD_CHEF'}, 
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
    path: 'add-dish',
    pathMatch: 'full',
    component: AddDishComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'HEAD_CHEF' }
  },{
    path: 'view-menu-items',
    pathMatch: 'full',
    component: MenuViewComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'MANAGER' },
  },
  {
    path: 'create-order/:parameter',
    pathMatch: 'full',
    component: CreateOrderComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'WAITER' },
  },
  {
    path: 'edit-order/:parameter',
    pathMatch: 'full',
    component: EditOrderComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'WAITER' },
  },
  {
    path: 'reports',
    pathMatch: 'full',
    component: ReportComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'MANAGER' },
  },
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
