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
import { BadgeComponent } from './components/badge/badge.component';
import { ReplaceUnderscorePipe } from './pipes/replace-underscore.pipe';
import { AfterLogoutComponent } from './components/after-logout/after-logout.component';

@NgModule({
  declarations: [
    ChangePasswordComponent,
    LogoutComponent,
    OtherAccountsComponent,
    BadgeComponent,
    ReplaceUnderscorePipe,
    AfterLogoutComponent,
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
    ReplaceUnderscorePipe,
    AfterLogoutComponent,
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
