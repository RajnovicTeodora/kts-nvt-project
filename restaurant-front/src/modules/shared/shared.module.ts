import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorInterceptor } from './interceptors/interceptor.interceptor';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../root/app-routing.module';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { LogoutComponent } from './components/logout/logout.component';
import { AuthModule } from '../auth/auth.module';
import { OtherAccountsComponent } from './components/other-accounts/other-accounts.component';
import { DrinkTableComponent } from './components/drink-table/drink-table.component';
import { OneDrinkCardComponent } from './components/one-drink-card/one-drink-card.component';
import { OrderViewComponent } from './components/order-view/order-view.component';
import { FinishDialogComponent } from './components/finish-dialog/finish-dialog.component';
import { IngredientsTableComponent } from './components/ingredients-table/ingredients-table.component';
import { InputIngredientDialogComponent } from './components/input-ingredient-dialog/input-ingredient-dialog.component';
import { BadgeComponent } from './components/badge/badge.component';
import { ReplaceUnderscorePipe } from './pipes/replace-underscore.pipe';
import { AfterLogoutComponent } from './components/after-logout/after-logout.component';
import { ConfirmActionComponent } from './components/confirm-action/confirm-action.component';
import { DeleteIngredientDialogComponent } from './components/delete-ingredient-dialog/delete-ingredient-dialog.component';
import { ViewNotificationsComponent } from './components/view-notifications/view-notifications.component';
import { ShortenStringPipe } from './pipes/shorten-string/shorten-string.pipe';

@NgModule({
  declarations: [
    ChangePasswordComponent,
    LogoutComponent,
    OtherAccountsComponent,
    BadgeComponent,
    OneDrinkCardComponent, 
    DrinkTableComponent, 
    OrderViewComponent, 
    OrderViewComponent, 
    FinishDialogComponent, 
    FinishDialogComponent, 
    IngredientsTableComponent, 
    InputIngredientDialogComponent,
    ReplaceUnderscorePipe,
    AfterLogoutComponent,
    ConfirmActionComponent,
    DeleteIngredientDialogComponent,
    ViewNotificationsComponent,
    ShortenStringPipe,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    AuthModule,
  ],
  exports: [
    ChangePasswordComponent,
    LogoutComponent,
    OtherAccountsComponent,
    BadgeComponent,
    DrinkTableComponent,
    OneDrinkCardComponent,
    OrderViewComponent, 
    FinishDialogComponent, 
    IngredientsTableComponent, 
    InputIngredientDialogComponent,
    ReplaceUnderscorePipe,
    AfterLogoutComponent,
    ConfirmActionComponent,
    DeleteIngredientDialogComponent,
    ViewNotificationsComponent,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorInterceptor,
      multi: true,
    },
  ],
})
export class SharedModule {}
