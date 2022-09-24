import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class UserDetailsService {
  constructor(private http: HttpClient) {}

  public register(data: any) {
    return this.http.post("auth/register", data);
  }
}
