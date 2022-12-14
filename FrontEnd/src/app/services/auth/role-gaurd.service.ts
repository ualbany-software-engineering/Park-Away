import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
} from "@angular/router";
import { AuthService } from "./auth.service";

@Injectable()
export class RoleGuardService implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(["login"], { queryParams: { retUrl: route.url } });
      return false;
    }
    this.authService.userLoggedinStatus();
    return true;
  }
}
