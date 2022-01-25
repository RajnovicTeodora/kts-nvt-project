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
import { AfterLogoutComponent } from './pages/after-logout/after-logout.component';
import { ManagerModule } from '../manager/manager.module';
import { ChefModule } from '../chef/chef.module';

@NgModule({
  declarations: [AppComponent, AfterLogoutComponent],
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
    SharedModule,
    BartenderModule,
    ManagerModule,
    ChefModule,
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
