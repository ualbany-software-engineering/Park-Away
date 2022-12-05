import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AdminComponent } from "./components/admin/admin.component";
import { ChangePasswordComponent } from "./components/change-password/change-password.component";
import { LoginpageComponent } from "./components/loginpage/loginpage.component";
import { ProfileComponent } from "./components/profile/profile.component";
import { SearchPageComponent } from "./components/search-page/search-page.component";
import { SignUpPageComponent } from "./components/sign-up-page/sign-up-page.component";
import { CustomErrorComponent } from "./components/custom-error/custom-error.component";
import { MyBookingComponent } from "./components/my-booking/my-booking.component";
import { SearchResultComponent } from "./components/search-result/search-result.component";
import { BookingPageComponent } from "./components/booking-page/booking-page.component";
import { AuthGuardService } from "./services/auth/auth-guard.service";
import { environment } from "../environments/environment";

const routes: Routes = [
  { path: "login", component: LoginpageComponent },
  { path: "signup", component: SignUpPageComponent },
  { path: "search", component: SearchPageComponent },
  { path: "home", component: SearchPageComponent },
  {
    path: "admin",
    component: AdminComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedLogin: environment.adminMail,
    },
  },
  {
    path: "change-password",
    component: ChangePasswordComponent,
    canActivate: [AuthGuardService],
  },
  {
    path: "profile",
    component: ProfileComponent,
    canActivate: [AuthGuardService],
  },
  {
    path: "mybooking",
    component: MyBookingComponent,
    canActivate: [AuthGuardService],
  },
  { path: "parkings", component: SearchResultComponent },
  {
    path: "booking",
    component: BookingPageComponent,
  },
  { path: "", redirectTo: "search", pathMatch: "full" },
  { path: "error", component: CustomErrorComponent },
  { path: "**", component: CustomErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
