import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { LoginpageComponent } from "./components/loginpage/loginpage.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import "@angular/localize/init";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { SearchPageComponent } from "./components/search-page/search-page.component";
import { SignUpPageComponent } from "./components/sign-up-page/sign-up-page.component";
import { WelcomePageComponent } from "./components/welcome-page/welcome-page.component";
import { AuthService } from "./services/auth/auth.service";
import { AuthInterceptor } from "./services/interceptors/auth.interceptor";
import { environment } from "src/environments/environment";
import { ProfileComponent } from "./components/profile/profile.component";
import { ChangePasswordComponent } from "./components/change-password/change-password.component";
import { AdminComponent } from "./components/admin/admin.component";
import { CustomErrorComponent } from "./components/custom-error/custom-error.component";
import { AdminService } from "./services/admin/admin.service";
import { AgGridModule } from "ag-grid-angular";
import { MyBookingComponent } from "./components/my-booking/my-booking.component";
import { SearchResultComponent } from "./components/search-result/search-result.component";
import { BookingPageComponent } from "./components/booking-page/booking-page.component";
import { UserService } from "./services/user/user.service";
import { AuthGuardService } from "./services/auth/auth-guard.service";
import { ParkingService } from "./services/parking/parking.service";
import { HelperService } from "./services/helper/helper.service";
import { AlertComponent } from "./components/alert/alert.component";
import { HttpErrorInterceptor } from "./services/interceptors/http-error.interceptor";
import { AlertService } from "./services/alert/alert.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginpageComponent,
    WelcomePageComponent,
    SignUpPageComponent,
    SearchPageComponent,
    ProfileComponent,
    ChangePasswordComponent,
    AdminComponent,
    CustomErrorComponent,
    MyBookingComponent,
    SearchResultComponent,
    BookingPageComponent,
    AlertComponent,
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    HttpClientModule,
    AgGridModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    AuthService,
    AdminService,
    UserService,
    ParkingService,
    AuthGuardService,
    HelperService,
    AlertService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },
    { provide: "BASE_API_URL", useValue: environment.apiUrl },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
