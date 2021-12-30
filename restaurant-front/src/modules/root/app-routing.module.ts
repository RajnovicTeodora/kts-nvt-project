import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from '../admin/pages/admin-dashboard/admin-dashboard.component';
import { RoleGuard } from '../auth/guards/role/role.guard';
import { LoginFormComponent } from '../auth/pages/login/login-form.component';
import { AddDrinkComponent } from '../bartender/pages/add-drink/add-drink.component';
import { DrinkCardComponent } from '../bartender/pages/drink-card/drink-card.component';
import { OneDrinkCardComponent } from '../bartender/pages/one-drink-card/one-drink-card.component';
import { OrderViewComponent } from '../bartender/pages/order-view/order-view.component';
import { WaiterDashboardComponent } from '../waiter/pages/waiter-dashboard/waiter-dashboard.component';


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
  // {
  //   path: 'bartender-dashboard',
  //   pathMatch: 'full',
  //   component: WaiterDashboardComponent,
  //   canActivate: [RoleGuard],
  //   data: { expectedRoles: 'BARTENDER' },
  // },
  {
    path: 'add-drink',
    pathMatch: 'full',
    component: AddDrinkComponent,
  },
  {
    path: 'order-view',
    pathMatch: 'full',
    component: OrderViewComponent,
  },
  {
    path: 'drink-card',
    pathMatch: 'full',
    component: DrinkCardComponent,
  },
  {
    path: 'one-drink',
    pathMatch: 'full',
    component: OneDrinkCardComponent,
  },
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
