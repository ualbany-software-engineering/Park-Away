import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import * as moment from "moment";
import { BehaviorSubject, map, shareReplay, Subject, tap } from "rxjs";
import { User } from "src/app/interfaces/user";

@Injectable()
export class AuthService {
  private userLoginStatus$ = new BehaviorSubject<boolean>(false);
  public getUserLoginStatus$ = this.userLoginStatus$.asObservable();

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<User>("auth/login", { username, password }).pipe(
      tap((response) => this.setSession(response)),
      shareReplay()
    );
  }

  private setSession(authResult: any) {
    const expiresAt = moment().add(authResult.expiresIn, "second");

    localStorage.setItem("id_token", authResult.token);
    localStorage.setItem(
      "expires_at",
      JSON.stringify(new Date(new Date().getTime() + 10 * 60000))
    );
    this.userLoginStatus$.next(this.isLoggedIn());
  }

  logout() {
    localStorage.removeItem("id_token");
    localStorage.removeItem("expires_at");
    localStorage.removeItem("user");
    this.userLoggedinStatus();
  }

  isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration!);
    return moment(expiresAt);
  }

  userLoggedinStatus() {
    this.userLoginStatus$.next(this.isLoggedIn());
  }
}
