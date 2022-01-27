import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MaterialModule } from './material-module';
import { AuthModule } from '../auth/auth.module';
import { WaiterModule } from '../waiter/waiter.module';
import { BartenderModule } from '../bartender/bartender.module';
import { SharedModule } from '../shared/shared.module';
import { InterceptorInterceptor } from '../shared/interceptors/interceptor.interceptor';
import { ManagerModule } from '../manager/manager.module';
import { AdminModule } from '../admin/admin.module';
import { ChefModule } from '../chef/chef.module';
import { HeadChefModule } from '../head-chef/head-chef.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    AuthModule,
    WaiterModule,
    AdminModule,
    SharedModule,
    BartenderModule,
    ManagerModule,
    ChefModule,
    HeadChefModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
