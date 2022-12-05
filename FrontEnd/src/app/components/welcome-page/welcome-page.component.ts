import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AlertService } from "../../services/alert/alert.service";
import { AuthService } from "../../services/auth/auth.service";
import { UserDetailsService } from "../../services/user-details.service";

@Component({
  selector: "app-welcome-page",
  templateUrl: "./welcome-page.component.html",
  styleUrls: ["./welcome-page.component.css"],
})
export class WelcomePageComponent implements OnInit {
  isUserLoggedIn: boolean = false;
  lastName: string = "";
  showAlert = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.authService.userLoggedinStatus();

    this.authService.getUserLoginStatus$.subscribe((loginStatus) => {
      this.isUserLoggedIn = loginStatus;
      if (this.isUserLoggedIn) {
        const user = localStorage.getItem("user");
        const userDetail = user ? JSON.parse(user) : {};
        this.lastName = userDetail?.lastName;
      } else {
        this.lastName = "User";
      }
    });

    this.alertService.getStatus$.subscribe((status) => {
      this.showAlert = true;
    });
  }

  signOut() {
    this.authService.logout();
    this.router.navigate(["/home"]);
  }

  showLink(item: string) {
    return !(this.router.url === item);
  }
}
