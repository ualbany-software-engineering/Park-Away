import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
} from "@angular/router";
import { environment } from "src/environments/environment";
import { AuthService } from "./auth.service";

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    if (!this.authService.isLoggedIn()) {
      alert(
        "You are not allowed to view this page. You are redirected to login Page"
      );

      this.router.navigate(["login"], { queryParams: { retUrl: route.url } });
      return false;
    }
    this.authService.userLoggedinStatus();

    if (state.url === "/admin") {
      const item = localStorage.getItem("user");
      const user = item ? JSON.parse(item) : {};
      if (user.email === environment.adminMail) {
        return true;
      } else {
        this.router.navigate(["/search"]);
      }
    }

    return true;
  }
}
